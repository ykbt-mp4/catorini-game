package actors;

/**
 * Represents a worker unit in the game, belonging to a specific player.
 * Tracks the worker's position on the board and current height level.
 */
public class Worker {
    public int row;
    public int col;
    public int workerId;
    public int playerId;
    public int height;

    /**
     * Constructs a new worker with specified attributes.
     *
     * @param playerId The ID of the player that owns this worker (1 or 2)
     * @param workerId The unique identifier for this worker (1 or 2)
     * @param row The initial row position on the gameboard
     * @param col The initial row position on the gameboard
     * @param height The initial height level
     */
    public Worker(int playerId, int workerId, int row, int col, int height) {
        this.playerId = playerId;
        this.workerId = workerId;
        this.row = row;
        this.col = col;
        this.height = height;

    }

    /**
     * Gets the current row position of the worker.
     *
     * @return The row index
     */
    public int getRow() {
        return row;
    }

    /**
     * Sets the worker's row position.
     *
     * @param row The chosen row index
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Gets the current column position of the worker.
     *
     * @return The column index
     */
    public int getCol() {
        return col;
    }

    /**
     * Sets the worker's column position.
     *
     * @param col The chosen column index
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * Gets the worker's current height level.
     *
     * @return The height level (0 = ground, 1-3 = levels of building)
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the worker's current height level.
     *
     * @return The height level (0 = ground, 1-3 = levels of building)
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Gets the worker's unique identifier.
     *
     * @return The worker ID (1 or 2)
     */
    public int getWorkerId() {
        return workerId;
    }

    /**
     * Sets the worker's unique identifier.
     *
     * @return The worker ID (1 or 2)
     */
    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    /**
     * Gets the ID of the player that owns the worker.
     *
     * @return The player ID (1 or 2)
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Sets the ID of the player that owns the worker.
     *
     * @return The player ID (1 or 2)
     */
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}

