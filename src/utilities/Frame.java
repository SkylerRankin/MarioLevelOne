package utilities;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class Frame {
    
    
    Point absoluteFocus;
    Point prevAbsoluteFocus;
    
    int dx;
    int offset;
    int width;
    int height;
    
    public Frame(int _width, int _height, int _fx, int _fy) {
        width = _width;
        height = _height;
        absoluteFocus = new Point(_width/2, _height/2);
        prevAbsoluteFocus = new Point(_width/2, _height/2);
        offset = width/2 - 75;
    }
    
    public void setFocus(int x, int y, int _dx) {
        if (_dx >= 0) {
            //player moved right, follow player
            if (x > absoluteFocus.x) {
                if (x + offset <= 3240) { //do not shift if at the end of the level
                    prevAbsoluteFocus = absoluteFocus;
                    absoluteFocus = new Point(x-1, y); //the minus 1 removes that weird jitter
                }
            }
            else absoluteFocus = prevAbsoluteFocus;
            
        } else if (_dx < 0) {
            //player moved left, do not follow player
            absoluteFocus = prevAbsoluteFocus;
        }
        dx = _dx;
    }
    
    public void printFocus() {
        System.out.println(absoluteFocus.x+", "+absoluteFocus.y);
    }
    
    public void drawImage(Graphics g, BufferedImage bf, int _x, int _y) {
        g.drawImage(bf, _x - absoluteFocus.x + offset, _y, null);
    }
    
    public void drawImage(Graphics g, BufferedImage bf, int _x, int _y, boolean flip) {
        int x = _x - absoluteFocus.x + offset;
        g.drawImage(bf, x + bf.getWidth(), _y, -bf.getWidth(), bf.getHeight(), null);
    }
    
}
