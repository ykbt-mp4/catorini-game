package util;

import main.GamePanel;
import main.LossCondition;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The TurnTimer class manages a countdown timer for each player's turn.
 * If the timer reaches zero, the current player automatically loses the game.
 */
public class TurnTimer {

    private final Timer timer;
    private int timeLeft;
    private final int turnTimeLimit;

    // JLabel used to display the timer countdown in the UI.
    private final JLabel timerLabel;

    private final LossCondition lossCondition;

    /**
     * Constructs a TurnTimer with the specified game panel, time limit,
     * and timer display label.
     * @param gamePanel   the game panel associated with the timer
     * @param seconds     the turn time limit in seconds
     * @param timerLabel  the UI label to update with remaining time
     */
    public TurnTimer(GamePanel gamePanel, int seconds, JLabel timerLabel) {
        this.turnTimeLimit = seconds;
        this.timerLabel = timerLabel;
        this.timeLeft = seconds;
        this.lossCondition = new LossCondition(gamePanel);

        this.timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                timeLeft--;
                updateTimerLabel();

                // If the timer reaches zero, the current player automatically loses the game
                if (timeLeft <= 0) {
                    timer.stop();
                    lossCondition.handleTimeoutLoss(gamePanel.turnManager.getCurrentPlayer().getPlayerId());
                    gamePanel.gameOver();
                }
            }
        });
    }

    /**
     * Starts the turn timer from the beginning.
     */
    public void start() {
        reset();
        timer.start();
    }

    /**
     * Stops the current timer without resetting the countdown.
     */
    public void stop() {
        timer.stop();
    }

    /**
     * Restarts the timer and resets the countdown.
     */
    public void restart() {
        reset();
        timer.restart();
    }

    /**
     * Resets the timer countdown to the full turn time limit.
     */
    public void reset() {
        this.timeLeft = turnTimeLimit;
        updateTimerLabel();
    }

    /**
     * Updates the timer label on screen with the current remaining time.
     */
    private void updateTimerLabel() {
        timerLabel.setText("Time Left: " + timeLeft + " sec");
    }
}
