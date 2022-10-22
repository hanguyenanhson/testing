package uet.oop.bomberman.entities;

import uet.oop.bomberman.graphics.Sprite;
import javafx.scene.image.Image;
import java.awt.*;

public abstract class AnimatedEntity extends Entity {
    protected int animate = 0;
    protected final int MAX_ANIMATE = 7500; //save the animation status and dont let this get too big
    protected int desX = x;
    protected int desY = y;
    protected int speed;
    protected int left = 0;
    protected int right = 0;
    protected int up = 0;
    protected int down = 0;

    public AnimatedEntity(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        alive = true;
    }

public void setSpeed(int speed) {
        this.speed = speed;
}
    public void goLeft() {
        desX = x - speed;
    }

    public void goRight() {
        desX = x + speed;
    }
    public void goUp() {
        desY = y - speed;
    }

    public void goDown() {
        desY = y + speed;
    }

    public void move() {
        x = desX;
        y = desY;
    }

    public void stay() {
        desX = x;
        desY = y;
    }

    public Rectangle getHitBox() {
        return new Rectangle(desX, desY, Sprite.SCALED_SIZE-15  , Sprite.SCALED_SIZE);
    }

    protected void animate() {
        if(animate < MAX_ANIMATE) {
            animate++;
        } else {
            animate = 0; //reset animation
        }
    }

}
