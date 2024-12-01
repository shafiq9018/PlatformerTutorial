import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class CenterSpriteTool extends JPanel implements KeyListener {
    private BufferedImage spriteSheet;
    private BufferedImage[] sprites;

    private int currentFrame = 0;
//    private static final int SPRITE_WIDTH = 180;  // Width of each sprite
//    private static final int SPRITE_HEIGHT = 195; // Height of each sprite
    // Adjust the SPRITE_WIDTH until the bird stops scrolling and is still.
    // Then press D to add the offset.
    private static final int SPRITE_WIDTH = 58;  // Width of each sprite
    private static final int SPRITE_HEIGHT = 40; // Height of each sprite

    private static final int TOTAL_SPRITES = 3; // Total number of sprites
    private static final int FRAME_DELAY = 100; // Delay between frames in milliseconds
    private int xPosition = 0; // Initial X position of the sprite
    private int yPosition = 0; // Initial Y position of the sprite
    private int moveSpeed = 1; // Speed of movement
    private boolean keyRel = true;
    private int findOffset = 0; // Initially calculated by Shafiq. You can start with zero.

    public CenterSpriteTool() {
        try {
            // Load the sprite sheet
            // Note if you get an error reading the file load it from your poject root
            // remove path / or res/ just do "file.png"
            spriteSheet = ImageIO.read(new File("YelloBirdsColorsx64.png")); // Replace with your file path
            // Extract individual sprites from the sprite sheet
            sprites = new BufferedImage[TOTAL_SPRITES];
            for (int i = 0; i < TOTAL_SPRITES; i++) {

                System.out.println(i*SPRITE_WIDTH);
                sprites[i] = spriteSheet.getSubimage((i)  * SPRITE_WIDTH + findOffset, 0, SPRITE_WIDTH, SPRITE_HEIGHT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Timer to update animation frame
        Timer timer = new Timer(FRAME_DELAY, e -> {
            currentFrame = (currentFrame + 1) % TOTAL_SPRITES;
            repaint();
        });
        timer.start();

        // Add key listener for movement
        setFocusable(true);
        addKeyListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // System.out.println("xPosition: " + xPosition);
        if (xPosition < 0) xPosition = 0;
        if (xPosition > SPRITE_WIDTH) xPosition = SPRITE_WIDTH;

        findOffset = xPosition;

        for (int i = 0; i < TOTAL_SPRITES; i++) {
            sprites[i] = spriteSheet.getSubimage(i * SPRITE_WIDTH + findOffset, 0, SPRITE_WIDTH, SPRITE_HEIGHT);
        }
        super.paintComponent(g);
        if (sprites != null && sprites[currentFrame] != null) {
            g.drawImage(sprites[currentFrame], 50, 50, SPRITE_WIDTH, SPRITE_HEIGHT, null);
        }
     }

    @Override
    public void keyTyped(KeyEvent e) {
        // No action needed on key typed
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_A:
                xPosition = xPosition - 1; // Move left
                System.out.println("Left key pressed");
                System.out.println(findOffset);
                keyRel = false;
                break;
            case KeyEvent.VK_D:
                xPosition = xPosition + 1; // Move right
                System.out.println("Right key pressed");
                System.out.println(findOffset);
                keyRel = false;
                break;
            case KeyEvent.VK_SPACE:
                System.out.println("SPACE Pressed");
                break;
            case KeyEvent.VK_ESCAPE:
                System.out.println("ESCAPE pressed");
                break;
        }
        repaint(); // Update the screen after movement
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_A:
                keyRel = true;
                break;
            case KeyEvent.VK_D:
                keyRel = true;
                break;
            case KeyEvent.VK_SPACE:
                break;
            case KeyEvent.VK_ESCAPE:
                break;
        }
        repaint();
        // No action needed on key release
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sprite Animation");
        CenterSpriteTool animation = new CenterSpriteTool();
        frame.add(animation);
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
