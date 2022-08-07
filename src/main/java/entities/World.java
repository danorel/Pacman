package entities;

import java.util.Arrays;

public class World {
    final static char Pacman = 'P';
    final static char Wall = '%';
    final static char Food = '.';

    char[][] grid;
    int width;
    int height;

    public World(char[][] grid, int width, int height) {
        this.grid = grid;
        this.width = width;
        this.height = height;
    }

    public boolean isInBounds(int r, int c) {
        return r >= 0 && r < width && c >= 0 && c < height;
    }

    public boolean isWall(int r, int c) {
        return this.grid[r][c] == Wall;
    }

    public boolean isFood(int r, int c) {
        return this.grid[r][c] == Food;
    }

    @Override
    public String toString() {
        return "World{" +
                "grid=" + Arrays.toString(Arrays.stream(grid).map(Arrays::toString).toArray()) +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}