package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.AnimatedEntity;

public abstract class Enemy extends AnimatedEntity {
    protected int startX;
    protected int startY;
    public Enemy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        startX = xUnit;
        startY = yUnit;
    }


    public abstract void generateDirection();
    public abstract void restartEnemy();
}
