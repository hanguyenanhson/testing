package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public class Doll extends Enemy{
    private int direction;

    public Doll(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        setSpeed(2);
        generateDirection();
        alive = true;
    }

    @Override
    public void update() {
        if(isAlive()) {
            if (direction == 0) goLeft();
            if (direction == 1) goRight();
        } else if(animated < 30){
            animated ++;
            img = Sprite.doll_dead.getFxImage();
        }else
            BombermanGame.enemies.remove(this);
    }

    public void goLeft() {
        super.goLeft();
        img = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, left++, 18).getFxImage();
    }

    public void goRight() {
        super.goRight();
        img = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, right++, 18).getFxImage();
    }

    @Override
    public void stay() {
        super.stay();
        generateDirection();
    }

    @Override
    public void generateDirection() {
        System.out.println(direction);
        if (direction == 0) direction = 1;
        else if (direction == 1) direction = 0;
    }

    @Override
    public void restartEnemy() {

    }
}
