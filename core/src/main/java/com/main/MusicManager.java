package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicManager {
    private static Music backgroundMusic;

    public static void playMenuMusic() {
        if (backgroundMusic == null) {
            backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("MenuMusic/mii.mp3"));
            backgroundMusic.setLooping(true);
            backgroundMusic.setVolume(0.5f); // tuỳ chỉnh âm lượng nếu cần
            backgroundMusic.play();
        }
    }

    public static void stopMenuMusic() {
        if (backgroundMusic != null && backgroundMusic.isPlaying()) {
            backgroundMusic.stop();
        }
    }

    public static void dispose() {
        if (backgroundMusic != null) {
            backgroundMusic.dispose();
        }
    }
}
