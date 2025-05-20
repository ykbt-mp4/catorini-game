package tile;

import actors.Worker;

public class Tile {
    private final int row;  // Add these fields
    private final int col;
    private Worker worker;
    private int level;
    private boolean hasDome;

    public Tile(int row, int col) {  // Modified constructor
        this.row = row;
        this.col = col;
        this.worker = null;
        this.level = 0;
        this.hasDome = false;
    }

    // Getters for position
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    // Check if a worker occupies a tile
    public boolean isOccupiedByWorker() {
        return worker != null;
    }

    public boolean isEmpty() {
        return !isOccupiedByWorker() && !hasDome;
    }

    public boolean isAdjacentTo(int adjRow, int adjCol) {
        int rowDiff = Math.abs(worker.getRow() - adjRow);
        int colDiff = Math.abs(worker.getCol() - adjCol);
        return (rowDiff <= 1 && colDiff <= 1) && (rowDiff + colDiff != 0);
    }


    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Worker getWorker() {
        return worker;
    }

    // Clear worker
    public void clearWorker() {
        this.worker = null;
    }

    // Building level management
    public int getLevel() {
        return level;
    }

    public void increaseLevel() {
        if (level < 3) {
            level++;
        }
    }

    public boolean hasDome() {
        return hasDome;
    }

    public void setDome(boolean hasDome) {
        this.hasDome = hasDome;
    }

}
