package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.FlappyGame;
import ui.MenuButton;
import utils.LoadSave;

public class Menu extends State implements Statemethods {

    private MenuButton[] buttons = new MenuButton[4];
    private BufferedImage backgroundImg, backgroundImgFlappyBird;
    private int menuX, menuY, menuWidth, menuHeight;

    public Menu(FlappyGame flappyGame) {
        super(flappyGame);
        loadButtons();
        loadBackground();
        backgroundImgFlappyBird = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);

    }

    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
        menuWidth = (int) (backgroundImg.getWidth() * FlappyGame.SCALE);
        menuHeight = (int) (backgroundImg.getHeight() * FlappyGame.SCALE);
        menuX = FlappyGame.GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int) (25 * FlappyGame.SCALE);
    }

    private void loadButtons() {
        buttons[0] = new MenuButton(FlappyGame.GAME_WIDTH / 2, (int) (130 * FlappyGame.SCALE), 0, Gamestate.PLAYER_SELECTION);
        buttons[1] = new MenuButton(FlappyGame.GAME_WIDTH / 2, (int) (200 * FlappyGame.SCALE), 1, Gamestate.OPTIONS);
        buttons[2] = new MenuButton(FlappyGame.GAME_WIDTH / 2, (int) (270 * FlappyGame.SCALE), 3, Gamestate.CREDITS);
        buttons[3] = new MenuButton(FlappyGame.GAME_WIDTH / 2, (int) (340 * FlappyGame.SCALE), 2, Gamestate.QUIT);
    }

    @Override
    public void update() {
        for (MenuButton mb : buttons)
            mb.update();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImgFlappyBird, 0, 0, FlappyGame.GAME_WIDTH, FlappyGame.GAME_HEIGHT, null);
        g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);

        for (MenuButton mb : buttons)
            mb.draw(g);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                mb.setMousePressed(true);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                if (mb.isMousePressed())
                    mb.applyGamestate();
                if (mb.getState() == Gamestate.PLAYING)
                    flappyGame.getAudioPlayer().setLevelSong(flappyGame.getPlaying().getLevelManager().getLevelIndex());
                break;
            }
        }
        resetButtons();
    }

    private void resetButtons() {
        for (MenuButton mb : buttons)
            mb.resetBools();

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton mb : buttons)
            mb.setMouseOver(false);

        for (MenuButton mb : buttons)
            if (isIn(e, mb)) {
                mb.setMouseOver(true);
                break;
            }

    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

}