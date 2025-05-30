package util;

import main.GamePanel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TurnTimer {
    private final Timer timer;
    private int timeLeft;
    private final int turnTimeLimit;
    private final GamePanel gamePanel;
    private final JLabel timerLabel;
    private final LossCondition lossCondition;


    public TurnTimer(GamePanel gamePanel, int seconds, JLabel timerLabel) {
        this.turnTimeLimit = seconds;
        this.gamePanel = gamePanel;
        this.timerLabel = timerLabel;
        this.timeLeft = seconds;
        this.lossCondition = new LossCondition(gamePanel);

        this.timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                timeLeft--;
                updateTimerLabel();
                if (timeLeft <= 0) {
                    timer.stop();
                    lossCondition.handleTimeoutLoss(gamePanel.turnManager.getCurrentPlayer().getPlayerId());
                    gamePanel.gameOver();
                }
            }
        });
    }

    public void start() {
        reset();
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public void restart() {
        reset();
        timer.restart();
    }

    public void reset() {
        this.timeLeft = turnTimeLimit;
        updateTimerLabel();
    }


    private void updateTimerLabel() {
        timerLabel.setText("Time Left: " + timeLeft + " sec");
    }
}
