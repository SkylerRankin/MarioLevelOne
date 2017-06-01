package utilities;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class SpriteSheetManager {
    
    public BufferedImage[] getSprites(int width, int height, String path) {
        BufferedImage sheet = loadSheet(path);
        if (sheet == null) return null;
        int col = sheet.getWidth() / width;
        int row = sheet.getHeight() / height;
        ArrayList<BufferedImage> temp = new ArrayList<>();
        BufferedImage[] imgs = new BufferedImage[row*col];
        for (int i = 0; i < col; ++i) {
            for (int j = 0; j < row; ++j) {
                BufferedImage img = sheet.getSubimage(i * width, j * height, width, height);
                temp.add(img);
            }
        }
       for (int i = 0; i < temp.size(); ++i) imgs[i] = temp.get(i);
       return imgs;
    }
    
    public BufferedImage loadSheet(String path) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(path));
        }
        catch (IOException e) {System.out.println("Failed to load image \"" + path + "\"");}
        return img;
    }
    
}