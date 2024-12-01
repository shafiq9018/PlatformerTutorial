package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;

import entities.EnemyManager;
import entities.Player;
import entities.PlayerCharacter;
import levels.LevelManager;
import main.FlappyGame;
import objects.ObjectManager;
import ui.GameCompletedOverlay;
import ui.GameOverOverlay;
import ui.LevelCompletedOverlay;
import ui.PauseOverlay;
import utils.LoadSave;
import effects.DialogueEffect;
import effects.Rain;

import static utils.Constants.Environment.*;
import static utils.Constants.Dialogue.*;

public class Playing extends State implements Statemethods {

    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private ObjectManager objectManager;
    private PauseOverlay pauseOverlay;
    private GameOverOverlay gameOverOverlay;
    private GameCompletedOverlay gameCompletedOverlay;
    private LevelCompletedOverlay levelCompletedOverlay;
    private Rain rain;

    private boolean paused = false;

    private int xLvlOffset;

    private int leftBorder = (int) (0.2 * FlappyGame.GAME_WIDTH);
    private int rightBorder = (int) ((FlappyGame.GAME_WIDTH) / 2); // This determines the how far the bird travels to the right.
    private int maxLvlOffsetX;

    private BufferedImage backgroundImg, bigCloud, smallCloud, shipImgs[];
    private BufferedImage[] questionImgs, exclamationImgs;
    private ArrayList<DialogueEffect> dialogEffects = new ArrayList<>();

    // Flappy bird
    private BufferedImage flappyBKGLayer1, flappyBKGLayer2, flappyBKGLayer3;
    private int backgroundImgWidth = FlappyGame.GAME_WIDTH;

    private float backgroundImgSpeed = .07f;
    private float backLayer1Speed = 0.06f;
    private float backLayer2Speed = 0.08f;
    private int backgroundImgCounter = 0;


    private int[] smallCloudsPos;
    private Random rnd = new Random();

    private boolean gameOver;
    private boolean lvlCompleted;
    private boolean gameCompleted;
    private boolean playerDying;
    private boolean drawRain;

    // Ship will be decided to drawn here. It's just a cool addition to the flappyGame
    // for the first level. Hinting on that the player arrived with the boat.

    // If you would like to have it on more levels, add a value for objects when
    // creating the level from lvlImgs. Just like any other object.

    // Then play around with position values so it looks correct depending on where
    // you want
    // it.

    // This works, but we don't need it for current application.
    private boolean drawShip = false;
    private int shipAni, shipTick, shipDir = 1;
    private float shipHeightDelta, shipHeightChange = 0.05f * FlappyGame.SCALE;

    public Playing(FlappyGame flappyGame) {
        super(flappyGame);
        initClasses();

        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG);

        //        bigCloud = LoadSave.GetSpriteAtlas(LoadSave.BIG_CLOUDS);
        //        smallCloud = LoadSave.GetSpriteAtlas(LoadSave.SMALL_CLOUDS);
        //        smallCloudsPos = new int[8];
        //        for (int i = 0; i < smallCloudsPos.length; i++)
        //            smallCloudsPos[i] = (int) (90 * FlappyGame.SCALE) + rnd.nextInt((int) (100 * FlappyGame.SCALE));

        //        shipImgs = new BufferedImage[4];
        //        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.SHIP);
        //        for (int i = 0; i < shipImgs.length; i++)
        //            shipImgs[i] = temp.getSubimage(i * 78, 0, 78, 72);
        // loadDialogue();

        calcLvlOffset();
        loadStartLevel();
        setDrawRainBoolean();
    }

    private void loadDialogue() {
        loadDialogueImgs();

        // Load dialogue array with pre-made objects, that gets activated when needed.
        // This is a simple
        // way of avoiding ConcurrentModificationException error. (Adding to a list that
        // is being looped through.

        for (int i = 0; i < 10; i++)
            dialogEffects.add(new DialogueEffect(0, 0, EXCLAMATION));
        for (int i = 0; i < 10; i++)
            dialogEffects.add(new DialogueEffect(0, 0, QUESTION));

        for (DialogueEffect de : dialogEffects)
            de.deactive();
    }

    private void loadDialogueImgs() {
        BufferedImage qtemp = LoadSave.GetSpriteAtlas(LoadSave.QUESTION_ATLAS);
        questionImgs = new BufferedImage[5];
        for (int i = 0; i < questionImgs.length; i++)
            questionImgs[i] = qtemp.getSubimage(i * 14, 0, 14, 12);

        BufferedImage etemp = LoadSave.GetSpriteAtlas(LoadSave.EXCLAMATION_ATLAS);
        exclamationImgs = new BufferedImage[5];
        for (int i = 0; i < exclamationImgs.length; i++)
            exclamationImgs[i] = etemp.getSubimage(i * 14, 0, 14, 12);
    }

    public void loadNextLevel() {
        levelManager.setLevelIndex(levelManager.getLevelIndex() + 1);
        levelManager.loadNextLevel();
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
        resetAll();
        drawShip = false;
    }

    private void loadStartLevel() {
        enemyManager.loadEnemies(levelManager.getCurrentLevel());
        objectManager.loadObjects(levelManager.getCurrentLevel());
    }

    private void calcLvlOffset() {
        maxLvlOffsetX = levelManager.getCurrentLevel().getLvlOffset();
    }

    private void initClasses() {
        levelManager = new LevelManager(flappyGame);
        enemyManager = new EnemyManager(this);
        objectManager = new ObjectManager(this);


        pauseOverlay = new PauseOverlay(this);
        gameOverOverlay = new GameOverOverlay(this);
        levelCompletedOverlay = new LevelCompletedOverlay(this);
        gameCompletedOverlay = new GameCompletedOverlay(this);

        rain = new Rain();
    }

    public void setPlayerCharacter(PlayerCharacter pc) {
        player = new Player(pc, this);
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
    }

    @Override
    public void update() {
        if (paused)
            pauseOverlay.update();
        else if (lvlCompleted)
            levelCompletedOverlay.update();
        else if (gameCompleted)
            gameCompletedOverlay.update();
        else if (gameOver)
            gameOverOverlay.update();
        else if (playerDying)
            player.update();
        else {
            // updateDialogue();
            if (drawRain)
                rain.update(xLvlOffset);
            levelManager.update();
            objectManager.update(levelManager.getCurrentLevel().getLevelData(), player);
            player.update();
            checkCloseToBorder();
            enemyManager.update(levelManager.getCurrentLevel().getLevelData());
            if (drawShip)
                updateShipAni();
        }
    }

    private void updateShipAni() {
        shipTick++;
        if (shipTick >= 35) {
            shipTick = 0;
            shipAni++;
            if (shipAni >= 4)
                shipAni = 0;
        }

        shipHeightDelta += shipHeightChange * shipDir;
        shipHeightDelta = Math.max(Math.min(10 * FlappyGame.SCALE, shipHeightDelta), 0);

        if (shipHeightDelta == 0)
            shipDir = 1;
        else if (shipHeightDelta == 10 * FlappyGame.SCALE)
            shipDir = -1;

    }

//    private void updateDialogue() {
//        for (DialogueEffect de : dialogEffects)
//            if (de.isActive())
//                de.update();
//    }
//
//    private void drawDialogue(Graphics g, int xLvlOffset) {
//        for (DialogueEffect de : dialogEffects)
//            if (de.isActive()) {
//                if (de.getType() == QUESTION)
//                    g.drawImage(questionImgs[de.getAniIndex()], de.getX() - xLvlOffset, de.getY(), DIALOGUE_WIDTH, DIALOGUE_HEIGHT, null);
//                else
//                    g.drawImage(exclamationImgs[de.getAniIndex()], de.getX() - xLvlOffset, de.getY(), DIALOGUE_WIDTH, DIALOGUE_HEIGHT, null);
//            }
//    }

    public void addDialogue(int x, int y, int type) {
        // Not adding a new one, we are recycling. #ThinkGreen lol
        dialogEffects.add(new DialogueEffect(x, y - (int) (FlappyGame.SCALE * 15), type));
        for (DialogueEffect de : dialogEffects)
            if (!de.isActive())
                if (de.getType() == type) {
                    de.reset(x, -(int) (FlappyGame.SCALE * 15));
                    return;
                }
    }

    private void checkCloseToBorder() {
        System.out.println("xLvlOffset: " + xLvlOffset);
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - xLvlOffset;
        if (diff > rightBorder) {
            xLvlOffset += diff - rightBorder;
        }
        else if (diff < leftBorder)
            xLvlOffset += diff - leftBorder;
        if (xLvlOffset > maxLvlOffsetX) {
            xLvlOffset = maxLvlOffsetX;
            System.out.println("entered maxLvlOffsetX :" + maxLvlOffsetX);
        } else if (xLvlOffset < 0) {
            xLvlOffset = 0;
            //  xLvlOffset = Math.max(Math.min(xLvlOffset, maxLvlOffsetX), 0);
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, FlappyGame.GAME_WIDTH, FlappyGame.GAME_HEIGHT, null);
        // drawClouds(g);
        setDrawRainBoolean(); // Make it rain 25% of the time.

        if (drawRain)
            rain.draw(g, xLvlOffset);

        //        if (drawShip)
        //            g.drawImage(shipImgs[shipAni], (int) (100 * FlappyGame.SCALE) - xLvlOffset, (int) ((288 * FlappyGame.SCALE) + shipHeightDelta), (int) (78 * FlappyGame.SCALE), (int) (72 * FlappyGame.SCALE), null);

        levelManager.draw(g, xLvlOffset);

        // objectManager.draw(g, xLvlOffset);
        // enemyManager.draw(g, xLvlOffset);

        player.render(g, xLvlOffset);

        // objectManager.drawBackgroundTrees(g, xLvlOffset);

        // drawDialogue(g, xLvlOffset);

        if (paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, FlappyGame.GAME_WIDTH, FlappyGame.GAME_HEIGHT);
            pauseOverlay.draw(g);
        } else if (gameOver)
            gameOverOverlay.draw(g);
        else if (lvlCompleted)
            levelCompletedOverlay.draw(g);
        else if (gameCompleted)
            gameCompletedOverlay.draw(g);

    }

    //    private void drawClouds(Graphics g) {
    //        for (int i = 0; i < 4; i++)
    //            g.drawImage(bigCloud, i * BIG_CLOUD_WIDTH - (int) (xLvlOffset * 0.3), (int) (204 * FlappyGame.SCALE), BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT, null);
    //
    //        for (int i = 0; i < smallCloudsPos.length; i++)
    //            g.drawImage(smallCloud, SMALL_CLOUD_WIDTH * 4 * i - (int) (xLvlOffset * 0.7), smallCloudsPos[i], SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT, null);
    //    }

    public void setGameCompleted() {
        gameCompleted = true;
    }

    public void resetGameCompleted() {
        gameCompleted = false;
    }

    public void resetAll() {
        gameOver = false;
        paused = false;
        lvlCompleted = false;
        playerDying = false;
        drawRain = false;

        setDrawRainBoolean();

        player.resetAll();
        enemyManager.resetAllEnemies();
        objectManager.resetAllObjects();
        dialogEffects.clear();
    }

    // Make it snow or rain.
    private void setDrawRainBoolean() {
        // This method makes it rain 20% of the time you load a level.
        if (rnd.nextFloat() >= 0.8f) {
            drawRain = true;
        }
        drawRain = false;

    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void checkObjectHit(Rectangle2D.Float attackBox) {
        objectManager.checkObjectHit(attackBox);
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        enemyManager.checkEnemyHit(attackBox);
    }

    public void checkPotionTouched(Rectangle2D.Float hitbox) {
        objectManager.checkObjectTouched(hitbox);
    }

    public void checkSpikesTouched(Player p) {
        objectManager.checkSpikesTouched(p);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gameOver) {
            if (e.getButton() == MouseEvent.BUTTON1)
                player.setAttacking(true);
            else if (e.getButton() == MouseEvent.BUTTON3)
                player.powerAttack();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameOver && !gameCompleted && !lvlCompleted)
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(false); // avoid bird pausing when A key pressed .
                    break;
                case KeyEvent.VK_D:

                    player.setRight(true);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(true);
                    break;
                case KeyEvent.VK_ESCAPE:
                    paused = !paused;
            }
        player.setRight(true); // I added this to set the forward motion of the bird.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!gameOver && !gameCompleted && !lvlCompleted)
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(false);
                    break;
            }
        player.setRight(true); // I added this to set the forward motion of the bird.
    }

    public void mouseDragged(MouseEvent e) {
        if (!gameOver && !gameCompleted && !lvlCompleted)
            if (paused)
                pauseOverlay.mouseDragged(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (gameOver)
            gameOverOverlay.mousePressed(e);
        else if (paused)
            pauseOverlay.mousePressed(e);
        else if (lvlCompleted)
            levelCompletedOverlay.mousePressed(e);
        else if (gameCompleted)
            gameCompletedOverlay.mousePressed(e);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (gameOver)
            gameOverOverlay.mouseReleased(e);
        else if (paused)
            pauseOverlay.mouseReleased(e);
        else if (lvlCompleted)
            levelCompletedOverlay.mouseReleased(e);
        else if (gameCompleted)
            gameCompletedOverlay.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (gameOver)
            gameOverOverlay.mouseMoved(e);
        else if (paused)
            pauseOverlay.mouseMoved(e);
        else if (lvlCompleted)
            levelCompletedOverlay.mouseMoved(e);
        else if (gameCompleted)
            gameCompletedOverlay.mouseMoved(e);
    }

    // Set level compelted when bird reaches the end.

    public void setLevelCompleted(boolean levelCompleted) {
        flappyGame.getAudioPlayer().lvlCompleted();
        if (levelManager.getLevelIndex() + 1 >= levelManager.getAmountOfLevels()) {
            // No more levels
            gameCompleted = true;
            levelManager.setLevelIndex(0);
            levelManager.loadNextLevel();
            resetAll();
            return;
        }
        this.lvlCompleted = levelCompleted;
    }

    public void setMaxLvlOffset(int lvlOffset) {
        this.maxLvlOffsetX = lvlOffset;
    }

    public void unpauseGame() {
        paused = false;
    }

    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    public Player getPlayer() {
        return player;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public void setPlayerDying(boolean playerDying) {
        this.playerDying = playerDying;
    }


}