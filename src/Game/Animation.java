package Game;
import java.awt.image.BufferedImage;


public class Animation {
    
    int frameDelay;
    int delay;
    int increment = 0;
    String name;
    int[] frames;
    BufferedImage[] sprites;
    
    
    public Animation(String _name, String _frames, BufferedImage[] _sprites, int f) {
        frameDelay = f;
        sprites = _sprites;
        name = _name;
        frames = new int[_frames.split(",").length];
        String[] a = _frames.split(",");
        for (int i = 0; i < frames.length; ++i)
            frames[i] = Integer.parseInt(a[i]);
            
    }
    
    public BufferedImage getFrame() {
        if (delay > frameDelay) {
            increment();
            delay = 0;
            return sprites[frames[increment]];
        }
        delay++;
        return sprites[frames[increment]];
    }
    
    public void increment() {
        if (increment < frames.length-1)
            increment++;
        else increment = 0;
    }
    //dont need anymore 
    public String toString() {
        String s = "";
        for (int i = 0; i < frames.length; ++i) s+=frames[i];
        return frameDelay + ", " + s;
    }
    
}