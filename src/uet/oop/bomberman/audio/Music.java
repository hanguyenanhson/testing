package uet.oop.bomberman.audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static uet.oop.bomberman.audio.Music.Loopable.LOOP;

public class Music implements Runnable {

    // Tên file các audio
    public static final String BACKGROUND_MUSIC = "background_music";
    public static final String PLACE_BOMB = "place_bomb";
    public static final String POWER_UP = "power_up";
    public static final String EXPLOSION = "explosion";
    public static final String DEAD = "dead";
    public static final String ENEMY_DEAD = "dead2";
    public static final String CLICKED = "mc_click";
    public static final String MENU_BACKGROUND = "sweden";

    private static boolean _muted = false;

    private Clip clip;

    public enum Loopable {
        NONELOOP,
        LOOP;
    }

    // Mặc định không phát lại
    private Loopable loopable = LOOP;

    public Music(String fileName) {
        String path = "/audio/" + fileName + ".wav";

        try {
            URL defaultSound = getClass().getResource(path);
            AudioInputStream sound = AudioSystem.getAudioInputStream(defaultSound);
            // load the sound into memory (a Clip)
            clip = AudioSystem.getClip();
            clip.open(sound);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Malformed URL: " + e);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Input/Output Error: " + e);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Line Unavailable Exception Error: " + e);
        }
    }

    public Loopable getLoopable() {
        return loopable;
    }

    public void setLoopable(Loopable loopable) {
        this.loopable = loopable;
    }

    public void play(){
        if (!_muted) {
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void loop(){
        if (!_muted) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
    public void stop(){
        clip.stop();
    }

    @Override
    public void run() {
        switch (loopable) {
            case LOOP:
                this.loop();
                break;
            case NONELOOP:
                this.play();
                break;
        }
    }
    public static boolean isMuted() {
        return _muted;
    }

    public void set_muted(boolean _muted) {
        Music._muted = _muted;
    }
}