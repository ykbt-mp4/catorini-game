package main;

import actors.Player;
import util.FontLoader;

import javax.swing.*;
import java.awt.*;

public class SidePanel extends JPanel {
    int panelWidth = 500;
    int panelHeight = 700;

    int imageWidth = 135;
    int imageHeight = 180;

    // Players
    private final Player player1;
    private final Player player2;

    public SidePanel(Player player1, Player player2) {

        this.setPreferredSize(new Dimension(panelWidth, panelHeight)); // fixed gameboard size
        this.setBackground(new Color(156, 212, 200));
        this.player1 = player1;
        this.player2 = player2;

        // Components
        FontLoader fontLoader = new FontLoader();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        if (player1.getGodCard() != null) {
        int x1 = (panelWidth - imageWidth)/2;
        int y1 = (panelHeight - imageHeight) / 4;
        player1.getGodCard().draw(g2, x1, y1, imageWidth, imageHeight, "Player 1");
    }

        if (player2.getGodCard() != null) {
        int x2 = (panelWidth - imageWidth)/2;
        int y2 = 3*((panelHeight - imageHeight) / 4);
        player2.getGodCard().draw(g2, x2, y2, imageWidth, imageHeight, "Player 2");
    }

}}
