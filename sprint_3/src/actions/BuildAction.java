package actions;

import actors.Worker;
import main.GamePanel;
import tile.Tile;

public class BuildAction extends Action {

    @Override
    public void execute(Worker worker, GamePanel gamePanel) {
        System.out.println("Executing build");
    }

    @Override
    public void onTileClick(int row, int col) {
        System.out.println("done");
    }
}

//package actions;
//
//import board.board.Tile;
//
//public class BuildAction extends Action {
//    @Override
//    public Action execute(board.Tile[][] board, int row, int col) {
//        if (worker != null && board[row][col].isBuildable()) {
//            board[row][col].buildLevel();
//            System.out.println("Built on " + row + "," + col);
//        } else {
//            System.out.println("Cannot build there.");
//        }
//        return null;
//    }
//}

