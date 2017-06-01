package items;

import game.Item;
import utilities.Animator;

public class Fireball extends Item{
    
     
    
        public Fireball(int _x, int _y, int w, int h, boolean dir) {
        super(_x, _y, w, h);
        animator = new Animator("resources/itemAnim.txt", "resources/fire.png", 8, 8, 5);
        animator.setAnimation("fire");
        type="fireball";
        if (dir) dx=-3;
        else dx=3;
        gravity = .8;
    }
        
        public void updatePosition() {
            prevX = x;
            prevY = y;
            x+=dx;
            dy+=gravity;
            y+=dy;
        }
        
        
}
