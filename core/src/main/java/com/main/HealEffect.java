package com.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HealEffect {
    private Texture texture;
    private float x, y;
    private float alpha = 1f;
    private float riseSpeed = 60f;
    private float fadeSpeed = 1.2f;
    private boolean finished = false;

    public HealEffect(float x, float y) {
        this.x = x;
        this.y = y;
        this.texture = new Texture("Heal/healingEffect.png"); // ✨ icon hồi máu glow
    }

    public void update(float delta) {
        y += riseSpeed * delta;
        alpha -= fadeSpeed * delta;
        if (alpha <= 0f) {
            alpha = 0f;
            finished = true;
        }
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        batch.setColor(1f, 1f, 1f, alpha);
        batch.draw(texture, x - 16, y - 16, 128, 128);
        batch.setColor(1f, 1f, 1f, 1f);
        batch.end();
    }

    public boolean isFinished() {
        return finished;
    }

    public void dispose() {
        texture.dispose();
    }
}
