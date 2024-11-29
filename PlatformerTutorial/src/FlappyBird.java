//jack's comment
// David was here
//Eli was here
//public class FlappyBird {
//
//    //TESTING TESTING TESTING
//    public static void main(String[] args) {
//        System.out.println("Hello World!");
//    }
//
//
//
//}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FlappyBird extends JPanel implements ActionListener {

    private final int BIRD_SIZE = 20;
    private final int WINDOW_WIDTH = 800;
    private final int WINDOW_HEIGHT = 600;
    private final float GRAVITY = 0.5f;
    private final float JUMP_STRENGTH = -10f;

    private Timer timer;
    private Bird bird;
    public boolean spaceReleased = true;

    public FlappyBird() {
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("keyReleased");
                spaceReleased = true;
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (spaceReleased) {
                        bird.setVelocity(JUMP_STRENGTH);
                    }
                    spaceReleased = false;
                }
            }
        });

        bird = new Bird(WINDOW_WIDTH / 2 - BIRD_SIZE / 2, WINDOW_HEIGHT / 2 - BIRD_SIZE / 2);
        timer = new Timer(16, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        bird.update();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect((int) bird.getX(), (int) bird.getY(), BIRD_SIZE, BIRD_SIZE);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Flappy Bird");
        FlappyBird game = new FlappyBird();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}


