package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.entities.Music.DEAD;
import static uet.oop.bomberman.entities.Music.EXPLOSION;

public class Bomb extends Entity {
    private int timeCounter = 0;
    private int radius;
    public Bomb(int x, int y, Image img , int radius ) {
        super( x, y, img);
        alive = true;
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
    @Override
    public void update() {
        timeCounter++;
        if (timeCounter == 100) {
            explode();
        }
        img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, xx++, 20).getFxImage();
    }

    public void explode() {
        new Music(EXPLOSION).play();
        Flame e = new Flame(x, y);
        e.setRadius(radius);
        e.render_explosion();
        alive = false;
    }
}
