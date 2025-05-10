package util;

import actions.MoveAction;
import actors.Player;
import actors.Worker;
import buildings.Building;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import main.GamePanel;

public class LossCondition {
    private GamePanel gamePanel;
    private MoveAction moveAction;
    private ArrayList<Building> buildings;
    private ArrayList<Worker> workers;

    public LossCondition(GamePanel gamePanel, MoveAction moveAction,
                         ArrayList<Building> buildings, ArrayList<Worker> workers) {
        this.gamePanel = gamePanel;
        this.moveAction = moveAction;
        this.buildings = buildings;
        this.workers = workers;
    }

    public boolean checkLossCondition(Player player) {
        List<Worker> playerWorkers = player.getWorkers();

        // Check if all player's workers can't move
        for (Worker worker : playerWorkers) {
            if (canWorkerMove(worker)) {
                return false; // At least one worker can move
            }
        }
        return true; // All workers are unable to move
    }

    private boolean canWorkerMove(Worker worker) {
        int currentRow = worker.getRow();
        int currentCol = worker.getCol();

        // Check all adjacent tiles
        for (int row = currentRow - 1; row <= currentRow + 1; row++) {
            for (int col = currentCol - 1; col <= currentCol + 1; col++) {
                // Skip current position
                if (row == currentRow && col == currentCol) continue;

                // Check if move can
                if (row >= 1 && row <= gamePanel.playTiles &&
                        col >= 1 && col <= gamePanel.playTiles &&
                        moveAction.canMove(worker, row, col)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void handleLoss(int losingPlayerId) {
        System.out.println("Player " + losingPlayerId + " has no valid moves!");
        gamePanel.setGameOver(true);

        // Determine winning player (the other player)
        int winningPlayerId = (losingPlayerId == 1) ? 2 : 1;

        // Winning Message
        JOptionPane.showMessageDialog(gamePanel,
                "Player " + losingPlayerId + " has no valid moves!\n" + "Player " + winningPlayerId + " wins!",
                "Game Over",
                JOptionPane.INFORMATION_MESSAGE);
    }
}