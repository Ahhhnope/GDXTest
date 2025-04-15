package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicManager {
    private static Music backgroundMusic;
    private static float currentVolume = 0.5f;
    private static float fadeDuration = 1f;  // Thời gian fade (giây)
    private static float fadeTimer = 0f;
    private static boolean isMuted = false;

    private static boolean fadingIn = false;
    private static boolean fadingOut = false;

    public static void playMenuMusic() {
        if (isMuted) return; // 🔇 Đừng play nếu đang mute

        if (backgroundMusic == null) {
            backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("MenuMusic/mii.mp3"));
            backgroundMusic.setLooping(true);
        }

        if (!backgroundMusic.isPlaying()) {
            backgroundMusic.setVolume(0f);
            currentVolume = 0f;
            fadeTimer = 0f;
            fadingIn = true;
            backgroundMusic.play();
        }
    }

    public static void setMuted(boolean muted) {
        isMuted = muted;
        if (backgroundMusic != null) {
            if (isMuted) {
                backgroundMusic.setVolume(0f);
                backgroundMusic.pause();
            } else {
                backgroundMusic.setVolume(currentVolume);
                if (!backgroundMusic.isPlaying()) {
                    backgroundMusic.play();
                }
            }
        }
    }

    public static void setVolume(float volume) {
        currentVolume = Math.max(0f, Math.min(1f, volume)); // Clamp
        if (!isMuted && backgroundMusic != null) {
            backgroundMusic.setVolume(currentVolume);
        }
    }

    public static void stopMenuMusic() {
        if (backgroundMusic != null && backgroundMusic.isPlaying()) {
            fadingOut = true;
            fadeTimer = 0f;
        }
    }

    public static void update(float delta) {
        if (backgroundMusic == null || isMuted) return;

        if (fadingIn) {
            fadeTimer += delta;
            float progress = Math.min(fadeTimer / fadeDuration, 1f);
            currentVolume = progress * 0.5f;
            backgroundMusic.setVolume(currentVolume);
            if (progress >= 1f) {
                fadingIn = false;
            }
        }

        if (fadingOut) {
            fadeTimer += delta;
            float progress = Math.min(fadeTimer / fadeDuration, 1f);
            currentVolume = (1f - progress) * 0.5f;
            backgroundMusic.setVolume(currentVolume);
            if (progress >= 1f) {
                fadingOut = false;
                backgroundMusic.stop();
            }
        }
    }

    public static void dispose() {
        if (backgroundMusic != null) {
            backgroundMusic.dispose();
        }
    }
}
