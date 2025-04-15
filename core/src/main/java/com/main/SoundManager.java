package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;

public class SoundManager {

    private static final HashMap<String, Sound> soundMap = new HashMap<>();

    public static void load() {
        soundMap.put("click", Gdx.audio.newSound(Gdx.files.internal("MenuMusic/Click.mp3")));
        // Bạn có thể thêm nhiều âm khác nữa:
        // soundMap.put("hover", Gdx.audio.newSound(Gdx.files.internal("audio/hover.wav")));
    }

    public static void play(String soundName) {
        Sound sound = soundMap.get(soundName);
        if (sound != null) {
            sound.play();
        } else {
            System.out.println("⚠ Sound not found: " + soundName);
        }
    }

    public static void dispose() {
        for (Sound sound : soundMap.values()) {
            sound.dispose();
        }
        soundMap.clear();
    }
}
