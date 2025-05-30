package util;

import actors.Worker;
import main.GamePanel;
import tile.Tile;

import javax.swing.*;
import java.awt.*;

public class WinCondition {
    private final GamePanel gamePanel;
    private final FontLoader fontLoader;

    public WinCondition(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.fontLoader = new FontLoader();
    }

    /**
     * Checks if the specified worker meets the win condition.
     * If so, triggers the win handler and returns true.
     */
    public boolean checkWinCondition(Worker selectedWorker) {
        return hasWon(selectedWorker);
    }

    /**
     * Returns true if the worker is standing on a level 3 tile.
     */
    public boolean hasWon(Worker selectedWorker) {
        int row = selectedWorker.getRow();
        int col = selectedWorker.getCol();
        Tile tile = gamePanel.getBoard()[row][col];

        return tile.getLevel() == 3;
    }

    /**
     * Handles showing the win message dialog.
     */
    public void handleWin(int winningPlayerId) {
        System.out.println("Player " + winningPlayerId + " wins!");

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
