package items;

import game.Entity;
import game.Item;
import utilities.Animator;

public class Mushroom extends Item {
    
    public Mushroom(int _x, int _y, int _width, int _height) {
        super(_x, _y, _width, _height);
        animator = new Animator("resources/itemAnim.txt", "resources/items.png", 16, 16, 5);
        animator.setAnimation("mushroom");
        dx = 2;
        type = "mushroom";
        gravity = 0.5;
    }
    
    public void updatePosition() {
        prevX = x;
        prevY = y;
        x+=dx;
        if (!onGround) {
            dy+=gravity;
            y+=dy;
        } else {
            dy=0;
        }
        onGround = false;
    }
}
