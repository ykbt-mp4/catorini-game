package main;

import actions.Action;
import actions.ActionList;
import actors.Player;
import actors.Worker;
import tile.Tile;
import util.LossCondition;
import util.WinCondition;

public class TurnManager {
    private final GamePanel gamePanel;
    private final Player player1;
    private final Player player2;
    private Player currentPlayer;

    public Worker selectedWorker = null;
    public Action currentAction = null;
    public int currentActionIndex = 0;

    private final WinCondition winCondition;
    private final LossCondition lossCondition;

    public TurnManager(GamePanel gamePanel, Player player1, Player player2) {
        this.gamePanel = gamePanel;
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.winCondition = new WinCondition(gamePanel);
        this.lossCondition = new LossCondition(gamePanel);
    }

    public void switchTurn() {
        this.currentPlayer = (currentPlayer == player1) ? player2 : player1;
        System.out.println("Player: " + currentPlayer.getPlayerId() + "'s turn");

        if (lossCondition.checkLossCondition(currentPlayer, gamePanel)) {
            gamePanel.gameOver();
        }

        // Clear highlights
        clearHighlights(gamePanel);
        gamePanel.repaint();
    }

    public void handleClick(int mouseX, int mouseY) {
        if (gamePanel.isGameEnded()) {
            System.out.println("Game has ended. No more moves allowed.");
            return;
        }
        int col = mouseX / gamePanel.tileSize;
        int row = mouseY / gamePanel.tileSize;

        if (row < 1 || row > gamePanel.playTiles || col < 1 || col > gamePanel.playTiles) {
            System.out.println("Clicked outside the board!");
            gamePanel.repaint();
            return;
        }

        int boardRow = row - 1;
        int boardCol = col - 1;

        Tile clickedTile = gamePanel.getBoard()[boardRow][boardCol];
        if (clickedTile == null) return;

        if (currentAction == null) {
            selectWorker(clickedTile);
        } else {
            boolean success = currentAction.onTileClick(clickedTile.getRow(), clickedTile.getCol());

            if (success) {
                if (winCondition.checkWinCondition(selectedWorker)) {
                    gamePanel.gameOver();
                    clearHighlights(gamePanel);
                    return;
                }

                currentActionIndex++;
                ActionList actions = currentPlayer.getGodCard().getActions();

                if (currentActionIndex < actions.size()) {
                    currentAction = actions.get(currentActionIndex);
                    currentAction.execute(selectedWorker, gamePanel);
                    if (gamePanel.isGameEnded()) return;
                } else {
                    currentAction = null;
                    currentActionIndex = 0;
                    selectedWorker = null;
                    switchTurn();
                }
                gamePanel.repaint();
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
                System.out.println("Player " + currentPlayer.getPlayerId() + " selected worker " + worker.getWorkerId() + "\n");
                gamePanel.repaint();
                currentActionIndex = 0;
                ActionList actions = currentPlayer.getGodCard().getActions();

                if (actions.size() > 0) {
                    currentAction = actions.get(currentActionIndex);
                    currentAction.execute(selectedWorker, gamePanel);
                }
            } else {
                System.out.println("Worker belongs to another player.");
            }
        } else {
            System.out.println("No worker on that tile.");
        }
    }

    public void skipCurrentAction() {
        if (currentAction != null && currentAction.isGodAction()) {
            ActionList actions = currentPlayer.getGodCard().getActions();

            if (currentActionIndex < actions.size() - 1) {
                currentActionIndex++;
                currentAction = actions.get(currentActionIndex);
                System.out.println("Skipped God action. Next action: " + currentAction.getClass().getSimpleName() + "\n");
                currentAction.execute(selectedWorker, gamePanel);
            } else {
                currentAction = null;
                currentActionIndex = 0;
                selectedWorker = null;
                System.out.println("Skipped God action. Ending turn.\n");
                switchTurn();
            }
            gamePanel.repaint();
        } else {
            System.out.println("Cannot skip a required action.");
        }
        gamePanel.repaint();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Worker getSelectedWorker() {
        return selectedWorker;
    }

    private void clearHighlights(GamePanel gamePanel){
        for (int row = 0; row < gamePanel.playTiles; row++) {
            for (int col = 0; col < gamePanel.playTiles; col++) {
                gamePanel.getBoard()[row][col].setHighlighted(false);
            }
        }
    }
}
