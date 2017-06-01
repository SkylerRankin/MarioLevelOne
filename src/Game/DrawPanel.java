package Game;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;
import Blocks.*;

public class DrawPanel extends JPanel{
    
    String[] data;
    int width = 250; //1240
    int height = 232;
    Font font;
    int offset;
    BufferedImage a = null;
    BufferedImage background = null;
    BufferedImage[][] tiles = null;
    Block[][] blocks = null;
    Player player = null;
    Frame frame;
    ArrayList<Enemy> enemies = new ArrayList<>();
    ArrayList<Item> items = new ArrayList<>();
    
    public DrawPanel() {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.WHITE);
        frame = new Frame(width, height, 250, 250);
        blocks = new Block[0][0];
        data = new String[3];
        data[0] = "0";
        data[1] = "0";
        data[2] = "0";
    }
    
    public void resetView() {
        frame = new Frame(width, height, 50, 100);
    }
    
    
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g); 
        
        frame.setFocus(player.x, player.y, player.xDirection);
        
        frame.drawImage(g, background, 0, 0);
        
        //g.drawString(player.x + ", " + player.y, 10, 50);
        
        for (int i = 0; i < blocks.length; ++i)
            for (int j = 0; j < blocks[0].length; ++j) {
                Block b = blocks[i][j];
                if (b != null) {
                    //frame.drawImage(g, b.image, j*16, i*16);
                    frame.drawImage(g, b.getImage(), b.x, b.y);
                }
            }
        
        for (Enemy e : enemies) {
            if (e.flipped) frame.drawImage(g, e.getImage(), e.x, e.y, true);
            else frame.drawImage(g, e.getImage(), e.x, e.y);
        }
        
        for (Item i : items) frame.drawImage(g, i.getImage(), i.x, i.y);
        
        if (!player.flipped) frame.drawImage(g, player.getImage(), player.x, player.y);
        else frame.drawImage(g, player.getImage(), player.x, player.y, true);
        //player.drawRect(g);
        
        drawHUD(g);
        
    }
    
    public void drawHUD(Graphics g) {
        g.setColor(Color.white);
        g.setFont(font);
        g.drawString("Mario", 20, 20);
        g.drawString(data[0], 15, 30);
        g.drawString("X"+data[1], width - 140, 30);
        g.drawString("World", width-90, 20);
        g.drawString("1-1", width-80, 30);
        g.drawString("Time", width-40, 20);
        g.drawString(data[2], width-33, 30);
        
    }
    
}
