package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;

public class SoundManager {

    private static final HashMap<String, Sound> soundMap = new HashMap<>();
    private static float defaultVolume = 0.8f; // 🎚️ Âm lượng mặc định: 40%

    public static void load() {
        soundMap.put("click", Gdx.audio.newSound(Gdx.files.internal("MenuMusic/Click.mp3")));
        soundMap.put("shoot", Gdx.audio.newSound(Gdx.files.internal("ShootSound/shootsound.mp3")));
    }

    public static void play(String soundName) {
        play(soundName, defaultVolume); // Gọi hàm overload bên dưới
    }

    public static void play(String soundName, float volume) {
        Sound sound = soundMap.get(soundName);
        if (sound != null) {
            sound.play(volume); // ✅ volume 0.0f → 1.0f
        } else {
            System.out.println("⚠ Sound not found: " + soundName);
        }
    }

    public static void setDefaultVolume(float volume) {
        defaultVolume = Math.max(0f, Math.min(1f, volume)); // clamp về [0, 1]
    }

    public static void dispose() {
        for (Sound sound : soundMap.values()) {
            sound.dispose();
        }
        soundMap.clear();
    }
}
