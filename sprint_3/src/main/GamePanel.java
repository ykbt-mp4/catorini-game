package main;

import tile.Tile;
import actions.Action;
import actions.ActionList;
import actors.Player;
import actors.Worker;
import tile.TileManager;
import util.FontLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;


public class GamePanel extends JPanel {

    public int BOARD_WIDTH = 700;
    public int BOARD_HEIGHT = 700;

    public int playTiles = 5;
    public int tileSize = BOARD_HEIGHT/(playTiles + 2);

    int startX = tileSize;
    int startY = tileSize;

    public Tile[][] board = new Tile[playTiles][playTiles];

    public final Player player1;
    public final Player player2;
    private Player currentPlayer;

    public ArrayList<Worker> workerPos = new ArrayList<>();
    int workerCount = 2;
    Random random = new Random();

    private Worker selectedWorker = null;

    public Action currentAction = null;
    public int currentActionIndex = 0;

    private final TileManager tileManager;

    Font pixelFont = new FontLoader().getPixelFont();

    public GamePanel(Player player1, Player player2) {
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        setBackground(new Color(156, 212, 200));
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.tileManager = new TileManager(this);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick(e.getX(), e.getY());
            }
        });
    }

    public void gameStart() {
        currentPlayer = player1;

        for (int row = 0; row < playTiles; row++) {
            for (int col = 0; col < playTiles; col++) {
                board[row][col] = new Tile(row, col);
            }
        }

        setWorkerPos();

        try {
            int width = 190;
            int height = 60;

            ImageIcon defaultIcon = new ImageIcon(
                    new ImageIcon(getClass().getResource("/uiextra/button1.png"))
                            .getImage()
                            .getScaledInstance(width, height, Image.SCALE_SMOOTH)
            );

            ImageIcon pressedIcon = new ImageIcon(
                    new ImageIcon(getClass().getResource("/uiextra/button2.png"))
                            .getImage()
                            .getScaledInstance(width, height, Image.SCALE_SMOOTH)
            );

            JButton skipButton = new JButton("Skip");
            skipButton.setIcon(defaultIcon);
            skipButton.setPressedIcon(pressedIcon);
            skipButton.setHorizontalTextPosition(SwingConstants.CENTER);
            skipButton.setVerticalTextPosition(SwingConstants.CENTER);
            skipButton.setFont(pixelFont.deriveFont(20f));

            // Make it look clean
            skipButton.setBorderPainted(false);
            skipButton.setContentAreaFilled(false);
            skipButton.setFocusPainted(false);
            skipButton.setOpaque(false);

            skipButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    skipCurrentAction();
                }
            });
            add(skipButton, BorderLayout.SOUTH);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Failed to load button images.");
        }

        repaint();
        System.out.println("Game start! Player: " + currentPlayer.getPlayerId() + "'s turn");
    }

    public Tile[][] getBoard() {
        return board;
    }

    public void switchTurn() {
        this.currentPlayer = (currentPlayer == player1) ? player2 : player1;
        System.out.println("Player: " + currentPlayer.getPlayerId() + "'s turn");
        for (int row = 0; row < playTiles; row++) {
            for (int col = 0; col < playTiles; col++) {
                board[row][col].setHighlighted(false);
            }
        }
        repaint();
    }

    public void setWorkerPos() {
        this.workerPos.clear();

        Player[] players = {player1, player2};
        for (Player player : players) {
            int workerLeft = workerCount;
            while (workerLeft > 0) {
                int r = random.nextInt(playTiles); // + 1 to account for padding
                int c = random.nextInt(playTiles);

                // create worker
                Worker worker = new Worker(player.getPlayerId(), workerLeft, r, c, 0);

                if (!board[r][c].isOccupiedByWorker()) {
                    this.workerPos.add(worker);
                    board[r][c].setWorker(worker);
                    workerLeft--;
                    player.getWorkers().add(worker);
                    System.out.println(workerPos);
                }
                System.out.println(worker.getRow() + " " + worker.getCol());
            }
        }
    }

    public void skipCurrentAction() {
        if (currentAction != null && currentAction.isGodAction()) {
            ActionList actions = currentPlayer.getGodCard().getActions();

            if (currentActionIndex < actions.size() - 1) {
                currentActionIndex++;
                currentAction = actions.get(currentActionIndex);
                currentAction.execute(selectedWorker, this);
                System.out.println("Skipped to next action: " + currentAction.getClass().getSimpleName());
            } else {
                currentAction = null;
                currentActionIndex = 0;
                selectedWorker = null;
                System.out.println("Skipped final god action. Ending turn.");
                switchTurn();
            }

            repaint();
        } else {
            System.out.println("Cannot skip a required action.");
        }
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
            selectWorker(clickedTile);
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

        tileManager.drawWaterTile(g2);
        tileManager.drawBoardTiles(g2);
        tileManager.drawBuildings(g2);
        tileManager.drawWorkers(g2);
        tileManager.drawValidTiles(g2);

        if (selectedWorker != null) {
            int highlightX = startX + selectedWorker.getCol() * tileSize;
            int highlightY = startY + selectedWorker.getRow() * tileSize;

            g2.setColor(new Color(255, 215, 0, 128)); // semi-transparent yellow
            g2.fillRect(highlightX, highlightY, tileSize, tileSize);
        }
    }

}
