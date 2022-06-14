package projectiles;

import java.awt.geom.Point2D;

public class Explosion {

    private Point2D.Float pos;
    private int exploTick, exploIndex;

    public Explosion(Point2D.Float pos) {
        this.pos = pos;
    }

    public void update() {
        exploTick++;
        if (exploTick >= 6) {
            exploTick = 0;
            exploIndex++;
        }
    }

    public int getIndex() {
        return exploIndex;
    }

    public Point2D.Float getPos() {
        return pos;
    }
}