package main;

import actors.Player;
import actors.Worker;
import util.FontLoader;
import util.TurnTimer;
import view.BoardView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

/**
 * The GamePanel class represents the main game board panel in the Santorini Game.
 * It handles the initialisation and flow of the game as well as integrating
 * the UI elements like buttons and timers.
 */

public class GamePanel extends JPanel {

    // Dimensions of the game board
    private final int boardWidth = 700;
    private final int boardHeight = 700;

    // Number of playable tiles per row/column (5x5 board) and size of each tile
    public int playTiles = 5;
    public int tileSize = boardHeight /(playTiles + 2);

    // 2D grid of tiles representing the game board
    private final Tile[][] board = new Tile[playTiles][playTiles];

    // Players in the game
    public final Player player1;
    public final Player player2;
    private Player currentPlayer;

    // All workers placed on the board
    public ArrayList<Worker> workerPos = new ArrayList<>();
    private final int workerCount = 2;
    Random random = new Random();

    private final BoardView boardView;
    public final TurnManager turnManager;
    private TurnTimer turnTimer;

    private boolean gameEnded = false; // Flag to check if the game has ended
    Font pixelFont = new FontLoader().getPixelFont();

    /**
     * Constructs a GamePanel with the given players and initializes game components.
     * @param player1 The first player.
     * @param player2 The second player.
     */
    public GamePanel(Player player1, Player player2) {

        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(new Color(156, 212, 200));
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.boardView = new BoardView(this);
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

    /**
     * Retrieves the name of the specified player by ID.
     * @param playerId the ID of the player (1 or 2).
     * @return the player's name.
     */
    public String getPlayerName(int playerId) {
        return playerId == 1 ? player1.getPlayerName() : player2.getPlayerName();
    }

    /**
     * Initialises and starts the game: sets up board, workers, buttons, and timer.
     */
    public void gameStart() {
        currentPlayer = player1;
        System.out.println("Game started! Player: " + currentPlayer.getPlayerId() + "'s turn");

        // initialise gameboard
        for (int row = 0; row < playTiles; row++) {
            for (int col = 0; col < playTiles; col++) {
                board[row][col] = new Tile(row, col);
            }
        }

        // setting worker positions for all players
        setWorkerPos();

        // setting the buttons needed for game functionality (skip button etc)
        JPanel buttonPanel = boardView.createButtonPanel();
        JPanel timePanel = boardView.createTimerPanel();
        JLabel player1TimerLabel = boardView.getPlayer1TimerLabel();
        JLabel player2TimerLabel = boardView.getPlayer2TimerLabel();

        this.turnTimer = new TurnTimer(this, 5, player1TimerLabel, player2TimerLabel); // 5 minutes per player
        turnManager.setTimer(turnTimer);
        turnTimer.start(); // Start first turn

        add(buttonPanel, BorderLayout.SOUTH);
        add(timePanel, BorderLayout.NORTH);

        repaint();
    }

    /**
     * Ends the game, stopping the timer and setting the gameEnded flag.
     */
    public void gameOver() {
        System.out.println("Game over!");
        if (turnTimer != null) {
            turnTimer.stop();
        }
        setGameEnded(true);
        repaint();
    }

    /**
     * Checks if the game has ended.
     * @return true if the game is over, false otherwise.
     */
    public boolean isGameEnded() {
        return gameEnded;
    }

    /**
     * Sets the game-ended state.
     * @param gameEnded whether the game is ended.
     */
    private void setGameEnded(boolean gameEnded) {
        this.gameEnded = gameEnded;
    }

    /**
     * Returns the current state of the board.
     * @return the 2D Tile array representing the board.
     */
    public Tile[][] getBoard() {
        return board;
    }

    /**
     * Randomly places each player's workers on unoccupied tiles.
     */
    private void setWorkerPos() {
        this.workerPos.clear();

        Player[] players = {player1, player2};
        for (Player player : players) {
            int workerLeft = workerCount;
            while (workerLeft > 0) {
                int r = random.nextInt(playTiles);
                int c = random.nextInt(playTiles);

                // create worker
                Worker worker = new Worker(player.getPlayerId(), workerLeft, r, c);

                if (!board[r][c].isOccupiedByWorker()) {
                    this.workerPos.add(worker);
                    board[r][c].setWorker(worker);
                    workerLeft--;
                    player.getWorkers().add(worker);
                }
            }
        }
    }

    /**
     * Draws the game components using the custom boardView rendering system.
     * @param g the graphics context.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        boardView.drawWaterTile(g2);
        boardView.drawBoardTiles(g2);
        boardView.drawBuildings(g2);
        boardView.drawWorkers(g2);
        boardView.drawValidTiles(g2);
        boardView.drawWorkerHighlight(g2, turnManager.selectedWorker);
    }
}