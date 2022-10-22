package uet.oop.bomberman.entities;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.audio.Music;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;
import java.util.ArrayList;

import static uet.oop.bomberman.audio.Music.DEAD;
import static uet.oop.bomberman.audio.Music.PLACE_BOMB;


public class Bomber extends AnimatedEntity {
    private int timeCounter = 0;
    private int bombNum ;

    public int getBombNum() {
        return bombNum;
    }

    public void setBombNum(int bombNum) {
        this.bombNum = bombNum;
    }

    private boolean isBomb = false;
    private ArrayList<Bomb> bombs = new ArrayList<>();
    private int radius;
    private KeyCode direction = null;
    private int timeAfterDie = 0;
    public Bomber(int x, int y, Image img ) {
        super( x, y, img);
        setLayer(1);
        setSpeed(2);
        bombNum = 1;
        setRadius(1);
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    // xu ly animation cua bomberman
    public void update() {

        if(direction==KeyCode.RIGHT) {
            goRight();
        }
        if (direction == KeyCode.LEFT) {
            goLeft();
        }
        if (direction == KeyCode.DOWN) {
            goDown();
        }
        if (direction == KeyCode.UP) {
            goUp();
        }
        if(isBomb) {
            placeBomb();
        }
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            if (!bomb.isAlive()) {
                bombs.remove(bomb);
                bombNum++;
            }
        }
        //
        if(!isAlive()) {
            direction = null;
            timeAfterDie ++;
            die();
            img = Sprite.player_dead1.getFxImage();
        }
    }
    // sử lý di chuyển cho bomb
    public void handleKeyPressedEvent(KeyCode keyCode) {

        if (keyCode == KeyCode.LEFT || keyCode == KeyCode.RIGHT
                || keyCode == KeyCode.UP || keyCode == KeyCode.DOWN ||keyCode==KeyCode.SPACE) {
            this.direction = keyCode;
        }
        if (keyCode == KeyCode.SPACE) {
            isBomb = true;
        }
    }
    // sử lý di chuyển cho bomb
    public void handleKeyReleasedEvent(KeyCode keyCode) {
        if (direction == keyCode) {
            if (direction == KeyCode.LEFT) {
                img = Sprite.player_left.getFxImage();
            }
            if (direction == KeyCode.RIGHT) {
                img = Sprite.player_right.getFxImage();
            }
            if (direction == KeyCode.UP) {
                img = Sprite.player_up.getFxImage();
            }
            if (direction == KeyCode.DOWN) {
                img = Sprite.player_down.getFxImage();
            }
            direction = null;
        }
        if (keyCode == KeyCode.SPACE) {
            isBomb = false;
        }
    }
    public void goLeft() {
        super.goLeft();
        img = Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, xx++, 20).getFxImage();
    }

    public void goRight() {
        super.goRight();
        img = Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2,xx++ , 20).getFxImage();
    }

    public void goUp() {
        super.goUp();
        img = Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, xx++, 20).getFxImage();
    }

    public void goDown() {
        super.goDown();
        img = Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2,xx++ , 20).getFxImage();
    }
    public void placeBomb() {
        if (bombNum > 0) {
            int xB = (int) Math.round((x + 4) / (double) Sprite.SCALED_SIZE);
            int yB = (int) Math.round((y + 4) / (double) Sprite.SCALED_SIZE);
            for (Bomb bomb : bombs) {
                if (xB * Sprite.SCALED_SIZE == bomb.x && yB * Sprite.SCALED_SIZE == bomb.y) return;
            }
            bombs.add(new Bomb(xB, yB, Sprite.bomb.getFxImage(),this.radius));
            new Music(PLACE_BOMB).play();
            bombNum--;
        }
    }
    public ArrayList<Bomb> getBombs() {
        return bombs;
    }
@Override
public Rectangle getHitBox() {
    return new Rectangle(desX, desY, Sprite.SCALED_SIZE-10 , Sprite.SCALED_SIZE);
}
    public void die() {
        timeCounter++;
        direction = null;
        stay();
        if(timeCounter == 1) {
            new Music(DEAD).play();
        }
        if(timeAfterDie <= 30) {
            img = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2,
                    Sprite.player_dead3, timeAfterDie, 20).getFxImage();
        }

    }
}


