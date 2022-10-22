package uet.oop.bomberman;

//test for menu

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.audio.Music;
import uet.oop.bomberman.entities.enemies.Balloon;
import uet.oop.bomberman.entities.enemies.Doll;
import uet.oop.bomberman.entities.enemies.Enemy;
import uet.oop.bomberman.entities.enemies.Oneal;
import uet.oop.bomberman.entities.item.BombItem;
import uet.oop.bomberman.entities.item.FlameItem;
import uet.oop.bomberman.entities.item.SpeedItem;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.*;

import static uet.oop.bomberman.audio.Music.*;

public class BombermanGame extends Application  {
    public static int WIDTH = 31;
    public static Music music = new Music(Music.BACKGROUND_MUSIC);
    public static Music menuMusic = new Music(MENU_BACKGROUND);
    public static Muted muted = new Muted();
    public Music music() {
        return music;
    }
    public void setMusic(Music _music) {
        music = _music;
    }
    public static int HEIGHT = 13;
    private int xStart = 0;
    private int yStart = 0;
    public static int level = 1;
    private GraphicsContext gc;
    private Canvas canvas;
    private Scanner scanner;
    public static List<Entity> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();
    public static final List<Enemy> enemies = new ArrayList<>();
    public static final List<Flame> flame = new ArrayList<>();
    public static int[][] map = new int[HEIGHT][WIDTH];
    //start flame radius, neu co powerups thi tang len
    public int flameRadius = 1;
    public int startBomb = 1;
    public int startSpeed = 2;
    public int startFlame = 1;
    public static Bomber bomberman = new Bomber(1 , 1, Sprite.player_right.getFxImage());
    public static LevelController lc = new LevelController();
    public boolean finishedLevel = false;
    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        Scene lvscene = createSceneLevel();
        lc.setLvScene(lvscene);
        load(level);

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Menu.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Bomberman");
            menuMusic.loop();
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
        startGame(stage, lvscene);
    }

    public void startGame(Stage stage, Scene lvscene) {
        ArrayList<Bomb> bombs = bomberman.getBombs();
        entities.add(bomberman);
        AnimationTimer timer = new AnimationTimer() {
            public long prevTime = 0;
            @Override
            public void handle(long now) {
                long dt = now - prevTime;
                if (dt > 10000000) {
                    render();
                    update();
                    if (muted.isMutedMusic()) {
                        //music.stop();
                    } else {
                        //music.loop();
                    }
                    if (finishedLevel) {
                        Scene lvscene = createSceneLevel();
                        lc.setLvScene(lvscene);
                        load(level);
                        stage.setScene(lvscene);
                        stage.show();
                        finishedLevel = false;
                    }
                    prevTime = now;
                }
            }
        };
        timer.start();
        lvscene.setOnKeyPressed(event -> {
            bomberman.handleKeyPressedEvent(event.getCode());
        });
        lvscene.setOnKeyReleased(event -> bomberman.handleKeyReleasedEvent(event.getCode()));
    }

    public Scene createSceneLevel() {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();
        // Tao level container
        Group lv = new Group();
        lv.getChildren().add(canvas);
        // Tao scene
        Scene scene = new Scene(lv);
        return scene;
    }

    public void load(int _level) {
        try {
            scanner = new Scanner(new FileReader("res/levels/level" + _level + ".txt"));
        } catch (FileNotFoundException e) {
            System.out.println(e);
            e.printStackTrace();
        }
        scanner.nextInt();
        HEIGHT = scanner.nextInt();
        WIDTH = scanner.nextInt();
        enemies.removeAll(enemies);
        stillObjects.removeAll(stillObjects);
        flame.removeAll(flame);
        scanner.nextLine();

        createMap();
    }

    public void createMap() {
        createMatrixCoordinates();
        for (int i = 0; i < HEIGHT; i++) {
            String r = scanner.nextLine();
            for (int j = 0; j < WIDTH; j++) {
                if (r.charAt(j) == '#') {
                    stillObjects.add(new Wall(j, i, Sprite.wall.getFxImage()));
                    map[i][j] = -1;
                } else {
                    stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                    if (r.charAt(j) == '*') {
                        stillObjects.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        map[i][j] = -1;
                    }
                    if (r.charAt(j) == 'x') {
                        stillObjects.add(new Portal(j, i, Sprite.portal.getFxImage()));
                        stillObjects.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        map[i][j] = -1;
                    }
                    if (r.charAt(j) == '1') {
                        enemies.add(new Balloon(j, i, Sprite.balloom_left1.getFxImage()));
                        //map[i][j] = 0;
                    }
                    if (r.charAt(j) == '2') {
//                        enemies.add(new Oneal(j, i, Sprite.oneal_left1.getFxImage(), myBomber));
                        enemies.add(new Oneal(j, i, Sprite.oneal_left1.getFxImage()));
                        //map[i][j] = 0;
                    }
                    //if (r.charAt(j) == '3') {
                        //enemies.add(new Minvo(j, i, Sprite.minvo_left1.getFxImage()));
                        //map[i][j] = 0;
                    //}
                    //if (r.charAt(j) == '4') {
                        //enemies.add(new Kondoria(j, i, Sprite.kondoria_left1.getFxImage()));
                        //map[i][j] = 0;
                    //}
                    if (r.charAt(j) == '5') {
                        enemies.add(new Doll(j, i, Sprite.doll_left1.getFxImage()));
                        //map[i][j] = 0;
                    }
                    if (r.charAt(j) == 'b') {
                        stillObjects.add(new BombItem(j, i, Sprite.powerup_bombs.getFxImage()));
                        stillObjects.add(new Brick(i, j, Sprite.brick.getFxImage()));
                        map[i][j] = -1;
                    }
                    if (r.charAt(j) == 'f') {
                        stillObjects.add(new FlameItem(j, i, Sprite.powerup_flames.getFxImage()));
                        stillObjects.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        map[i][j] = -1;
                    }
                    if (r.charAt(j) == 's') {
                        stillObjects.add(new SpeedItem(j, i, Sprite.powerup_speed.getFxImage()));
                        stillObjects.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        map[i][j] = -1;
                    }
                    if (r.charAt(j) == 'p') {
                        bomberman = new Bomber(j, i, Sprite.player_right.getFxImage());
                        xStart = j;
                        yStart = i;
                        map[i][j] = 0;
                    }
//                    if(r.charAt(j) == ' ') {
//                        map[i][j] = 0;
//                    }
                }
            }
        }
        //stillObjects.sort(new Layer());
    }

    public void createMatrixCoordinates() {
        for(int i = 0; i < HEIGHT; i ++) {
            for(int j = 0; j < WIDTH; j ++) {
                map[i][j] = 0;
            }
        }
    }

    public void update() {
        bomberman.move();
        // animation cho cac entity
        entities.forEach(Entity::update);
        for (int i = 0; i < flame.size(); i ++) {
            flame.get(i).update();
        }
        for (int i = 0; i < enemies.size(); i ++) {
            enemies.get(i).move();
            enemies.get(i).update();
        }
        //bomberman.update();
        ArrayList<Bomb> bombs = bomberman.getBombs();
        // cac hoat dong cua bom
        for(Bomb bomb : bombs) {
            bomb.update();
        }
        for (int i = 0; i < stillObjects.size(); i ++) {
            stillObjects.get(i).update();
        }
        handleCollision();
        checkCollisionFlame();
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
        enemies.forEach(g -> g.render(gc));
        // cai nay cung chua xu ly duoc
        ArrayList<Bomb> bombs = bomberman.getBombs();
        for (Bomb bomb : bombs) {
            bomb.render(gc);
        }
        flame.forEach(g -> g.render(gc));
    }
// handle collision, da xong phan cuc da
    public void handleCollision() {
        Rectangle bomber = bomberman.getHitBox();
        for (Entity stillObject : stillObjects) {
            Rectangle r = stillObject.getHitBox();
            if (bomber.intersects(r)) {
                if (stillObject instanceof Wall || stillObject instanceof Brick) {
                    bomberman.stay();
                } else if (stillObject instanceof Portal) {
                    if (enemies.size() == 0) {
                        level++;
                        finishedLevel = true;
                    }
                }
            }
        }
        //Bomber vs Item
        for (int i = 0 ; i < stillObjects.size(); i++) {
            Rectangle item = stillObjects.get(i).getHitBox();
                if(item.intersects(bomber)) {
                    if(stillObjects.get(i) instanceof  FlameItem) {
                        new Music(POWER_UP).play();
                        bomberman.setRadius(startFlame+=2);
                        stillObjects.remove(stillObjects.get(i));
                    }
                    if(stillObjects.get(i) instanceof BombItem) {
                        new Music(POWER_UP).play();
                        bomberman.setBombNum(startBomb+=2);
                        stillObjects.remove(stillObjects.get(i));

                    }
                    if(stillObjects.get(i) instanceof  SpeedItem) {
                        new Music(POWER_UP).play();
                        bomberman.setSpeed(startSpeed+=2);
                        stillObjects.remove(stillObjects.get(i));
                    }
                }
            }
        //Bomber vs Enemies
        for (Enemy enemy : enemies) {
            Rectangle r2 = enemy.getHitBox();
            if (bomber.intersects(r2)) {
                bomberman.stay();
                bomberman.setAlive(false);
                startBomb = 1;
                startFlame = 1;
                startSpeed = 1;
                if(!bomberman.isAlive()) {
                    Timer count = new Timer();
                    count.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            entities.remove(bomberman);
                            bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
                            entities.add(bomberman);
                            new Music(DEAD).play();
                            count.cancel();
                        }
                    }, 500,1);
                    //sound
                }
            }
        }
        //Enemies vs Bombs
        for (Enemy enemy : enemies) {
            Rectangle r2 = enemy.getHitBox();
            for (Bomb bomb : bomberman.getBombs()) {
                Rectangle r3 = bomb.getHitBox();
                //!bomb.isAllowedToPassThrough(enemy)
                if (r2.intersects(r3)) {
                    enemy.stay();
                    break;
                }
            }
        }
        //Enemies vs StillObjects
        for (Enemy enemy : enemies) {
            Rectangle r2 = enemy.getHitBox();
            for (Entity stillObject : stillObjects) {
                Rectangle r3 = stillObject.getHitBox();
                if (r2.intersects(r3)) {
                    if (stillObject instanceof Wall || stillObject instanceof Brick) {
                        enemy.stay();
                    } else {
                        enemy.move();
                    }

                }
            }
        }
    }

    /**
     * flame collision.
     *
     */
    public void checkCollisionFlame() {
        //flame vs stillobjects
        for (Flame flame : flame) {
            Rectangle r1 = flame.getHitBox();
            for (Entity stillObject : stillObjects) {
                Rectangle r2 = stillObject.getHitBox();
                //!stillObject instanceof Item
                if (r1.intersects(r2)) {
                    stillObject.setAlive(false);
                    map[stillObject.getY() / Sprite.SCALED_SIZE][stillObject.getX() / Sprite.SCALED_SIZE] = 0;
                }
            }
            //flame vs enemies
            for (Enemy enemy : enemies) {
                Rectangle r2 = enemy.getHitBox();
                if (r1.intersects(r2)) {
                    enemy.setAlive(false);
                    new Music(ENEMY_DEAD).play();
                }
            }
            //flame vs bomberman
            Rectangle r2 = bomberman.getHitBox();
            if (r1.intersects(r2)) {
                bomberman.setAlive(false);
                startBomb = 1;
                startFlame = 1;
                startSpeed = 1;
            }
                if (!bomberman.isAlive()) {
                    bomberman.stay();
                    Timer count = new Timer();
                    count.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            entities.remove(bomberman);
                            bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
                            entities.add(bomberman);
                            count.cancel();

                        }

                    }
                    , 500, 1);

                    break;

                }
            }
        }
        }

