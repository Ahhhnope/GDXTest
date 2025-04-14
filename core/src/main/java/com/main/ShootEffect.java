package com.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class ShootEffect {
    private Vector2 position;
    private Animation<TextureRegion> animation;
    private float stateTime = 0f;
    private boolean finished = false;
    private float rotation;
    private float angle; // Góc quay

    public ShootEffect(float x, float y, float rotation) {
        this.position = new Vector2(x, y);
        this.rotation = rotation;

        Texture sheet = new Texture("Player/Effects/Explosion-duplicate frames.png"); // Sprite sheet
        TextureRegion[][] tmp = TextureRegion.split(sheet, 32, 32);
        animation = new Animation<>(0.01f, tmp[0]);
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
            batch.draw(frame,
                position.x, position.y,    // vị trí
                8, 8,                              // origin center (xoay quanh giữa)
                16, 16,                            // width, height
                1f, 1f,                            // scale
                rotation                          // góc quay
            );
            batch.end();
        }
    }

    public boolean isFinished() {
        return finished;
    }
}
