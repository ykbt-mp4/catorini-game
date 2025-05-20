package actors;

public class Worker {

    public int row;
    public int col;
    public int workerId;
    public int playerId;
    public int height;
    private int lastRow = -1;
    private int lastCol = -1;

    public Worker(int playerId, int workerId, int row, int col, int height) {
        this.playerId = playerId;
        this.workerId = workerId;
        this.lastRow = this.row;
        this.lastCol = this.col;
        this.row = row;
        this.col = col;
        this.height = height;

    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setLastRow(int lastRow) {
        this.lastRow = lastRow;
    }
    public void setLastCol(int lastCol) {
        this.lastCol = lastCol;
    }

    public int getLastRow() {
        return lastRow;
    }

    public int getLastCol() {
        return lastCol;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

}