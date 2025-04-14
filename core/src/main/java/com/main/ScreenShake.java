package com.main;

import com.badlogic.gdx.math.MathUtils;

public class ScreenShake {
    private float duration = 0f;
    private float maxDuration = 0f;
    private float intensity = 0f;

    public void start(float duration, float intensity) {
        this.duration = duration;
        this.maxDuration = duration;
        this.intensity = intensity;
    }

    public float getOffsetX() {
        if (duration > 0) {
            return MathUtils.random(-1f, 1f) * intensity;
        }
        return 0;
    }

    public float getOffsetY() {
        if (duration > 0) {
            return MathUtils.random(-1f, 1f) * intensity;
        }
        return 0;
    }

    public void update(float delta) {
        if (duration > 0) {
            duration -= delta;
        }
    }

    public boolean isShaking() {
        return duration > 0;
    }
}
