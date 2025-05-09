package main;

import actors.Player;
import actors.Worker;
import buildings.Building;
import actions.BuildAction;
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
    public static ArrayList<Building> buildings = new ArrayList<>();
    Random random = new Random();

    MoveAction moveAction;
    BuildAction buildAction;

    private final Player player1;
    private final Player player2;
    private Player currentPlayer;

    private boolean isBuildingPhase = false;
    private Worker workerThatMoved;
    private boolean isGameStarted = false;

    public GamePanel(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.buildAction = new BuildAction(buildings);
        this.moveAction = new MoveAction(buildings);
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
        this.currentPlayer = (currentPlayer == player1) ? player2 : player1;
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

        // Draw buildings
        for (Building building : buildings) {
            if (building.getBuildingImage() != null) {
                int x = building.getCol() * tileSize;
                int y = building.getRow() * tileSize;
                g2.drawImage(building.getBuildingImage(), x, y, tileSize, tileSize, null);
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

                Worker worker = new Worker(player.getPlayerId(), workerLeft, r, c, 0);

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

        if (isBuildingPhase) {
            if (workerThatMoved != null) {
                // Pass GamePanel's 'buildings' list to canBuild, which is consistent
                // as canBuild expects the current state of all buildings for its checks.
                if (buildAction.canBuild(workerThatMoved, row, col, workerPos, buildings)) {
                    buildAction.build(row, col);
                    System.out.println("Player " + workerThatMoved.getPlayerId() + " built at (" + row + ", " + col + ")");
                    
                    // Check for win condition after building (optional, add later if needed)

                    isBuildingPhase = false;
                    workerThatMoved = null;
                    // Switch player
                    switchTurn();
                    System.out.println("Turn: Player " + currentPlayer.getPlayerId());
                } else {
                    System.out.println("Cannot build there. Try an adjacent, unoccupied, and buildable tile.");
                    // Player remains in building phase to try another spot
        // // If worker is selected, check if movement is possible (Adjacent tiles)
        // if (moveAction.getWorker() != null && moveAction.getWorker().getPlayerId() == currentPlayer.getPlayerId()) {
        //     if (clickedWorker == null) {
        //         if (moveAction.canMove(moveAction.getWorker(), row, col, workerPos)) {
        //             moveAction.moveWorker(row, col);
        //             System.out.println("Moved worker to (" + row + ", " + col + ")");
        //             switchTurn();
                }
            } else {
                // This case should ideally not be reached if logic is correct
                System.err.println("Error: In building phase but no worker is designated to build.");
                isBuildingPhase = false; // Reset to avoid getting stuck
                currentPlayer = (currentPlayer == player1) ? player2 : player1; // Switch turn to be safe
                 System.out.println("Turn: Player " + currentPlayer.getPlayerId());
            }
        } else { // Move phase or worker selection
            Worker clickedWorker = getWorkerAtPosition(row, col);

            if (moveAction.getWorker() != null && moveAction.getWorker().getPlayerId() == currentPlayer.getPlayerId()) { // A worker is already selected, try to move it
                Worker selectedWorker = moveAction.getWorker();
                if (clickedWorker == null) { 
                    if (selectedWorker.getPlayerId() == currentPlayer.getPlayerId()) {
                        if (moveAction.canMove(selectedWorker, row, col, workerPos, buildings)) {
                            // Store previous position for win condition check if needed before move
                            // int prevHeight = selectedWorker.getHeight();
                            moveAction.moveWorker(row, col); // Worker's height is updated in moveWorker
                            System.out.println("Player " + selectedWorker.getPlayerId() + " moved worker to (" + row + ", " + col + ")");
                            
                            // Check for win condition after moving (e.g. reaching level 3)
                            // Building targetBuilding = getBuildingAtPosition(row, col);
                            // if (targetBuilding != null && targetBuilding.getLevel() == BuildingLevel.LEVEL_THREE && prevHeight < BuildingLevel.LEVEL_THREE.getHeight()){
                            //    System.out.println("Player " + selectedWorker.getPlayerId() + " wins!");
                            //    // Handle game over
                            //    return;
                            // }

                            isBuildingPhase = true;
                            workerThatMoved = selectedWorker;
                            moveAction.clearSelection();
                            System.out.println("Player " + workerThatMoved.getPlayerId() + " must now build.");
                        } else {
                            System.out.println("Invalid move for Player " + selectedWorker.getPlayerId() + ".");
                            moveAction.clearSelection(); // Clear selection on invalid move attempt
                        }
                    } else {
                        System.out.println("Not Player " + currentPlayer.getPlayerId() + "'s worker to move.");
                        moveAction.clearSelection();
                    }
                } else if (clickedWorker == selectedWorker) { // Clicked on the already selected worker
                    moveAction.clearSelection(); // Deselect
                    System.out.println("Deselected worker.");
                } else { // Target tile has another worker
                    System.out.println("Cannot move to a tile occupied by another worker.");
                    // moveAction.clearSelection(); // Keep worker selected to try another move
                }
            } else if (clickedWorker != null) { // No worker selected, try to select this one
                if (clickedWorker.getPlayerId() == currentPlayer.getPlayerId()) {
                    moveAction.setWorker(clickedWorker);
                     System.out.println("Selected worker - " +
                        "Player ID: " + clickedWorker.getPlayerId() + ", " +
                        "Worker ID: " + clickedWorker.getWorkerId() + ", " +
                        "Position: (" + clickedWorker.getRow() + ", " + clickedWorker.getCol() + ")");
                } else {
                    System.out.println("Cannot select Player " + clickedWorker.getPlayerId()+ "'s worker!");
                }
            } else { // Clicked on an empty tile with no worker selected
                System.out.println("No worker at position (" + row + ", " + col + ")");
            }
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

    // Helper to get building at position, useful for win condition or other logic
    private Building getBuildingAtPosition(int row, int col) {
        for (Building building : buildings) {
            if (building.getRow() == row && building.getCol() == col) {
                return building;
            }
        }
        return null;
    }
}
