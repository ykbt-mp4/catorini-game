package actions;

import actors.Worker;
import main.GamePanel;
import tile.Tile;

public class BuildAction extends Action {

    @Override
    public void execute(Worker worker, GamePanel gamePanel) {
        super.execute(worker, gamePanel);
        System.out.println("Executing build");
    }

    @Override
    public boolean onTileClick(int row, int col) {
//        if (worker != null && board[row][col].isBuildable()) {
//            board[row][col].buildLevel();
//            System.out.println("Built on " + row + "," + col);
//        } else {
//            System.out.println("Cannot build there.");
//        }
        System.out.println("done");
        return true;
    }
}


