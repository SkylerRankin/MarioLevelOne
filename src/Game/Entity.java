package game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Entity {
    
    protected int x;
    protected int y;
    protected int prevX;
    protected int prevY;
    protected double dx;
    protected double dy;
    
    int width;
    int height;
    
    public boolean flipped = false;
    
    public Entity(int _x, int _y, int _width, int _height) {
        x= _x; y = _y;
        width = _width;
        height = _height;
    }
    
    public Rectangle getRectangle() {
        return new Rectangle(x, y, width, height);
    }
    
    public void drawRect(Graphics g) {
        g.setColor(Color.red);
        g.drawRect(x, y, width, height);
    }
    
    public String getRectString() {
        return x + ", " + y + " : " + width + ", " + height;
    }   
}