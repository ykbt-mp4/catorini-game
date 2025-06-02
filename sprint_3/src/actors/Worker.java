package actors;

/**
 * Represents a worker in the game, controlled by a player. Each worker has a unique ID, a current position
 * on the board, and keeps track of its previous and last build positions.
 */
public class Worker {

    private int row;
    private int col;
    private final int workerId;
    private final int playerId;
    private int lastRow = -1;
    private int lastCol = -1;
    private int lastBuildRow = -1;
    private int lastBuildCol = -1;


    /**
     * Constructs a Worker for a given player at a specified board position.
     * @param playerId the ID of the player who controls the worker
     * @param workerId the unique ID of the worker
     * @param row      the initial row of the worker
     * @param col      the initial column of the worker
     */
    public Worker(int playerId, int workerId, int row, int col) {
        this.playerId = playerId;
        this.workerId = workerId;
        this.lastRow = this.row;
        this.lastCol = this.col;
        this.row = row;
        this.col = col;

    }

    /**
     * Returns the ID of this worker.
     * @return the worker's ID
     */
    public int getWorkerId() {
        return workerId;
    }

    /**
     * Returns the ID of the player who owns this worker.
     * @return the player's ID
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Sets the current row of the worker.
     * @param row the new row
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Returns the current row of the worker.
     * @return the current row
     */
    public int getRow() {
        return row;
    }

    /**
     * Sets the current column of the worker.
     * @param col the new column
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * Returns the current column of the worker.
     * @return the current column
     */
    public int getCol() {
        return col;
    }

    /**
     * Sets the last row position before the most recent move.
     * @param lastRow the last row
     */
    public void setLastRow(int lastRow) {
        this.lastRow = lastRow;
    }

    /**
     * Returns the last row position before the most recent move.
     * @return the last row
     */
    public int getLastRow() {
        return lastRow;
    }

    /**
     * Sets the last column position before the most recent move.
     * @param lastCol the last column
     */
    public void setLastCol(int lastCol) {
        this.lastCol = lastCol;
    }

    /**
     * Returns the last column position before the most recent move.
     * @return the last column
     */
    public int getLastCol() {
        return lastCol;
    }

    /**
     * Sets the current position of the worker.
     * @param row the new row
     * @param col the new column
     */
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Sets the position of the last build action.
     * @param row the row of the last build
     * @param col the column of the last build
     */
    public void setLastBuildPosition(int row, int col) {
        this.lastBuildRow = row;
        this.lastBuildCol = col;
    }

    /**
     * Returns the row of the last build action.
     * @return the last build row
     */
    public int getLastBuildRow() {
        return lastBuildRow;
    }

    /**
     * Returns the column of the last build action.
     * @return the last build column
     */
    public int getLastBuildCol() {
        return lastBuildCol;
    }
}