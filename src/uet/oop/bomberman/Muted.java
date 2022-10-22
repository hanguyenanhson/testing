package uet.oop.bomberman;

public class Muted {
    public boolean mutedSound;
    public boolean mutedMusic;

    public Muted() {
        mutedSound = false;
        mutedMusic = false;
    }

    public boolean isMutedSound() {
        return mutedSound;
    }

    public void setMutedSound(boolean mutedSound) {
        this.mutedSound = mutedSound;
    }

    public boolean isMutedMusic() {
        return mutedMusic;
    }

    public void setMutedMusic(boolean mutedMusic) {
        this.mutedMusic = mutedMusic;
    }
}

