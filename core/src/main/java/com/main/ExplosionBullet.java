package com.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class ExplosionBullet extends Bullet {
    private Texture explosionTexture;
    private boolean exploded = false;
    private float explodeDelay = 1f;
    private float timer = 0f;


    private float width = 16f;
    private float height = 16f;
    private Animation<TextureRegion> meteorAnimation;
    private float animTime = 0f;
    private TextureRegion currentFrame;

    private List<Bullet> fragments = new ArrayList<>();

    public ExplosionBullet(float startX, float startY, float targetX, float targetY) {
        super(startX, startY, targetX, targetY); // bay về hướng bất kỳ
        explosionTexture = new Texture("Bosses/ExplosiveBullet/Explosive1/0.png");
        this.setBulletTexture(explosionTexture);
        this.explodeDelay = MathUtils.random(0.5f, 1.5f);

        Texture bulletSheet = new Texture("Bosses/ExplosiveBullet/Explosive1/ExplosiveAnimation/2.png");
        TextureRegion[][] tmp = TextureRegion.split(bulletSheet, 16, 15); // mỗi frame 32x32 chẳng hạn
        meteorAnimation = new Animation<>(0.3f, tmp[0]);
    }

    @Override
    public void update(float delta) {
        if (!exploded) {
            super.update(delta);
            timer += delta;
            if (timer >= explodeDelay) {
                explode();
                exploded = true;
            }
        } else {
            for (Bullet bullet : fragments) {
                bullet.update(delta);
            }
        }
        animTime += delta;
        currentFrame = meteorAnimation.getKeyFrame(animTime, true);
    }

    @Override
    public void render(SpriteBatch batch) {
        if (!exploded && currentFrame != null) {
            batch.begin();
            batch.draw(currentFrame, position.x, position.y,39,39);
            batch.end();
        } else {
            for (Bullet bullet : fragments) {
                bullet.render(batch);
            }
        }
        currentFrame = meteorAnimation.getKeyFrame(animTime, true);
    }
    public void explode() {
        Texture fragmentTexture = new Texture("Bosses/ExplosiveBullet/SmallBulletOfExplosives/0.png");

        int numBullets = 16;
        float speed = 80f;
        float angleStep = 360f / numBullets;

        for (int i = 0; i < numBullets; i++) {
            float angle = (float) Math.toRadians(i * angleStep);
            float dx = (float) Math.cos(angle);
            float dy = (float) Math.sin(angle);
            Vector2 dir = new Vector2(dx, dy).nor().scl(speed);

            float startX = getX();
            float startY = getY();

            Bullet child = new Bullet(startX, startY, startX + dir.x, startY + dir.y, 100f);
            child.setBulletTexture(fragmentTexture);
            child.setSize(16,16);
            fragments.add(child);
        }

    }

    private float getX() {
        return super.position.x;
    }

    private float getY() {
        return super.position.y;
    }

    @Override
    public void dispose() {
        super.dispose();
        for (Bullet bullet : fragments) {
            bullet.dispose();
        }
    }

    @Override
    public boolean isOutOfScreen() {
        if (!exploded) return super.isOutOfScreen();

        for (Bullet b : fragments) {
            if (!b.isOutOfScreen()) return false;
        }
        return true;
    }
}
