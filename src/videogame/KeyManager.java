/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author antoniomejorado
 */
public class KeyManager implements KeyListener {
    
    public boolean q;      // flag to move up the player
    public boolean p;    // flag to move down the player
    public boolean a;    // flag to move left the player
    public boolean l;   // flag to move right the player

    private boolean keys[];  // to store all the flags for every key
    
    public KeyManager() {
        keys = new boolean[256];
    }
    /**
     * Checks if a key was typed
     * @param e is a key event
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }
    /**
     * Checks if a key was pressed
     * @param e is a key event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        // set true to every key pressed
        keys[e.getKeyCode()] = true;
    }
    
    /**
     * Checks if a key was released
     * @param e is a key event
     */
    @Override
    public void keyReleased(KeyEvent e) {
        // set false to every key released
        keys[e.getKeyCode()] = false;
    }
    
    /**
     * to enable or disable moves on every tick
     */
    public void tick() {
        q = keys[KeyEvent.VK_Q];
        p = keys[KeyEvent.VK_P];
        a = keys[KeyEvent.VK_A];
        l = keys[KeyEvent.VK_L];
    }
}
