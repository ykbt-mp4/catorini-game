package main;

import actors.Worker;
import actors.Player;
import util.FontLoader;
import util.LeaderBoard;

import javax.swing.*;
import java.awt.*;

public class LossCondition {
    private final GamePanel gamePanel;
    private final FontLoader fontLoader;
    private final LeaderBoard leaderBoard;

    public LossCondition(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.fontLoader = new FontLoader();
        this.leaderBoard = new LeaderBoard();
    }

    public boolean checkLossCondition(Player currentPlayer, GamePanel gamePanel) {
        return !canAnyWorkerMove(currentPlayer, gamePanel);
    }

    public boolean canAnyWorkerMove(Player currentPlayer, GamePanel gamePanel) {
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

    public void handleLoss(int losingPlayerId) {
        int winningPlayerId = (losingPlayerId == 1) ? 2 : 1;
        String winnerName = gamePanel.getPlayerName(winningPlayerId);
        String loserName = gamePanel.getPlayerName(losingPlayerId);

        System.out.println("Player " + losingPlayerId + " has no valid moves!");
        System.out.println("Player " + winningPlayerId + " wins!");

        leaderBoard.addScore(winnerName, 1);

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

    public void handleTimeoutLoss(int losingPlayerId) {
        int winningPlayerId = (losingPlayerId == 1) ? 2 : 1;
        String winnerName = gamePanel.getPlayerName(winningPlayerId);
        String loserName = gamePanel.getPlayerName(losingPlayerId);

        System.out.println("Player " + losingPlayerId + " ran out of time!");
        System.out.println("Player " + winningPlayerId + " wins!");

        leaderBoard.addScore(winnerName, 1);

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

