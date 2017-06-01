package game;
import java.awt.image.BufferedImage;

import utilities.Animator;

public class Item extends Entity {
    
    protected Animator animator;
    protected String type = "item";
    protected double gravity;
    protected boolean onGround;
    protected boolean remove = false;
    
    public Item(int _x, int _y, int _width, int _height) {
        super(_x, _y, _width, _height);
    }
    
    public void updatePosition() {}
    
    public BufferedImage getImage() {
        return animator.currentAnimation.getFrame();
    }
    
}
