package actors;

public class Worker {
    public int row;
    public int col;
    public int workerId;
    public int playerId;

    public Worker(int playerId, int workerId, int row, int col) {
        this.playerId = playerId;
        this.workerId = workerId;
        this.row = row;
        this.col = col;


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

    public void setCol(int col) {
        this.col = col;
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
}