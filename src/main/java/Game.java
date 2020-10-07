package src.main.java;

import java.awt.EventQueue;
import javax.swing.JFrame;

import src.main.java.bfs.BFS;

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

        BFS bfs = new BFS();
        bfs.insert(5);
        bfs.insert(1);
        bfs.insert(0);
        bfs.insert(3);
        bfs.insert(8);
        bfs.insert(9);
        bfs.insert(11);
        bfs.levelOrder();

        EventQueue.invokeLater(() -> {

            Game ex = new Game();
            ex.setVisible(true);
        });
    }
}