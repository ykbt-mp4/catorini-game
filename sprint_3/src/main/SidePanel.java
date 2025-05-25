package main;

import actors.Player;
import util.FontLoader;

import javax.swing.*;
import java.awt.*;
import java.io.OutputStream;
import java.io.PrintStream;

public class SidePanel extends JPanel {
    // Panel dimensions
    int panelWidth = 500;
    int panelHeight = 700;

    int startX = 0;
    int startY = 0;

    // Image dimensions
    int godImgWidth = 100;
    int godImgHeight = 145;
    int dialogBoxWidth = 300;
    int dialogBoxHeight = 100;
    int printBoxWidth = 400;
    int printBoxHeight = 300;

    int dialogX = godImgWidth + 25;
    int godY1 = (panelHeight - godImgHeight) / 14;
    int dialogY1 = godY1 + (godImgHeight - dialogBoxHeight) / 2;
    int godY2 = 5 * (panelHeight - godImgHeight) / 14;
    int dialogY2 = godY2 + (godImgHeight - dialogBoxHeight) / 2;

    // Players
    private final Player player1;
    private final Player player2;

    private final FontLoader fontLoader;

    public SidePanel(Player player1, Player player2)  {

        this.setPreferredSize(new Dimension(panelWidth, panelHeight)); // fixed side panel size
        this.player1 = player1;
        this.player2 = player2;
        this.fontLoader = new FontLoader();
        setLayout(null);

        JTextArea player1Text = new JTextArea(player1.getGodCard().getDescription());
        player1Text.setBounds(dialogX, dialogY1 + 25, dialogBoxWidth - 25, dialogBoxHeight - 20);
        player1Text.setOpaque(false);
        player1Text.setEditable(false);
        player1Text.setLineWrap(true);
        player1Text.setWrapStyleWord(true);
        player1Text.setFont(fontLoader.getPixelFont().deriveFont(14f));
        add(player1Text);

        // JTextArea for player 2
        JTextArea player2Text = new JTextArea(player2.getGodCard().getDescription());
        player2Text.setBounds(dialogX, dialogY2 + 25, dialogBoxWidth - 25, dialogBoxHeight - 20);
        player2Text.setOpaque(false);
        player2Text.setEditable(false);
        player2Text.setLineWrap(true);
        player2Text.setWrapStyleWord(true);
        player2Text.setFont(fontLoader.getPixelFont().deriveFont(14f));
        add(player2Text);

        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(fontLoader.getPixelFont().deriveFont(14f));
        scrollPane.setBounds(startX + 25, panelHeight/2 + 25, printBoxWidth - 50, printBoxHeight - 50);
        add(scrollPane);

        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) {
                textArea.append(String.valueOf((char) b));
            }
        };
        System.setOut(new PrintStream(out, true));
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setFont(fontLoader.getPixelFont().deriveFont(20f));

        Image dialogBox = new ImageIcon(getClass().getResource("/uiextra/dialog_box.png")).getImage();
        Image printBox = new ImageIcon(getClass().getResource("/uiextra/menupanel.png")).getImage();
        Image waterTile = new ImageIcon(getClass().getResource("/tiles/water.png")).getImage();

        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 7; col++) {
                int x = startX + col * 100;
                int y = startY + row * 100;
                g2.drawImage(waterTile, x, y, 100, 100, null);
            }
        }

        if (player1.getGodCard() != null) {
            Image godImage = new ImageIcon(getClass().getResource(player1.getGodCard().getImagePath())).getImage();
            g2.drawImage(godImage, startX, godY1, godImgWidth, godImgHeight, null);
            g2.drawImage(dialogBox, godImgWidth, dialogY1, dialogBoxWidth, dialogBoxHeight, null);
            g2.drawString("Player " + player1.getPlayerId() + ": " + player1.getGodCard().getName(), godImgWidth + 10, dialogY1);
        }

        if (player2.getGodCard() != null) {
            Image godImage = new ImageIcon(getClass().getResource(player2.getGodCard().getImagePath())).getImage();
            g2.drawImage(godImage, startX, godY2, godImgWidth, godImgHeight, null);
            g2.drawImage(dialogBox, godImgWidth, dialogY2, dialogBoxWidth, dialogBoxHeight, null);
            g2.drawString("Player " + player2.getPlayerId() + ": " + player2.getGodCard().getName(), godImgWidth + 10, dialogY2);
        }

        g2.drawImage(printBox, startX, panelHeight/2, printBoxWidth, printBoxHeight, null);
    }
}

