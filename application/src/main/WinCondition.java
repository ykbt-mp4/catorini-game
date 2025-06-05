package main;

import actors.Worker;
import util.FontLoader;
import util.LeaderBoard;

import javax.swing.*;
import java.awt.*;

/**
 * Handles the win condition for the game.
 * A player wins when their worker reaches the third level of a building.
 */
public class WinCondition {
    private final GamePanel gamePanel;
    private final FontLoader fontLoader;
    private final LeaderBoard leaderBoard;

    /**
     * Constructs a WinCondition object with access to the game panel.
     * @param gamePanel the game panel to access board and player data
     */
    public WinCondition(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.fontLoader = new FontLoader();
        this.leaderBoard = new LeaderBoard();
    }

    /**
     * Checks if the given worker meets the win condition (stands on level 3).
     * @param selectedWorker the worker to check
     * @return true if the worker is on level 3, false otherwise
     */
    public boolean checkWinCondition(Worker selectedWorker) {
        return hasWon(selectedWorker);
    }

    /**
     * Determines if a worker has won the game.
     * @param selectedWorker the worker to check
     * @return true if the worker stands on a level 3 tile
     */
    private boolean hasWon(Worker selectedWorker) {
        int row = selectedWorker.getRow();
        int col = selectedWorker.getCol();
        Tile tile = gamePanel.getBoard()[row][col];

        return tile.getLevel() == 3;
    }

    /**
     * Handles win event for the given player.
     * Displays a popup and updates the leaderboard.
     * @param winningPlayerId the ID of the player who won
     */
    public void handleWin(int winningPlayerId) {
        String winnerName = gamePanel.getPlayerName(winningPlayerId);
        System.out.println("Player " + winningPlayerId + " wins!");
        leaderBoard.addScore(winnerName, 1); // adds winner to leaderboard

        // UI pop up for when player wins
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/uiextra/winemoji.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel messageLabel = new JLabel("Player " + winningPlayerId + " wins!");
        messageLabel.setFont(fontLoader.getPixelFont().deriveFont(20f));
        messageLabel.setForeground(Color.BLACK);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(messageLabel, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JOptionPane.showMessageDialog(gamePanel,
                panel,
                "Game Over!",
                JOptionPane.INFORMATION_MESSAGE,
                scaledIcon);
    }
}
