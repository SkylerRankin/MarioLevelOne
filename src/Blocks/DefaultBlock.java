package blocks;

import java.awt.image.BufferedImage;

public class DefaultBlock extends Block{
    
    public DefaultBlock(int x, int y, int w, int h, BufferedImage bf) {
        super(x, y, w, h, bf);
        type="Default";
        
    }
    
}