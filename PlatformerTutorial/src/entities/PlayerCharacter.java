package entities;

import main.FlappyGame;
import utils.LoadSave;

import static utils.Constants.PlayerConstants.*;

public enum PlayerCharacter {
    // NOTES: The cap letter A stands for amount. For example if you have a sheet with 3 dying pics you choose 3 for spriteA_DEAD etc.
    //
    EAGLE(11, 11, 11, 11, 1, 1, 1,
            0, 0, 0, 0, 0, 0, 0,
            LoadSave.PLAYER_EAGLE, 10, 1, 11, 72, 70,
            40, 30, 21, 25),
    BAT(8, 8, 8, 8, 1, 1, 1,
            0, 0, 0, 0, 0, 0, 0,
            LoadSave.PLAYER_BAT, 13, 1, 8, 72, 96,
            30, 25, 21, 25),
    YELLOWBIRD(3, 3, 3, 3, 3, 1, 1,
            0, 0, 0, 0, 0, 0, 0,
            LoadSave.PLAYER_YELLOWBIRD, 0, 4, 3, 58, 40,
            13, 15, 44, 42),
    REDBIRD(3, 3, 3, 3, 1, 1, 1,
            1, 1, 1, 1, 1, 1, 1,
            LoadSave.PLAYER_REDBIRD, 0, 4, 3, 58, 40,
            12, 18, 44, 39);

    // Instead of affecting the other characters we will equate RUNNING == FLYING for bird characters.
    // As for the return value 7 for FLYING is debatable.
    public int spriteA_IDLE, spriteA_RUNNING, spriteA_JUMP, spriteA_FALLING, spriteA_ATTACK, spriteA_HIT, spriteA_DEAD;
    public int rowIDLE, rowRUNNING, rowJUMP, rowFALLING, rowATTACK, rowHIT, rowDEAD;
    public String playerAtlas;
    public int centerPixelOffset;
    public int numRows, numColms;
    public int spriteW, spriteH;
    public int hitboxW, hitboxH;
    public int xDrawOffset, yDrawOffset;


    /*
     private float xDrawOffset = 21 * FlappyGame.SCALE;
     private float yDrawOffset = 4 * FlappyGame.SCALE;
     */

    PlayerCharacter(int spriteA_IDLE, int spriteA_RUNNING, int spriteA_JUMP, int spriteA_FALLING, int spriteA_ATTACK, int spriteA_HIT, int spriteA_DEAD,
                    int rowIDLE, int rowRUNNING, int rowJUMP, int rowFALLING, int rowATTACK, int rowHIT, int rowDEAD,
                    String playerAtlas, int centerPixelOffset, int numbRows, int numbCols, int spriteW, int spriteH,
                    int hitboxW, int hitboxH,
                    int xDrawOffset, int yDrawOffset) {

        this.spriteA_IDLE = spriteA_IDLE;
        this.spriteA_RUNNING = spriteA_RUNNING;
        this.spriteA_JUMP = spriteA_JUMP;
        this.spriteA_FALLING = spriteA_FALLING;
        this.spriteA_ATTACK = spriteA_ATTACK;
        this.spriteA_HIT = spriteA_HIT;
        this.spriteA_DEAD = spriteA_DEAD;

        this.rowIDLE = rowIDLE;
        this.rowRUNNING = rowRUNNING;
        this.rowJUMP = rowJUMP;
        this.rowFALLING = rowFALLING;
        this.rowATTACK = rowATTACK;
        this.rowHIT = rowHIT;
        this.rowDEAD = rowDEAD;

        this.playerAtlas = playerAtlas;
        this.centerPixelOffset = centerPixelOffset; // This value corrects the char if it's loading to the side.
        this.numRows = numbRows;
        this.numColms = numbCols;
        this.spriteW = spriteW;
        this.spriteH = spriteH;

        this.hitboxW = hitboxW;
        this.hitboxH = hitboxH;

        this.xDrawOffset = (int) (xDrawOffset * FlappyGame.SCALE);
        this.yDrawOffset = (int) (yDrawOffset * FlappyGame.SCALE);
    }

    public int getSpriteAmount(int player_action) {
        return switch (player_action) {
            case IDLE -> spriteA_IDLE;
            case RUNNING -> spriteA_RUNNING;
            case JUMP -> spriteA_JUMP;
            case FALLING -> spriteA_FALLING;
            case ATTACK -> spriteA_ATTACK;
            case HIT -> spriteA_HIT;
            case DEAD -> spriteA_DEAD;
            default -> 1;
        };
    }

    public int getRowIndex(int player_action) {
        return switch (player_action) {
            case IDLE -> rowIDLE;
            case RUNNING -> rowRUNNING;
            case JUMP -> rowJUMP;
            case FALLING -> rowFALLING;
            case ATTACK -> rowATTACK;
            case HIT -> rowHIT;
            case DEAD -> rowDEAD;
            default -> 1;
        };
    }

}
