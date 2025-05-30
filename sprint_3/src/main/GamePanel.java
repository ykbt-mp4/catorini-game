package main;

import tile.Tile;
import actors.Player;
import actors.Worker;
import tile.TileManager;
import util.FontLoader;
import util.TurnTimer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel {

    public int BOARD_WIDTH = 700;
    public int BOARD_HEIGHT = 700;

    public int playTiles = 5;
    public int tileSize = BOARD_HEIGHT/(playTiles + 2);

    public Tile[][] board = new Tile[playTiles][playTiles];

    public final Player player1;
    public final Player player2;
    private Player currentPlayer;

    public ArrayList<Worker> workerPos = new ArrayList<>();
    int workerCount = 2;
    Random random = new Random();

    private final TileManager tileManager;
    public final TurnManager turnManager;
    Font pixelFont = new FontLoader().getPixelFont();

    private boolean gameEnded = false;

    public GamePanel(Player player1, Player player2) {
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        setBackground(new Color(156, 212, 200));
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.tileManager = new TileManager(this);
        this.turnManager = new TurnManager(this, player1, player2);

        this.setFont(pixelFont);
        setLayout(new BorderLayout());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                turnManager.handleClick(e.getX(), e.getY());
            }
        });
    }

    public void gameStart() {
        // setting player 1 as the first player
        currentPlayer = player1;
        System.out.println("Game started! Player: " + currentPlayer.getPlayerId() + "'s turn");

        // initalise gameboard
        for (int row = 0; row < playTiles; row++) {
            for (int col = 0; col < playTiles; col++) {
                board[row][col] = new Tile(row, col);
            }
        }

        // setting worker positions for all players
        setWorkerPos();

        // setting the buttons needed for game functionality (skip button etc)
        JPanel controlPanel = tileManager.createControlPanel();
        JLabel timerLabel = tileManager.getTimerLabel();

        TurnTimer turnTimer = new TurnTimer(this, 15, timerLabel);
        turnManager.setTimer(turnTimer);
        turnTimer.start(); // start first turn

        add(controlPanel, BorderLayout.NORTH);

        repaint();
    }

    public void gameOver() {
        System.out.println("Game over!");
        setGameEnded(true);
        repaint();
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public void setGameEnded(boolean gameEnded) {
        this.gameEnded = gameEnded;
    }


    public Tile[][] getBoard() {
        // getter for the gameboard
        return board;
    }

    public void setWorkerPos() {
        // method to randomly place workers on the gameboard
        this.workerPos.clear();

        Player[] players = {player1, player2};
        for (Player player : players) {
            int workerLeft = workerCount;
            while (workerLeft > 0) {
                int r = random.nextInt(playTiles);
                int c = random.nextInt(playTiles);

                // create worker
                Worker worker = new Worker(player.getPlayerId(), workerLeft, r, c, 0);

                if (!board[r][c].isOccupiedByWorker()) {
                    this.workerPos.add(worker);
                    board[r][c].setWorker(worker);
                    workerLeft--;
                    player.getWorkers().add(worker);
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileManager.drawWaterTile(g2);
        tileManager.drawBoardTiles(g2);
        tileManager.drawBuildings(g2);
        tileManager.drawWorkers(g2);
        tileManager.drawValidTiles(g2);
        tileManager.drawWorkerHighlight(g2, turnManager.selectedWorker);
    }
}
