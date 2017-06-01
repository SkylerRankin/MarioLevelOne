package items;

import game.Item;
import utilities.Animator;

//todo: new package with all the items
public class BrokenBrick extends Item {
    
    double gravity = 0.3;
    int dist = 0;
    
    public BrokenBrick(int _x, int _y, int _width, int _height, int dir, String loc) {
        super(_x, _y, _width, _height);
        animator = new Animator("resources/itemAnim.txt", "resources/pieces.png", 8, 8, 5);
        animator.setAnimation(loc);
        type = "piece";
        
        switch(dir) {
            case 1: dx=1; dy=-3; break; //up right
            case 2: dx=-1; dy=-3; break; //up left
            case 3: dx=-1; dy=-1; break; //down left
            case 4: dx=1; dy=-1; break; //down right
        }
        
    }
    
    public void updatePosition() {
        dist+=dy;
        x += dx;
        y += dy;
        dy+=gravity;
        if (dist > 30) remove=true;
    }
}
