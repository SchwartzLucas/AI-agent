package ia.stagages;

import java.util.*;

public class StageFourVariationOne {

    static final int TAM = 10;

    static class Node {
        int x, y, g, h;

        Node(int x, int y, int g, int h) {
            this.x = x;
            this.y = y;
            this.g = g;
            this.h = h;
        }

        int f() {
            return g + h;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);

        System.out.print("Posição x inicial (0-9): ");
        int startX = sc.nextInt();
        System.out.print("Posição y inicial (0-9): ");
        int startY = sc.nextInt();

        System.out.print("Posição x objetivo (0-9): ");
        int goalX = sc.nextInt();
        System.out.print("Posição y objetivo (0-9): ");
        int goalY = sc.nextInt();

        boolean[][] obstaculo = new boolean[TAM][TAM];
        boolean[][] visitado = new boolean[TAM][TAM];
        String[][] grid = new String[TAM][TAM];
        int[][] custo = new int[TAM][TAM];

        // verde (custo 1)
        for (int i = 0; i < TAM; i++) {
            for (int j = 0; j < TAM; j++) {
                custo[i][j] = 1;
            }
        }

        // amarelo (custo 2)
        custo[2][2] = 2;
        custo[2][3] = 2;
        custo[3][2] = 2;
        custo[3][7] = 2;
        custo[4][7] = 2;

        // vermelho (custo 3)
        custo[1][5] = 3;
        custo[2][5] = 3;
        custo[2][6] = 3;
        custo[3][5] = 3;
        custo[3][6] = 3;
        custo[4][5] = 3;
        custo[5][5] = 3;
        custo[2][4] = 3;
        custo[3][4] = 3;
        custo[4][4] = 3;

        // obstáculos
        obstaculo[5][5] = false;
        obstaculo[6][5] = false;

        List<int[]> caminho = aStar(startX, startY, goalX, goalY, custo, obstaculo);

        if (caminho == null) {
            System.out.println("Sem caminho!");
            return;
        }

        int movimentos = 0;
        int custoTotal = 0;

        for (int[] passo : caminho) {
            int x = passo[0];
            int y = passo[1];

            visitado[x][y] = true;
            custoTotal += custo[x][y];

            montarGrid(grid, visitado, obstaculo, custo, x, y, startX, startY, goalX, goalY);
            imprimirGrid(grid);

            Thread.sleep(300);
            movimentos++;

        }

        System.out.println("Caminho concluído em " + movimentos + " movimentos");
        System.out.println("Custo total do caminho: " + custoTotal);
    }


    public static List<int[]> aStar(int startX, int startY, int goalX, int goalY, int[][] custo, boolean[][] obstaculo) {

        PriorityQueue<Node> open = new PriorityQueue<>(Comparator.comparingInt(Node::f));
        boolean[][] fechado = new boolean[TAM][TAM];

        int[][][] pai = new int[TAM][TAM][2];
        int[][] gScore = new int[TAM][TAM];

        for (int[] row : gScore)
            Arrays.fill(row, Integer.MAX_VALUE);

        gScore[startX][startY] = 0;

        open.add(new Node(startX, startY, 0,
                heuristica(startX, startY, goalX, goalY)));

        int[][] dirs = {
                {0, -1}, {-1, 0}, {0, 1}, {1, 0}
        };

        while (!open.isEmpty()) {
            Node atual = open.poll();

            if (atual.x == goalX && atual.y == goalY) {
                return reconstruirCaminho(pai, goalX, goalY, startX, startY);
            }

            if (fechado[atual.x][atual.y]) continue;
            fechado[atual.x][atual.y] = true;

            for (int[] d : dirs) {
                int nx = atual.x + d[0];
                int ny = atual.y + d[1];

                if (!valido(nx, ny) || obstaculo[nx][ny]) continue;

                int novoG = atual.g + custo[nx][ny];

                if (novoG < gScore[nx][ny]) {
                    gScore[nx][ny] = novoG;

                    pai[nx][ny][0] = atual.x;
                    pai[nx][ny][1] = atual.y;

                    int h = heuristica(nx, ny, goalX, goalY);
                    open.add(new Node(nx, ny, novoG, h));
                }
            }
        }

        return null;
    }

    public static int heuristica(int x, int y, int gx, int gy) {
        return Math.abs(x - gx) + Math.abs(y - gy);
    }

    public static List<int[]> reconstruirCaminho(int[][][] pai, int x, int y, int sx, int sy) {
        List<int[]> caminho = new ArrayList<>();

        while (!(x == sx && y == sy)) {
            caminho.add(0, new int[]{x, y});
            int px = pai[x][y][0];
            int py = pai[x][y][1];
            x = px;
            y = py;
        }

        return caminho;
    }

    public static boolean valido(int x, int y) {
        return x >= 0 && x < TAM && y >= 0 && y < TAM;
    }

    public static void montarGrid(String[][] grid, boolean[][] visitado,
                                  boolean[][] obstaculo, int[][] custo,
                                  int ax, int ay, int sx, int sy, int gx, int gy) {

        for (int i = 0; i < TAM; i++) {
            for (int j = 0; j < TAM; j++) {

                if (obstaculo[i][j]) grid[i][j] = "X";
                else if (i == ax && j == ay) grid[i][j] = "A";
                else if (i == sx && j == sy) grid[i][j] = "I";
                else if (i == gx && j == gy) grid[i][j] = "O";
                else if (visitado[i][j]) grid[i][j] = "*";
                else grid[i][j] = String.valueOf(custo[i][j]);
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
}