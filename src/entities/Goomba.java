package entities;

import utilities.Animator;

public class Goomba extends Enemy {
    
    int deathCount = 0;
    
    public Goomba(int _x, int _y, int _dir) {
        super(_x, _y, 16, 16);
        type = "goomba";
        this.direction = _dir;
        this.dx = 1;
        animator = new Animator("resources/enemyAnim.txt", "resources/enemies16.png", 16, 16, 5);
        animator.setAnimation("goombaWalk");
    }
    
    public void hitAction() {
        deathCount++;
        if (deathCount == 20) {
            dead = true;
            System.out.println("remove enemy");
        }
    }
     
    public void kill() {
        
        dying = true;
        animator.setAnimation("death");
    }
    
}
