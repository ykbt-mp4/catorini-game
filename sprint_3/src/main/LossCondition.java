package main;

import actors.Worker;
import actors.Player;
import util.FontLoader;
import util.LeaderBoard;

import javax.swing.*;
import java.awt.*;

/**
 * Handles the logic for detecting and responding to a player's loss in the game.
 * A player loses if neither of their workers can move or if they run out of time.
 */
public class LossCondition {
    private final GamePanel gamePanel;
    private final FontLoader fontLoader;
    private final LeaderBoard leaderBoard;

    /**
     * Constructs a LossCondition handler for the current game.
     * @param gamePanel the game panel associated with the game
     */
    public LossCondition(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.fontLoader = new FontLoader();
        this.leaderBoard = new LeaderBoard();
    }

    /**
     * Checks if the current player has lost due to no possible moves.
     * @param currentPlayer the player being checked
     * @param gamePanel     the current game panel
     * @return true if the player has no valid moves, false otherwise
     */
    public boolean checkLossCondition(Player currentPlayer, GamePanel gamePanel) {
        return !canAnyWorkerMove(currentPlayer, gamePanel);
    }

    /**
     * Checks whether any worker of the current player can make a valid move.
     * @param currentPlayer the player being checked
     * @param gamePanel     the current game panel
     * @return true if at least one worker can move, false otherwise
     */
    private boolean canAnyWorkerMove(Player currentPlayer, GamePanel gamePanel) {
        Tile[][] board = gamePanel.getBoard();

        for (Worker worker : currentPlayer.getWorkers()) {
            Tile currentTile = board[worker.getRow()][worker.getCol()];

            for (int row = 0; row < gamePanel.playTiles; row++) {
                for (int col = 0; col < gamePanel.playTiles; col++) {
                    Tile targetTile = board[row][col];

                    if (!currentTile.isAdjacentTo(targetTile.getRow(), targetTile.getCol())) continue;
                    if (targetTile.isOccupiedByWorker()) continue;
                    if (targetTile.hasDome()) continue;

                    int levelDiff = targetTile.getLevel() - currentTile.getLevel();
                    if (levelDiff <= 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Displays a loss message when the player has no valid moves,
     * updates the leaderboard, and announces the winner.
     * @param losingPlayerId the ID of the player who lost
     */
    public void handleLoss(int losingPlayerId) {
        int winningPlayerId = (losingPlayerId == 1) ? 2 : 1;
        String winnerName = gamePanel.getPlayerName(winningPlayerId);

        System.out.println("Player " + losingPlayerId + " has no valid moves!");
        System.out.println("Player " + winningPlayerId + " wins!");

        leaderBoard.addScore(winnerName, 1); // adds winner to leaderboard

        // UI pop up for when the player loses
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/uiextra/winemoji.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel messageLabel = new JLabel("Player " + losingPlayerId + " has no valid moves! " + "Player " + winningPlayerId + " wins!");
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

    /**
     * Handles the case where a player loses due to running out of time.
     * @param losingPlayerId the ID of the player who lost by timeout
     */
    public void handleTimeoutLoss(int losingPlayerId) {
        int winningPlayerId = (losingPlayerId == 1) ? 2 : 1;
        String winnerName = gamePanel.getPlayerName(winningPlayerId);

        System.out.println("Player " + losingPlayerId + " ran out of time!");
        System.out.println("Player " + winningPlayerId + " wins!");

        leaderBoard.addScore(winnerName, 1); // adds winner to leaderboard

        // UI pop up for when the player loses
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/uiextra/timeremoji.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel messageLabel = new JLabel("Player " + losingPlayerId + " ran out of time! Player " + winningPlayerId + " wins!");
        messageLabel.setFont(fontLoader.getPixelFont().deriveFont(20f));
        messageLabel.setForeground(Color.BLACK);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(messageLabel, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(gamePanel,
                panel,
                "Game Over - Time's Up!",
                JOptionPane.INFORMATION_MESSAGE,
                scaledIcon);
    }

}

