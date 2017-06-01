package items;

import game.Item;
import utilities.Animator;

public class Flower extends Item{
    
    public Flower(int _x, int _y, int w, int h) {
        super(_x, _y, w, h);
        animator = new Animator("resources/itemAnim.txt", "resources/items.png", 16, 16, 5);
        animator.setAnimation("flower");
        type="flower";
    }
    
}
