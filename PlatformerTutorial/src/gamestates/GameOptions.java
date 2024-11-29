package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.FlappyGame;
import ui.AudioOptions;
import ui.PauseButton;
import ui.UrmButton;
import utils.LoadSave;
import static utils.Constants.UI.URMButtons.*;

public class GameOptions extends State implements Statemethods {

	private AudioOptions audioOptions;
	private BufferedImage backgroundImg, optionsBackgroundImg;
	private int bgX, bgY, bgW, bgH;
	private UrmButton menuB;

	public GameOptions(FlappyGame flappyGame) {
		super(flappyGame);
		loadImgs();
		loadButton();
		audioOptions = flappyGame.getAudioOptions();
	}

	private void loadButton() {
		int menuX = (int) (387 * FlappyGame.SCALE);
		int menuY = (int) (325 * FlappyGame.SCALE);

		menuB = new UrmButton(menuX, menuY, URM_SIZE, URM_SIZE, 2);
	}

	private void loadImgs() {
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
		optionsBackgroundImg = LoadSave.GetSpriteAtlas(LoadSave.OPTIONS_MENU);

		bgW = (int) (optionsBackgroundImg.getWidth() * FlappyGame.SCALE);
		bgH = (int) (optionsBackgroundImg.getHeight() * FlappyGame.SCALE);
		bgX = FlappyGame.GAME_WIDTH / 2 - bgW / 2;
		bgY = (int) (33 * FlappyGame.SCALE);
	}

	@Override
	public void update() {
		menuB.update();
		audioOptions.update();
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(backgroundImg, 0, 0, FlappyGame.GAME_WIDTH, FlappyGame.GAME_HEIGHT, null);
		g.drawImage(optionsBackgroundImg, bgX, bgY, bgW, bgH, null);

		menuB.draw(g);
		audioOptions.draw(g);
	}

	public void mouseDragged(MouseEvent e) {
		audioOptions.mouseDragged(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (isIn(e, menuB)) {
			menuB.setMousePressed(true);
		} else
			audioOptions.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (isIn(e, menuB)) {
			if (menuB.isMousePressed())
				Gamestate.state = Gamestate.MENU;
		} else
			audioOptions.mouseReleased(e);
		menuB.resetBools();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		menuB.setMouseOver(false);

		if (isIn(e, menuB))
			menuB.setMouseOver(true);
		else
			audioOptions.mouseMoved(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			Gamestate.state = Gamestate.MENU;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	private boolean isIn(MouseEvent e, PauseButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}

}
