package ia.stagages;

import java.util.Scanner;

public class StageOne {
    private static boolean visitou00 = false;
    private static boolean visitou09 = false;
    private static boolean visitou90 = false;
    private static boolean visitou99 = false;

    public static void main(String[] args) {
        String[][] grid = new String[10][10];
        Scanner scanner = new Scanner(System.in);

        System.out.print("x inicial (0-9): ");
        int agenteX = Integer.parseInt(scanner.next());
        System.out.print("y inicial (0-9): ");
        int agenteY = Integer.parseInt(scanner.next());

        int direcao = 1; // 0=norte, 1=leste, 2=sul, 3=oeste

        while (true) {
            limparGrid(grid);

            marcarCantoVisitado(agenteX, agenteY);

            grid[agenteX][agenteY] = "A";
            imprimirGrid(grid);

            System.out.println("Posição atual: [" + agenteX + "][" + agenteY + "]");
            System.out.println("Cantos visitados: "
                    + "[0,0]=" + visitou00 + " "
                    + "[0,9]=" + visitou09 + " "
                    + "[9,0]=" + visitou90 + " "
                    + "[9,9]=" + visitou99);

            if (visitou00 && visitou09 && visitou90 && visitou99) {
                System.out.println("Os quatro cantos foram alcançados!");
                break;
            }

            int[][] dirs = {
                    {-1, 0}, // norte
                    {0, 1},  // leste
                    {1, 0},  // sul
                    {0, -1}  // oeste
            };

            boolean moveu = false;

            // prioridade: frente -> direita -> trás -> esquerda
            int[] ordem = {
                    direcao,
                    (direcao + 1) % 4,
                    (direcao + 2) % 4,
                    (direcao + 3) % 4
            };

            for (int i = 0; i < ordem.length; i++) {
                int novaDir = ordem[i];
                int nx = agenteX + dirs[novaDir][0];
                int ny = agenteY + dirs[novaDir][1];

                if (nx >= 0 && nx < 10 && ny >= 0 && ny < 10) {
                    agenteX = nx;
                    agenteY = ny;
                    direcao = novaDir;
                    moveu = true;
                    break;
                }
            }

            if (!moveu) {
                System.out.println("Agente não conseguiu se mover.");
                break;
            }

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                break;
            }
        }

        scanner.close();
    }

    public static void marcarCantoVisitado(int x, int y) {
        if (x == 0 && y == 0) visitou00 = true;
        if (x == 0 && y == 9) visitou09 = true;
        if (x == 9 && y == 0) visitou90 = true;
        if (x == 9 && y == 9) visitou99 = true;
    }

    public static void limparGrid(String[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = ".";
            }
        }
    }

    public static void imprimirGrid(String[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("=".repeat(30));
    }
}