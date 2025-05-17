package actions;

import actors.Worker;
import main.GamePanel;
import tile.Tile;

public class MoveAction extends Action {


    public void execute(Worker worker, GamePanel gamePanel) {
        super.execute(worker, gamePanel);
    }

    @Override
    public void onTileClick(int row, int col) {
        Tile[][] board = gp.getBoard();
        if (row < 0 || row >= gp.playTiles || col < 0 || col >= gp.playTiles) {
            System.out.println("Invalid tile clicked.");
            return;
        }

        Tile targetTile = board[row][col];

        if (targetTile.isEmpty()) {
            int oldRow = worker.getRow();
            int oldCol = worker.getCol();
            board[oldRow][oldCol].clearWorker();

            worker.setPosition(row, col);
            targetTile.setWorker(worker);
            System.out.println("Moved worker to " + row + "," + col);
            gp.repaint();

        } else {
            System.out.println("Target tile occupied.");
        }
    }

}
