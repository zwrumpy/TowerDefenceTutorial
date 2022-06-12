package scenes;

import main.Game;
import ui.MyButton;

import java.awt.*;

public class Debugger {
    Game game;
    boolean debugging = false;

    public Debugger(Game game) {
        this.game = game;
    }

    public boolean isDebugging(){
        return this.debugging;
    }

    public void setDebugging(Boolean toggle){
        this.debugging = toggle;
    }

    public void draw(Graphics g) {
        if (debugging == true) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(10, 10, 180,250);
            g.setFont(new Font("LucidaSans", Font.BOLD, 10));
            g.setColor(Color.WHITE);
            g.drawString("FPS: "+game.FPS, 20, 20);
            g.drawString("TICKS: "+game.TICKS, 20, 20+15);
        }
    }
}
