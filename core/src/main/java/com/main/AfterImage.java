package com.main;

import com.badlogic.gdx.math.Vector2;

public class AfterImage {
    public Vector2 position;
    public float alpha;
    public float timeToLive;

    public AfterImage(Vector2 pos, float alpha, float ttl) {
        this.position = new Vector2(pos);
        this.alpha = alpha;
        this.timeToLive = ttl;
    }

    public void update(float delta) {
        timeToLive -= delta;
        alpha = Math.max(0, timeToLive); // fade
    }

    public boolean isDead() {
        return timeToLive <= 0;
    }
}
