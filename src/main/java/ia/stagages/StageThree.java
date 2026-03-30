package ia.stagages;

import java.util.Scanner;

public class StageThree {

    static final int TAM = 10;

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);

        int Iniciox, InicioY, ObjetivoX, ObjetivoY;
        int contadorDemovimentos = 0;

        boolean[][] obstaculo = new boolean[TAM][TAM];
        boolean[][] visitado = new boolean[TAM][TAM];
        String[][] grid = new String[TAM][TAM];

        System.out.print("Posição x inicial (0-9): ");
        Iniciox = sc.nextInt();
        System.out.print("posição y inicial (0-9): ");
        InicioY = sc.nextInt();

        System.out.print("Posição x objetivo (0-9): ");
        ObjetivoX = sc.nextInt();
        System.out.print("posição y objetivo (0-9): ");
        ObjetivoY = sc.nextInt();

        int[] inicio = {Iniciox, InicioY};
        int[] objetivo = {ObjetivoX, ObjetivoY};

        // Exemplo simples de obstáculos
        obstaculo[0][4] = true;
        obstaculo[1][0] = true;
        obstaculo[1][3] = true;
        obstaculo[2][2] = true;
        obstaculo[3][2] = true;
        obstaculo[3][5] = true;
        obstaculo[4][1] = true;
        obstaculo[4][6] = true;
        obstaculo[5][3] = true;
        obstaculo[5][5] = true;
        obstaculo[5][6] = true;
        obstaculo[5][7] = true;
        obstaculo[5][8] = true;
        obstaculo[6][5] = true;
        obstaculo[6][8] = true;
        obstaculo[7][5] = true;
        obstaculo[7][8] = true;
        obstaculo[8][5] = true;
        obstaculo[8][7] = true;
        obstaculo[8][8] = true;
        obstaculo[9][5] = true;

        java.util.List<int[]> caminho = encontrarCaminho(Iniciox, InicioY, ObjetivoX, ObjetivoY, obstaculo);
        int passoIndex = 0;

        while (true) {

            if (caminho == null || passoIndex >= caminho.size()) {
                System.out.println("Sem caminho até o objetivo. Encerrando.");
                System.out.println("Ambiente percorrido em: " + contadorDemovimentos + " movimentos");
                break;
            }

            int[] prox = caminho.get(passoIndex++);
            Iniciox = prox[0];
            InicioY = prox[1];

            if (Iniciox == ObjetivoX && InicioY == ObjetivoY) {
                montarGrid(grid, visitado, obstaculo, inicio, objetivo, Iniciox, InicioY, ObjetivoX, ObjetivoY);
                imprimirGrid(grid);
                System.out.println("Objetivo alcançado em: " + contadorDemovimentos + " movimentos");
                break;
            }

            montarGrid(grid, visitado, obstaculo, inicio, objetivo, Iniciox, InicioY, ObjetivoX, ObjetivoY);
            imprimirGrid(grid);

            visitado[Iniciox][InicioY] = true;
            contadorDemovimentos++;

            Thread.sleep(300);
        }

    }

    public static void montarGrid(String[][] grid, boolean[][] visitado, boolean[][] obstaculo, int[] inicio, int[] objetivo,
                                  int Iniciox, int InicioY, int ObjetivoX, int ObjetivoY) {
        for (int i = 0; i < TAM; i++) {
            for (int j = 0; j < TAM; j++) {
                if (obstaculo[i][j]) {
                    grid[i][j] = "X";
                } else if (i == Iniciox && j == InicioY) {
                    grid[i][j] = "A";
                } else if (i == objetivo[0] && j == objetivo[1]) {
                    grid[i][j] = "O";
                } else if (visitado[i][j]) {
                    grid[i][j] = "*";
                } else {
                    grid[i][j] = ".";
                }
            }
        }
    }

    public static void imprimirGrid(String[][] grid) {
        for (int i = 0; i < TAM; i++) {
            for (int j = 0; j < TAM; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("----------------------");
    }

    public static boolean valido(int x, int y) {
        return x >= 0 && x < TAM && y >= 0 && y < TAM;
    }

    public static java.util.List<int[]> encontrarCaminho(int startX, int startY, int goalX, int goalY, boolean[][] obstaculo) {
        java.util.Queue<int[]> fila = new java.util.LinkedList<>();
        boolean[][] visitado = new boolean[TAM][TAM];
        int[][][] pai = new int[TAM][TAM][2];

        fila.add(new int[]{startX, startY});
        visitado[startX][startY] = true;

        int[][] direcoes = {
                {0, -1}, // esquerda
                {-1, 0}, // cima
                {0, 1},  // direita
                {1, 0}   // baixo
        };

        while (!fila.isEmpty()) {
            int[] atual = fila.poll();
            int x = atual[0];
            int y = atual[1];

            if (x == goalX && y == goalY) {
                java.util.List<int[]> caminho = new java.util.ArrayList<>();
                while (!(x == startX && y == startY)) {
                    caminho.add(0, new int[]{x, y});
                    int px = pai[x][y][0];
                    int py = pai[x][y][1];
                    x = px;
                    y = py;
                }
                return caminho;
            }

            for (int[] dir : direcoes) {
                int nx = x + dir[0];
                int ny = y + dir[1];

                if (valido(nx, ny) && !visitado[nx][ny] && !obstaculo[nx][ny]) {
                    fila.add(new int[]{nx, ny});
                    visitado[nx][ny] = true;
                    pai[nx][ny][0] = x;
                    pai[nx][ny][1] = y;
                }
            }
        }

        return null;
    }
}
