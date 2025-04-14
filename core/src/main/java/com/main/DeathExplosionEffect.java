package com.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class DeathExplosionEffect {
    private Vector2 position;
    private Animation<TextureRegion> animation;
    private float stateTime = 0f;
    private boolean finished = false;

    public DeathExplosionEffect(float x, float y) {
        this.position = new Vector2(x, y);
        Texture sheet = new Texture("Player/Effects/Explosion-duplicate frames.png"); // Sprite sheet 16x16 or tùy bạn
        TextureRegion[][] tmp = TextureRegion.split(sheet, 32, 32);
        animation = new Animation<>(0.05f, tmp[0]);
    }

    public void update(float delta) {
        stateTime += delta;
        if (animation.isAnimationFinished(stateTime)) {
            finished = true;
        }
    }

    public void render(SpriteBatch batch) {
        if (!finished) {
            TextureRegion frame = animation.getKeyFrame(stateTime, false);
            batch.begin();
            batch.draw(frame, position.x + 15, position.y + 8,128,128); // căn giữa
            batch.end();
        }
    }

    public boolean isFinished() {
        return finished;
    }
}
