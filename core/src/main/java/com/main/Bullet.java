package com.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Circle;

public class Bullet {
    //hitbox


    //khởi tạo bình thường
    private boolean isTracking = true;
    private boolean hasFinishedTracking = false; // ✅ NEW
    private float trackingTime = 3.5f;
    private float trackingTimer = 0f;
    private Player player;

    private Texture trackingBullet;
    private Texture bulletTexture;

    private Vector2 position;
    private Vector2 velocity;
    private float speed = 650f;

    public Bullet(float startX, float startY, float targetX, float targetY) {
        this.bulletTexture = new Texture("Bosses/Ship6/Exhaust/Turbo_flight/Exhaust3/exhaust4.png");
        this.position = new Vector2(startX, startY);
        Vector2 direction = new Vector2(targetX - startX, targetY - startY).nor();
        this.velocity = direction.scl(speed);
        this.isTracking = false;
    }

    public Bullet(float startX, float startY, Player player) {
        this.trackingBullet = new Texture("Bosses/EnergyBall/0.png");
        this.position = new Vector2(startX, startY);
        this.player = player;
        this.isTracking = true;
        this.speed = 200f;

        Vector2 direction = new Vector2(player.getX() - startX, player.getY() - startY).nor();
        this.velocity = direction.scl(speed);
    }

    public void update(float delta) {
        if (this.isTracking) {
            this.trackingTimer += delta;

            if (this.trackingTimer <= this.trackingTime) {
                Vector2 direction = new Vector2(
                    this.player.getX() - this.position.x,
                    this.player.getY() - this.position.y
                ).nor();
                this.velocity = direction.scl(175);
            } else {
                this.isTracking = false;
                this.hasFinishedTracking = true; // ✅ Mark là đã hết tracking
            }
        }

        this.position.add(this.velocity.x * delta, this.velocity.y * delta);
    }

    public void render(SpriteBatch batch) {
        if ((isTracking || hasFinishedTracking) && trackingBullet != null) {
            batch.draw(trackingBullet, position.x, position.y, 32, 32);
        } else if (!isTracking && bulletTexture != null) {
            batch.draw(bulletTexture, position.x, position.y, 32, 32);
        }
    }

    public boolean isOutOfScreen() {
        return position.x < -200 || position.x > 2000 || position.y < -50 || position.y > 2000;
    }

    public void dispose() {
        if (bulletTexture != null) bulletTexture.dispose();
        if (trackingBullet != null) trackingBullet.dispose();
    }
}
