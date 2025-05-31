package main;

import actors.Worker;
import util.FontLoader;
import util.LeaderBoard;

import javax.swing.*;
import java.awt.*;

public class WinCondition {
    private final GamePanel gamePanel;
    private final FontLoader fontLoader;
    private final LeaderBoard leaderBoard;

    public WinCondition(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.fontLoader = new FontLoader();
        this.leaderBoard = new LeaderBoard();
    }

    public boolean checkWinCondition(Worker selectedWorker) {
        return hasWon(selectedWorker);
    }

    public boolean hasWon(Worker selectedWorker) {
        int row = selectedWorker.getRow();
        int col = selectedWorker.getCol();
        Tile tile = gamePanel.getBoard()[row][col];

        return tile.getLevel() == 3;
    }

    public void handleWin(int winningPlayerId) {
        String winnerName = gamePanel.getPlayerName(winningPlayerId);
        System.out.println("Player " + winningPlayerId + " wins!");
        leaderBoard.addScore(winnerName, 1);

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
