package src.main.java;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Game extends JFrame {

    public Game() {

        initUI();
    }

    private void initUI() {

        add(new Board());

        setTitle("Pacman");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(380, 420);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            Game ex = new Game();
            ex.setVisible(true);
        });
    }
}