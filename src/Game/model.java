package game;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.*;
import javafx.util.Duration;
import blocks.Block;
import entities.Enemy;
import items.BrokenBrick;
import items.Fireball;
import items.Flower;
import items.Mushroom;
import items.SmallCoin;

public class model {
    
    Rectangle window;
    Player player;
    Level level;
    long startTime;
    int time = 400;
    int score = 0;
    int coins = 0;
    int[] keyData;
    Point hitBlock = null;
    
    public model() {
        startTime = System.currentTimeMillis();
        level = new Level("resources/Level1.txt", "resources/Level1-Blocks.png", "resources/specialBlockData.txt");
        player = new Player(125, 16, 16, 16);
        window = new Rectangle(0, 0, 250, 250);
        
        final JFXPanel fxPanel = new JFXPanel();
        String file = "resources/theme.wav";
        Media hit = new Media(new File(file).toURI().toString());
        MediaPlayer mp = new MediaPlayer(hit);
        
        mp.setOnEndOfMedia(new Runnable() {
            public void run() {
                mp.seek(Duration.ZERO);
            }
        });
        mp.play();
         
    }
    
    public String[] getData() {
        String[] data = new String[3];
                data[0] = "0";
                data[1] = "0";
                data[2] = "0";
        time = 400 - (int)(System.currentTimeMillis() - startTime)/1000;
        
        String t =  Integer.toString(time);
        int diff = 3 - Integer.toString(time).length();
        if (diff > 0) {
            for (int i = 0; i < diff; ++i) t = "0"+t;
        }
        data[2] = t;
        
        String s = Integer.toString(score);
        for (int i = 0; s.length() < 6; ++i)
            s = "0" + s;
        data[0] = s;
        
        String c = Integer.toString(coins);
        for (int i = 0; c.length() < 2; ++i)
            c = "0" + c;
        data[1] = c;
        
        return data;
    }
    
    public void applyPhysics() {
        player.updatePosition(keyData, window);
        for (Enemy e : level.enemies) e.updatePosition();
        for (Item i : level.items) i.updatePosition();
        window = player.window;
    }
    
    public void processCollisions() {
        
        player.onGround = false;
        
        /*
        LOOP 1: Classify which enemies have collides with the player
        */
        
        for (Enemy e : level.enemies) {
            Rectangle eRec = e.getRectangle();
            Rectangle pRec = player.getRectangle();
            if (eRec.intersects(pRec)) {
                e.collision = true;
                if (pRec.x > eRec.x) e.hitDirection = 1;
                else e.hitDirection = -1;
                if (pRec.y + pRec.height < eRec.y+eRec.height && player.dy > 0) {
                    e.topCollision=true;
                    //System.out.println("top collision");
                    } else {
                    //System.out.println("not top collision");
                        e.topCollision = false;
                        if (e.type.equals("goomba")) {
                            if (!e.dying) player.takeDamage();
                        } else if (e.type.equals("koopa")) {
                            if (!e.stopped) player.takeDamage();
                        }
                    }
            }
            else
                e.topCollision = false;
        }
        
        /*
        LOOP 1.5: Remove the items that are out of the world
        */
        
        ArrayList<Item> lostItems = new ArrayList<>();
        for (Item item: level.items) if (item.y > 300) lostItems.add(item);
        level.items.removeAll(lostItems);
        
        /*
        LOOP 1.75: Collisions between items and enemies - only fireballs and enemies thus far
        */
        
        ArrayList<Item> hitItems = new ArrayList<>();
        for (Enemy e : level.enemies) {
            for (Item i : level.items) {
                if (e.getRectangle().intersects(i.getRectangle())) {
                    hitItems.add(i);
                    e.dead = true;
                }
            }
        }
        
        level.items.removeAll(hitItems);
        
        /*
        LOOP 2: Remove the killed enemies, add points, and do the special actions for enemies
        */
        
        ArrayList<Enemy> deaths = new ArrayList<>();
        
        for (Enemy e : level.enemies) {
            
            if (e.dead) { 
                deaths.add(e); 
                if (e.type.equals("goomba")) score+=100;
                else if (e.type.equals("koopa")) score+=200;
            }
            else if (e.dying && !e.topCollision && !e.moving) e.hitAction();
            else if (e.topCollision) {
                
                if (e.type.equals("koopa")) {
                    //if (e.dying) {
                    if (e.collision) {
                        e.move();
                        
                    } else if (e.moving) {
                        e.stopped = true;
                    } else {
                        e.kill();
                        player.jump = true;
                        //player.dy = -12;
                        //player.y = player.prevY;
                    }
                } else if (!e.dying) {
                    e.kill();
                    player.jump = true;
                    //player.dy = -12;
                    //player.y = player.prevY;
                }
            }
        }
        
        level.enemies.removeAll(deaths);
        
        /*
        LOOP 2.5: Collisions of items and player
        */
        ArrayList<Item> usedItems = new ArrayList<>();
        for (Item item : level.items) {
            if (item.remove) usedItems.add(item);
            else if (item.getRectangle().intersects(player.getRectangle())) {
                if (!item.type.equals("fireball"))
                    usedItems.add(item);
                if (item.type.equals("mushroom")) player.hitMushroom();
                else if (item.type.equals("flower")) player.hitFlower();
                else if (item.type.equals("star")) player.mode = "i";
            }
        }
        level.items.removeAll(usedItems);
        
        /*
        LOOP 3: Check collisions between enemies
        */
        
        for (Enemy e : level.enemies) {
            for (Enemy e2 : level.enemies) {
                if (e != e2) {
                    if (e.getRectangle().intersects(e2.getRectangle())) {
                        if (e.type.equals("koopa") && e.moving) {
                            e2.kill();
                        } else
                            e.direction*= -1;
                    }
                }
            }
        }
        
        /*
        LOOP 4: Check collisions between player, items, and enemies with blocks
        */
         
        for (int i = 0; i < level.blocks.length; ++i) {
            for (int j = 0; j < level.blocks[0].length; ++j) {
                
                Block b = level.blocks[i][j];
                
                
                if (b != null) { //if the block is not empty
                    
                    for (Enemy e : level.enemies) {
                        Rectangle eRec = e.getRectangle();
                        Rectangle bRec = b.getRectangle();
                        if (eRec.intersects(bRec)) {
                            e.x = e.prevX;
                            e.direction*= -1;
                        }
                    }
                    
                    for (Item item : level.items) {
                        Rectangle bRec = b.getRectangle();
                        Rectangle iRec = item.getRectangle();
                        if (iRec.intersects(bRec)) {
                            
                            if (item.type.equals("mushroom") || item.type.equals("star")) {
                                if (item.prevY < bRec.y) {
                                item.onGround = true;
                                item.y = item.prevY;
                                }
                                else {
                                    item.dx *= -1;
                                }
                                item.x = item.prevX;
                            }
                            
                            if ( item.type.equals("fireball")) {
                                if (item.prevY < bRec.y) {
                                    item.dy = -5;
                                    item.y = item.prevY;
                                }
                                else {
                                    item.dx *= -1;
                                }
                                item.x = item.prevX;
                            }
                        }
                    }
                    
                    Rectangle pRec = player.getRectangle();
                    Rectangle bRec = b.getRectangle();
                    if (pRec.intersects(bRec)) { //COLLISION!
                        //System.out.println(b.type);
                        boolean topCollision = false;
                        boolean bottomCollision = false;
                        boolean sideCollision = false;
                        
                        player.x = player.prevX;
                        
                        if (player.prevY < bRec.y) {//collision happened by player going through the ground, so its on the ground
                            player.onGround = true;
                            bottomCollision = true;
                        }
                        
                        if (player.y - bRec.y + bRec.height > bRec.height) {
                            topCollision = true;
                        }
                        
                        if (player.x  < b.getRectangle().x - 1 || player.x  > b.getRectangle().x) {
                            sideCollision = true;
                        }
                        
                        if (bottomCollision) {
                            if (!player.dead) {
                                int y0 = pRec.y + pRec.height; //y value for the bottom of the box
                                int y1 = bRec.y; //y value for the top of the obstacle
                                int diff = y1 - y0; //difference between the values
                                if (diff < 0 && diff > -10) player.y += diff; //if the box is below the obstacle, put the box right on top of the obstacle
                            }
                        }
                        hitBlock = null;
                        if (topCollision) {
                            //System.out.println(level.blocks[i][j].coins+" coins");
                            //System.out.println(i + ", " + j);
                            if (level.blocks[i][j].coins > 0) {
                                coins++;
                                level.items.add(new SmallCoin(level.blocks[i][j].x, level.blocks[i][j].y - 16, 16, 16));
                                score+=200;
                                level.blocks[i][j].coins--;
                            } else if (level.blocks[i][j].item != null) {
                                if ((level.blocks[i][j].item.equals("m") || level.blocks[i][j].item.equals("f")) && !level.blocks[i][j].used) {
                                    level.blocks[i][j].used = true;
                                    if (player.mode.equals("s")) level.items.add(new Mushroom(level.blocks[i][j].x, level.blocks[i][j].y - 30, 16, 16));
                                    else if (player.mode.equals("b")) level.items.add(new Flower(level.blocks[i][j].x, level.blocks[i][j].y - 16, 16, 16));
                                    else if (player.mode.equals("f")) level.items.add(new Flower(level.blocks[i][j].x, level.blocks[i][j].y - 16, 16, 16));
                                }
                                else if (level.blocks[i][j].item.equals("f"))
                                    level.items.add(new Flower(level.blocks[i][j].x, level.blocks[i][j].y - 16, 16, 16));
                            }
                            else if (level.blocks[i][j].coins == 0) {
                                level.blocks[i][j].animator.setAnimation("end");
                            }
                            level.blocks[i][j].specialHit = true; 
                            hitBlock = new Point(j, i);
                            
                            if (player.mode.equals("b") || player.mode.equals("f"))
                                if (level.blocks[i][j].type.equals("Brick") && level.blocks[i][j].coins == -1) {
                                    level.items.add(new BrokenBrick(level.blocks[i][j].x, level.blocks[i][j].y, 8, 8, 1, "b3"));
                                    level.items.add(new BrokenBrick(level.blocks[i][j].x, level.blocks[i][j].y, 8, 8, 2, "b1"));
                                    level.items.add(new BrokenBrick(level.blocks[i][j].x, level.blocks[i][j].y, 8, 8, 3, "b4"));
                                    level.items.add(new BrokenBrick(level.blocks[i][j].x, level.blocks[i][j].y, 8, 8, 4, "b2"));
                                    level.blocks[i][j] = null;                                }
                            
                            int y0 = player.y; //y value for the top of the playerqwe
                            int y1 = bRec.y + bRec.height; // y value for the bottom of the obstacle
                            int diff = y1 - y0;
                            
                            player.y += diff;
                            player.dy = player.gravity; //stop player from continuing upwards
                        }
                        
                        if (sideCollision) {
                            player.dy = player.gravity;
                        }
                        
                    }
                    
                    Rectangle r0 = player.getRectangle();
                    Rectangle r = new Rectangle(r0.x, r0.y + 1, r0.width, r0.height);
                    
                    if (r.intersects(b.getRectangle())) player.onGround = true;
                }
            }
        }
    }
    
    public void processSpecialBlocks() {
        if (player.mode.equals("f") && keyData[4] == 1 && player.nextFireball) {
            level.items.add(new Fireball(player.x, player.y, 8, 8, player.flipped));
            player.nextFireball = false;
        }
        
        for (int i = 0; i < level.blocks.length; ++i)
            for (int j = 0; j < level.blocks[0].length; ++j) {
                if (level.blocks[i][j] != null && level.blocks[i][j].specialHit) {
                    level.blocks[i][j].hit();
                }
            }
    }
    
    public void checkEnd() {
        if (Math.abs(player.x - 3152) <= 2) {
            player.auto = true;
            player.x = 3152+16;
            player.dy = 2;
        } else if (Math.abs(player.x - 3265) <= 2) {
            player.end = true;
        }
    }
    
    public void restart() {
        
        startTime = System.currentTimeMillis();
        level = new Level("resources/Level1.txt", "resources/Level1-Blocks.png", "resources/specialBlockData.txt");
        this.score = 0;
        this.coins = 0;
        player = new Player(125, 16, 16, 16);
        player.end = false;
        window = new Rectangle(0, 0, 1200, 250);
    }
}
