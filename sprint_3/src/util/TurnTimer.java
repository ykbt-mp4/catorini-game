package util;

import main.GamePanel;
import main.LossCondition;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Manages a countdown timer for each player's total game time.
 * If a player's timer reaches zero, they lose the game.
 */
public class TurnTimer {

    private final Timer timer;
    private int player1TimeLeft;
    private int player2TimeLeft;
    private final int initialTime;

    private final JLabel player1TimerLabel; // UI label for player 1's time
    private final JLabel player2TimerLabel; // UI label for player 2's time
    private final LossCondition lossCondition;
    private final GamePanel gamePanel;

    private int currentPlayerId;

    /**
     * Constructs a TurnTimer with the specified game panel, time limit, and timer display labels.
     * @param gamePanel         the game panel associated with the timer
     * @param minutes          the total time limit per player in minutes
     * @param player1TimerLabel the UI label for player 1's remaining time
     * @param player2TimerLabel the UI label for player 2's remaining time
     */
    public TurnTimer(GamePanel gamePanel, int minutes, JLabel player1TimerLabel, JLabel player2TimerLabel) {
        this.gamePanel = gamePanel;
        this.initialTime = minutes * 60; // Convert minutes to seconds
        this.player1TimeLeft = initialTime;
        this.player2TimeLeft = initialTime;
        this.player1TimerLabel = player1TimerLabel;
        this.player2TimerLabel = player2TimerLabel;
        this.lossCondition = new LossCondition(gamePanel);
        this.currentPlayerId = 1; // Start with player 1

        this.timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (currentPlayerId == 1) {
                    player1TimeLeft--;
                    updateTimerLabel(player1TimerLabel, player1TimeLeft);
                    if (player1TimeLeft <= 0) {
                        timer.stop();
                        lossCondition.handleTimeoutLoss(1);
                        gamePanel.gameOver();
                    }
                } else {
                    player2TimeLeft--;
                    updateTimerLabel(player2TimerLabel, player2TimeLeft);
                    if (player2TimeLeft <= 0) {
                        timer.stop();
                        lossCondition.handleTimeoutLoss(2);
                        gamePanel.gameOver();
                    }
                }
            }
        });
    }

    /**
     * Starts the timer for the current player.
     */
    public void start() {
        timer.start();
        updateTimerLabels();
    }

    /**
     * Stops the timer without resetting the time.
     */
    public void stop() {
        timer.stop();
    }

    /**
     * Switches the timer to the specified player's turn.
     * @param playerId the ID of the player whose turn is starting (1 or 2)
     */
    public void switchToPlayer(int playerId) {
        this.currentPlayerId = playerId;
        updateTimerLabels();
    }

    /**
     * Updates the timer label with the current remaining time in MM:SS format.
     * @param label the JLabel to update
     * @param timeLeft the remaining time in seconds
     */
    private void updateTimerLabel(JLabel label, int timeLeft) {
        int minutes = timeLeft / 60;
        int seconds = timeLeft % 60;
        label.setText(String.format("Player %d Time: %02d:%02d",
                label == player1TimerLabel ? 1 : 2, minutes, seconds));
    }

    /**
     * Updates both timer labels to reflect current times.
     */
    private void updateTimerLabels() {
        updateTimerLabel(player1TimerLabel, player1TimeLeft);
        updateTimerLabel(player2TimerLabel, player2TimeLeft);
    }
}