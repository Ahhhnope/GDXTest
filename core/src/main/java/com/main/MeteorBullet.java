package com.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class MeteorBullet extends Bullet {
    private Texture texture;
    private Vector2 velocity;

    private float width = 128f;
    private float height = 64f;


    public MeteorBullet(float startX, float startY) {
        super(startX, startY, startX, startY); // placeholder position

        // ➤ Rơi thẳng từ trên xuống
        this.velocity = new Vector2(0, -1).scl(200f); // 200 là tốc độ rơi

        this.texture = new Texture("Bosses/ExplosiveBullet/Meteor/0.png");

        this.setBulletTexture(texture);
        this.setSize(width, height);


    }

    @Override
    public void update(float delta) {
        position.add(velocity.x * delta, velocity.y * delta);

        this.getBulletHitbox().x = position.x + width / 2;
        this.getBulletHitbox().y = position.y;
        System.out.println(this.getBulletHitbox().x + " | " + this.getBulletHitbox().y);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(texture, position.x, position.y, width / 2, height / 2, width, height, 1f, 1f, 270f, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
        batch.end();

        renderHitbox();
    }

    @Override
    public void dispose() {
        if (texture != null) texture.dispose();
    }
}

