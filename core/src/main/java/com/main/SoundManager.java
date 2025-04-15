package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;

public class SoundManager {

    private static final HashMap<String, Sound> soundMap = new HashMap<>();
    private static float defaultVolume = 0.8f; // 🎚️ Âm lượng mặc định: 40%
    private static boolean isMuted = false;

    public static void load() {
        soundMap.put("click", Gdx.audio.newSound(Gdx.files.internal("MenuMusic/Click.mp3")));
        soundMap.put("shoot", Gdx.audio.newSound(Gdx.files.internal("ShootSound/shootsound.mp3")));
        soundMap.put("click2", Gdx.audio.newSound(Gdx.files.internal("MenuMusic/Click2.wav")));
    }

    public static void play(String soundName) {
        if (isMuted) return;
        play(soundName, defaultVolume); // Gọi hàm overload bên dưới
    }

    public static void play(String soundName, float volume) {
        Sound sound = soundMap.get(soundName);
        if (sound != null) {
            sound.play(volume);
        } else {
            System.out.println("⚠ Sound not found: " + soundName);
        }
    }
    public static void setMuted(boolean muted) {
        isMuted = muted;
    }

    public static void setDefaultVolume(float volume) {
        defaultVolume = Math.max(0f, Math.min(1f, volume));
    }

    public static void dispose() {
        for (Sound sound : soundMap.values()) {
            sound.dispose();
        }
        soundMap.clear();
    }
}
