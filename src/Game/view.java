package Game;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.JFrame;

public class view extends JFrame{
    
    DrawPanel dp;
    
    public view() {
        super("Super Mario Bros 1-1");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        dp = new DrawPanel();
        add(dp);
        pack();
        loadFont();
    } 
    
    public void loadFont() {
        try {
            Font f = Font.createFont(Font.TRUETYPE_FONT, new File("resources\\Pixel Emulator.otf")).deriveFont(12f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("resources\\Pixel Emulator.otf")));
            dp.font = f;
        }
        catch (Exception e) {System.out.println("Failed to load game font.");}
    }
    
    public void setTiles(BufferedImage[][] a) {
        dp.tiles = a;
    } 
    
    public void setPlayer(Player p) {
        dp.player = p;
    }
    
    public void setBackground(BufferedImage a) {
        dp.background = a;
    }
    
    public void updateImage(BufferedImage a) { dp.a = a; }
    
    public void refresh() { dp.repaint(); }    
}