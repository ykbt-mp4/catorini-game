package tile;

import actors.Worker;
import buildings.Building;

import java.awt.image.BufferedImage;

public class Tile {
    private final int row;
    private final int col;

    private Worker worker;
    public Building building;

    public BufferedImage image;
    private boolean highlighted = false;

    public Tile(int row, int col) {
        this.row = row;
        this.col = col;
        this.worker = null;
        this.building = new Building();
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isOccupiedByWorker() {
        return worker != null;
    }

    public boolean isEmpty() {
        return !isOccupiedByWorker() && !hasDome();
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Worker getWorker() {
        return worker;
    }

    public void clearWorker() {
        this.worker = null;
    }

    public int getLevel() {
        return building.getHeight();
    }

    public boolean isBuildable() {
        return !isOccupiedByWorker() && building.canBuild();
    }

    public void build() {
        if (isBuildable()) {
            building.build();
        } else {
            System.out.println("Cannot build on this tile.");
        }
    }

    public boolean hasDome() {
        return building.hasDome();
    }

    public boolean isAdjacentTo(int adjRow, int adjCol) {
        int rowDiff = Math.abs(this.row - adjRow);
        int colDiff = Math.abs(this.col - adjCol);
        return (rowDiff <= 1 && colDiff <= 1) && (rowDiff + colDiff != 0);
    }

    public boolean isHighlighted() {
        return highlighted;
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }
}
