package util;

import actors.Worker;
import buildings.Building;
import java.util.ArrayList;
import main.GamePanel;

import javax.swing.*;

public class WinCondition {
    private GamePanel gamePanel;
    private ArrayList<Building> buildings;
    private ArrayList<Worker> workers;

    public WinCondition(GamePanel gamePanel, ArrayList<Building> buildings, ArrayList<Worker> workers) {
        this.gamePanel = gamePanel;
        this.buildings = buildings;
        this.workers = workers;
    }

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
