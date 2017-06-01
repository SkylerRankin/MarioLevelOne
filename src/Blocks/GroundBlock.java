package blocks;

import java.awt.image.BufferedImage;

public class GroundBlock extends Block{
    
    public GroundBlock(int x, int y, int w, int h, BufferedImage bf) {
        super(x, y, w, h, bf);
        type="Ground";
    }
    
}