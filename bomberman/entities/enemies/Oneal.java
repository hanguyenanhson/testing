package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.enemies.Enemy;
import uet.oop.bomberman.graphics.Sprite;


import java.awt.*;
import java.util.Random;
import java.util.List;

public class Oneal extends Enemy {
    private int prevBombX = 1;
    private int prevBombY = 1;
    private boolean changed = false;
    private int[][] AStarTemp = new int[BombermanGame.HEIGHT][BombermanGame.WIDTH];
    private int prevI = 0;
    AStar as;
    List<AStar.Node> path ;
    //private Rectangle onealRadius = new Rectangle(x - 160, y - 160, 160 * 1000000, 160 * 1000000);
    private int direction;
    public Oneal(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        //this.bomber = bomber;
        //setLayer(1);
        setSpeed(1);
        AStarTemp = BombermanGame.map;
        AStar as = new AStar(AStarTemp, this.x / Sprite.SCALED_SIZE,
                this.y / Sprite.SCALED_SIZE, true);
        List<AStar.Node> path = as.findPathTo(BombermanGame.bomberman.getX() / Sprite.SCALED_SIZE,
                BombermanGame.bomberman.getY() / Sprite.SCALED_SIZE);
        generateDirection();
    }

    public void goLeft() {
        super.goLeft();
        img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, left++, 18).getFxImage();
    }

    public void goRight() {
        super.goRight();
        img = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, right++, 28).getFxImage();
    }

    public void goUp() {
        super.goUp();
        img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, up++, 18).getFxImage();
    }

    public void goDown() {
        super.goDown();
        img = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, down++, 18).getFxImage();
    }
    @Override
    public void update() {
        generateDirection();
        if (direction == 0) goLeft();
        if (direction == 1) goRight();
        if (direction == 2) goUp();
        if (direction == 3) goDown();
        if (direction == 4) super.stay();
        if(! BombermanGame.bomberman.isAlive()) {
            restartEnemy();
        }

        if(isAlive()){

        } else if(animated < 30) {
            super.stay();

            animated++;
            img = Sprite.oneal_dead.getFxImage();
        } else {
            BombermanGame.enemies.remove(this);
        }
    }

    @Override
    public void generateDirection() {
        if (!(BombermanGame.bomberman.getX() / Sprite.SCALED_SIZE == prevBombX &&
                BombermanGame.bomberman.getY() / Sprite.SCALED_SIZE == prevBombY)) {
            as = new AStar(AStarTemp, this.x / Sprite.SCALED_SIZE,
                    this.y / Sprite.SCALED_SIZE, true);
            path = as.findPathTo(BombermanGame.bomberman.getX() / Sprite.SCALED_SIZE,
                    BombermanGame.bomberman.getY() / Sprite.SCALED_SIZE);
            prevBombX = BombermanGame.bomberman.getX() / Sprite.SCALED_SIZE;
            prevBombY = BombermanGame.bomberman.getY() / Sprite.SCALED_SIZE;
            changed = true;
            direction();
        } else {
            changed = false;
            direction();
        }
    }

    public void direction() {
        double xConverted = (double) Math.round(((double) this.x / Sprite.SCALED_SIZE) * 100) / 100;
        double yConverted = (double) Math.round(((double) this.y / Sprite.SCALED_SIZE) * 100) / 100;
        if (BombermanGame.bomberman.isAlive()) {
            if (changed) {
                prevI = 0;
            } else {
                if (path == null) {
                    super.stay();
                    direction = 4;
                } else if (prevI == path.size()) {
                    if ((double) BombermanGame.bomberman.getX() / Sprite.SCALED_SIZE - (double) x / Sprite.SCALED_SIZE < 0)
                        direction = 0;
                    if ((double) BombermanGame.bomberman.getX() / Sprite.SCALED_SIZE - (double) x / Sprite.SCALED_SIZE > 0)
                        direction = 1;
                    if ((double) BombermanGame.bomberman.getY() / Sprite.SCALED_SIZE - (double) y / Sprite.SCALED_SIZE < 0)
                        direction = 2;
                    if ((double) BombermanGame.bomberman.getY() / Sprite.SCALED_SIZE - (double) y / Sprite.SCALED_SIZE > 0)
                        direction = 3;
                    // lỗi không đi được trọn vẹn vào ô kill nhân vật
                } else if (path != null) {
                    double xPath = (double) Math.round((double) path.get(prevI).x * 100) / 100;
                    double yPath = (double) Math.round((double) path.get(prevI).y * 100) / 100;
                    if (xPath - xConverted == 0 && yPath - yConverted > 0) {
                        direction = 3;
                    } else if (xPath - xConverted == 0 && yPath - yConverted < 0) {
                        direction = 2;
                    } else if (xPath - xConverted < 0 && yPath - yConverted == 0) {
                        direction = 0;
                    } else if (xPath - xConverted > 0 && yPath - yConverted == 0) {
                        direction = 1;
                    } else if (xPath - xConverted == 0 && yPath - yConverted == 0) {
                        direction = 4;
                        prevI++;
                    }
                }
            }
        } else {
            restartEnemy();
        }
    }
    @Override
    public void restartEnemy() {
        super.stay();
        this.desX = startX * Sprite.SCALED_SIZE;
        this.desY = startY * Sprite.SCALED_SIZE;
    }

}



