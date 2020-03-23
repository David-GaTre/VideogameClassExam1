/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Graphics;

/**
 *
 * @author davidg
 */
public class Ally extends Item{

    private int direction;
    private Game game;
    /**
     * The ally constructor
     * @param x the x position
     * @param y the y positon
     * @param direction where the sprite is going
     * @param width the width of the sprite
     * @param height the height of the sprite
     * @param game the game it belongs
     */
    public Ally(int x, int y, int direction, int width, int height, Game game) {
        super(x, y, width, height);
        this.direction = direction;
        this.game = game;
    }
    /**
     * 
     * @return 
     */
    public int getDirection() {
        return direction;
    }


    public void setDirection(int direction) {
        this.direction = direction;
    }

    // makes the movement of the allies
    @Override
    public void tick() {
        this.setX(this.getX() + ((int) (Math.random() * 3) + 1));
        this.setY(this.getY());
        
        if (this.getX() > game.getWidth()) {
            this.setX(-10);
            this.setY((int) (Math.random() * game.getHeight()));
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.ally, getX(), getY(), getWidth(), getHeight(), null);
    }
}