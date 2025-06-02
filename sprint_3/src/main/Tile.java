package main;

import actors.Worker;
import buildings.Building;

import java.awt.image.BufferedImage;

/**
 * Represents a single tile on the game board.
 * Each tile can contain a worker, a building, and can be highlighted for UI purposes.
 */
public class Tile {
    private final int row;
    private final int col;

    private Worker worker;
    public Building building;

    public BufferedImage image;
    private boolean highlighted = false;

    /**
     * Constructs a Tile at the specified row and column.
     * @param row the row index of the tile on the board
     * @param col the column index of the tile on the board
     */
    public Tile(int row, int col) {
        this.row = row;
        this.col = col;
        this.worker = null;
        this.building = new Building();
    }

    /**
     * Gets the row index of this tile.
     * @return the row index
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column index of this tile.
     * @return the column index
     */
    public int getCol() {
        return col;
    }


    /**
     * Checks if the tile is occupied by a worker.
     * @return true if a worker is on this tile, false otherwise
     */
    public boolean isOccupiedByWorker() {
        return worker != null;
    }

    /**
     * Sets the worker occupying this tile.
     * @param worker the worker to place on this tile
     */
    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    /**
     * Gets the worker currently occupying this tile.
     * @return the worker on this tile, or null if none
     */
    public Worker getWorker() {
        return worker;
    }


    /**
     * Removes any worker from this tile.
     */
    public void clearWorker() {
        this.worker = null;
    }

    /**
     * Gets the current building level on this tile.
     * @return the building height/level
     */
    public int getLevel() {
        return building.getHeight();
    }


    /**
     * Checks if the building can be built on this tile.
     * A tile is buildable if not occupied by a worker, and building allows further building (no dome on it).
     * @return true if buildable, false otherwise
     */
    public boolean isBuildable() {
        return !isOccupiedByWorker() && building.canBuild();
    }

    /**
     * Builds one level on the building at this tile if possible.
     * Prints an error message if building is not allowed.
     */
    public void build() {
        if (isBuildable()) {
            building.build();
        } else {
            System.out.println("Cannot build on this tile.");
        }
    }

    /**
     * Checks if this tile currently has a dome on the building.
     * @return true if there is a dome, false otherwise
     */
    public boolean hasDome() {
        return building.hasDome();
    }

    /**
     * Checks if this tile is adjacent to the tile at the given row and column.
     * Adjacent includes diagonals but excludes the same tile.
     * @param adjRow the row of the other tile
     * @param adjCol the column of the other tile
     * @return true if adjacent, false otherwise
     */
    public boolean isAdjacentTo(int adjRow, int adjCol) {
        int rowDiff = Math.abs(this.row - adjRow);
        int colDiff = Math.abs(this.col - adjCol);
        return (rowDiff <= 1 && colDiff <= 1) && (rowDiff + colDiff != 0);
    }

    /**
     * Checks if this tile is currently highlighted for UI purposes.
     * @return true if highlighted, false otherwise
     */
    public boolean isHighlighted() {
        return highlighted;
    }


    /**
     * Sets the highlighted state of this tile.
     * @param highlighted true to highlight, false to remove highlight
     */
    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }
}
