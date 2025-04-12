package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.main.Bullet;
import jdk.jfr.Frequency;

import java.util.ArrayList;

public class Boss {
    private Texture BossOne;
    private Vector2 position;
    private ArrayList<Bullet> bullets;
    private float shootTimer = 0f;
    private float shootInterval = 0.3f;
    private Player player;

    private ArrayList<Enemy> enemies = new ArrayList<>();
    private float spawnEnemyTimer = 0f;
    private float spawnEnemyInterval = 5f;

    //MeteorTime

    float meteorTimer = 0f;
    float meteorInterval = 2f;

    //Đạn nổ timer
    private float explosionTimer = 0f;
    private float explosionInterval = 2f;
    //
    private float trackingShootTimer = 0f;
    private float trackingShootInterval = 1f;

    // di chuyển random
    private float randomizeTimer = 0f;
    private float randomizeInterval = 5f;

    //dao động Y

    private float time = 0f;
    private float baseY;
    private float amplitude = 200f;
    private float frequency = 1f;

    //dao động X

    private float baseX;
    private float amplitudeX = 80f;
    private float frequencyX = 0.5f;


    public Boss (float x, float y){
        BossOne = new Texture("Bosses/Ship6/Ship6.png");
        position = new Vector2(x,y);
        bullets = new ArrayList<>();
        baseY = y;

        baseX = MathUtils.clamp(position.x, 100f, Gdx.graphics.getWidth() - 150f);

    }

    public void update(float delta, Player player){
        this.player = player;
        shootTimer += delta;
        trackingShootTimer += delta;

        if (shootTimer >= shootInterval) {
            shoot(player.getX(), player.getY()); // đạn thường
            shootTimer = 0;
        }

        if (trackingShootTimer >= trackingShootInterval) {
            spawnTrackingBullet(player);
            trackingShootTimer = 0;
        }

        meteorTimer += delta;
        if (meteorTimer >= meteorInterval) {
            spawnMeteor();
            meteorTimer = 0f;
        }

        for (int i = 0; i < bullets.size();i++){
            Bullet b = bullets.get(i);
            b.update(delta);
            if (b.isOutOfScreen()){
                b.dispose();
                bullets.remove(i);
                i--;
            }
        }
        explosionTimer += delta;
        if (explosionTimer >= explosionInterval) {
            shootExplosion();
            explosionTimer = 0;
        }
        time += delta;
        position.x = baseX + MathUtils.sin(time * frequencyX) * amplitudeX;
        position.y = baseY + MathUtils.sin(time * frequency) * amplitude;

        spawnEnemyTimer += delta;
        if (spawnEnemyTimer >= spawnEnemyInterval) {
            enemies.add(new Enemy(position.x, position.y)); // spawn gần boss
            spawnEnemyTimer = 0f;
        }

        for (Enemy enemy : enemies){
            enemy.update(delta);
        }

    }

    public void spawnMeteor() {
        float startX = MathUtils.random(64, Gdx.graphics.getWidth() - 64);
        float startY = Gdx.graphics.getHeight() + 50; // spawn trên màn hình

        bullets.add(new MeteorBullet(startX, startY));
    }

    public void shoot(float targetX, float targetY){
        float centerX = position.x;
        float centerY = position.y + 64 - 15;
        bullets.add(new Bullet(centerX, centerY, targetX, targetY, 650f));
    }

    public void shootExplosion() {
        float startX = position.x + 64 / 2f;
        float startY = position.y + 64 - 15;

        float offsetX = MathUtils.random(-200f, 200f);
        float offsetY = MathUtils.random(-100f, 150f);
        float targetX = player.getX() + offsetX;
        float targetY = player.getY() + offsetY;

        bullets.add(new ExplosionBullet(startX, startY, targetX, targetY));
    }

    public void spawnTrackingBullet(Player player) {
        float delta = Gdx.graphics.getDeltaTime();
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        int side = MathUtils.random(2); // 0: top, 1: bottom, 2: left, 3: right

        float startX = 0, startY = 0;
        switch (side) {
            case 0: startX = MathUtils.random(0, screenWidth); startY = screenHeight + 50; break;
            case 1: startX = MathUtils.random(0, screenWidth); startY = -50; break;
            case 2: startX = screenWidth + 50; startY = MathUtils.random(0, screenHeight); break;
        }

        bullets.add(new Bullet(startX, startY, player));
    }

    public void render(SpriteBatch batch){
        batch.draw(BossOne, position.x, position.y, 128, 128);
        for (Bullet bullet : bullets){
            bullet.render(batch);
        }
        for (Enemy enemy : enemies){
            enemy.render(batch);
        }

    }
    public void dispose(){
        for (Bullet bullet : bullets){
            bullet.dispose();
        }
    }
}

