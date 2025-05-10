package util;

import actors.Worker;
import buildings.Building;
import java.util.ArrayList;
import main.GamePanel;

import javax.swing.*;

/**
 * This class handles the win condition checking and processing for the game.
 * It determines when a player has won (Worker has moved to a building of 3 height)
 * Also handles the game over state.
 */
public class WinCondition {
    private GamePanel gamePanel;
    private ArrayList<Building> buildings;
    private ArrayList<Worker> workers;

    /**
     * Constructs a WinCondition object with necessary game components.
     *
     * @param gamePanel The main game panel containing game state information.
     * @param buildings The list of buildings in the game.
     * @param workers The list of workers in the game.
     */
    public WinCondition(GamePanel gamePanel, ArrayList<Building> buildings, ArrayList<Worker> workers) {
        this.gamePanel = gamePanel;
        this.buildings = buildings;
        this.workers = workers;
    }

    /**
     * Checks if the specified worker has met the win condition (standing on a level 3 building).
     *
     * @param selectedWorker The worker to check for win condition.
     * @return true if the worker is on a level 3 building, false otherwise.
     */
    public boolean checkWinCondition(Worker selectedWorker) {
        // Check if the worker is now on a level 3 building
        for (Building building : buildings) {
            if (building.getRow() == selectedWorker.getRow() &&
                    building.getCol() == selectedWorker.getCol() &&
                    building.getLevel().getHeight() == 3) {
                return true;
            }
        }
        return false;
    }

    /**
     * Handles the win condition when triggered, showing a game over message and declaring the specified player as the winner.
     *
     * @param winningPlayerId The ID of the player who has won.
     */
    public void handleWin(int winningPlayerId) {
        System.out.println("Player " + winningPlayerId + " wins!");
        gamePanel.setGameOver(true);

        // Winning Message
        JOptionPane.showMessageDialog(gamePanel,
                "Player " + winningPlayerId + " wins!",
                "Game Over",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
