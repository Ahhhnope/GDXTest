package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Enemy {
    private Texture texture;
    private Texture enemyBullet;

    private Vector2 position;
    private Vector2 velocity;
    private float speed = 200f;

    private ArrayList<Bullet> bullets;

    private Animation<TextureRegion> meteorAnimation;
    private float animTime = 0f;

    private float shootTimer = 0f;
    private float shootInterval = 2f;

    private boolean stopped = false;
    private float stopX; // vị trí X sẽ dừng lại (gần boss)
    private Texture bulletTexture;
    private int damage;

    //dao động
    private float baseY;
    private float floatTime = 0f;
    private float floatAmplitude = 20f; // Độ cao dao động
    private float floatFrequency = 2f;  // Tốc độ dao động

    private Rectangle hitbox;
    private int hp = 200;
    private int currentHP = 200;
    private float hpX;
    private float hpY;

    private ShapeRenderer shapeRenderer;
    public Enemy(float bossX, float bossY) {
        texture = new Texture("Bosses/Ship3/Ship3.png");
        enemyBullet = new Texture("Bosses/ExplosiveBullet/SmallEnemiesBullets/Green/0.png");
        float screenWidth = Gdx.graphics.getWidth();
        position = new Vector2(screenWidth + 50, bossY);

        stopX = MathUtils.random(bossX - 50f, bossX - 100f);
        this.baseY = bossY;
        velocity = new Vector2(-1, 0).scl(speed);
        bullets = new ArrayList<>();


        Texture bulletSheet = new Texture("Bosses/ExplosiveBullet/SmallEnemiesBullets/Green/GreenAnimationBullet/0.png");
        TextureRegion[][] tmp = TextureRegion.split(bulletSheet, 15, 13);
        meteorAnimation = new Animation<>(0.3f, tmp[0]);


        shapeRenderer = new ShapeRenderer();
        hitbox = new Rectangle(0, 0, 80, 35);
    }

    public void update(float delta) {
        // Di chuyển đến khi tới vị trí gần boss
        if (!stopped) {
            floatTime += delta;
            position.y = baseY + MathUtils.sin(floatTime * floatFrequency) * floatAmplitude;
            position.add(velocity.x * delta, velocity.y * delta);
            if (position.x <= stopX) {
                stopped = true;
                velocity.setZero(); // dừng lại
            }
            animTime += delta;
        }

        // Bắn nếu đã dừng
        if (stopped) {
            shootTimer += delta;
            floatTime += delta;
            position.y = baseY + MathUtils.sin(floatTime * floatFrequency) * floatAmplitude;
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

//        Cập nhật vị trí hitbox
        hitbox.setPosition(position.x + 25, position.y + hitbox.height + 12);
    }

    public void takeDamage(int damage) {
        currentHP -= damage;
        if (currentHP < 0) currentHP = 0;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }




    public void shoot() {
        Texture enemyBulletSheet = new Texture("Bosses/ExplosiveBullet/SmallEnemiesBullets/Green/GreenAnimationBullet/0.png");
        TextureRegion[][] tmp = TextureRegion.split(enemyBulletSheet, 15, 13);
        Animation<TextureRegion> enemyBulletAnim = new Animation<>(0.1f, tmp[0]);

        // Bắn 3 viên đạn rẽ quạt (góc 160, 180, 200 độ)
        float angleBase = 180f; // bắn sang trái
        for (int i = -1; i <= 1; i++) {
            float angle = angleBase + i * 20f;
            float radians = MathUtils.degreesToRadians * angle;
            Vector2 dir = new Vector2(MathUtils.cos(radians), MathUtils.sin(radians)).nor().scl(400f);

            Bullet bullet = new Bullet(
                position.x,
                position.y + 48,
                position.x + dir.x,
                position.y + dir.y,
                600f,
                enemyBulletAnim,
                32, 32,
                10
            );

            bullets.add(bullet);
        }
    }



    public void renderHitbox() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 0, 0, 1f);
        shapeRenderer.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
        shapeRenderer.end();

        for (Bullet b : bullets) {
            b.renderHitbox();
        }
    }

    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = meteorAnimation.getKeyFrame(animTime, true);

        batch.begin();
        batch.draw(texture, position.x, position.y, 128, 128);
        batch.end();

        for (Bullet bullet : bullets) {
            bullet.render(batch);
        }

        renderHitbox();
    }

    public void dispose() {
        texture.dispose();
        for (Bullet bullet : bullets) {
            bullet.dispose();
        }
    }
}
