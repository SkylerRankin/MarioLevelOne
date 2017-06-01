package game;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import blocks.*;
import entities.Enemy;
import entities.Goomba;
import entities.Koopa;
import utilities.SpriteSheetManager;
import utilities.TileManager;

public class Level {
    
    /*
    
    LEVEL
    --load the level data from text file
    --convert level data to 2 2d arrays of tile images (static and dynamic)
    --pass those arrays to the view to be displayed
    --handle information for special block hits
    
    */
    
    TileManager tm;
    int width; //number of tiles horizontally
    int height; //number of tiles vertically
    int coinsCollected = 0;
    int score = 0;
    
    BufferedImage background;
    BufferedImage[][] tiles;
    Entity[][] blocks2;
    Block[][] blocks;
    ArrayList<Enemy> enemies = new ArrayList<>();
    ArrayList<Item> items = new ArrayList<>();
    
    public Level(String data, String sheet, String special) {
        tm = new TileManager(data, sheet); //parameters are urls for the data info and the tile sprite sheet
        tiles = tm.loadTiles();
        background = new SpriteSheetManager().getSprites(3392, 232, "resources/background.png")[0];
        blocks2 = tm.loadBlocks2();
        blocks = tm.loadBlocks();
        blocks = tm.addSpecialBlocks(special, blocks);
        enemies.add(new Koopa(500, 174, 1));
        enemies.add(new Goomba(550, 192, 1));
        enemies.add(new Goomba(413, 192, 1));
        enemies.add(new Goomba(708, 192, 1));
        enemies.add(new Goomba(650, 192, 1));
        enemies.add(new Koopa(830, 174, 1));
        enemies.add(new Goomba(880, 192, 1));
    }
    
}
