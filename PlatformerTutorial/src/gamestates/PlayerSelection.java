package gamestates;

import entities.PlayerCharacter;
import main.FlappyGame;
import ui.MenuButton;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utils.Constants.ANI_SPEED;
import static utils.Constants.PlayerConstants.IDLE;

public class PlayerSelection extends State implements Statemethods {

    private BufferedImage backgroundImg, backgroundImgPink;
    private int menuX, menuY, menuWidth, menuHeight;
    private MenuButton playButton;
    private int playerIndex = 0;

    private CharacterAnimation[] characterAnimations;


    public PlayerSelection(FlappyGame flappyGame) {
        super(flappyGame);

        loadButtons();
        loadBackground();
        backgroundImgPink = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);

        loadCharAnimations();
    }

    private void loadCharAnimations() {
        characterAnimations = new CharacterAnimation[4];
        int i = 0;
        characterAnimations[i++] = new CharacterAnimation(PlayerCharacter.EAGLE);
        characterAnimations[i++] = new CharacterAnimation(PlayerCharacter.BAT);
        characterAnimations[i++] = new CharacterAnimation(PlayerCharacter.YELLOWBIRD);
        characterAnimations[i++] = new CharacterAnimation(PlayerCharacter.REDBIRD);
    }

    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
        menuWidth = (int) (backgroundImg.getWidth() * FlappyGame.SCALE);
        menuHeight = (int) (backgroundImg.getHeight() * FlappyGame.SCALE);
        menuX = FlappyGame.GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int) (25 * FlappyGame.SCALE);
    }

    private void loadButtons() {

        playButton = new MenuButton(FlappyGame.GAME_WIDTH / 2, (int) (340 * FlappyGame.SCALE), 0, Gamestate.PLAYING);

    }

    @Override
    public void update() {
        playButton.update();
        for (CharacterAnimation ca : characterAnimations)
            ca.update();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImgPink, 0, 0, FlappyGame.GAME_WIDTH, FlappyGame.GAME_HEIGHT, null);
        g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);

        playButton.draw(g);

        //Center
        drawChar(g, playerIndex, menuX + menuWidth / 2, menuY + menuHeight / 2);

        //Left
        drawChar(g, playerIndex - 1, menuX, menuY + menuHeight / 2);

        //Left
        drawChar(g, playerIndex + 1, menuX + menuWidth, menuY + menuHeight / 2);

    }

    private void drawChar(Graphics g, int playerIndex, int x, int y) {
        if (playerIndex < 0)
            playerIndex = characterAnimations.length - 1; // Count goes from 0 to X to -1 to get char count.
        else if (playerIndex >= characterAnimations.length)
            playerIndex = 0;
        characterAnimations[playerIndex].draw(g, x, y);
    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (isIn(e, playButton))
            playButton.setMousePressed(true);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if (isIn(e, playButton)) {
            if (playButton.isMousePressed()) {
                flappyGame.getPlaying().setPlayerCharacter(characterAnimations[playerIndex].getPc());
                flappyGame.getAudioPlayer().setLevelSong(flappyGame.getPlaying().getLevelManager().getLevelIndex());
                playButton.applyGamestate();
            }
        }

        resetButtons();
    }

    private void resetButtons() {
        playButton.resetBools();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        playButton.setMouseOver(false);


        if (isIn(e, playButton))
            playButton.setMouseOver(true);


    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            deltaIndex(1);
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            deltaIndex(-1);
    }

    private void deltaIndex(int i) {
        playerIndex += i;
        if (playerIndex < 0)
            playerIndex = characterAnimations.length - 1;
        else if (playerIndex >= characterAnimations.length)
            playerIndex = 0;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    public class CharacterAnimation {
        private final PlayerCharacter pc;
        private int aniTick, aniIndex;
        private final BufferedImage[][] animations;
        private int scale;

        public CharacterAnimation(PlayerCharacter pc) {
            this.pc = pc;
            this.scale = (int) (FlappyGame.SCALE + 6);
            animations = LoadSave.loadAnimations(pc);
        }

        public void draw(Graphics g, int drawX, int drawY) {
            // System.out.println("Drawing character animation x = " + drawX + " and y = " + drawY + " aniIndex = " + aniIndex);
            g.drawImage(animations[pc.getRowIndex(IDLE)][aniIndex],
                    drawX - pc.spriteW * scale / 3,
                    drawY - pc.spriteH * scale / 3,
                    pc.spriteW * scale / 4,
                    pc.spriteH * scale / 4,
                    null);
        }

        public void update() {
            aniTick++;
            if (aniTick >= ANI_SPEED) {
                aniTick = 0;
                aniIndex++;
                if (aniIndex >= pc.getSpriteAmount(IDLE)) {
                    aniIndex = 0;

                }
            }
        }

        public PlayerCharacter getPc() {
            return pc;
        }
    }
}
