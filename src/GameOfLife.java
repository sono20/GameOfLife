import java.util.Random;

public class GameOfLife {


    private static final int WIDTH = 40;
    private static final int HEIGHT = 20;
    private static final int GENERATIONS = 50;

    private static boolean[][] grid = new boolean[HEIGHT][WIDTH];

    public static void main(String[] args) {
        initializeGrid();

        for (int i = 0; i < GENERATIONS; i++) {
            printGrid(i);
            updateGrid();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Symulacja zakończona.");
    }
    private static void initializeGrid() {
        Random random = new Random();
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                grid[y][x] = random.nextDouble() < 0.2;
            }
        }
    }

    private static void printGrid(int gen) {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("Generacja: " + gen);
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                System.out.print(grid[y][x] ? " O " : " . ");
            }
            System.out.println();
        }
        System.out.println("========================================");
    }


    private static void updateGrid() {
        boolean[][] nextGrid = new boolean[HEIGHT][WIDTH];

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                int aliveNeighbors = countAliveNeighbors(y, x);
                boolean isAlive = grid[y][x];

                if (!isAlive && aliveNeighbors == 3) {
                    nextGrid[y][x] = true;
                }
                else if (isAlive && (aliveNeighbors == 2 || aliveNeighbors == 3)) {
                    nextGrid[y][x] = true;
                }
                else {
                    nextGrid[y][x] = false;
                }
            }
        }
        grid = nextGrid;
    }

    private static int countAliveNeighbors(int y, int x) {
        int count = 0;
        // Sprawdzamy 3x3 obszar wokół punktu (y, x)
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int newY = y + i;
                int newX = x + j;

                if (newY >= 0 && newY < HEIGHT && newX >= 0 && newX < WIDTH) {
                    if (grid[newY][newX]) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}