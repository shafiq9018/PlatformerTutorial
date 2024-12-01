package utils;

import entities.PlayerCharacter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;

public class LoadSave {
    ///***********************************************************************************
    ///                          IMPORTANT NOTES
    ///                          - by Shafiq
    /// - This game uses a pixel level map to load graphics for the background
    /// - Explanation for collisions.
    ///   https://youtu.be/PrAmaeQF4f0?list=PL4rzdwizLaxYmltJQRjq18a9gsSyEQQ-0&t=70
    ///
    /// - Moving the background images
    ///   https://youtu.be/JmcBRVz2Voo?list=PL4rzdwizLaxYmltJQRjq18a9gsSyEQQ-0&t=913
    ///
    /// UPDATE This FlappyBird has been placed on the newest EP29 Char Selection
    /// The videos above may contain old methods and classes. Please be careful.
    /// - Shafiq
    ///

   // public static final String PLAYER_ATLAS = "eagle_Linear_Sheet_Fixed.png";
    public static final String PLAYER_EAGLE = "eagle_Linear_Sheet_New.png"; // Using 64 pixel by 12 birds
    public static final String PLAYER_BAT = "BaddyBats_64pixX9.png";
    public static final String PLAYER_YELLOWBIRD = "YelloBirdsColorsx64.png";
    public static final String PLAYER_REDBIRD = "YelloBirdsColorsx64.png";
    public static final String LEVEL_ATLAS = "outside_sprites.png";
    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BACKGROUND = "menu_background.png";
    public static final String PAUSE_BACKGROUND = "pause_menu.png";
    public static final String SOUND_BUTTONS = "sound_button.png";
    public static final String URM_BUTTONS = "urm_buttons.png";
    public static final String VOLUME_BUTTONS = "volume_buttons.png";
    public static final String MENU_BACKGROUND_IMG = "bk_images/halloween2.png";
    public static final String PLAYING_BG_IMG = "playing_bg_img.png";
    // Halloween theme backgrounds
    public static final String Folder = "HalloweenThemes/";
    public static final String FlappyLayer_1 = Folder + "Layer_1.png";  // This is the background downloaded from free sites.
    public static final String FlappyLayer_2 = Folder + "Layer_2.png";  // This is the background downloaded from free sites.
    public static final String FlappyLayer_3 = Folder + "Layer_3.png";  // This is the background downloaded from free sites.

    // Halloween theme backgrounds
    public static final String Folder2 = "Christmas/";
    public static final String FlappyLayer_cm1 = Folder + "Layer_1.png";  // This is the background downloaded from free sites.
    public static final String FlappyLayer_cm2 = Folder + "Layer_2.png";  // This is the background downloaded from free sites.
    public static final String FlappyLayer_cm3 = Folder + "Layer_3.png";  // This is the background downloaded from free sites.

    public static final String BIG_CLOUDS = "big_clouds.png";
    public static final String SMALL_CLOUDS = "small_clouds.png";
    public static final String CRABBY_SPRITE = "crabby_sprite.png";
    public static final String STATUS_BAR = "health_power_bar.png";
    public static final String COMPLETED_IMG = "completed_sprite.png";
    public static final String POTION_ATLAS = "potions_sprites.png";
    public static final String CONTAINER_ATLAS = "objects_sprites.png";
    public static final String TRAP_ATLAS = "trap_atlas.png";
    public static final String CANNON_ATLAS = "cannon_atlas.png";
    public static final String CANNON_BALL = "ball.png";
    public static final String DEATH_SCREEN = "death_screen.png";
    public static final String OPTIONS_MENU = "options_background.png";
    public static final String PINKSTAR_ATLAS = "pinkstar_atlas.png";
    public static final String QUESTION_ATLAS = "question_atlas.png";
    public static final String EXCLAMATION_ATLAS = "exclamation_atlas.png";
    public static final String SHARK_ATLAS = "shark_atlas.png";
    public static final String CREDITS = "credits_list.png";
    public static final String GRASS_ATLAS = "grass_atlas.png";
    public static final String TREE_ONE_ATLAS = "tree_one_atlas.png";
    public static final String TREE_TWO_ATLAS = "tree_two_atlas.png";
    public static final String GAME_COMPLETED = "game_completed.png";
    public static final String RAIN_PARTICLE = "rain_particle.png";
    public static final String WATER_TOP = "water_atlas_animation.png";
    public static final String WATER_BOTTOM = "water.png";
    public static final String SHIP = "ship.png";

    public static BufferedImage[][] loadAnimations(PlayerCharacter pc) {
        System.out.println("Loading animations pc   " + pc);
        BufferedImage img = LoadSave.GetSpriteAtlas(pc.playerAtlas);
        BufferedImage[][] animations = new BufferedImage[pc.numRows][pc.numColms];
        for (int j = 0; j < animations.length; j++)
            for (int i = 0; i < animations[j].length; i++) {
                System.out.println("animations[j][i] =" + animations[j][i]);
                animations[j][i] = img.getSubimage((i * pc.spriteW) + pc.centerPixelOffset, j * pc.spriteH, pc.spriteW, pc.spriteH);
            }
        return animations;
    }

    // Will add ability to choose different birds coming up next.
    //    public static BufferedImageNew[][] loadBirdAnimations(PlayerCharacter pc) {
    //        BufferedImage img = LoadSave.GetSpriteAtlas(pc.playerAtlas);
    //        BufferedImage[][] animations = new BufferedImage[pc.numRows][pc.numColms];
    //        for (int j = 0; j < animations.length; j++)
    //            for (int i = 0; i < animations[j].length; i++)
    //                animations[j][i] = img.getSubimage(i * pc.spriteW, j * pc.spriteH, pc.spriteW, pc.spriteH);
    //        return animations;
    //    }

    // Load birds

    public static BufferedImage GetSpriteAtlas(String fileName) {
        // System.out.println("LoadSave.java fileName: " + fileName);
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
        try {
            img = ImageIO.read(is);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    public static BufferedImage[] GetAllLevels() {
        URL url = LoadSave.class.getResource("/lvls");
        System.out.println("url: " + url);
        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        File[] files = file.listFiles();
        File[] filesSorted = new File[files.length];
        for (int i = 0; i < filesSorted.length; i++)
            for (int j = 0; j < files.length; j++) {
                if (files[j].getName().equals((i + 1) + ".png")){
                    filesSorted[i] = files[j];
                    System.out.println("File: " + files[j].getName() + ", File: " + files[j].getPath());
                }
            }
        BufferedImage[] imgs = new BufferedImage[filesSorted.length];
        for (int i = 0; i < imgs.length; i++)
            try {
                imgs[i] = ImageIO.read(filesSorted[i]);
                System.out.println("imgs: length of each image " + imgs[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        return imgs;
    }
}