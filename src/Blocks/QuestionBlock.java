package Blocks;

import java.awt.image.BufferedImage;

import Game.Animator;

public class QuestionBlock extends Block {
    
    String item;
    int coins = 0;
    int moveMax = 10;
    int moveCount = 0;
    int moveDir = -1;
    boolean end = false;
    int initY;
    
    public QuestionBlock(int x, int y, int w, int h, BufferedImage bf) {
        super(x, y, w, h, bf);
        initY = y;
        type = "Question";
        animator = new Animator("resources/questionBlockAnim.txt", "resources/Level1-Blocks.png", 16, 16, 10);
        animator.setAnimation("blink");
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
            end = true;
            animator.setAnimation("end");
        }
    }
    
    public BufferedImage getImage() {
        return animator.currentAnimation.getFrame();
    }
}
