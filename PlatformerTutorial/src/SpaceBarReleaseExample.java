import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SpaceBarReleaseExample extends JFrame {

    public SpaceBarReleaseExample() {
        setTitle("Space Bar Release Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Not used for this example
            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("Space bar pressed!");
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    System.out.println(KeyEvent.VK_SPACE);
                    System.out.println("Space bar released!");
                }
            }
        });

        add(panel);
        panel.setFocusable(true);
        panel.requestFocusInWindow();

        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new SpaceBarReleaseExample();
    }
}