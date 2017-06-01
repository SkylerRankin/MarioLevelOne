package Game;

import java.awt.image.BufferedImage;

public class Enemy extends Entity {
    
    /*
    
    Enemy
        --movement
        --action for collisions (reverse direction)
    
    */
    
    Animator animator;
    public int direction;
    public int hitDirection = 1;
    
    public boolean dead = false;
    public boolean dying = false;
    public boolean moving = false;
    public boolean stopped = false;
    public boolean collision = false;
    public boolean topCollision = false;
    
    public String type;
    
    public Enemy(int _x, int _y, int _width, int _height) {
        super(_x, _y, _width, _height);
        
    }
    
    public void updatePosition() {
        prevX = x;
        if (direction==-1) flipped = false;
        else flipped = true;
        this.x += dx*direction;
    }
    
    public void kill() {}
    public void hitAction() {}
    public void move() {}
    
    public BufferedImage getImage() {
        return animator.currentAnimation.getFrame();
    }
    
}