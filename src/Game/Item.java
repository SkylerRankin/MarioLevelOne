package Game;
import java.awt.image.BufferedImage;

public class Item extends Entity {
    
    Animator animator;
    String type = "item";
    double gravity;
    boolean onGround;
    boolean remove = false;
    
    public Item(int _x, int _y, int _width, int _height) {
        super(_x, _y, _width, _height);
    }
    
    public void updatePosition() {}
    
    public BufferedImage getImage() {
        return animator.currentAnimation.getFrame();
    }
    
}
