package blocks;

import java.awt.image.BufferedImage;

public class PipeBaseBlock extends Block {
    
    String item;
    int coins = 0;
    
    public PipeBaseBlock(int x, int y, int w, int h, BufferedImage bf) {
        super(x, y, w, h, bf);
        type="Pipe Base";
    }
}
