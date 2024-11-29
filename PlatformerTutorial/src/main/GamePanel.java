package main;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import inputs.KeyboardInputs;
import inputs.MouseInputs;
import static main.FlappyGame.GAME_HEIGHT;
import static main.FlappyGame.GAME_WIDTH;

public class GamePanel extends JPanel {

	private MouseInputs mouseInputs;
	private FlappyGame flappyGame;

	public GamePanel(FlappyGame flappyGame) {
		mouseInputs = new MouseInputs(this);
		this.flappyGame = flappyGame;
		setPanelSize();
		addKeyListener(new KeyboardInputs(this));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
	}

	private void setPanelSize() {
		Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
		setPreferredSize(size);
	}

	public void updateGame() {

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		flappyGame.render(g);
	}

	public FlappyGame getGame() {
		return flappyGame;
	}

}