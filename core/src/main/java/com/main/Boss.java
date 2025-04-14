package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.main.Bullet;
import jdk.jfr.Frequency;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;

public class Boss {

    private Texture BossOne;
    private Vector2 position;
    private ArrayList<Bullet> bullets;
    private float shootTimer = 0f;
    private float shootInterval = 0.3f;
    private ShapeRenderer shapeRenderer;
    private Player player;
    private ArrayList<FragmentBullet> fragmentBullets = new ArrayList<>();


    //MeteorTime

    float meteorTimer = 0f;
    float meteorInterval = 2f;

    //Đạn nổ timer
    private float explosionTimer = 0f;
    private float explosionInterval = 2f;
    //
    private float trackingShootTimer = 0f;
    private float trackingShootInterval = 1f;

    // di chuyển random (What the fuck is random!?)
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

    private Rectangle hitbox;
    private int width = 250;
    private int height = 85;

    private int hp = 2000;
    private int currentHp = 2000;

    public Boss (float x, float y){
        BossOne = new Texture("Bosses/BossAlternate.png");
        position = new Vector2(x,y);
        bullets = new ArrayList<>();
        baseY = y;
        baseX = MathUtils.clamp(position.x, 100f, Gdx.graphics.getWidth() - 150f);

        shapeRenderer = new ShapeRenderer();
        hitbox = new Rectangle(position.x, position.y, width, height);
    }
    public ArrayList<FragmentBullet> getFragmentBullets() {
        return fragmentBullets;
    }
    public void takeDamage(float damage) {
        currentHp -= damage;
        if (currentHp < 0) currentHp = 0;
        System.out.println("Boss HP: " + currentHp); // debug tạm
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
    public void update(float delta, Player player){
        this.player = player;
        shootTimer += delta;
        trackingShootTimer += delta;

//        Hitbox position
        hitbox.setPosition(position.x, position.y + 85);

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
        for (int i = fragmentBullets.size() - 1; i >= 0; i--) {
            FragmentBullet frag = fragmentBullets.get(i);
            frag.update(delta);
            if (frag.isOutOfScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())) {
                fragmentBullets.remove(i);
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

    }

    public void spawnMeteor() {
        float startX = MathUtils.random(64, Gdx.graphics.getWidth() - 64);
        float startY = Gdx.graphics.getHeight() + 50; // spawn trên màn hình

        bullets.add(new MeteorBullet(startX, startY));
    }

    public void shoot(float targetX, float targetY){
        float centerX = position.x;
        float centerY = position.y + 150;
        bullets.add(new Bullet(centerX, centerY, targetX, targetY, 650f, 8));
        bullets.add(new Bullet(centerX, centerY - 80, targetX, targetY, 650f, 8));
    }

    public void shootExplosion() {
        float startX = position.x + 64 / 2f;
        float startY = position.y + 64 - 15;

        float offsetX = MathUtils.random(-200f, 200f);
        float offsetY = MathUtils.random(-100f, 150f);
        float targetX = player.getX() + offsetX;
        float targetY = player.getY() + offsetY;

        bullets.add(new ExplosionBullet(startX, startY, targetX, targetY, 18, fragmentBullets));
    }

    public void spawnTrackingBullet(Player player) {
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

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public float getPositionX() {
        return position.x;
    }

    public float getPositionY() {
        return position.y;
    }


    public void renderHitbox() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 0, 0, 1f);
        shapeRenderer.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
        shapeRenderer.end();

    }

    public void render(SpriteBatch batch){
        batch.begin();
        batch.draw(BossOne, position.x, position.y, 256, 256);
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        float PlayerbarX = 100;
        float PlayerbarY = Gdx.graphics.getHeight() - 30;
        float BarWidthNew = 300;
        float BarHeightNew = 20;

        float percent = (float) currentHp / hp;
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(PlayerbarX, PlayerbarY, BarWidthNew, BarHeightNew);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(PlayerbarX, PlayerbarY, BarWidthNew * percent, BarHeightNew);
        shapeRenderer.end();

        for (Bullet bullet : bullets){
            bullet.render(batch);
        }
        for (FragmentBullet frag : fragmentBullets) {
            frag.render(batch);
        }
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        float screenWidth = Gdx.graphics.getWidth();
        float barHeight = 24f;

        float barX = 0f;
        float barY = Gdx.graphics.getHeight() - barHeight - 10f;

        float hpPercent = (float) currentHp / hp;
        float innerPadding = 4f;

        float outerWidth = screenWidth;
        float innerWidth = screenWidth - innerPadding * 2;
        float filledWidth = innerWidth * hpPercent;

// Outer border (dark gray)
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(barX, barY, outerWidth, barHeight);

// Inner red (actual HP)
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(barX + innerPadding, barY + innerPadding, filledWidth, barHeight - innerPadding * 2);

        shapeRenderer.end();
    }

    public void dispose(){
        for (Bullet bullet : bullets){
            bullet.dispose();
        }
    }
}

