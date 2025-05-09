import actors.Player;
import main.GamePanel;
import main.SidePanel;
import util.GodCardAssigner;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        JFrame window = new JFrame("Santorini Game");
        window.setSize(1200, 700);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);


        Player player1 = new Player(1);
        Player player2 = new Player(2);

        GodCardAssigner.assignRandomGod(player1);
        GodCardAssigner.assignRandomGod(player2);
        System.out.println("Player 1: "+ player1.getGodCard().getName());
        System.out.println("Player 2: "+ player2.getGodCard().getName());

        GamePanel gamePanel = new GamePanel(player1, player2);
        SidePanel sidePanel = new SidePanel(player1, player2);

        // Initialise game
        gamePanel.setWorkerPos();
        gamePanel.gameStart();

        window.add(gamePanel, BorderLayout.WEST);
        window.add(sidePanel, BorderLayout.EAST);
        window.setVisible(true);




    }
}