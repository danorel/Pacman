package src.main.java;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private Dimension
            dimension;

    private final Font
            smallFont = new Font("Helvetica", Font.BOLD, 14);

    private Image ii;

    private Color
            colorMaze;
    private final Color
            dotColor = new Color(192, 192, 0);

    private boolean inGame = false;

    private final int
            INT_BLOCK_SIZE = 24,
            INT_BLOCKS = 15,
            INT_SCREEN_SIZE = INT_BLOCKS * INT_BLOCK_SIZE,
            INT_PACMAN_DELAY = 2,
            INT_PACMAN_SPEED = 6,
            INT_PACMAN_ANIMATIONS = 4;

    private int
            pacAnimCount = INT_PACMAN_DELAY,
            pacAnimDir = 1,
            intPacmanPosition = 0,
            intPacmanLeft,
            intSpeed = 3,
            intScore;

    private final int
            INT_MAX_SPEED = 6;

    private Image
            pacman,
            pacman2up,
            pacman2left,
            pacman2right,
            pacman2down;

    private Image
            pacman3up,
            pacman3down,
            pacman3left,
            pacman3right;

    private Image
            pacman4up,
            pacman4down,
            pacman4left,
            pacman4right;

    private int
            pacman_x,
            pacman_y,
            pacmand_x,
            pacmand_y;

    private int
            req_dx,
            req_dy,
            view_dx,
            view_dy;

    private final short arrDataLevel[] = {
            19, 26, 26, 26, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 22,
            21, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
            21, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
            21, 0, 0, 0, 17, 16, 16, 24, 16, 16, 16, 16, 16, 16, 20,
            17, 18, 18, 18, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 20,
            17, 16, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 16, 24, 20,
            25, 16, 16, 16, 24, 24, 28, 0, 25, 24, 24, 16, 20, 0, 21,
            1, 17, 16, 20, 0, 0, 0, 0, 0, 0, 0, 17, 20, 0, 21,
            1, 17, 16, 16, 18, 18, 22, 0, 19, 18, 18, 16, 20, 0, 21,
            1, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 20, 0, 21,
            1, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 20, 0, 21,
            1, 17, 16, 16, 16, 16, 16, 18, 16, 16, 16, 16, 20, 0, 21,
            1, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0, 21,
            1, 25, 24, 24, 24, 24, 24, 24, 24, 24, 16, 16, 16, 18, 20,
            9, 8, 8, 8, 8, 8, 8, 8, 8, 8, 25, 24, 24, 24, 28
    };

    private short[] arrDataScreen;
    private Timer timer;

    Board() {

        initImages();
        initVariables();
        initBoard();
    }

    private void initImages() {

        pacman = new ImageIcon("images/pacman.png").getImage();
        pacman2up = new ImageIcon("images/up1.png").getImage();
        pacman3up = new ImageIcon("images/up2.png").getImage();
        pacman4up = new ImageIcon("images/up3.png").getImage();
        pacman2down = new ImageIcon("images/down1.png").getImage();
        pacman3down = new ImageIcon("images/down2.png").getImage();
        pacman4down = new ImageIcon("images/down3.png").getImage();
        pacman2left = new ImageIcon("images/left1.png").getImage();
        pacman3left = new ImageIcon("images/left2.png").getImage();
        pacman4left = new ImageIcon("images/left3.png").getImage();
        pacman2right = new ImageIcon("images/right1.png").getImage();
        pacman3right = new ImageIcon("images/right2.png").getImage();
        pacman4right = new ImageIcon("images/right3.png").getImage();
    }

    private void initVariables() {

        arrDataScreen = new short[INT_BLOCKS * INT_BLOCKS];
        colorMaze = new Color(5, 100, 5);
        dimension = new Dimension(400, 400);

        timer = new Timer(20, this);
        timer.start();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.black);
    }

    @Override
    public void addNotify() {
        super.addNotify();

        initGame();
    }

    private void doAnim() {

        pacAnimCount--;

        if (pacAnimCount <= 0) {
            pacAnimCount = INT_PACMAN_DELAY;
            intPacmanPosition = intPacmanPosition + pacAnimDir;

            if (intPacmanPosition == (INT_PACMAN_ANIMATIONS - 1) || intPacmanPosition == 0) {
                pacAnimDir = -pacAnimDir;
            }
        }
    }

    private void playGame(Graphics2D g2d) {

        movePacman();
        drawPacman(g2d);
        checkMaze();
    }

    private void showIntroScreen(Graphics2D g2d) {

        g2d.setColor(new Color(0, 32, 48));
        g2d.fillRect(50, INT_SCREEN_SIZE / 2 - 30, INT_SCREEN_SIZE - 100, 50);
        g2d.setColor(Color.white);
        g2d.drawRect(50, INT_SCREEN_SIZE / 2 - 30, INT_SCREEN_SIZE - 100, 50);

        String      message = "Press s to start.";
        Font        font    = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metrics = this.getFontMetrics(font);

        g2d.setColor(Color.white);
        g2d.setFont(font);
        g2d.drawString(message, (INT_SCREEN_SIZE - metrics.stringWidth(message)) / 2, INT_SCREEN_SIZE / 2);
    }

    private void drawScore(Graphics2D g) {

        int i;
        String s;

        g.setFont(smallFont);
        g.setColor(new Color(96, 128, 255));
        s = "Score: " + intScore;
        g.drawString(s, INT_SCREEN_SIZE / 2 + 96, INT_SCREEN_SIZE + 16);

        for (i = 0; i < intPacmanLeft; i++) {
            g.drawImage(
                    pacman3left,
                    i * 28 + 8,
                    INT_SCREEN_SIZE + 1,
                    this
            );
        }
    }

    private void checkMaze() {

        short i = 0;
        boolean finished = true;

        while (i < INT_BLOCKS * INT_BLOCKS && finished) {

            if ((arrDataScreen[i] & 48) != 0)
                finished = false;

            i++;
        }

        if (finished) {

            intScore += 50;

            if (intSpeed < INT_MAX_SPEED)
                intSpeed++;

            initLevel();
        }
    }

    private void movePacman() {

        int pos;
        short ch;

        if (req_dx == -pacmand_x && req_dy == -pacmand_y) {
            pacmand_x = req_dx;
            pacmand_y = req_dy;
            view_dx = pacmand_x;
            view_dy = pacmand_y;
        }

        if (pacman_x % INT_BLOCK_SIZE == 0 && pacman_y % INT_BLOCK_SIZE == 0) {
            pos = pacman_x / INT_BLOCK_SIZE + INT_BLOCKS * (int) (pacman_y / INT_BLOCK_SIZE);
            ch = arrDataScreen[pos];

            if ((ch & 16) != 0) {
                arrDataScreen[pos] = (short) (ch & 15);
                intScore++;
            }

            if (req_dx != 0 || req_dy != 0) {
                if (
                        !((req_dx == -1 && req_dy == 0 && (ch & 1) != 0)
                        || (req_dx == 1 && req_dy == 0 && (ch & 4) != 0)
                        || (req_dx == 0 && req_dy == -1 && (ch & 2) != 0)
                        || (req_dx == 0 && req_dy == 1 && (ch & 8) != 0))) {
                    pacmand_x = req_dx;
                    pacmand_y = req_dy;
                    view_dx = pacmand_x;
                    view_dy = pacmand_y;
                }
            }

            // Check for standstill
            if ((pacmand_x == -1 && pacmand_y == 0 && (ch & 1) != 0)
                    || (pacmand_x == 1 && pacmand_y == 0 && (ch & 4) != 0)
                    || (pacmand_x == 0 && pacmand_y == -1 && (ch & 2) != 0)
                    || (pacmand_x == 0 && pacmand_y == 1 && (ch & 8) != 0)) {
                pacmand_x = 0;
                pacmand_y = 0;
            }
        }
        pacman_x = pacman_x + INT_PACMAN_SPEED * pacmand_x;
        pacman_y = pacman_y + INT_PACMAN_SPEED * pacmand_y;
    }

    private void drawPacman(Graphics2D g2d) {

        if (view_dx == -1)
            drawPacmanLeft(g2d);
        else if (view_dx == 1)
            drawPacmanRight(g2d);
        else if (view_dy == -1)
            drawPacmanUp(g2d);
        else
            drawPacmanDown(g2d);
    }

    private void drawPacmanSketch(
            Graphics2D g2d,
            Image pacmanPosition1,
            Image pacmanPosition2,
            Image pacmanPosition3) {

        switch (intPacmanPosition) {
            case 1:
                g2d.drawImage(pacmanPosition1, pacman_x + 1, pacman_y + 1, this);
                break;
            case 2:
                g2d.drawImage(pacmanPosition2, pacman_x + 1, pacman_y + 1, this);
                break;
            case 3:
                g2d.drawImage(pacmanPosition3, pacman_x + 1, pacman_y + 1, this);
                break;
            default:
                g2d.drawImage(pacman, pacman_x + 1, pacman_y + 1, this);
                break;
        }
    }

    private void drawPacmanUp(Graphics2D g2d) {

        drawPacmanSketch(g2d, pacman2up, pacman3up, pacman4up);
    }

    private void drawPacmanDown(Graphics2D g2d) {

        drawPacmanSketch(g2d, pacman2down, pacman3down, pacman4down);
    }

    private void drawPacmanLeft(Graphics2D g2d) {

        drawPacmanSketch(g2d, pacman2left, pacman3left, pacman4left);
    }

    private void drawPacmanRight(Graphics2D g2d) {

        drawPacmanSketch(g2d, pacman2right, pacman3right, pacman4right);
    }

    private void drawMaze(Graphics2D g2d) {

        short i = 0;
        int x, y;

        for (y = 0; y < INT_SCREEN_SIZE; y += INT_BLOCK_SIZE) {
            for (x = 0; x < INT_SCREEN_SIZE; x += INT_BLOCK_SIZE) {

                g2d.setColor(colorMaze);
                g2d.setStroke(new BasicStroke(2));

                if ((arrDataScreen[i] & 1) != 0) {
                    g2d.drawLine(
                            x,
                            y,
                            x,
                            y + INT_BLOCK_SIZE - 1
                    );
                }

                if ((arrDataScreen[i] & 2) != 0) {
                    g2d.drawLine(
                            x,
                            y,
                            x + INT_BLOCK_SIZE - 1,
                            y
                    );
                }

                if ((arrDataScreen[i] & 4) != 0) {
                    g2d.drawLine(
                            x + INT_BLOCK_SIZE - 1,
                            y,
                            x + INT_BLOCK_SIZE - 1,
                            y + INT_BLOCK_SIZE - 1
                    );
                }

                if ((arrDataScreen[i] & 8) != 0) {
                    g2d.drawLine(
                            x,
                            y + INT_BLOCK_SIZE - 1,
                            x + INT_BLOCK_SIZE - 1,
                            y + INT_BLOCK_SIZE - 1
                    );
                }

                if ((arrDataScreen[i] & 16) != 0) {
                    g2d.setColor(dotColor);
                    g2d.fillRect(x + 11, y + 11, 2, 2);
                }

                i++;
            }
        }
    }

    private void initGame() {

        intScore      = 0;
        intSpeed      = 3;
        intPacmanLeft = 3;
        initLevel();
    }

    private void initLevel() {

        int i;

        for (i = 0; i < INT_BLOCKS * INT_BLOCKS; i++)
            arrDataScreen[i] = arrDataLevel[i];

        continueLevel();
    }

    private void continueLevel() {

        req_dx    = 0;
        req_dy    = 0;
        view_dx   = -1;
        view_dy   = 0;
        pacmand_x = 0;
        pacmand_y = 0;
        pacman_x  = 7 * INT_BLOCK_SIZE;
        pacman_y  = 11 * INT_BLOCK_SIZE;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, dimension.width, dimension.height);

        drawMaze(g2d);
        drawScore(g2d);
        doAnim();

        if (inGame)
            playGame(g2d);
        else
            showIntroScreen(g2d);

        g2d.drawImage(ii, 5, 5, this);
        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }

    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent evt) {

            int key = evt.getKeyCode();

            if (inGame) {
                if (key == KeyEvent.VK_LEFT) {
                    req_dx = -1;
                    req_dy = 0;
                } else if (key == KeyEvent.VK_RIGHT) {
                    req_dx = 1;
                    req_dy = 0;
                } else if (key == KeyEvent.VK_UP) {
                    req_dx = 0;
                    req_dy = -1;
                } else if (key == KeyEvent.VK_DOWN) {
                    req_dx = 0;
                    req_dy = 1;
                } else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                    inGame = false;
                } else if (key == KeyEvent.VK_PAUSE) {
                    if (timer.isRunning())
                        timer.stop();
                    else
                        timer.start();
                }
            } else {
                if (key == 's' || key == 'S') {
                    inGame = true;
                    initGame();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent evt) {

            int key = evt.getKeyCode();

            if (key == Event.LEFT || key == Event.RIGHT
                    || key == Event.UP || key == Event.DOWN) {
                req_dx = 0;
                req_dy = 0;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent evt) {

        repaint();
    }
}