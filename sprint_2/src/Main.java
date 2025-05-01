import javax.swing.*;
import java.awt.*;
import actors.Player;
import main.GamePanel;


public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("Santorini Game");
        window.setSize(1200, 700);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);

        Player player1 = new Player(1);
        Player player2 = new Player(2);

        GamePanel santorini = new GamePanel(player1, player2);

        santorini.setWorkerPos();

        window.add(santorini, BorderLayout.WEST);
        window.setVisible(true);

    }
}
