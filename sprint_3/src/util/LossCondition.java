package util;

import actors.Worker;
import actors.Player;
import main.GamePanel;
import tile.Tile;
import actions.Action;

import javax.swing.*;
import java.awt.*;

public class LossCondition {
    private GamePanel gamePanel;
    private final FontLoader fontLoader;
    private Action action;

    public LossCondition(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.fontLoader = new FontLoader();
        this.action = gamePanel.currentAction;
    }

    public boolean checkLossCondition(Player currentPlayer, GamePanel gamePanel) {
        if (!canWorkerMove(currentPlayer, gamePanel)) {
            handleLoss(currentPlayer.getPlayerId());
            return true;
        }
        return false;
    }

    public boolean canWorkerMove(Player currentPlayer, GamePanel gamePanel) {
        Tile[][] board = gamePanel.getBoard();

        for (Worker worker : currentPlayer.getWorkers()) {
            Tile currentTile = board[worker.getRow()][worker.getCol()];

            for (int row = 0; row < gamePanel.playTiles; row++) {
                for (int col = 0; col < gamePanel.playTiles; col++) {
                    Tile target = board[row][col];

                    if (action.getValidMoveTiles(currentTile, target)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void handleLoss(int losingPlayerId) {
        int winningPlayerId = (losingPlayerId == 1) ? 2 : 1;
        System.out.println("Player " + winningPlayerId + " wins!");
        System.out.println("Player " + losingPlayerId + " has no valid moves!");
        gamePanel.gameOver();

        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/uiextra/winemoji.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel messageLabel = new JLabel("Player " + losingPlayerId + " has no valid moves!\n" + "Player " + winningPlayerId + " wins!");
        messageLabel.setFont(fontLoader.getPixelFont().deriveFont(20f));
        messageLabel.setForeground(Color.BLACK);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(messageLabel, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JOptionPane.showMessageDialog(gamePanel,
                panel,
                "Game Over",
                JOptionPane.INFORMATION_MESSAGE,
                scaledIcon);
    }
}
