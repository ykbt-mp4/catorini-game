package main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import actors.Player;
import actors.Worker;


public class GamePanel extends JPanel {
    int boardWidth = 700;
    int boardHeight = 700;
    int playTiles = 5;
    int tiles = playTiles + 2;
    int tileSize = boardWidth / tiles;
    int workerCount = 2;
    public static ArrayList<Worker> workerPos = new ArrayList<>();
    Random random = new Random();

    private final Player player1;
    private final Player player2;

    public GamePanel(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(new Color(156, 212, 200));
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.BLACK); // Set line color

        // Draw vertical lines
        for (int row = 1; row <= (playTiles); row++) {
            for (int col = 1; col <= (playTiles); col++) {
                int x = col * tileSize;
                int y = row * tileSize;
                g2.drawRect(x, y, tileSize, tileSize);
            }
        }
        // placeholder worker
        for (Worker worker : workerPos) {
            int x = (worker.getCol()) * tileSize; // +1 to account for border
            int y = (worker.getRow()) * tileSize;
            g2.fillOval(x, y, tileSize, tileSize);
            g2.setColor(Color.BLACK);
            g2.drawOval(x, y, tileSize, tileSize);
        }
    }


    public void setWorkerPos() {
        workerPos = new ArrayList<Worker>();
        Player[] players = {player1, player2};

        for (Player player : players) {
            int workerLeft = workerCount;
            while (workerLeft > 0) {
                int r = random.nextInt(playTiles)+1; // + 1 to account for padding
                int c = random.nextInt(playTiles)+1;

                Worker worker = new Worker(player.getPlayerId(), workerLeft, r, c);

                if (!isPositionOccupied(r, c)) {
                    workerPos.add(worker);
                    workerLeft--;
                }
                System.out.println(worker.getRow() + " " + worker.getCol());
            }
        }
    }

    private boolean isPositionOccupied(int row, int col) {
        for (Worker worker : workerPos) {
            if (worker.getRow() == row && worker.getCol() == col) {
                return true;
            }
        }
        return false;
    }
}
