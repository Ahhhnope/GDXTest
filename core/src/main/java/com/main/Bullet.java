package com.main;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;

public class Bullet {
    protected Texture PlayerBulletTexture;
    //hitbox

    Circle playerBulletHitbox;

    //Animation
    private Texture TrackingBulletSheet;
    private Texture ExplosiveBulletSheet;
    private Animation<TextureRegion> TrackingAnimation;
    private float TrackingBulletAnimTime = 0f;

    private Animation<TextureRegion> enemyAnimation;
    private float enemyAnimTime = 0f;
    //khởi tạo bình thường
    private boolean isTracking = true;
    private boolean hasFinishedTracking = false;
    private float trackingTime = 4.5f;
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
    private Animation<TextureRegion> animation;
    private float animTime = 0f;

    private int damage;
    private Circle bulletHitbox;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();


    //        EXPLOSION
    public Bullet(float startX, float startY, float targetX, float targetY, float radius) {
        this(startX, startY, targetX, targetY, 650f, radius); // gọi constructor bên dưới
        bulletHitbox = new Circle(100, 100, radius);
        damage = 20;

    }



    //Đạn thường
    public Bullet(float startX, float startY, float targetX, float targetY, float customspeed, float radius) {
        this.bulletTexture = new Texture("Bosses/Ship6/Exhaust/Turbo_flight/Exhaust3/exhaust4.png");
        this.position = new Vector2(startX, startY);
        Vector2 direction = new Vector2(targetX - startX, targetY - startY).nor();
        this.velocity = direction.scl(customspeed);
        this.isTracking = false;

        bulletHitbox = new Circle(20, 20, radius);
        damage = 10;
    }

    //Đạn player
    public Bullet(float startX, float startY, float targetX, float targetY, float customspeed, Texture PlayerBulletTexture, float width, float height, float radius) {
        this.PlayerBulletTexture = PlayerBulletTexture;
        this.position = new Vector2(startX, startY);
        Vector2 direction = new Vector2(targetX - startX, targetY - startY).nor();
        this.velocity = direction.scl(customspeed);
        this.isTracking = false;
        bulletWidth = width;
        bulletHeight = height;
        this.playerBulletHitbox = new Circle(position.x + 4, position.y + 4, radius);
        this.bulletHitbox = playerBulletHitbox;

        damage = 500;
    }
    //Đạn spiral
    public Bullet(float startX, float startY, float targetX, float targetY, float speed, Animation<TextureRegion> bulletAnimation, float width, float height, float radius, int damage) {
        this.position = new Vector2(startX, startY);
        Vector2 direction = new Vector2(targetX - startX, targetY - startY).nor();
        this.velocity = direction.scl(speed);

        this.enemyAnimation = bulletAnimation;
        this.width = width;
        this.height = height;
        this.animTime = 0f;

        this.bulletHitbox = new Circle(position.x + width / 2, position.y + height / 2, radius);
        this.damage = damage;

        this.isTracking = false;
        damage = 5;

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
        bulletHitbox = new Circle(position.x, position.y, 15);

        //Animation
        TrackingBulletSheet = new Texture("Bosses/EnergyBall/EnergyBallAnimation/0.png");
        TextureRegion[][] tmp = TextureRegion.split(TrackingBulletSheet, 16, 16);
        if (tmp.length > 0) {
            this.TrackingAnimation = new Animation<>(0.3f, tmp[0]); // 5 frame đầu tiên ở hàng 0
        }

        damage = 15;
    }
    //Đạn enemy
    public Bullet(float startX, float startY, float targetX, float targetY, float speed, Animation<TextureRegion> animation, float width, float height, float radius) {
        this.position = new Vector2(startX, startY);
        Vector2 direction = new Vector2(targetX - startX, targetY - startY).nor();
        this.velocity = direction.scl(speed);
        this.enemyAnimation = animation;
        this.width = width;
        this.height = height;
        this.bulletHitbox = new Circle(position.x + width / 2, position.y + height / 2, radius);
        this.isTracking = false;

        damage = 15;
    }


    public Circle getBulletHitbox() {
        return bulletHitbox;
    }

    public int getDamage() {
        return  damage;
    }


    public void update(float delta) {
        if (isTracking) {
            trackingTimer += delta;
            if (trackingTimer <= trackingTime) {
                Vector2 targetDir = new Vector2(player.getHitbox().x - position.x + 4, player.getHitbox().y - position.y + 4).nor().scl(175);
                velocity.lerp(targetDir, 0.5f);
            } else {
                isTracking = false;
                hasFinishedTracking = true;
            }
        }

        position.add(velocity.x * delta, velocity.y * delta);

        // Update hitboxes
        if (bulletHitbox != null) {
            bulletHitbox.setPosition(position.x + width / 2, position.y + height / 2);
        }
        if (playerBulletHitbox != null) {
            playerBulletHitbox.setPosition(position.x + bulletWidth / 2, position.y + bulletHeight / 2);
        }
        if (enemyAnimation != null) {
            enemyAnimTime += delta;
        }
        //animation time
        TrackingBulletAnimTime += delta;
    }

    public Circle getHitbox() {
        return bulletHitbox;
    }

    //custom size
    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void renderHitbox() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        if (bulletHitbox != null) {
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.circle(bulletHitbox.x, bulletHitbox.y, bulletHitbox.radius);
        }
        if (playerBulletHitbox != null) {
            shapeRenderer.setColor(Color.GREEN);
            shapeRenderer.circle(playerBulletHitbox.x, playerBulletHitbox.y, playerBulletHitbox.radius);
        }
        shapeRenderer.end();
    }


    public void render(SpriteBatch batch) {
        TextureRegion TrackingBulletCurrentFrame = null;

        batch.begin();

        if (TrackingAnimation != null && (isTracking || hasFinishedTracking)) {
            TextureRegion frame = TrackingAnimation.getKeyFrame(TrackingBulletAnimTime, true);
            batch.draw(frame, position.x, position.y, width, height);
        } else if (enemyAnimation != null) {
            TextureRegion frame = enemyAnimation.getKeyFrame(enemyAnimTime, true);
            batch.draw(frame, position.x, position.y, width, height);
        } else if (bulletTexture != null) {
            batch.draw(bulletTexture, position.x, position.y, width, height);
        } else if (PlayerBulletTexture != null) {
            batch.draw(PlayerBulletTexture, position.x, position.y, bulletWidth, bulletHeight);
        }else if (enemyAnimation != null) {
            TextureRegion frame = enemyAnimation.getKeyFrame(enemyAnimTime, true);
            batch.draw(frame, position.x, position.y, width, height);
        }

        batch.end();

//        renderHitbox();
    }

    public void setBulletTexture(Texture texture) {
        this.bulletTexture = texture;
    }

    public boolean isOutOfScreen() {
        return position.x < - 3000 || position.x > 3000 || position.y < - 3000 || position.y > 3000;
    }
    public void dispose() {
        if (bulletTexture != null) bulletTexture.dispose();
        if (trackingBullet != null) trackingBullet.dispose();
        if (TrackingBulletSheet != null) TrackingBulletSheet.dispose();
    }
}
