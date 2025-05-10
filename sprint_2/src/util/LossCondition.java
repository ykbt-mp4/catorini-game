package util;

import actions.MoveAction;
import actors.Player;
import actors.Worker;
import buildings.Building;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import main.GamePanel;

/**
 * This class handles the loss condition checking and processing for the game.
 * It determines when a player has lost (No valid moves for any assigned workers)
 * Also handles the game over state.
 */
public class LossCondition {
    private GamePanel gamePanel;
    private MoveAction moveAction;
    private ArrayList<Building> buildings;
    private ArrayList<Worker> workers;

    /**
     * Constructs a LossCondition with necessary game components.
     *
     * @param gamePanel The main game panel containing game state information.
     * @param moveAction The move action handler for checking valid moves.
     * @param buildings The list of buildings in the game.
     * @param workers The list of workers in the game.
     */
    public LossCondition(GamePanel gamePanel, MoveAction moveAction,
                         ArrayList<Building> buildings, ArrayList<Worker> workers) {
        this.gamePanel = gamePanel;
        this.moveAction = moveAction;
        this.buildings = buildings;
        this.workers = workers;
    }

    /**
     * Checks if the specified player has met the loss condition (no valid moves for any workers).
     *
     * @param player The player to check for loss condition.
     * @return true if the player has lost (no workers can move), false otherwise.
     */
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

    /**
     * Checks if a specific worker has any valid moves available.
     *
     * @param worker The worker to check for valid moves.
     * @return true if the worker can make at least one valid move, false otherwise.
     */
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

    /**
     * Handles the loss condition when triggered, showing game over message
     * and declaring the other player as the winner.
     *
     * @param losingPlayerId The ID of the player who has lost.
     */
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