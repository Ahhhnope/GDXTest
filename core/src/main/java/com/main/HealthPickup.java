package com.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;

public class HealthPickup {
    private Texture texture;
    private float x, y;
    private Circle hitbox;
    private float radius = 20f;
    private boolean collected = false;

    public HealthPickup(float x, float y) {
        this.x = x;
        this.y = y;
        this.texture = new Texture("Heal/health.png");
        this.hitbox = new Circle(x, y, radius);
    }

    public void render(SpriteBatch batch) {
        if (!collected) {
            batch.begin();
            batch.draw(texture, x - radius, y - radius, radius * 2, radius * 2);
            batch.end();
        }
    }

    public boolean isCollected() {
        return collected;
    }

    public Circle getHitbox() {
        return hitbox;
    }

    public void collect() {
        collected = true;
    }

    public void dispose() {
        texture.dispose();
    }
}
