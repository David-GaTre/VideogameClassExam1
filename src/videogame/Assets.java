/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.image.BufferedImage;

/**
 *
 * @author antoniomejorado
 */
public class Assets {

    public static BufferedImage background; // to store background image
    public static BufferedImage player;     // to store the player image
    public static SoundClip backSound;
    public static SoundClip gunShot;
    public static SoundClip oof;
    public static SoundClip yay;
    public static SoundClip end;
    public static BufferedImage enemy;
    public static BufferedImage ally;
    public static BufferedImage endImage;

    /**
     * initializing the images of the game
     */
    public static void init() {
        background = ImageLoader.loadImage("/images/Background.jpg");
        player = ImageLoader.loadImage("/images/mario.png");
        backSound = new SoundClip("/sounds/naruto.wav");
        gunShot = new SoundClip("/sounds/Gunshot.wav");
        oof = new SoundClip("/sounds/bad.wav");
        yay = new SoundClip("/sounds/coin.wav");
        end = new SoundClip("/sounds/end.wav");
        enemy = ImageLoader.loadImage("/images/chuek.png");
        ally = ImageLoader.loadImage("/images/chocomil.png");
        endImage = ImageLoader.loadImage("/images/youdied.jpg");
    }

}
