package com.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;

public class HitEffect {
    private static Texture hitTexture = new Texture("Bosses/HitEffect/hiteffect.png");
    private static Animation<TextureRegion> hitAnimation;

    private float stateTime = 0f;
    private Vector2 position;
    private boolean finished = false;

    static {
        TextureRegion[][] frames = TextureRegion.split(hitTexture, 128, 128); // chia thành 32x32 frame
        hitAnimation = new Animation<>(0.02f, frames[0]); // hàng đầu tiên
    }

    public HitEffect(float x, float y) {
        this.position = new Vector2(x, y);
    }

    public void update(float delta) {
        stateTime += delta;
        if (hitAnimation.isAnimationFinished(stateTime)) {
            finished = true;
        }
    }

    public void render(SpriteBatch batch) {
        if (!finished) {
            TextureRegion currentFrame = hitAnimation.getKeyFrame(stateTime, false);
            batch.begin();
            batch.draw(currentFrame, position.x - 16, position.y - 16, 32, 32);
            batch.end();
        }
    }

    public boolean isFinished() {
        return finished;
    }
}
