package entities;
/*

hiding, stopped, dying, moving

hitAction()
kill()
move()

*/

import utilities.Animator;

public class Koopa extends Enemy {
    int deathCount = 0;
    public boolean hiding = false;
    int revivalTime = 200;
    
    public Koopa(int _x, int _y, int _dir) {
        super(_x, _y, 16, 32);
        type = "koopa";
        this.direction = _dir;
        this.dx = 1;
        stopped = false;
        animator = new Animator("resources/enemyAnim.txt", "resources/koopa.png", 16, 32, 5);
        animator.setAnimation("koopaWalk");
    }
    
    
    @Override
    public void updatePosition() {
        prevX = x;
        if (direction==-1) flipped = false;
        else flipped = true;
        if (!hiding && !stopped)
            this.x += dx*direction;
    }
    
    @Override
    public void hitAction() {
        deathCount++;
            if (deathCount >= revivalTime) {
                animator.setAnimation("koopaWalk");
                dx=1;
                hiding = false;
                dying = false;
                deathCount = 0;
            }
    }
    
    @Override
    public void kill() {
        
        dying = true;
        hiding = true;
        animator.setAnimation("koopaHiding");
        
    }
    
    @Override
    public void move() {
        System.out.println("move");
        dx=2*hitDirection*-1;
        moving = true;
        hiding = false;
        animator.setAnimation("koopaHiding");
    }
    
}