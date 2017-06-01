package Blocks;

import java.awt.image.BufferedImage;
import Game.Animator;

public class BrickBlock extends Block{
    
    boolean end = false;
    
    
    int moveMax = 10;
    int moveCount = 0;
    int moveDir = -1;
    
    int initX;
    int initY;
    
    public BrickBlock(int x, int y, int w, int h, BufferedImage bf) {
        super(x, y, w, h, bf);
        initX = x;
        initY = y;
        type = "Brick";
        animator = new Animator("resources/questionBlockAnim.txt", "resources/Level1-Blocks.png", 16, 16, 10);
        animator.setAnimation("brick");
    }
    
    public void hit() {
        if (!end) move();
    }
    
    public void move() {
        this.y += 1*moveDir;
        moveCount++;
        if (moveCount >= moveMax) {
            moveDir = 1;
        }
        if (moveDir == 1 && y == initY) {
            
                this.specialHit = false;
                moveCount = 0;
                moveDir = -1;
                if (coins == 0)  {
                    end = true;
                    animator.setAnimation("end");
                }
        }
    }
    
    public BufferedImage getImage() {
        return animator.currentAnimation.getFrame();
    }
    
}
