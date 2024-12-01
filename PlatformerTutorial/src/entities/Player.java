package entities;

import static utils.Constants.PlayerConstants.*;
import static utils.HelpMethods.*;
import static utils.Constants.*;
import static utils.Constants.Directions.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import audio.AudioPlayer;
import gamestates.Playing;
import main.FlappyGame;
import utils.LoadSave;

public class Player extends Entity {

    private BufferedImage[][] animations;
    private boolean moving = false, attacking = false;
    private boolean left, right, jump;
    private int[][] lvlData;
    //    private float xDrawOffset = 21 * FlappyGame.SCALE;
    //    private float yDrawOffset = 4 * FlappyGame.SCALE;

    // Jumping / Gravity
    private float jumpSpeed = -2.25f * FlappyGame.SCALE;
    private float fallSpeedAfterCollision = 0.5f * FlappyGame.SCALE;

    // StatusBarUI
    private BufferedImage statusBarImg;

    private int statusBarWidth = (int) (192 * FlappyGame.SCALE);
    private int statusBarHeight = (int) (58 * FlappyGame.SCALE);
    private int statusBarX = (int) (10 * FlappyGame.SCALE);
    private int statusBarY = (int) (10 * FlappyGame.SCALE);

    private int healthBarWidth = (int) (150 * FlappyGame.SCALE);
    private int healthBarHeight = (int) (4 * FlappyGame.SCALE);
    private int healthBarXStart = (int) (34 * FlappyGame.SCALE);
    private int healthBarYStart = (int) (14 * FlappyGame.SCALE);
    private int healthWidth = healthBarWidth;

    private int powerBarWidth = (int) (104 * FlappyGame.SCALE);
    private int powerBarHeight = (int) (2 * FlappyGame.SCALE);
    private int powerBarXStart = (int) (44 * FlappyGame.SCALE);
    private int powerBarYStart = (int) (34 * FlappyGame.SCALE);
    private int powerWidth = powerBarWidth;
    private int powerMaxValue = 200;
    private int powerValue = powerMaxValue;

    private int flipX = 0;
    private int flipW = 1;

    private boolean attackChecked;
    private Playing playing;

    private int tileY = 0;

    // private int birdStartHeight =

    private boolean powerAttackActive;
    private int powerAttackTick;
    private int powerGrowSpeed = 15;
    private int powerGrowTick;

    private final PlayerCharacter playerCharacter;
    private  int birdScore = 0;
    private  boolean birdEntered = false;
    private  boolean birdExited = true;

    public Player(PlayerCharacter playerCharacter, Playing playing) {
        // The line super below seems to changing the EAGLE to left
        // I need to run more tests.
        super(0, 0, (int) ((playerCharacter.spriteW * FlappyGame.SCALE)) - playerCharacter.centerPixelOffset, (int) (playerCharacter.spriteH * FlappyGame.SCALE));
        this.playerCharacter = playerCharacter;
        this.playing = playing;
        this.state = IDLE;
        this.maxHealth = 100;
        this.currentHealth = maxHealth;
        this.walkSpeed = FlappyGame.SCALE * 1.0f;
        animations = LoadSave.loadAnimations(playerCharacter);
        statusBarImg = LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);
        initHitbox(playerCharacter.hitboxW, playerCharacter.hitboxH);
        initAttackBox();
    }


//    public Player(PlayerCharacter playerCharacter, Playing playing) {
//        super(0, 0, (int) (playerCharacter.spriteW * FlappyGame.SCALE), (int) (playerCharacter.spriteH * FlappyGame.SCALE));
//        this.playerCharacter = playerCharacter;
//        this.playing = playing;
//        this.state = IDLE;
//        this.maxHealth = 100;
//        this.currentHealth = maxHealth;
//        this.walkSpeed = FlappyGame.SCALE * 1.0f;
//
//        // Load other player characters
//        // animations = LoadSave.loadAnimations(playerCharacter);
//
//        // Load birds instead of other characters.
//
//        loadBirdAnimations();
//
//
//        // animations = LoadSave.loadBirdAnimations(playerCharacter);
//
//        statusBarImg = LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);
//
//        initHitbox(playerCharacter.hitboxW, playerCharacter.hitboxH);
//
//        initAttackBox();
//    }

//    private void loadBirdAnimations() {
//        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
//        //               BufferedImage[i Row][j columns]
//        animations = new BufferedImage[4][11];
//        // System.out.println("BufferedImage[] []" + animations.length);
//        // System.out.println("BufferedImage[] []" + animations[0].length);
//        for (int j = 0; j < animations.length; j++)
//            for (int i = 0; i < animations[j].length; i++) {
//                // Original player
//                // animations[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
//                // Flappy Bird
//                // animations[j][i] = img.getSubimage(i * 158, (j * 0) + 2, 158, 122);
//                // Crow Flappy Bird
//
//                animations[j][i] = img.getSubimage(i * 180 + 30, 0, 200, 185);
//                //  System.out.println("animations " + animations[j].length);
//
//            }
//    }

    // I hard coded this since we do not have the original map for a different game.
    // this only applies to the Flappy game.
    public void setSpawn(Point spawn) {
    //        this.x = spawn.x;
    //        this.y = spawn.y;

        this.x = 33;
        this.y = (int)(FlappyGame.GAME_HEIGHT/ 2);
        hitbox.x = x;
        hitbox.y = y;
    }

    // The logic in this method works as a toggle switch to keep score
    // It avoids duplicating the score if the bird spends too much inside the fly zone.
    public void updateBirdScore(float x, float y, int[][] lvlData) {
        int xIndex = (int) (x / FlappyGame.TILES_SIZE);
        int yIndex = (int) (y / FlappyGame.TILES_SIZE);
        int currentValue = lvlData[yIndex][xIndex];
        if (currentValue == 23) {
            // Bird is on the scoring tile
            if (!birdEntered) {
                birdEntered = true;
                birdExited = false;
                System.out.println("Bird entered the scoring zone");
            }
        } else {
            // Bird is not on the scoring tile
            if (birdEntered && !birdExited) {
                birdExited = true;
                birdEntered = false;
                birdScore++;
                System.out.println("Bird exited the scoring zone. Current score: " + birdScore);
            }
        }
    }

    private void initAttackBox() {

        //        attackBox = new Rectangle2D.Float(x, y, (int) ((35 * FlappyGame.SCALE) + playerCharacter.centerPixelOffset), (int) (20 * FlappyGame.SCALE));
        attackBox = new Rectangle2D.Float(x, y, (int) (35 * FlappyGame.SCALE), (int) (20 * FlappyGame.SCALE));
        resetAttackBox();
    }

    public void update() {
        updateHealthBar();
        updatePowerBar();

        if (currentHealth <= 0) {
            if (state != DEAD) {
                state = DEAD;
                aniTick = 0;
                aniIndex = 0;
                playing.setPlayerDying(true);
                playing.getGame().getAudioPlayer().playEffect(AudioPlayer.DIE);

                // Check if player died in air
                if (!IsEntityOnFloor(hitbox, lvlData)) {
                    inAir = true;
                    airSpeed = 0;
                }
            } else if (aniIndex == playerCharacter.getSpriteAmount(DEAD) - 1 && aniTick >= ANI_SPEED - 1) {
                playing.setGameOver(true);
                playing.getGame().getAudioPlayer().stopSong();
                playing.getGame().getAudioPlayer().playEffect(AudioPlayer.GAMEOVER);
            } else {
                updateAnimationTick();

                // Fall if in air
                if (inAir)
                    if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
                        hitbox.y += airSpeed;
                        airSpeed += GRAVITY;
                    } else
                        inAir = false;

            }

            return;
        }

        updateAttackBox();
        if (state == HIT) {
            if (aniIndex <= playerCharacter.getSpriteAmount(state) - 3)
                pushBack(pushBackDir, lvlData, 1.25f);
            updatePushBackDrawOffset();
        } else
            updatePos();
        if (moving) {
            checkPotionTouched();
            checkSpikesTouched();
            checkInsideWater();
            tileY = (int) (hitbox.y / FlappyGame.TILES_SIZE);
            if (powerAttackActive) {
                powerAttackTick++;
                if (powerAttackTick >= 35) {
                    powerAttackTick = 0;
                    powerAttackActive = false;
                }
            }
        }
        if (attacking || powerAttackActive)
            checkAttack();
        updateAnimationTick();
        setAnimation();
    }

    private void checkInsideWater() {
        if (IsEntityInWater(hitbox, playing.getLevelManager().getCurrentLevel().getLevelData()))
            currentHealth = 0;
    }

    private void checkSpikesTouched() {
        playing.checkSpikesTouched(this);
    }

    private void checkPotionTouched() {
        playing.checkPotionTouched(hitbox);
    }

    private void checkAttack() {
        if (attackChecked || aniIndex != 1)
            return;
        attackChecked = true;

        if (powerAttackActive)
            attackChecked = false;

        playing.checkEnemyHit(attackBox);
        playing.checkObjectHit(attackBox);
        playing.getGame().getAudioPlayer().playAttackSound();
    }

    private void setAttackBoxOnRightSide() {
        attackBox.x = hitbox.x + hitbox.width - (int) (FlappyGame.SCALE * 5);
    }

    private void setAttackBoxOnLeftSide() {
        attackBox.x = hitbox.x - hitbox.width - (int) (FlappyGame.SCALE * 10);
    }

    private void updateAttackBox() {
        if (right && left) {
            if (flipW == 1) {
                setAttackBoxOnRightSide();
            } else {
                setAttackBoxOnLeftSide();
            }

        } else if (right || (powerAttackActive && flipW == 1))
            setAttackBoxOnRightSide();
        else if (left || (powerAttackActive && flipW == -1))
            setAttackBoxOnLeftSide();

        attackBox.y = hitbox.y + (FlappyGame.SCALE * 10);
    }

    private void updateHealthBar() {
        healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);
    }

    private void updatePowerBar() {
        powerWidth = (int) ((powerValue / (float) powerMaxValue) * powerBarWidth);
        powerGrowTick++;
        if (powerGrowTick >= powerGrowSpeed) {
            powerGrowTick = 0;
            changePower(1);
        }
    }

    public void render(Graphics g, int lvlOffset) {
        g.drawImage(animations[playerCharacter.getRowIndex(state)][aniIndex], ((int) (hitbox.x - playerCharacter.xDrawOffset) - lvlOffset + flipX), (int) (hitbox.y - playerCharacter.yDrawOffset + (int) (pushDrawOffset)), width * flipW, height, null);
        drawHitbox(g, lvlOffset); //
        // drawAttackBox(g, lvlOffset); This is for if we need to set an attack area for the bird. Not used.
        drawUI(g);
        updateScore(birdScore, g); // every player re-draw check if scored.

    }

    // Draws the health bar of the bird in the upper left corner.
    public void updateScore(int score,Graphics g ) {
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Score: " + score, 50, 150);
    }

    // Draws the health bar of the bird in the upper left corner.
    private void drawUI(Graphics g) {
        // Background ui
        g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
        // Health bar
        g.setColor(Color.red);
        g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
        // Power Bar
        g.setColor(Color.yellow);
        g.fillRect(powerBarXStart + statusBarX, powerBarYStart + statusBarY, powerWidth, powerBarHeight);
    }

    private void updateAnimationTick() {
       // System.out.println("playerCharacter: " + playerCharacter);
       //  System.out.println("sprite state sprite amount " + playerCharacter.getSpriteAmount(state));
        aniTick++;
        if (aniTick >= ANI_SPEED) { // <---- This make no sense - Shafiq I will have to test why this is here.
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= playerCharacter.getSpriteAmount(state)) {
                aniIndex = 0;
                attacking = false;
                attackChecked = false;
                if (state == HIT) {
                    newState(IDLE);
                    airSpeed = 0f;
                    if (!IsFloor(hitbox, 0, lvlData))
                        inAir = true;
                }
            }
        }
    }

    private void setAnimation() {

        int startAni = state;
        // System.out.println("---->  startAni: " + startAni);

        if (state == HIT)
            return;

        if (moving)
            state = RUNNING;
        else
            state = IDLE;

        if (inAir) {
            if (airSpeed < 0)
                state = JUMP;
            else
                state = FALLING;
        }

        if (powerAttackActive) {
            state = ATTACK;
            aniIndex = 1;
            aniTick = 0;
            return;
        }

        if (attacking) {
            state = ATTACK;
            if (startAni != ATTACK) {
                aniIndex = 1;
                aniTick = 0;
                return;
            }
        }
        if (startAni != state) {
            // resetAniTick();
        }
    }

    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    private void updatePos() {
        moving = true; // Bird should never be false. Keeps moving.

        if (jump)
            jump();

        if (!inAir)
            if (!powerAttackActive)
                if ((!left && !right) || (right && left))
                    return;

        float xSpeed = 0;

        if (left && !right) {
            xSpeed += walkSpeed; // Changed the so bird is not able to go back wards.
            // flipX = width;      // Changed the so bird is not able to go back wards.
            // flipW = -1;         // Changed the so bird is not able to go back wards.
        }
        if (right && !left) {
            xSpeed += walkSpeed;
            // flipX = 0; // Changed the so bird is not able to go back wards.
            // flipW = 1;// Changed the so bird is not able to go back wards.
        }

        if (powerAttackActive) {
            if ((!left && !right) || (left && right)) {
                if (flipW == -1)
                    xSpeed = -walkSpeed;
                else
                    xSpeed = walkSpeed;
            }

            xSpeed *= 3;
        }

        if (!inAir)
            if (!IsEntityOnFloor(hitbox, lvlData))
                inAir = true;

        if (inAir && !powerAttackActive) {
            if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
                hitbox.y += airSpeed;
                airSpeed += GRAVITY;
                updateXPos(xSpeed);
            } else {
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                if (airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;
                updateXPos(xSpeed);
            }

        } else                  // Removing this will cause the bird to speed up. Do not remove.
              updateXPos(xSpeed);
        moving = true;
    }

    private void jump() {
        //        if (inAir)      // These are remarked out so bird is able to fly after space bar pressed.
        //            return;
        playing.getGame().getAudioPlayer().playEffect(AudioPlayer.JUMP);   // Change this flap sound when flying.
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    // Score tracker is updated here.
    private void updateXPos(float xSpeed) {
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            updateBirdScore(hitbox.x, hitbox.y, lvlData); // Update the score if the bird is able to cross into and out of tile 23. See Legends in folder.
            hitbox.x += xSpeed;
        }
        else {
            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
            if (powerAttackActive) {
                powerAttackActive = false;
                powerAttackTick = 0;
            }
        }
    }

    public void changeHealth(int value) {
        if (value < 0) {
            if (state == HIT)
                return;
            else
                newState(HIT);
        }

        currentHealth += value;
        currentHealth = Math.max(Math.min(currentHealth, maxHealth), 0);
    }

    public void changeHealth(int value, Enemy e) {
        if (state == HIT)
            return;
        changeHealth(value);
        pushBackOffsetDir = UP;
        pushDrawOffset = 0;

        if (e.getHitbox().x < hitbox.x)
            pushBackDir = RIGHT;
        else
            pushBackDir = LEFT;
    }

    public void kill() {
        currentHealth = 0;
    }

    public void changePower(int value) {
        powerValue += value;
        powerValue = Math.max(Math.min(powerValue, powerMaxValue), 0);
    }

    // Set this value to false to pause bird when game starts.
    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
        if (!IsEntityOnFloor(hitbox, lvlData))
            inAir = false;
    }

    public void resetDirBooleans() {
        left = false;
        right = false;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void resetAll() {
        resetDirBooleans();
        inAir = false;
        attacking = false;
        moving = false;
        airSpeed = 0f;
        state = IDLE;
        currentHealth = maxHealth;
        powerAttackActive = false;
        powerAttackTick = 0;
        powerValue = powerMaxValue;

        hitbox.x = x;
        hitbox.y = y;
        resetAttackBox();

        if (!IsEntityOnFloor(hitbox, lvlData))
            inAir = true;
    }

    private void resetAttackBox() {
        if (flipW == 1)
            setAttackBoxOnRightSide();
        else
            setAttackBoxOnLeftSide();
    }

    public int getTileY() {
        return tileY;
    }

    public void powerAttack() {
        if (powerAttackActive)
            return;
        if (powerValue >= 60) {
            powerAttackActive = true;
            changePower(-60);
        }

    }

}