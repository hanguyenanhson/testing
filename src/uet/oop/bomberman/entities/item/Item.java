package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;

public class Item extends Entity {
    public Item(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {

    }

    @Override
    public Rectangle getHitBox() {
        return new Rectangle(x, y, Sprite.SCALED_SIZE-30, Sprite.SCALED_SIZE-30);
    }
}
