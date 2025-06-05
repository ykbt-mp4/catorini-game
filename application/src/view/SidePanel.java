package view;

import actors.Player;
import util.FontLoader;

import javax.swing.*;
import java.awt.*;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * The SidePanel class displays player information, god card descriptions,
 * and a print console on the right side of the game window.
 */
public class SidePanel extends JPanel {
    // Panel dimensions
    int panelWidth = 500;
    int panelHeight = 700;

    // Image dimensions
    int godImgWidth = 100;
    int godImgHeight = 145;
    int dialogBoxWidth = 300;
    int dialogBoxHeight = 100;
    int printBoxWidth = 400;
    int printBoxHeight = 300;

    // Y-coordinates for god card images and dialog boxes
    int godY1 = (panelHeight - godImgHeight) / 14;
    int dialogY1 = godY1 + (godImgHeight - dialogBoxHeight) / 2;
    int godY2 = 5 * (panelHeight - godImgHeight) / 14;
    int dialogY2 = godY2 + (godImgHeight - dialogBoxHeight) / 2;

    // Associated player instances
    private final Player player1;
    private final Player player2;
    private final FontLoader fontLoader;

    /**
     * Constructs a new SidePanel with the given players.
     * @param player1 the first player
     * @param player2 the second player
     */
    public SidePanel(Player player1, Player player2)  {
        this.setPreferredSize(new Dimension(panelWidth, panelHeight)); // fixed side panel size
        this.setBackground(new Color(156, 212, 200));
        this.player1 = player1;
        this.player2 = player2;
        this.fontLoader = new FontLoader();
        setLayout(null);

        // Text area for player 1's god card description
        setupPlayerTextArea(player1,godImgWidth + 25, dialogY1 + 25);
        // Text area for player 2's god card description
        setupPlayerTextArea(player2, godImgWidth + 25, dialogY2 + 25);

        // Text area for redirected console output
        setupConsoleOutput();
    }

    /**
     * Sets up the god card description text area for a player.
     * @param player the player
     * @param x horizontal position
     * @param y vertical position
     */
    private void setupPlayerTextArea(Player player, int x, int y) {
        JTextArea textArea = new JTextArea(player.getGodCard().getDescription());
        textArea.setBounds(x, y, dialogBoxWidth - 25, dialogBoxHeight - 20);
        textArea.setOpaque(false);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(fontLoader.getPixelFont().deriveFont(14f));
        add(textArea);
    }


    /**
     * Sets up the scrollable output area that redirects System.out.
     */
    private void setupConsoleOutput() {
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(fontLoader.getPixelFont().deriveFont(14f));
        scrollPane.setBounds(25, panelHeight/2 + 25, printBoxWidth - 50, printBoxHeight - 50);
        add(scrollPane);

        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                textArea.append(String.valueOf((char) b));
            }
        }, true));
    }

    /**
     * Custom rendering for the background, god cards, dialogue boxes, and the print box.
     * @param g the Graphics context
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(fontLoader.getPixelFont().deriveFont(20f));

        int startX = 0;
        int startY = 0;

        // Load UI images
        Image dialogBox = new ImageIcon(getClass().getResource("/uiextra/dialog_box.png")).getImage();
        Image printBox = new ImageIcon(getClass().getResource("/uiextra/leaderboard.png")).getImage();
        Image waterTile = new ImageIcon(getClass().getResource("/tiles/water.png")).getImage();

        // Draw background water tiles
        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 7; col++) {
                int x = startX + col * 100;
                int y = startY + row * 100;
                g2.drawImage(waterTile, x, y, 100, 100, null);
            }
        }

        // Render player 1's god card and dialog
        if (player1.getGodCard() != null) {
            drawPlayerElements(g2, player1, startX, godY1, dialogY1, dialogBox);
        }
        // Render player 2's god card and dialog
        if (player2.getGodCard() != null) {
            drawPlayerElements(g2, player2, startX, godY2, dialogY2, dialogBox);
        }
        // Draw the print box (System.out redirected output)
        g2.drawImage(printBox, startX, panelHeight / 2, printBoxWidth, printBoxHeight, null);
    }

    /**
     * Helper method that draws a player's god card image and dialogue box on the panel.
     * @param g2 the Graphics2D context
     * @param player the player
     * @param godX x-position of the god card
     * @param godY y-position of the god card
     * @param dialogY y-position of the dialogue box
     * @param dialogBox image of the dialogue box
     */
    private void drawPlayerElements(Graphics2D g2, Player player,
                                    int godX, int godY,
                                    int dialogY,
                                    Image dialogBox) {
        Image godImage = new ImageIcon(getClass().getResource(player.getGodCard().getImagePath())).getImage();
        g2.drawImage(godImage, godX, godY, godImgWidth, godImgHeight, null);
        g2.drawImage(dialogBox, godImgWidth, dialogY, dialogBoxWidth, dialogBoxHeight, null);
        g2.drawString(String.format("Player %d (%s): %s",
                        player.getPlayerId(),
                        player.getPlayerName(),
                        player.getGodCard().getName()),
                godImgWidth + 10, dialogY);
    }
}