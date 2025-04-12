package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Enemy {
    private Texture texture;
    private Vector2 position;
    private Vector2 velocity;
    private float speed = 200f;

    private ArrayList<Bullet> bullets;

    private float shootTimer = 0f;
    private float shootInterval = 2f;

    private boolean stopped = false;
    private float stopX; // vị trí X sẽ dừng lại (gần boss)

    public Enemy(float bossX, float bossY) {
        texture = new Texture("Bosses/Ship2/Ship2.png");

        float screenWidth = Gdx.graphics.getWidth();
        position = new Vector2(screenWidth + 50, bossY);

        stopX = MathUtils.random(bossX - 50f, bossX - 100f);

        velocity = new Vector2(-1, 0).scl(speed);
        bullets = new ArrayList<>();
    }

    public void update(float delta) {
        // Di chuyển đến khi tới vị trí gần boss
        if (!stopped) {
            position.add(velocity.x * delta, velocity.y * delta);
            if (position.x <= stopX) {
                stopped = true;
                velocity.setZero(); // dừng lại
            }
        }

        // Bắn nếu đã dừng
        if (stopped) {
            shootTimer += delta;
            if (shootTimer >= shootInterval) {
                shoot();
                shootTimer = 0f;
            }
        }

        // Cập nhật đạn
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            b.update(delta);
            if (b.isOutOfScreen()) {
                b.dispose();
                bullets.remove(i);
                i--;
            }
        }
    }

    public void shoot() {
        float angleBase = 180f; // bắn sang trái
        for (int i = -1; i <= 1; i++) {
            float angle = angleBase + i * 20f; // 160, 180, 200 độ
            float radians = MathUtils.degreesToRadians * angle;
            float dx = MathUtils.cos(radians);
            float dy = MathUtils.sin(radians);

            Vector2 dir = new Vector2(dx, dy).nor().scl(400f);
            Bullet bullet = new Bullet(position.x, position.y + 48, position.x + dir.x, position.y + dir.y);
            bullets.add(bullet);
        }
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(texture, position.x, position.y, 128, 128);
        batch.end();

        for (Bullet bullet : bullets) {
            bullet.render(batch);
        }
    }

    public void dispose() {
        texture.dispose();
        for (Bullet bullet : bullets) {
            bullet.dispose();
        }
    }
}
