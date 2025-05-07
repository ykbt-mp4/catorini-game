package src.main;

import actors.Player;
import actors.Worker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

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

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick(e.getX(), e.getY());
            }
        });
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
        // Draw workers
        for (Worker worker : workerPos) {
            int x = worker.getCol() * tileSize;
            int y = worker.getRow() * tileSize;

            // Get the correct player based on worker's playerId
            Player player = worker.getPlayerId() == player1.getPlayerId() ? player1 : player2;
            player.draw(g2, x, y, tileSize, tileSize);

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
                    player.getWorkers().add(worker);
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

    private void handleClick(int mouseX, int mouseY) {
        // Convert mouse coordinates to grid coordinates
        int col = mouseX / tileSize;
        int row = mouseY / tileSize;

        // Check if click is within playable area
        if (row < 1 || row > playTiles || col < 1 || col > playTiles) {
            System.out.println("Clicked outside play area");
            return;
        }

        // Check if a worker exists at this position
        for (Worker worker : workerPos) {
            if (worker.getRow() == row && worker.getCol() == col) {
                System.out.println("Worker clicked - " +
                        "Player ID: " + worker.getPlayerId() + ", " +
                        "Worker ID: " + worker.getWorkerId() + ", " +
                        "Position: (" + worker.getRow() + ", " + worker.getCol() + ")");
                return;
            }
        }

        System.out.println("No worker at position (" + row + ", " + col + ")");
    }
}
