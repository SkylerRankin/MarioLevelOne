package game;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import utilities.Animator;

public class Player extends Entity {
	
    String mode = "s";
    Rectangle window;
    int xDirection = 1;
    double gravity = 0.5;
    boolean onGround = false;
    boolean jump = false;
    boolean dead = false;
    boolean nextFireball = true;
    boolean auto = false;
    boolean end = false;
    int endCount = 0;
    long deathStart;
    int invincibility = 0;
    Point deathPoint;
    Animator animator;
    
    public Player(int _x, int _y, int _width, int _height) {
        super(_x, _y, _width, _height);
        animator = new Animator("resources/MarioAnim.txt", "resources/smallMario.png", 16, 16, 5);
        animator.addSprites("resources/largeMario.png", 16, 32);
        animator.setAnimation(mode+"_idle");
    }
    
    public void updateAnimations(int[] keyData) {
        if (end) {
            endCount++;
            animator.setAnimation(mode+"_idle");
            if (endCount >= 50)
                dead = true;
        } else {
            if (!auto) updateAnimationsPlayer(keyData);
            else updateAnimationsAuto();
        }
        
    }
    
    public void updateAnimationsPlayer(int[] keyData) {
        if (dead && !animator.getAnimation().equals(mode+"_death")) {
            deathStart = System.currentTimeMillis();
            deathPoint = new Point(x, y);
            animator.setAnimation(mode+"_death");
        }
        else if (!dead) {
        
        //this should be changed so that you get the names from the file rather than typing them in right here
        
        if (keyData[0] == 1) {
            flipped = true;
            if (!animator.getAnimation().equals(mode+"_run"))
            animator.setAnimation(mode+"_run");
        } else if (keyData[1] == 1) {
            if (!animator.getAnimation().equals(mode+"_jump"))
            animator.setAnimation(mode+"_jump");
        } else if (keyData[2] == 1) {
            flipped = false;
            if (!animator.getAnimation().equals(mode+"_run"))
            animator.setAnimation(mode+"_run");
        }
        if (keyData[0] + keyData[1] + keyData[2] + keyData[3]== 0) {
            if (!animator.getAnimation().equals(mode+"_idle"))
            animator.setAnimation(mode+"_idle");
        }
        
        }
    }
    
    public void updateAnimationsAuto() {
        if (!onGround &&!animator.currentAnimation.equals(mode+"_slide")) animator.setAnimation(mode+"_slide");
        if (onGround && !animator.currentAnimation.equals(mode+"_run")) {
            animator.setAnimation(mode+"_run");
            dy = 1;
        }
    }
    
    public void updatePosition(int[] keyData, Rectangle _window) {
        if (end) {
            
        } else {
            if (!auto) updatePositionPlayer(keyData, _window);
            else updatePositionAuto();
        }
        
    }
    
    public void updatePositionAuto() {
        
        if (!onGround) {
            dx = 0;
            dy = 1;
        } else {
            dx = 1;
            dy = 0;
        }
        y += dy;
        x += dx;
    }
    
    public void updatePositionPlayer(int[] keyData, Rectangle _window) {
        if (dead) {
            long t = System.currentTimeMillis() - deathStart;
            //x = Parabola((int)t, deathPoint.x, deathPoint.y).x;
            //y = Parabola((int)t, deathPoint.x, deathPoint.y).y;
            System.out.println(x+", "+y);
        } else {
            window = _window;
            dx = 0;
        
         /*
            initial jumping values
            if (keyData[1] == 1 && onGround) dy = -12;
            else if (keyData[1] == 0) if (dy < -3) dy = -3;
        */
            if (keyData[4] == 0) nextFireball = true;
            if (keyData[0] == 1) dx = -2;
            if ((keyData[1] == 1 && onGround) || jump) {dy = -8; jump = false;}
            else if (keyData[1] == 0) if (dy < -3) dy = -3;
            if (keyData[2] == 1) dx = 2;
            prevX = x;
            prevY = y;
        
            if (!onGround)
            dy += gravity;
        
            if (onGround) {
                x += dx;
                y += dy;
                if (y > prevY) y = prevY;
            }
        
            if (!onGround) {
                x += dx;
                y += dy;
            }
        
            if (x > prevX) xDirection = 1;
            else if (x < prevX) xDirection = -1;
        
            if (x < window.x) {
                x = prevX; y = prevY;
            } else if (dx > 0 && x > window.x + window.width/2) {
                int tx = window.x;
                int ty = window.y;
                int tw = window.width;
                int th = window.height;
                window = new Rectangle(tx + (int)dx, ty, tw, th);
            }
        }
        
        if (y > 240) dead = true;
        
        
    }
    
    public void hitMushroom() {
        mode = "b";
        this.height = 32;
        this.y-=16;
    }
    
    public void hitFlower() {
        mode = "f";
        this.height = 32;
    }
    
    public void takeDamage() {
        if (invincibility > 0)
            invincibility--;
        else {
            if (mode.equals("s")) dead = true;
            else if (mode.equals("b")) {
                mode = "s";
                height = 16;
                y+=16;
            }
            else if (mode.equals("f")) mode = "b";
            if (invincibility == 0) invincibility = 30;
        }
    }
    
    //function to get y values for a parametric parabola
    public Point Parabola(int _t, int a, int b) {
        int t = (int)(_t / 10);
        System.out.println(t);
        int _x = t + a;
        int _y = t*t + b;
        _y = a - _y;
        return new Point((int)_x, (int)_y);
    }
    
    public BufferedImage getImage() {
        switch (mode) {
            case "small":
                break;
            case "big":
                break;
            case "fire":
                break;
            case "invincible":
                break;
        }
        return animator.currentAnimation.getFrame();
    }
    
}
