package ia.stagages;

public class StageTwo {
    static final int TAM = 10;

    public static void main(String[] args) throws InterruptedException {
        String[][] grid = new String[TAM][TAM];
        boolean[][] visitado = new boolean[TAM][TAM];
        boolean[][] obstaculo = new boolean[TAM][TAM];
        int contadorDemovimentos = 0;

        // Obstáculos estáticos de exemplo
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

        int x = 9;
        int y = 9;

        while (true) {
            visitado[x][y] = true;

            montarGrid(grid, visitado, obstaculo, x, y);
            imprimirGrid(grid);

            int[] prox = escolherMovimento(x, y, visitado, obstaculo);

            if (prox == null) {
                System.out.println("Sem movimentos possíveis. Encerrando.");
                System.out.println("Ambiente percorrido em: "+ contadorDemovimentos + " movimentos");
                break;
            }

            x = prox[0];
            y = prox[1];

            Thread.sleep(300);
            contadorDemovimentos++;
        }
    }

    public static int[] escolherMovimento(int x, int y, boolean[][] visitado, boolean[][] obstaculo) {
        int[][] direcoes = {
                {0, -1}, // esquerda
                {-1, 0}, // cima
                {0, 1},  // direita
                {1, 0}   // baixo
        };
        for (int[] dir : direcoes) {
            int nx = x + dir[0];
            int ny = y + dir[1];

            if (valido(nx, ny) && !obstaculo[nx][ny] && !visitado[nx][ny]) {
                return new int[]{nx, ny};
            }
        }

        return null;
    }

    public static boolean valido(int x, int y) {
        return x >= 0 && x < TAM && y >= 0 && y < TAM;
    }

    public static void montarGrid(String[][] grid, boolean[][] visitado, boolean[][] obstaculo, int agenteX, int agenteY) {
        for (int i = 0; i < TAM; i++) {
            for (int j = 0; j < TAM; j++) {
                if (obstaculo[i][j]) {
                    grid[i][j] = "X";
                } else if (i == agenteX && j == agenteY) {
                    grid[i][j] = "A";
                } else if (visitado[i][j]) {
                    grid[i][j] = "*";
                } else {
                    grid[i][j] = ".";
                }
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
        System.out.println("----------------------");
    }
}