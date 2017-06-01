package Game;
public class SmallCoin extends Item {
    
    double grav = 0.5;
    double dy = 0;
    int count = 0;
    
    public SmallCoin(int _x, int _y, int w, int h) {
        super(_x, _y, w, h);
        animator = new Animator("resources/itemAnim.txt", "resources/items.png", 16, 16, 5);
        animator.setAnimation("smallcoin");
        type="smallcoin";
        dy = -5;
    }
    
    public void updatePosition() {
        count++;
        y+=dy;
        dy+=grav;
        if (count>=22) remove=true;
    }
    
}
