import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class ScrollingForeground extends JPanel implements ActionListener {
    // Screen dimensions
    private static final int WIDTH = 800;
    private static final int HEIGHT = 400; // Lower quarter size
    private static final int SPEED = 2;    // Scrolling speed

    // Foreground image
    private Image foreground;

    // Scroll position
    private int xPosition = 0;

    // Timer for animation
    private Timer timer;

    public ScrollingForeground() {
        // Load the image
        try {
            foreground = ImageIO.read(new File("res/scene_chatGPT.png")); // Ensure correct file path
            foreground = foreground.getScaledInstance(WIDTH * 2, HEIGHT, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            System.err.println("Image not found: " + e.getMessage());
            System.exit(1); // Exit if image is not loaded
        }

        // Start animation timer
        timer = new Timer(16, this); // ~60 FPS
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the scrolling foreground
        g.drawImage(foreground, xPosition, 0, null);
        g.drawImage(foreground, xPosition + WIDTH, 0, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Update scroll position
        xPosition -= SPEED;

        // Reset position for seamless scrolling
        if (xPosition <= -WIDTH) {
            xPosition = 0;
        }

        // Repaint the panel
        repaint();
    }

    public static void main(String[] args) {
        // Set up JFrame
        JFrame frame = new JFrame("Scrolling Foreground");
        ScrollingForeground panel = new ScrollingForeground();

        frame.add(panel);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
