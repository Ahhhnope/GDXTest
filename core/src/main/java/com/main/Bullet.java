package com.main;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Circle;

public class Bullet {
    protected Texture PlayerBulletTexture;
    //hitbox

    private Circle playerBulletHitbox;
    private Circle bulletHitbox;

    //khởi tạo bình thường
    private boolean isTracking = true;
    private boolean hasFinishedTracking = false;
    private float trackingTime = 3.5f;
    private float trackingTimer = 0f;
    private Player player;

    private Texture trackingBullet;
    private Texture bulletTexture;

    protected Vector2 position;
    private Vector2 velocity;
    private float speed = 650f;

    private float width = 32f;
    private float height = 32f;

    private float bulletWidth;
    private float bulletHeight;
    //Đạn meteor


    private ShapeRenderer shapeRenderer = new ShapeRenderer();


    public Bullet(float startX, float startY, float targetX, float targetY) {
//        EXPLOSION
        this(startX, startY, targetX, targetY, 650f); // gọi constructor bên dưới
        bulletHitbox = new Circle(100, 100, 32);
    }
    //Đạn thường
    public Bullet(float startX, float startY, float targetX, float targetY, float customspeed) {
//        NORMAL BULLET
        this.bulletTexture = new Texture("Bosses/Ship6/Exhaust/Turbo_flight/Exhaust3/exhaust4.png");
        this.position = new Vector2(startX, startY);
        Vector2 direction = new Vector2(targetX - startX, targetY - startY).nor();
        this.velocity = direction.scl(customspeed);
        this.isTracking = false;

        bulletHitbox = new Circle(20, 20, 7);
    }

    //Đạn player
    public Bullet(float startX, float startY, float targetX, float targetY, float customspeed, Texture PlayerBulletTexture, float radius, float width, float height) {
        this.PlayerBulletTexture = PlayerBulletTexture;
        this.position = new Vector2(startX, startY);
        Vector2 direction = new Vector2(targetX - startX, targetY - startY).nor();
        this.velocity = direction.scl(customspeed);
        this.isTracking = false;

        bulletWidth = width;
        bulletHeight = height;
        this.playerBulletHitbox = new Circle(position.x + 4, position.y + 4, radius);
    }


    //Đạn tracking
    public Bullet(float startX, float startY, Player player) {
//        TRACKING (your mom) BULLET
        this.trackingBullet = new Texture("Bosses/EnergyBall/0.png");
        this.position = new Vector2(startX, startY);
        this.player = player;
        this.isTracking = true;
        this.speed = 200f;

        Vector2 direction = new Vector2(player.getX() - startX, player.getY() - startY).nor();
        this.velocity = direction.scl(speed);
        this.bulletHitbox = new Circle(startX, startY, 8f);
        bulletHitbox = new Circle(position.x, position.y, 10);
    }


    public Circle getBulletHitbox() {
        return bulletHitbox;
    }


    public void update(float delta) {
        if (this.isTracking) {
            this.trackingTimer += delta;

            if (this.trackingTimer <= this.trackingTime) {
                Vector2 targetDir = new Vector2(
                    this.player.getX() - this.position.x,
                    this.player.getY() - this.position.y
                ).nor().scl(175);

                this.velocity.lerp(targetDir, 0.5f);
            } else {
                this.isTracking = false;
                this.hasFinishedTracking = true;
            }
        }

        this.position.add(this.velocity.x * delta, this.velocity.y * delta);

        // ✅ Cập nhật hitbox tương ứng
        if (bulletHitbox != null) {
            bulletHitbox.x = position.x + width / 2;
            bulletHitbox.y = position.y + height / 2;
        }

        if (playerBulletHitbox != null) {
            playerBulletHitbox.x = position.x + 4;  // Hoặc chỉnh lại nếu texture lệch
            playerBulletHitbox.y = position.y + 4;
        }
    }

    //custom size
    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void renderHitbox() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        if (bulletHitbox != null) {
            shapeRenderer.circle(bulletHitbox.x, bulletHitbox.y, bulletHitbox.radius);
        }
        if (playerBulletHitbox != null) {
            shapeRenderer.setColor(Color.GREEN); // đạn người chơi màu xanh
            shapeRenderer.circle(playerBulletHitbox.x, playerBulletHitbox.y, playerBulletHitbox.radius);
        }

        shapeRenderer.end();
    }

    public void render(SpriteBatch batch) {
        batch.begin();

        if ((isTracking || hasFinishedTracking) && trackingBullet != null) {
            batch.draw(trackingBullet, position.x, position.y, width, height);
        } else if (!isTracking && bulletTexture != null) {
            batch.draw(bulletTexture, position.x, position.y, width, height);
        } else if (PlayerBulletTexture != null) {
            batch.draw(PlayerBulletTexture, position.x, position.y, bulletWidth,bulletHeight);
        }
        batch.end();
        renderHitbox();


    }

    public void setBulletTexture(Texture texture) {
        this.bulletTexture = texture;
    }

    public boolean isOutOfScreen() {
        return position.x < -200 || position.x > 2000 || position.y < -50 || position.y > 2000;
    }

    public void dispose() {
        if (bulletTexture != null) bulletTexture.dispose();
        if (trackingBullet != null) trackingBullet.dispose();
    }
}
