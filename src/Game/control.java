package Game;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class control {
    
    private model model;
    private view view;
    private Timer timer;
    private InputManager im;
    
    public control(model m, view v) {
        model = m;
        view = v;
        im = new InputManager();
        timer = new Timer(10, new TimerListener());
        view.setBackground(model.level.background);
        view.addKeyListener(im.getKeyListener());
        view.setPlayer(model.player);
        timer.start();
    }
    
    public class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
             /*
                GAME LOOP
                --get the input data from the InputManager
                --apply the physics to the entities *no collisions
                --apply the collision physics 
                --make changes for the special blocks
                --update the view with the new data
                --draw the view with the new data
            */
             
             if (model.player.dead) { model.restart(); view.dp.resetView(); }
             
            model.keyData = im.getKeyData();
            model.player.updateAnimations(im.getKeyData());
            model.applyPhysics();
            model.processCollisions();
            model.processSpecialBlocks();
            model.checkEnd();
            view.dp.blocks = model.level.blocks;
            view.dp.items = model.level.items;
            view.dp.enemies = model.level.enemies;
            view.setPlayer(model.player);
            view.dp.data = model.getData();
            view.refresh();
            
        }
    }
    
}
