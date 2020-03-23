/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Graphics;

/**
 *
 * @author antoniomejorado
 */
public class Player extends Item{

    private int direction;
    private int lives;
    private int score;
    private Game game;
    
    public Player(int x, int y, int direction, int width, int height,
                  int lives, int score, Game game) {
        super(x, y, width, height);
        this.direction = direction;
        this.lives = lives;
        this.score = score;
        this.game = game;
    }

    public int getDirection() {
        return direction;
    }
    
    public int getLives() {
        return lives;
    }
    
    public int getScore() {
        return score;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
    
    public void setLives(int lives) {
        this.lives = lives;
    }
    
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public void tick() {
        // moving player depending on flags, only recieves diagonal
        if (game.getKeyManager().q) {
           setY(getY() - 1);
           setX(getX() - 1);
        }
        if (game.getKeyManager().p) {
           setY(getY() - 1);
           setX(getX() + 1);
        }
        if (game.getKeyManager().a) {
           setY(getY() + 1);
           setX(getX() - 1);
        }
        if (game.getKeyManager().l) {
           setY(getY() + 1);
           setX(getX() + 1);
        }
        // does not permit the player go out of boundaries
        if (getX() + 60 >= game.getWidth()) {
            setX(game.getWidth() - 60);
        }
        else if (getX() <= -30) {
            setX(-30);
        }
        if (getY() + 80 >= game.getHeight()) {
            setY(game.getHeight() - 80);
        }
        else if (getY() <= -20) {
            setY(-20);
        }
    }
    /**
     * the image it renders for the player
     * @param g the graphic renderer
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.player, getX(), getY(), getWidth(), getHeight(), null);
    }
}
