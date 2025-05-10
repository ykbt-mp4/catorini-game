package main;

import actors.Player;
import tile.TileManager;
import util.FontLoader;

import javax.swing.*;
import java.awt.*;

public class SidePanel extends JPanel {
    int panelWidth = 500;
    int panelHeight = 700;

    int godImgWidth = 135;
    int godImgHeight = 180;

    int playerImgWidth = 100;
    int playerImgHeight = 100;

    // Players
    private final Player player1;
    private final Player player2;
    private final TileManager tileManager;
    private final FontLoader fontLoader;

    public SidePanel(Player player1, Player player2, TileManager tileManager)  {

        this.setPreferredSize(new Dimension(panelWidth, panelHeight)); // fixed gameboard size
        this.setBackground(new Color(156, 212, 200));
        this.player1 = player1;
        this.player2 = player2;

        this.tileManager = tileManager;
        this.fontLoader = new FontLoader();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        tileManager.drawWaterTile(g2);
        g2.setFont(fontLoader.getPixelFont());

        if (player1.getGodCard() != null) {
            int pX1 = (panelWidth - godImgWidth)/4 ;
            int pY1 = (panelHeight - playerImgHeight) / 4;

            int x1 = (panelWidth - godImgWidth)/2;
            int y1 = (panelHeight - godImgHeight) / 4;

            player1.getGodCard().draw(g2, x1, y1, godImgWidth, godImgHeight);
            player1.draw(g2, pX1, pY1, playerImgWidth, playerImgHeight);
            g2.drawString("Player: " + player1.getPlayerId(), pX1, pY1);
    }

        if (player2.getGodCard() != null) {
            int pX2 = (panelWidth - godImgWidth)/4;
            int pY2 = 3* (panelHeight - playerImgHeight) / 4;

            int x2 = (panelWidth - godImgWidth)/2;
            int y2 = 3*((panelHeight - godImgHeight) / 4);
            player2.getGodCard().draw(g2, x2, y2, godImgWidth, godImgHeight);
            player2.draw(g2,pX2, pY2, playerImgWidth, playerImgHeight);
            g2.drawString("Player: " + player2.getPlayerId(), pX2, pY2);
    }


}}
