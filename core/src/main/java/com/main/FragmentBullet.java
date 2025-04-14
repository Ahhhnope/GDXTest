package com.main;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class FragmentBullet {
    public Vector2 position;
    public Vector2 velocity;
    public Animation<TextureRegion> animation;
    public float animTime = 0f;
    private int damage = 100;
    private static boolean textureDisposed = false;
    private ShapeRenderer shapeRenderer;
    private Circle bulletHitbox;
    private float width;
    private float height;

    public FragmentBullet(Vector2 position, Vector2 velocity, Animation<TextureRegion> animation, float width, float height, float radius) {
        this.position = position;
        this.velocity = velocity;
        this.animation = animation;
        shapeRenderer = new ShapeRenderer();
        this.width = width;
        this.height = height;
        this.bulletHitbox = new Circle(position.x + 4, position.y + 4, radius);

    }

    public int getDamage() {
        return damage;
    }

    public Circle getBulletHitbox() {
        return bulletHitbox;
    }

    public void update(float delta) {
        animTime += delta;
        position.add(velocity.x * delta, velocity.y * delta);

        bulletHitbox.setPosition(position.x + width / 2, position.y + height / 2);
    }

    public void renderHitbox() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.circle(bulletHitbox.x, bulletHitbox.y, bulletHitbox.radius);
        shapeRenderer.end();
    }

    public void render(SpriteBatch batch) {
        TextureRegion frame = animation.getKeyFrame(animTime, true);
        batch.begin();
        batch.draw(frame, position.x, position.y, width, height);
        batch.end();

        renderHitbox();
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
