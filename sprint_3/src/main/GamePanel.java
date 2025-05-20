package main;

import tile.Tile;
import actions.Action;
import actions.ActionList;
import actors.Player;
import actors.Worker;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class GamePanel extends JPanel {

    int BOARD_WIDTH = 700;
    int BOARD_HEIGHT = 700;

    public int playTiles = 5;
    public int tileSize = BOARD_HEIGHT/(playTiles + 2);

    int startX = tileSize;
    int startY = tileSize;

    private Tile[][] board = new Tile[playTiles][playTiles];

    private final Player player1;
    private final Player player2;
    private Player currentPlayer;

    public ArrayList<Worker> workerPos = new ArrayList<>();

    private Worker selectedWorker = null;

    private Action currentAction = null;
    private int currentActionIndex = 0;


    public GamePanel(Player player1, Player player2) {
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        setBackground(new Color(156, 212, 200));
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick(e.getX(), e.getY());
            }
        });
    }

    public void gameStart() {
        currentPlayer = player1;

        // Initialize board
        for (int row = 0; row < playTiles; row++) {
            for (int col = 0; col < playTiles; col++) {
                board[row][col] = new Tile(row, col);
            }
        }

        // Create workers for both players
        Worker w1 = new Worker(player1.getPlayerId(), 1, 0, 0, 0);
        Worker w2 = new Worker(player1.getPlayerId(), 2, 0, 1, 0);
        Worker w3 = new Worker(player2.getPlayerId(), 1, 4, 4, 0);
        Worker w4 = new Worker(player2.getPlayerId(), 2, 4, 3, 0);

        // Place workers on board
        board[0][0].setWorker(w1);
        board[0][1].setWorker(w2);
        board[4][4].setWorker(w3);
        board[4][3].setWorker(w4);

        // Add workers to players
        player1.getWorkers().add(w1);
        player1.getWorkers().add(w2);
        player2.getWorkers().add(w3);
        player2.getWorkers().add(w4);

        repaint();
        System.out.println("Game start! Player: " + currentPlayer.getPlayerId() + "'s turn");
    }

    public Tile[][] getBoard() {
        return board;
    }

    public void switchTurn() {
        this.currentPlayer = (currentPlayer == player1) ? player2 : player1;
        System.out.println("Player: " + currentPlayer.getPlayerId() + "'s turn");
        repaint();
    }

    private void handleClick(int mouseX, int mouseY) {
        int col = mouseX / tileSize;
        int row = mouseY / tileSize;

        if (row < 1 || row > playTiles || col < 1 || col > playTiles) {
            System.out.println("Clicked outside the board!");
            repaint();
            return;
        }

        int boardRow = row - 1;
        int boardCol = col - 1;
        Tile clickedTile = board[boardRow][boardCol];
        if (clickedTile == null) return;

        if (currentAction == null) {
            selectWorker(clickedTile); // initialize currentAction and index
        } else {
            boolean success = currentAction.onTileClick(clickedTile.getRow(), clickedTile.getCol());

            if (success) {
                currentActionIndex++;
                ActionList actions = currentPlayer.getGodCard().getActions();

                if (currentActionIndex < actions.size()) {
                    currentAction = actions.get(currentActionIndex);
                    currentAction.execute(selectedWorker, this);
                } else {
                    currentAction = null;
                    currentActionIndex = 0;
                    selectedWorker = null;
                    switchTurn(); // done with all actions
                }

                repaint();
            } else {
                // Just wait for valid click
                System.out.println("Try a different tile.");
            }
        }
    }

    private void selectWorker(Tile tile) {
        if (tile.isOccupiedByWorker()) {
            Worker worker = tile.getWorker();
            if (worker.getPlayerId() == currentPlayer.getPlayerId()) {
                selectedWorker = worker;
                System.out.println("Selected Worker " + worker.getWorkerId());
                repaint();
                currentActionIndex = 0;
                ActionList actions = currentPlayer.getGodCard().getActions();

                if (actions.size() > 0) {
                    currentAction = actions.get(currentActionIndex);
                    currentAction.execute(selectedWorker, this);
                }
            }
            else{
                System.out.println("Worker belongs to another player.");
            }
        }
        else{
            System.out.println("No worker on that tile.");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                int x = startX + col * tileSize;
                int y = startY + row * tileSize;
                g2.drawRect(x, y, tileSize, tileSize);

            }
        }

        if (selectedWorker != null) {
            int highlightX = startX + selectedWorker.getCol() * tileSize;
            int highlightY = startY + selectedWorker.getRow() * tileSize;

            g2.setColor(new Color(255, 215, 0, 128)); // semi-transparent yellow
            g2.fillRect(highlightX, highlightY, tileSize, tileSize);
        }


    }

}
