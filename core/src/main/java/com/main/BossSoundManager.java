package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class BossSoundManager {

    private Music phase1Music;
    private Music phase2Music;
    private Sound shakeSound;

    private boolean hasShaken = false;
    private boolean hasStartedPhase1 = false;
    private boolean hasStartedPhase2 = false;

    public BossSoundManager() {
        phase1Music = Gdx.audio.newMusic(Gdx.files.internal("BossMusic/phase1.mp3"));
        phase2Music = Gdx.audio.newMusic(Gdx.files.internal("BossMusic/phase2.mp3"));
        shakeSound = Gdx.audio.newSound(Gdx.files.internal("BossMusic/Undertale - Omega Flowey's Laugh.mp3"));

        phase1Music.setLooping(true);
        phase2Music.setLooping(true);
    }

    public void startPhase1Music() {
        if (!hasStartedPhase1) {
            phase1Music.play();
            hasStartedPhase1 = true;
        }
    }

    public void playShakeSoundOnce() {
        if (!hasShaken) {
            shakeSound.play();
            hasShaken = true;
        }
    }

    public void setPhase2Started() {
        if (!hasStartedPhase2) {
            if (hasStartedPhase1) {
                phase1Music.stop();
            }

            phase2Music.play();
            hasStartedPhase2 = true;
        }
    }

    public void stopAll() {
        phase1Music.stop();
        phase2Music.stop();
    }

    public void dispose() {
        phase1Music.dispose();
        phase2Music.dispose();
        shakeSound.dispose();
    }
}
