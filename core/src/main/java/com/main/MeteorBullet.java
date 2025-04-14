package com.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class MeteorBullet extends Bullet {
    private Texture texture;
    private Vector2 velocity;
    // animation
    private Animation<TextureRegion> meteorAnimation;
    private float animTime = 0f;

    private float width = 256f;
    private float height = 128f;


    public MeteorBullet(float startX, float startY) {
        super(startX, startY, startX, startY, 40); // placeholder position
        // ➤ Rơi thẳng từ trên xuống
        this.velocity = new Vector2(0, -1).scl(200f); // 200 là tốc độ rơi

        this.texture = new Texture("Bosses/ExplosiveBullet/Meteor/0.png");

        this.setBulletTexture(texture);
        this.setSize(width, height);

        Texture bulletSheet = new Texture("Bosses/ExplosiveBullet/Meteor/MeteorAnimationSheet/2.png");
        TextureRegion[][] tmp = TextureRegion.split(bulletSheet, 32, 16); // mỗi frame 32x32 chẳng hạn
        meteorAnimation = new Animation<>(0.3f, tmp[0]);

    }

    @Override
    public void update(float delta) {
        animTime += delta;
        position.add(velocity.x * delta, velocity.y * delta);

        this.getBulletHitbox().x = position.x + width / 2;
        this.getBulletHitbox().y = position.y + 20;
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = meteorAnimation.getKeyFrame(animTime, true);
        batch.begin();
        batch.draw(currentFrame, position.x, position.y, width / 2f, height / 2f, width, height, 1f, 1f, 270f);
        batch.end();

        renderHitbox();
    }

    @Override
    public void dispose() {
        if (texture != null) texture.dispose();
    }
}

