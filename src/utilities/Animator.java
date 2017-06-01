package utilities;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Animator {
    
    ArrayList<Animation> animations;
    public Animation currentAnimation;
    SpriteSheetManager sm;
    ReadFile reader;
    String[] frames;
    BufferedImage[] sprites;
    int delay;
    
    public Animator(String data, String sprite, int w, int h, int delay) {
        animations = new ArrayList<>();
        reader = new ReadFile(data);
        sm = new SpriteSheetManager();
        this.delay = delay;
        try { frames = reader.OpenFile();  }
        catch (IOException e) { System.out.println("Failed to read animation data from "+data); }
        
        sprites = sm.getSprites(w, h, sprite);
        
        addAnimations();
    }
    
    public void addAnimations() {
        for (int i = 0; i < frames.length; ++i) {
            int pos = frames[i].indexOf("=");
            String name = frames[i].substring(0, pos);
            String order = frames[i].substring(pos+1, frames[i].length());
            animations.add(new Animation(name, order, sprites, delay));
        }
    }
    
    public String getAnimation() {
        return currentAnimation.name;
    }
    
    public void setAnimation(String name) {
        for (Animation a : animations) {
            if (name.equals(a.name))
                currentAnimation = a;
        }
        //System.out.println("animation not found");
    }
    
    public ArrayList<Animation> getAnimations() { return animations; }
    
    public void addSprites(String path, int width, int height) {
        SpriteSheetManager tempSM = new SpriteSheetManager();
        BufferedImage[] tempSprites = tempSM.getSprites(width, height, path);
        BufferedImage[] newSprites = new BufferedImage[sprites.length + tempSprites.length];
        for (int i = 0; i < sprites.length; ++i)
            newSprites[i] = sprites[i];
        for (int i = sprites.length; i < sprites.length+tempSprites.length; ++i)
            newSprites[i] = tempSprites[i - sprites.length];
        sprites = newSprites;
        animations.clear();
        addAnimations();
        
    }
}
