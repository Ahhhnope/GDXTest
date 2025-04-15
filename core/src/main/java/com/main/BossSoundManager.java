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
    private Sound fireballshot;
    private Sound windshot;
    private Sound explode;
    private Sound bomberthrow;
    private Sound sonicboomsound;
    private Sound heal;
    public BossSoundManager() {
        phase1Music = Gdx.audio.newMusic(Gdx.files.internal("BossMusic/phase1.mp3"));
        phase2Music = Gdx.audio.newMusic(Gdx.files.internal("BossMusic/phase2.mp3"));
        shakeSound = Gdx.audio.newSound(Gdx.files.internal("BossMusic/Undertale - Omega Flowey's Laugh.mp3"));

        windshot = Gdx.audio.newSound(Gdx.files.internal("SoundEffectsSkills/dd2_betsy_wind_attack_2.wav"));
        fireballshot = Gdx.audio.newSound(Gdx.files.internal("SoundEffectsSkills/dd2_betsy_fireball_shot_0.wav"));
        explode = Gdx.audio.newSound(Gdx.files.internal("SoundEffectsSkills/dd2_explosive_trap_explode_0.wav"));
        bomberthrow = Gdx.audio.newSound(Gdx.files.internal("SoundEffectsSkills/dd2_goblin_bomber_throw_2.wav"));
        sonicboomsound = Gdx.audio.newSound(Gdx.files.internal("SoundEffectsSkills/dd2_sonic_boom_blade_slash_0.wav"));
        heal = Gdx.audio.newSound(Gdx.files.internal("Heal/heal.wav"));
        phase1Music.setLooping(true);
        phase2Music.setLooping(true);
    }

    public void startPhase1Music() {
        if (!hasStartedPhase1) {
            phase1Music.play();
            hasStartedPhase1 = true;
        }
    }
    public void pause() {
        if (phase1Music.isPlaying()) phase1Music.pause();
        if (phase2Music.isPlaying()) phase2Music.pause();
    }

    public void resume() {
        if (!phase2Music.isPlaying() && hasStartedPhase2) {
            phase2Music.play();
        } else if (!phase1Music.isPlaying() && hasStartedPhase1 && !hasStartedPhase2) {
            phase1Music.play();
        }
    }
    public void playShakeSoundOnce() {
        if (!hasShaken) {
            shakeSound.play();
            hasShaken = true;
        }
    }
    public void playHeal(){
        heal.play(1f);
    }
    public void playFireballShot() {
        fireballshot.play(0.8f); // hoặc chỉnh âm lượng nhỏ hơn nếu quá to
    }

    public void playWindShot() {
        windshot.play(0.8f);
    }

    public void playExplosion() {
        explode.play(0.8f);
    }

    public void playBomberThrow() {
        bomberthrow.play(0.8f);
    }

    public void playSonicBoom() {
        sonicboomsound.play(0.8f);
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
        heal.dispose();

    }
}
