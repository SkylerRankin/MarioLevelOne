package blocks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import utilities.Animator;

public class Block {
    
    public String type;
    
    public int x;
    public int y;
    public int width;
    public int height;
    public int coins = -1;
    public String item;
    public BufferedImage image;
    public boolean collision = false;
    public boolean used = false;
    public boolean specialHit = false; //block was hit from underneath by player
    public Animator animator;
    
    public Block(int _x, int _y, int _w, int _h, BufferedImage bf) {
        x = _x;
        y = _y;
        width = _w;
        height = _h;
        image = bf;
    }
    
    public BufferedImage getImage() {
        return image;
    }
    
    public Rectangle getRectangle() {
        return new Rectangle(x, y, width, height);
    }
    
    public void hit() {}
    
    public void drawRect(Graphics g) {
        g.setColor(Color.red);
        g.drawRect(x, y, width, height);
    }
}
