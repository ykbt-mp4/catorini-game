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

    private final BoardView boardView;
    public final TurnManager turnManager;
    private TurnTimer turnTimer;

    Font pixelFont = new FontLoader().getPixelFont();

    private boolean gameEnded = false;

    public GamePanel(Player player1, Player player2) {
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
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

    public String getPlayerName(int playerId) {
        return playerId == 1 ? player1.getPlayerName() : player2.getPlayerName();
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
        JPanel buttonPanel = boardView.createButtonPanel();
        JPanel timePanel = boardView.createTimerPanel();
        JLabel timerLabel = boardView.getTimerLabel();

        this.turnTimer = new TurnTimer(this, 20, timerLabel);

        turnManager.setTimer(turnTimer);
        turnTimer.start(); // start first turn

        add(buttonPanel, BorderLayout.SOUTH);
        add(timePanel, BorderLayout.NORTH);

        repaint();
    }

    public void gameOver() {
        System.out.println("Game over!");
        if (turnTimer != null) {
            turnTimer.stop();
        }
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

        boardView.drawWaterTile(g2);
        boardView.drawBoardTiles(g2);
        boardView.drawBuildings(g2);
        boardView.drawWorkers(g2);
        boardView.drawValidTiles(g2);
        boardView.drawWorkerHighlight(g2, turnManager.selectedWorker);
    }
}
