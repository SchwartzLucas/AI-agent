package ia.stagages;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class StageOne {
    private static Set<String> cantosVisitados = new HashSet<>();

    private static Integer minX = null;
    private static Integer maxX = null;
    private static Integer minY = null;
    private static Integer maxY = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Quantidade de linhas: ");
        int linhas = Integer.parseInt(scanner.next());

        System.out.print("Quantidade de colunas: ");
        int colunas = Integer.parseInt(scanner.next());

        String[][] grid = new String[linhas][colunas];

        System.out.print("x inicial (0-" + (linhas - 1) + "): ");
        int agenteX = Integer.parseInt(scanner.next());

        System.out.print("y inicial (0-" + (colunas - 1) + "): ");
        int agenteY = Integer.parseInt(scanner.next());

        imprimirEstado(grid, agenteX, agenteY);


        agenteX = moverAteBorda(grid, agenteX, agenteY, -1, 0)[0]; // norte
        agenteY = moverAteBorda(grid, agenteX, agenteY, 0, -1)[1]; // oeste

        minX = agenteX;
        minY = agenteY;

        marcarCantoVisitado(agenteX, agenteY);

        int[] pos;

        // Descobre maxY indo para leste
        pos = moverAteBorda(grid, agenteX, agenteY, 0, 1);
        agenteX = pos[0];
        agenteY = pos[1];
        maxY = agenteY;

        // Volta para o canto superior esquerdo
        pos = moverPara(grid, agenteX, agenteY, minX, minY);
        agenteX = pos[0];
        agenteY = pos[1];

        // Descobre maxX indo para sul
        pos = moverAteBorda(grid, agenteX, agenteY, 1, 0);
        agenteX = pos[0];
        agenteY = pos[1];
        maxX = agenteX;

        // Volta para o canto superior esquerdo
        pos = moverPara(grid, agenteX, agenteY, minX, minY);
        agenteX = pos[0];
        agenteY = pos[1];

        System.out.println("Limites descobertos:");
        System.out.println("minX=" + minX + ", maxX=" + maxX + ", minY=" + minY + ", maxY=" + maxY);
        System.out.println();


        cantosVisitados.clear();
        marcarCantoVisitado(agenteX, agenteY);
        imprimirEstado(grid, agenteX, agenteY);


        int[][] cantos = {
                {minX, minY},
                {minX, maxY},
                {maxX, maxY},
                {maxX, minY}
        };

        for (int i = 0; i < cantos.length; i++) {
            int alvoX = cantos[i][0];
            int alvoY = cantos[i][1];

            pos = moverPara(grid, agenteX, agenteY, alvoX, alvoY);
            agenteX = pos[0];
            agenteY = pos[1];

            marcarCantoVisitado(agenteX, agenteY);

            if (todosCantosVisitados()) {
                System.out.println("Os quatro cantos foram alcançados!");
                break;
            }
        }

        scanner.close();
    }

    public static int[] moverAteBorda(String[][] grid, int x, int y, int dx, int dy) {
        while (true) {
            int nx = x + dx;
            int ny = y + dy;

            if (dentro(grid, nx, ny)) {
                x = nx;
                y = ny;
                marcarCantoVisitado(x, y);
                imprimirEstado(grid, x, y);

                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    break;
                }
            } else {
                break;
            }
        }

        return new int[]{x, y};
    }

    public static int[] moverPara(String[][] grid, int x, int y, int alvoX, int alvoY) {
        while (x < alvoX) {
            x++;
            marcarCantoVisitado(x, y);
            imprimirEstado(grid, x, y);
            dormir();
        }

        while (x > alvoX) {
            x--;
            marcarCantoVisitado(x, y);
            imprimirEstado(grid, x, y);
            dormir();
        }

        while (y < alvoY) {
            y++;
            marcarCantoVisitado(x, y);
            imprimirEstado(grid, x, y);
            dormir();
        }

        while (y > alvoY) {
            y--;
            marcarCantoVisitado(x, y);
            imprimirEstado(grid, x, y);
            dormir();
        }

        return new int[]{x, y};
    }

    public static boolean dentro(String[][] grid, int x, int y) {
        return x >= 0 && x < grid.length && y >= 0 && y < grid[0].length;
    }

    public static void marcarCantoVisitado(int x, int y) {
        if (minX == null || maxX == null || minY == null || maxY == null) {
            return;
        }

        if (x == minX && y == minY) cantosVisitados.add(minX + "," + minY);
        if (x == minX && y == maxY) cantosVisitados.add(minX + "," + maxY);
        if (x == maxX && y == minY) cantosVisitados.add(maxX + "," + minY);
        if (x == maxX && y == maxY) cantosVisitados.add(maxX + "," + maxY);
    }

    public static boolean todosCantosVisitados() {
        return cantosVisitados.size() == 4;
    }

    public static void limparGrid(String[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = ".";
            }
        }
    }

    public static void imprimirEstado(String[][] grid, int agenteX, int agenteY) {
        limparGrid(grid);
        grid[agenteX][agenteY] = "A";
        imprimirGrid(grid);

        System.out.println("Posição atual: [" + agenteX + "][" + agenteY + "]");
        System.out.println("Cantos visitados: " + cantosVisitados);
        System.out.println();
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

    public static void dormir() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}