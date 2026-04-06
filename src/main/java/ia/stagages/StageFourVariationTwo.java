package ia.stagages;

import java.util.*;

public class StageFourVariationTwo {

    static final int TAM = 10;
    static final int DESCONHECIDO = -1;
    static final int CUSTO_PADRAO_DESCONHECIDO = 1;

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

        if (!valido(startX, startY) || !valido(goalX, goalY)) {
            System.out.println("Posições inválidas.");
            return;
        }

        int[][] custoReal = new int[TAM][TAM];
        boolean[][] obstaculoReal = new boolean[TAM][TAM];

        inicializarMundoReal(custoReal, obstaculoReal);

        if (obstaculoReal[startX][startY] || obstaculoReal[goalX][goalY]) {
            System.out.println("Início ou objetivo está em um obstáculo.");
            return;
        }

        int[][] custoConhecido = new int[TAM][TAM];
        boolean[][] obstaculoConhecido = new boolean[TAM][TAM];
        boolean[][] conhecido = new boolean[TAM][TAM];
        boolean[][] visitado = new boolean[TAM][TAM];
        String[][] grid = new String[TAM][TAM];

        for (int i = 0; i < TAM; i++) {
            Arrays.fill(custoConhecido[i], DESCONHECIDO);
        }

        int agenteX = startX;
        int agenteY = startY;

        int movimentos = 0;
        int custoTotal = 0;

        observar(agenteX, agenteY, custoReal, obstaculoReal, custoConhecido, obstaculoConhecido, conhecido);

        visitado[agenteX][agenteY] = true;

        montarGridParcial(grid, visitado, conhecido, obstaculoConhecido, custoConhecido,
                agenteX, agenteY, startX, startY, goalX, goalY);
        imprimirGrid(grid);

        while (!(agenteX == goalX && agenteY == goalY)) {

            List<int[]> caminhoPlanejado = aStar(agenteX, agenteY, goalX, goalY, custoConhecido, obstaculoConhecido);

            if (caminhoPlanejado == null || caminhoPlanejado.isEmpty()) {
                System.out.println("Sem caminho encontrado com o conhecimento atual do agente.");
                return;
            }

            int[] proximoPasso = caminhoPlanejado.get(0);
            int nx = proximoPasso[0];
            int ny = proximoPasso[1];

            if (conhecido[nx][ny] && obstaculoConhecido[nx][ny]) {
                continue;
            }

            agenteX = nx;
            agenteY = ny;

            visitado[agenteX][agenteY] = true;
            movimentos++;

            custoTotal += custoReal[agenteX][agenteY];

            observar(agenteX, agenteY, custoReal, obstaculoReal, custoConhecido, obstaculoConhecido, conhecido);

            montarGridParcial(grid, visitado, conhecido, obstaculoConhecido, custoConhecido,
                    agenteX, agenteY, startX, startY, goalX, goalY);
            imprimirGrid(grid);

            Thread.sleep(300);
        }

        System.out.println("Caminho concluído em " + movimentos + " movimentos");
        System.out.println("Custo total do caminho: " + custoTotal);
    }

    public static void inicializarMundoReal(int[][] custoReal, boolean[][] obstaculoReal) {
        for (int i = 0; i < TAM; i++) {
            for (int j = 0; j < TAM; j++) {
                custoReal[i][j] = 1;
                obstaculoReal[i][j] = false;
            }
        }

        // amarelo (custo 2)
        custoReal[2][2] = 2;
        custoReal[2][3] = 2;
        custoReal[3][2] = 2;
        custoReal[3][7] = 2;
        custoReal[4][7] = 2;

        // vermelho (custo 3)
        custoReal[1][5] = 3;
        custoReal[2][5] = 3;
        custoReal[2][6] = 3;
        custoReal[3][5] = 3;
        custoReal[3][6] = 3;
        custoReal[4][5] = 3;
        custoReal[5][5] = 3;
        custoReal[2][4] = 3;
        custoReal[3][4] = 3;
        custoReal[4][4] = 3;

        // obstáculos
        obstaculoReal[6][5] = false;
    }

    public static void observar(int x, int y,
                                int[][] custoReal, boolean[][] obstaculoReal, int[][] custoConhecido,
                                boolean[][] obstaculoConhecido, boolean[][] conhecido) {

        int[][] dirs = {
                {0, 0}, {0, -1}, {-1, 0}, {0, 1}, {1, 0}
        };

        for (int[] d : dirs) {
            int nx = x + d[0];
            int ny = y + d[1];

            if (valido(nx, ny)) {
                conhecido[nx][ny] = true;
                custoConhecido[nx][ny] = custoReal[nx][ny];
                obstaculoConhecido[nx][ny] = obstaculoReal[nx][ny];
            }
        }
    }

    public static List<int[]> aStar(int startX, int startY, int goalX, int goalY,
                                    int[][] custoConhecido, boolean[][] obstaculoConhecido) {

        PriorityQueue<Node> open = new PriorityQueue<>(
                Comparator.comparingInt(Node::f).thenComparingInt(n -> n.h)
        );

        boolean[][] fechado = new boolean[TAM][TAM];
        int[][][] pai = new int[TAM][TAM][2];
        int[][] gScore = new int[TAM][TAM];

        for (int i = 0; i < TAM; i++) {
            Arrays.fill(gScore[i], Integer.MAX_VALUE);
            for (int j = 0; j < TAM; j++) {
                pai[i][j][0] = -1;
                pai[i][j][1] = -1;
            }
        }

        gScore[startX][startY] = 0;
        open.add(new Node(startX, startY, 0, heuristica(startX, startY, goalX, goalY)));

        int[][] dirs = {
                {0, -1}, {-1, 0}, {0, 1}, {1, 0}
        };

        while (!open.isEmpty()) {
            Node atual = open.poll();

            if (fechado[atual.x][atual.y]) continue;
            fechado[atual.x][atual.y] = true;

            if (atual.x == goalX && atual.y == goalY) {
                return reconstruirCaminho(pai, goalX, goalY, startX, startY);
            }

            for (int[] d : dirs) {
                int nx = atual.x + d[0];
                int ny = atual.y + d[1];

                if (!valido(nx, ny)) continue;
                if (obstaculoConhecido[nx][ny]) continue;

                int custoPasso = obterCustoParaPlanejamento(custoConhecido[nx][ny]);
                int novoG = atual.g + custoPasso;

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

    public static int obterCustoParaPlanejamento(int custoCelula) {
        if (custoCelula == DESCONHECIDO) {
            return CUSTO_PADRAO_DESCONHECIDO;
        }
        return custoCelula;
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

            if (px == -1 && py == -1) {
                return null;
            }

            x = px;
            y = py;
        }

        return caminho;
    }

    public static boolean valido(int x, int y) {
        return x >= 0 && x < TAM && y >= 0 && y < TAM;
    }

    public static void montarGridParcial(String[][] grid,boolean[][] visitado, boolean[][] conhecido,
                                         boolean[][] obstaculoConhecido, int[][] custoConhecido,int ax, int ay,int sx,
                                         int sy,int gx, int gy) {

        for (int i = 0; i < TAM; i++) {
            for (int j = 0; j < TAM; j++) {

                if (i == ax && j == ay) {
                    grid[i][j] = "A";
                } else if (i == sx && j == sy) {
                    grid[i][j] = "I";
                } else if (i == gx && j == gy) {
                    grid[i][j] = "O";
                } else if (!conhecido[i][j]) {
                    grid[i][j] = "?";
                } else if (obstaculoConhecido[i][j]) {
                    grid[i][j] = "X";
                } else if (visitado[i][j]) {
                    grid[i][j] = "*";
                } else {
                    grid[i][j] = String.valueOf(custoConhecido[i][j]);
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
}