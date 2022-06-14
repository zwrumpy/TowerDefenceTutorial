package projectiles;

import enemies.Enemy;
import helpz.LoadSave;
import managers.ProjectileManager;
import objects.Projectile;
import objects.Tower;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helpz.Constants.Projectiles.*;
import static helpz.Constants.Towers.*;

public class Bomb {

    private Playing playing;
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private ArrayList<Explosion> explosions = new ArrayList<Explosion>();
    private BufferedImage[] proj_imgs, explo_imgs;
    private int proj_id = 0;

    // Temp variables
    private boolean callTrue;
    private long lastCall;

    public Bomb(Playing playing) {
        this.playing = playing;
        importImgs();
    }

    public void update() {
        for (Projectile p : projectiles)
            if (p.isActive()) {
                if (isProjHittingEnemy(p)) {
                    p.setActive(false);
                }
            }

        for (Explosion e : explosions)
            if (e.getIndex() < 7)
                e.update();
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        for (Projectile p : projectiles)
            if (p.isActive()) {
                if (p.getProjectileType() == BOMB) {
                    g2d.drawImage(proj_imgs[p.getProjectileType()], (int) p.getPos().x - 16, (int) p.getPos().y - 16, null);
                    return;
                }
            }
        drawExplosions(g2d);
    }


    private void importImgs() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        proj_imgs = new BufferedImage[3];

        for (int i = 0; i < 3; i++)
            proj_imgs[i] = atlas.getSubimage((7 + i) * 32, 32, 32, 32);
        importExplosion(atlas);
    }

    private void importExplosion(BufferedImage atlas) {
        explo_imgs = new BufferedImage[7];

        for (int i = 0; i < 7; i++)
            explo_imgs[i] = atlas.getSubimage(i * 32, 32 * 2, 32, 32);

    }

    private void damageEnemy(Projectile projectile, Enemy enemy) {
        if (projectile.getProjectileType() == BOMB) {

            explosions.add(new Explosion(projectile.getPos()));
            explodeOnEnemies(projectile);
            enemy.hurt(projectile.getDmg());
            return;
        }

    }

    private void drawExplosions(Graphics2D g2d) {
        for (Explosion e : explosions)
            if (e.getIndex() < 7)
                g2d.drawImage(explo_imgs[e.getIndex()], (int) e.getPos().x - 16, (int) e.getPos().y - 16, null);
    }

    private void explodeOnEnemies(Projectile p) {
        for (Enemy e : playing.getEnemyManger().getEnemies()) {
            if (e.isAlive()) {
                float radius = 40.0f;
                float xDist = Math.abs(p.getPos().x - e.getX());
                float yDist = Math.abs(p.getPos().y - e.getY());
                float realDist = (float) Math.hypot(xDist, yDist);

                if (realDist <= radius)
                    e.hurt(p.getDmg());
            }
        }
    }




    private boolean isProjHittingEnemy(Projectile p) {
        for (Enemy e : playing.getEnemyManger().getEnemies()) {
            if (e.isAlive() && e.getBounds().contains(p.getPos())) {
                damageEnemy(p, e);
                return true;
            }
        }
        return false;
    }
}
