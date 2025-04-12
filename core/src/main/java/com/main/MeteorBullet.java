package com.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MeteorBullet extends Bullet {
    private Texture texture;
    private Vector2 velocity;

    private float width = 128f;
    private float height = 64f;

    public MeteorBullet(float startX, float startY) {
        super(startX, startY, startX, startY); // placeholder position

        this.velocity = new Vector2(-1, 0).scl(200f); // bay ngang trái
        this.texture = new Texture("Bosses/ExplosiveBullet/Meteor/0.png");

        this.setBulletTexture(texture);
        this.setSize(width, height);
    }

    @Override
    public void update(float delta) {
        position.add(velocity.x * delta, velocity.y * delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, -width, height);
    }

    @Override
    public void dispose() {
        if (texture != null) texture.dispose();
    }
}
