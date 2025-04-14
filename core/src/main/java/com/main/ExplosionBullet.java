package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

public class ExplosionBullet extends Bullet {
    private boolean exploded = false;
    private float explodeDelay;
    private float timer = 0f;

    private Animation<TextureRegion> meteorAnimation;
    private float animTime = 0f;
    private TextureRegion currentFrame;

    private ArrayList<FragmentBullet> externalFragments;

    public ExplosionBullet(float startX, float startY, float targetX, float targetY, float radius,
                           ArrayList<FragmentBullet> externalFragments) {
        super(startX, startY, targetX, targetY, radius);
        this.externalFragments = externalFragments;

        this.explodeDelay = MathUtils.random(0.5f, 1.5f);

        Texture bulletSheet = new Texture("Bosses/ExplosiveBullet/Explosive1/ExplosiveAnimation/2.png");
        TextureRegion[][] tmp = TextureRegion.split(bulletSheet, 16, 15);
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
        }

        animTime += delta;
        currentFrame = meteorAnimation.getKeyFrame(animTime, true);
    }

    @Override
    public void render(SpriteBatch batch) {
        if (!exploded && currentFrame != null) {
            batch.begin();
            batch.draw(currentFrame, position.x - 4, position.y - 3, 39, 39);
            batch.end();
        }

        if (!exploded) {
            renderHitbox();
        }
    }

    public void explode() {
        Texture fragmentSheet = new Texture("Bosses/ExplosiveBullet/SmallBulletOfExplosives/SmallBulletOfExplosivesAnimation/0.png");
        TextureRegion[][] tmp = TextureRegion.split(fragmentSheet, 15, 15);
        Animation<TextureRegion> fragAnim = new Animation<>(0.3f, tmp[0]);

        int numBullets = 16;
        float speed = 80f;
        float angleStep = 360f / numBullets;

        for (int i = 0; i < numBullets; i++) {
            float angle = (float) Math.toRadians(i * angleStep);
            Vector2 dir = new Vector2((float) Math.cos(angle), (float) Math.sin(angle)).nor().scl(speed);
            Vector2 pos = new Vector2(position.x, position.y);

            externalFragments.add(new FragmentBullet(pos, dir, fragAnim, 16, 16, 5));
        }
    }

    @Override
    public boolean isOutOfScreen() {
        return position.x < 0 || position.x > Gdx.graphics.getWidth() || position.y < 0 || position.y > Gdx.graphics.getHeight();
    }
}
