package com.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class FragmentBullet {
    public Vector2 position;
    public Vector2 velocity;
    public Animation<TextureRegion> animation;
    public float animTime = 0f;

    private static boolean textureDisposed = false;

    public FragmentBullet(Vector2 position, Vector2 velocity, Animation<TextureRegion> animation) {
        this.position = position;
        this.velocity = velocity;
        this.animation = animation;
    }

    public void update(float delta) {
        animTime += delta;
        position.add(velocity.x * delta, velocity.y * delta);
    }

    public void render(SpriteBatch batch) {
        TextureRegion frame = animation.getKeyFrame(animTime, true);
        batch.begin();
        batch.draw(frame, position.x, position.y, 16, 16);
        batch.end();
    }
    public void dispose() {
        // Chỉ dispose 1 lần cho shared texture
        if (!textureDisposed) {
            Texture texture = animation.getKeyFrame(0f).getTexture();
            if (texture != null) {
                texture.dispose();
                textureDisposed = true;
            }
        }
    }

    public boolean isOutOfScreen(float screenWidth, float screenHeight) {
        return position.x < 0 || position.x > screenWidth || position.y < 0 || position.y > screenHeight;
    }
}
