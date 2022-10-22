package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;

public class Brick extends Entity{
    public Brick(int x, int y, Image img) {
        super(x, y, img);
        setLayer(3);
        alive = true;
    }

    @Override
    public void update() {
        if(!isAlive()){
            if(animated < 45) {
                animated++;
                img = Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1
                        , Sprite.brick_exploded2, animated, 45).getFxImage();
            } else {
                BombermanGame.stillObjects.remove(this);
            }
        }
    }

    @Override
    public Rectangle getHitBox() {
        return new Rectangle(x, y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE-10);
    }
}
