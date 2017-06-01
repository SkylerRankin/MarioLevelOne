package utilities;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import blocks.*;
import game.Entity;

public class TileManager {
    
    final String[] tileKey = {"Ground", "Brick", "QuestionBlock", "Block", "Pipe"};
    final String[] dataKey = {"blank", "Ground", "Brick", "Question-Used", "Block", "Question-1", "Question-2", "Question-3", "Break", "Pipe-Top-Left", "Pipe-Top-Right", "Pipe-Bottom-Left", "Pipe-Bottom-Right"};
    final String alpha = "abcdefghijklm";
    BufferedImage[] images;
    BufferedImage[][] tiles = null;
    Block[][] blocks = null;
    String[][] data;
    
    public TileManager(String path, String sheet) {
        loadData(path);
        SpriteSheetManager sm = new SpriteSheetManager();
        images = sm.getSprites(16, 16, sheet);
    }
    
    public void loadData(String path) {
        ReadFile reader = new ReadFile(path);
        String[] lines = null;
        try { lines = reader.OpenFile(); }
        catch (IOException e) { System.out.println("Failed to load level data from "+path); }
        if (lines != null) {
            data = new String[lines.length][lines[0].length()];
            for (int i = 0; i < lines.length; i++)
                for (int j = 0; j < lines[0].length(); j++)
                    data[i][j] = Character.toString(lines[i].charAt(j));
            System.out.println("Level data loaded: " + data[0].length + " x " + data.length);
        }
    }
    
    public BufferedImage[][] loadTiles() {
        tiles = new BufferedImage[data.length][data[0].length];
        for (int i = 0; i < data.length; ++i) {
            for (int j = 0; j < data[0].length; ++j) {
                String code = data[i][j];
                if (isNumeric(code)) {
                    if (Integer.parseInt(code) != 0)
                    tiles[i][j] = images[Integer.parseInt(code)-1];
                } else {
                    tiles[i][j] = images[convertAlpha(code) - 1];
                }
            }
        }
        return tiles;
    }
    
    public Entity[][] loadBlocks2() {
        Entity[][]blocks1 = new Entity[data.length][data[0].length];
        for (int i = 0; i < data.length; ++i) {
            for (int j = 0; j < data[0].length; ++j) {
                if (data[i][j].equals("0")) blocks1[i][j] = null;
                else blocks1[i][j] = new Entity(j*16, i*16, 16, 16);
            }
        }
        
        return blocks1;
    }
    
    public Block[][] loadBlocks() {
        blocks = new Block[data.length][data[0].length];
        for (int i = 0; i < data.length; ++i) {
            for (int j = 0; j < data[0].length; ++j) {
                blocks[i][j] = null;
                
                switch (data[i][j]) {
                    case "1":
                        blocks[i][j] = new GroundBlock(j*16, i*16, 16, 16, tiles[i][j]);
                        break;
                    case "2":
                        blocks[i][j] = new BrickBlock(j*16, i*16, 16, 16, tiles[i][j]);
                        break;
                    case "4":
                        blocks[i][j] = new DefaultBlock(j*16, i*16, 16, 16, tiles[i][j]);
                        break;
                    case "5":
                        blocks[i][j] = new QuestionBlock(j*16, i*16, 16, 16, tiles[i][j]);
                        break;
                    case "9":
                        blocks[i][j] = new PipeTopLeftBlock(j*16, i*16, 16, 16, tiles[i][j]);
                        break;
                    case "a":
                        blocks[i][j] = new PipeTopRightBlock(j*16, i*16, 16, 16, tiles[i][j]);
                        break;
                    case "b":
                    case "c":
                        blocks[i][j] = new PipeBaseBlock(j*16, i*16, 16, 16, tiles[i][j]); break;
                        
                }
            }
        }
        
        return blocks;
    }
    
    public Block[][] addSpecialBlocks(String path, Block[][] blocks) {
        ReadFile reader = new ReadFile(path);
        String[] lines = null;
        try { lines = reader.OpenFile(); }
        catch (IOException e) { System.out.println("Failed to load special block data from "+path); }
        if (lines != null) {
            for (int i = 0; i <lines.length; ++i) {
                String line = lines[i];
                int comma = line.indexOf(",");
                int pos = line.indexOf(":");
                int y = Integer.parseInt(line.substring(0, comma));
                int x = Integer.parseInt(line.substring(comma+1, pos));
                if (line.substring(pos+1, pos+2).matches("\\d")) {
                    blocks[y][x].coins = Integer.parseInt(line.substring(pos+1, line.length()));
                }
                else
                    blocks[y][x].item = line.substring(pos+1, pos+2);
            }
        }
        return blocks;
    }
    
    //returns the type of block from coordinates
    public String getType(Point p) {
        
        return data[p.x][p.y];
    }
    
    //TODO: if indexOf() returns -1, the function still returns a usable value, meaning errors in the data sheet would still produce a valid tile
    public int convertAlpha(String a) {
        int b = alpha.indexOf(a);
        return b+10;
    }
    public boolean isNumeric(String str) {
        return str.matches("[+-]?\\d*(\\.\\d+)?");
    }
}