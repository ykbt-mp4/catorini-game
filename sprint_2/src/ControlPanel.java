package sprint_2.src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class ControlPanel extends JPanel {

    public  final int controlWidth = 450;
    public final int controlHeight = 750;

    private final int paddingTiles = 1;
    public final int backgroundTiles = 7; 
    public final int userTiles = 10;
    public final int totalTiles = userTiles + paddingTiles * 2; // 7
    public final int tileSize = controlHeight / totalTiles; 

    public ControlManager controlM;

    public ControlPanel() {
        this.setPreferredSize(new Dimension(controlWidth, controlHeight)); 
        this.setBackground(new Color(156, 212, 200));

        controlM = new ControlManager(this);
    
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        controlM.draw(g2);
    }
}

