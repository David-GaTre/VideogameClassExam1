/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Color;
import static java.awt.Color.MAGENTA;
import static java.awt.Color.RED;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;

/**
 *
 * @author antoniomejorado
 */
public class Game implements Runnable {
    private BufferStrategy bs;      // to have several buffers when displaying
    private Graphics g;             // to paint objects
    private Display display;        // to display in the game
    String title;                   // title of the window
    private int width;              // width of the window
    private int height;             // height of the window
    private Thread thread;          // thread to create the game
    private boolean running;        // to set the game
    private Player player;          // to use a player
    private KeyManager keyManager;  // to manage the keyboard
    private LinkedList<Enemy> lista; // the array of enemies
    private LinkedList<Ally> amigos; //  the array of allies, they give points
    private int iCrashes; //  a constant integer that checks if the player has crashed
    
    
    /**
     * to create title, width and height and set the game is still not running
     * @param title to set the title of the window
     * @param width to set the width of the window
     * @param height  to set the height of the window
     */
    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        running = false;
        keyManager = new KeyManager();
    }
    
    public Player getPlayer() {
        return player;
    }

    /**
     * To get the width of the game window
     * @return an <code>int</code> value with the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * To get the height of the game window
     * @return an <code>int</code> value with the height
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * initializing the display window of the game
     */
    private void init() {
        display = new Display(title, getWidth(), getHeight());  
        Assets.init();
        player = new Player(getWidth()/2 - 50, getHeight()/2 - 50, 1,
                            100, 100, (int) (Math.random() * 3) + 3, 0, this); //Generates the player
        // generates a random number of enemies 
        lista = new LinkedList<Enemy>();
        int azar = (int) (Math.random() * 3) + 8;
        for (int i = 1; i <= azar; i++) {
            Enemy enemy = new Enemy(getWidth() + 50, (int) (Math.random() * getHeight()), 1, 50, 50, this);
            lista.add(enemy);
        }
        // generates a random number of allies
        amigos = new LinkedList<Ally>();
        azar = (int) (Math.random() * 6) + 10;
        for (int i = 1; i <= azar; i++) {
            Ally ally = new Ally(-10, (int) (Math.random() * getHeight()), 1, 50, 50, this);
            amigos.add(ally);
        }
         
        display.getJframe().addKeyListener(keyManager);
        Assets.backSound.setLooping(true);
        Assets.backSound.play();
    }
    /**
     * It keeps the game running
     */
    @Override
    public void run() {
        init();
        // frames per second
        int fps = 50;
        // time for each tick in nano segs
        double timeTick = 1000000000 / fps;
        // initializing delta
        double delta = 0;
        // define now to use inside the loop
        long now;
        // initializing last time to the computer time in nanosecs
        long lastTime = System.nanoTime();
        while (running) {
            // setting the time now to the actual time
            now = System.nanoTime();
            // acumulating to delta the difference between times in timeTick units
            delta += (now - lastTime) / timeTick;
            // updating the last time
            lastTime = now;
            
            // if delta is positive we tick the game
            if (delta >= 1) {
                tick();
                render();
                delta --;
            }
        }
        stop();
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }
    /**
     * it makes the sprites move
     */
    private void tick() {
        keyManager.tick();
        // avancing player with colision
        if(player.getLives() > 0) {
            player.tick();
            for (Enemy enemy : lista) {
                enemy.tick();
                // if collision
                if (player.collision(enemy)) {
                    Assets.oof.play(); // if collides it makes a sound
                    iCrashes++;
                    // each time it crashes it goes up in the counter, if reaches 5
                    // it reduces one life
                    if (iCrashes == 5) {
                        player.setLives(player.getLives() - 1);
                        iCrashes = 0;
                    }
                    enemy.setX(getWidth() + 50);
                    enemy.setY((int) (Math.random() * getHeight()));
                }
            }
            for (Ally ally : amigos) {
                ally.tick();
                // if collision
                if (player.collision(ally)) {
                    Assets.yay.play(); // if collides it makes a sound
                    player.setScore(player.getScore() + 5); // if collides it goes up 5 points
                    ally.setX(-50);
                    ally.setY((int) (Math.random() * getHeight()));
                }
            }
        }
        else {
            Assets.backSound.stop(); 
        }
    }
    
    private void render() {
        // get the buffer strategy from the display
        bs = display.getCanvas().getBufferStrategy();
        /* if it is null, we define one with 3 buffers to display images of
        the game, if not null, then we display every image of the game but
        after clearing the Rectanlge, getting the graphic object from the 
        buffer strategy element. 
        show the graphic and dispose it to the trash system
        */
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
        } else {
            if (player.getLives() > 0) {
                g = bs.getDrawGraphics();
                g.drawImage(Assets.background, 0, 0, width, height, null);
                g.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
                g.setColor(RED);
                g.drawString("Lives: " + Integer.toString(player.getLives()), 10,
                             10);
                g.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
                g.setColor(MAGENTA);
                g.drawString("Score: " + Integer.toString(player.getScore()), getWidth() - 90,
                             10) ;
                player.render(g);
                for (Enemy enemy : lista) {
                     enemy.render(g);
                }
                for (Ally ally : amigos) {
                    ally.render(g);
                }
                bs.show();
                g.dispose();
                Toolkit.getDefaultToolkit().sync();
            }
            else {
                g = bs.getDrawGraphics();
                g.drawImage(Assets.endImage, 0, 0, width, height, null);
                bs.show();
                g.dispose();
                Toolkit.getDefaultToolkit().sync();
            }
        }
       
    }
    
    /**
     * setting the thread for the game
     */
    public synchronized void start() {
        if (!running) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * stopping the thread
     */
    public synchronized void stop() {
        if (running) {
            running = false;
            try {
                thread.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

}
