package main;

import actors.Player;
import actors.Worker;
import actions.MoveAction;

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

    MoveAction moveAction = new MoveAction();

    private final Player player1;
    private final Player player2;
    private Player currentPlayer;

    private boolean isGameStarted = false;

    public GamePanel(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(new Color(156, 212, 200));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick(e.getX(), e.getY());
            }
        });
    }

    public void gameStart() {
        isGameStarted = true;
        currentPlayer = player1;
        System.out.println("Game start! Player: " + currentPlayer.getPlayerId() + "'s turn");
    }

    public void switchTurn() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
        moveAction.clearSelection();
        System.out.println("Player: " + currentPlayer.getPlayerId() + "'s turn");
        repaint();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.BLACK); // Set line color

        // Draw turn indicator
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        String turnText = "Player " + currentPlayer.getPlayerId() + "'s turn";
        g2.drawString(turnText, 20, 30);


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

            // Highlight selected worker
            if (moveAction.getWorker() == worker) {
                g2.setColor(new Color(255, 0, 0, 100)); // Red highlight
                g2.fillOval(x, y, tileSize, tileSize);
            }

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
            moveAction.clearSelection();
            repaint(); // Redraw component
            return;
        }

        Worker clickedWorker = getWorkerAtPosition(row, col);

        // If worker is selected, check if movement is possible (Adjacent tiles)
        if (moveAction.getWorker() != null && moveAction.getWorker().getPlayerId() == currentPlayer.getPlayerId()) {
            if (clickedWorker == null) {
                if (moveAction.canMove(moveAction.getWorker(), row, col, workerPos)) {
                    moveAction.moveWorker(row, col);
                    System.out.println("Moved worker to (" + row + ", " + col + ")");
                    switchTurn();
                }
                else {
                    System.out.println("Invalid move");
                }
            }
            else if (clickedWorker != moveAction.getWorker()) {
                System.out.println("Cannot move to occupied space");
            }
            moveAction.clearSelection();
        }
        // If no worker is selected, select the clicked worker
        else if (clickedWorker != null) {
            if (clickedWorker.getPlayerId() == currentPlayer.getPlayerId()) {
                moveAction.setWorker(clickedWorker);
                System.out.println("Selected worker - " +
                        "Player ID: " + clickedWorker.getPlayerId() + ", " +
                        "Worker ID: " + clickedWorker.getWorkerId() + ", " +
                        "Position: (" + clickedWorker.getRow() + ", " + clickedWorker.getCol() + ")");
            }
            else {
                System.out.println("Cannot select " + currentPlayer.getPlayerId() + "'s worker!");
            }
        }
        else {
            System.out.println("No worker at position (" + row + ", " + col + ")");
        }
        repaint();
    }

    private Worker getWorkerAtPosition(int row, int col) {
        for (Worker worker : workerPos) {
            if (worker.getRow() == row && worker.getCol() == col) {
                return worker;
            }
        }
        return null;
    }
}
