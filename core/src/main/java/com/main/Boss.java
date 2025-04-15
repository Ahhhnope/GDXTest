package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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
    float meteorInterval = 2.5f;

    //Đạn nổ timer
    private float explosionTimer = 0f;
    private float explosionInterval = 2f;
    //
    private float trackingShootTimer = 0f;
    private float trackingShootInterval = 1f;

    // di chuyển random (What the fuck is random!?) - idk
    private float randomizeTimer = 0f;
    private float randomizeInterval = 5f;

    //dao động Y
    private float time = 0f;
    private float baseY;
    private float amplitude = 300f;
    private float frequency = 1f;

    //dao động X
    private ScreenShake screenShake;
    private float baseX;
    private float amplitudeX = 80f;
    private float frequencyX = 0.5f;
    //Get shot
    private boolean isFlashing = false;
    private float flashTimer = 0f;
    private float flashDuration = 0.04f;

    private Rectangle hitbox;
    private int width = 250;
    private int height = 85;

    private int hp = 10000;
    private int currentHp = 10000;

    //phase 2
    private boolean isPhase2 = false;
    private float phase2Timer = 0f;
    private boolean hasFiredWave = false;
    private boolean waitingForShakeToEnd = false;
    private float shakeTimer = 0f;
    private float shakeDuration = 3f;
    private boolean isInvincible = false;

    private float patternCooldown = 1.5f;
    private float patternTimer = 0f;
    private int patternIndex = 0;
    //touhou ahh bullet
    float spiralAngle = 0f;
    private boolean isFiringSpiral = false;
    private float spiralDuration = 2.25f;
    private float spiralTimer = 0f;
    private boolean spiralStarted = false;
    private float spiralStartDelay = 5f; // Delay 5 giây sau khi fireWave
    private float spiralDelayTimer = 0f;
    private boolean spiralDone = false;

    private float patternDelayTimer = 0f;
    private float patternDelay = 0.5f;
    private ArrayList<AfterImage> afterimages = new ArrayList<>();
    private float afterimageTimer = 0f;
    private float afterimageInterval = 0.05f;
    private boolean DuAnh = false;

    private float fireballSfxTimer = 0f;
    private int fireballSfxCount = 0;
    private boolean fireballSfxActive = false;


    //sfx music
    private BossSoundManager soundManager;

    public Boss (float x, float y, ScreenShake screenShake){
        BossOne = new Texture("Bosses/BossAlternate.png");
        position = new Vector2(x,y);
        bullets = new ArrayList<>();
        baseY = y;
        baseX = MathUtils.clamp(position.x, 100f, Gdx.graphics.getWidth() - 150f);

        shapeRenderer = new ShapeRenderer();
        hitbox = new Rectangle(position.x, position.y, width, height);
        this.screenShake = screenShake;

        loadBulletAnimations();

        this.soundManager = new BossSoundManager();
        soundManager.startPhase1Music();
    }
    public ArrayList<FragmentBullet> getFragmentBullets() {
        return fragmentBullets;
    }
    public void takeDamage(float damage) {
        if (isInvincible) return;
        currentHp -= damage;
        if (currentHp < 0) currentHp = 0;

        isFlashing = true;
        flashTimer = 0f;

        System.out.println("Boss HP: " + currentHp);
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void enterPhase2() {
        isPhase2 = true;
        shootInterval += 0.7f;         // Bắn nhanh hơn
        trackingShootInterval += 0.5f; // Đạn tracking nhanh hơn
        explosionInterval *= 0.8f;     // Đạn nổ nhiều hơn
        frequencyX *= 3f;            // Di chuyển nhanh hơn
        frequency *= 3f;
        screenShake.start(3f, 20f);
    }
    public void fireWaveBullets() {
        fireballSfxTimer = 0f;
        fireballSfxCount = 0;
        fireballSfxActive = true;

        float centerX = Gdx.graphics.getWidth();
        float centerY = Gdx.graphics.getHeight() / 2;
        int numBullets = 18;
        float baseAngle = 180f; // bắn sang trái
        float spread = 120f;     // tổng góc tỏa ra
        float angleStep = spread / (numBullets - 1);

        for (int i = 0; i < numBullets; i++) {
            float angle = baseAngle - spread / 2f + i * angleStep;
            float radians = MathUtils.degreesToRadians * angle;

            Vector2 dir = new Vector2(MathUtils.cos(radians), MathUtils.sin(radians)).nor().scl(500f);

            bullets.add(new Bullet(centerX + 200, centerY, centerX + dir.x, centerY + dir.y, 250f, firewaveAnim, 16, 16, 8));
            bullets.add(new Bullet(centerX + 400, centerY, centerX + dir.x, centerY + dir.y, 250f, firewaveAnim, 16, 16, 8));
            bullets.add(new Bullet(centerX + 600, centerY, centerX + dir.x, centerY + dir.y, 250f, firewaveAnim, 16, 16, 8));
            bullets.add(new Bullet(centerX + 800, centerY, centerX + dir.x, centerY + dir.y, 250f, firewaveAnim, 16, 16, 8));
            bullets.add(new Bullet(centerX + 1000, centerY, centerX + dir.x, centerY + dir.y, 250f, firewaveAnim, 16, 16, 8));
            bullets.add(new Bullet(centerX + 1200, centerY, centerX + dir.x, centerY + dir.y, 250f, firewaveAnim, 16, 16, 8));
        }

    }

    private Animation<TextureRegion> fireAnim;
    private Animation<TextureRegion> touhouAnim;
    private Animation<TextureRegion> rainAnim;
    private Animation<TextureRegion> firewaveAnim;
    private Animation<TextureRegion> doubleSpiral;
    private Animation<TextureRegion> doubleSpiral2;
    private Animation<TextureRegion> something;
    private void loadBulletAnimations() {
        fireAnim = loadAnimation("SpiralBullets/All_Fire_Bullet_Pixel_16x16_05.png", 4, 0.3f,16,16);      // 4 frame
        touhouAnim = loadAnimation("SpiralBullets/touhou.png", 4, 0.3f,14,8); // 6 frame
        rainAnim = loadAnimation("SpiralBullets/rain.png", 4, 0.3f,15,14);
        firewaveAnim = loadAnimation("SpiralBullets/firewave.png", 4, 0.3f,16,15);
        doubleSpiral = loadAnimation("SpiralBullets/boomerang.png", 4, 0.3f,13,8);
        doubleSpiral2 = loadAnimation("Bosses/ExplosiveBullet/Explosive1/ExplosiveAnimation/2.png",4,0.3f,16,15);
        something = loadAnimation("SpiralBullets/All_Fire_Bullet_Pixel_16x16_05.png",4,0.3f,16,16);
    }
    private Animation<TextureRegion> loadAnimation(String path, int frameCount, float frameDuration, int frameWidth, int frameHeight) {
        Texture sheet = new Texture(path);
        TextureRegion[][] tmp = TextureRegion.split(sheet, frameWidth, frameHeight);

        TextureRegion[] frames = new TextureRegion[frameCount];
        for (int i = 0; i < frameCount; i++) {
            frames[i] = tmp[0][i];
        }

        return new Animation<>(frameDuration, frames);
    }

    public void stopMusic() {
        if (soundManager != null) {
            soundManager.stopAll();
        }
    }


    public void update(float delta, Player player){
        this.player = player;
        shootTimer += delta;
        trackingShootTimer += delta;


//        Hitbox position
        hitbox.setPosition(position.x, position.y + 85);

        if (!isPhase2 && shootTimer >= shootInterval) {
            shoot(player.getX(), player.getY()); // đạn thường
            shootTimer = 0;
        }
        if (isFlashing) {
            flashTimer += delta;
            if (flashTimer >= flashDuration) {
                isFlashing = false;
                flashTimer = 0f;
            }
        }
        if (trackingShootTimer >= trackingShootInterval) {
            spawnTrackingBullet(player);
            trackingShootTimer = 0;
        }
        if (!isPhase2){
            meteorTimer += delta;
            if (meteorTimer >= meteorInterval) {
                spawnMeteor();
                meteorTimer = 0f;
            }
        }
        if (fireballSfxActive) {
            fireballSfxTimer += delta;
            if (fireballSfxTimer >= 0.75f && fireballSfxCount < 6) {
                soundManager.playFireballShot();
                fireballSfxTimer = 0f;
                fireballSfxCount++;
            }

            if (fireballSfxCount >= 6) {
                fireballSfxActive = false;
            }
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

        if (!isPhase2) { // phase 2 rồi thì không bắn nữa
            explosionTimer += delta;
            if (explosionTimer >= explosionInterval) {
                shootExplosion();
                explosionTimer = 0;
            }
        }

        time += delta;
        float targetX = baseX + MathUtils.sin(time * frequencyX) * amplitudeX;
        float targetY = baseY + MathUtils.sin(time * frequency) * amplitude;
        position.lerp(new Vector2(targetX, targetY), 0.1f);

        if (!isPhase2 && !waitingForShakeToEnd && currentHp <= hp / 3) {
            waitingForShakeToEnd = true;
            shakeTimer = 0f;
            isInvincible = true;
            if (screenShake != null) {
                screenShake.start(shakeDuration, 20f);
                soundManager.setPhase2Started();
            }
        }
        if (waitingForShakeToEnd) {
            shakeTimer += delta;
            soundManager.playShakeSoundOnce();
            if (shakeTimer >= shakeDuration) {
                waitingForShakeToEnd = false;
                isInvincible = false;
                enterPhase2();
                DuAnh = true;
            }
        }

        if (isPhase2 && !hasFiredWave) {
            phase2Timer += delta;
            if (phase2Timer >= 4f) {
                fireWaveBullets();
                hasFiredWave = true;
                spiralDelayTimer = 0f;
            }
        }
        if (hasFiredWave && !spiralStarted) {
            spiralDelayTimer += delta;
            if (spiralDelayTimer >= 7f) {
                isFiringSpiral = true;
                spiralStarted = true;
                spiralTimer = 0f;

            }
        }
        if (isFiringSpiral) {
            fireTouhouSpiral();
            spiralTimer += delta;
            if (spiralTimer >= spiralDuration) {
                isFiringSpiral = false;
                spiralDone = true;
                patternDelayTimer = 0f;
            }
        }

        if (spiralDone && !isFiringSpiral) {
            patternDelayTimer += delta;
            if (patternDelayTimer >= 3f) {
                patternTimer += delta;
                if (patternTimer >= patternCooldown) {
                    patternTimer = 0f;
                    patternIndex = MathUtils.random(2);
                    switch (patternIndex) {
                        case 0:
                            fireDoubleSpiral();
                            break;
                        case 1: fireRainBullets(); break;
                        case 2: fireSpiralBurst(); break;
                    }
                }
            }
        }


        afterimageTimer += delta;
        if (afterimageTimer >= afterimageInterval) {
            afterimageTimer = 0f;
            afterimages.add(new AfterImage(position, 0.5f, 0.5f)); // alpha 0.5, tồn tại 0.3s
        }


        for (int i = 0; i < afterimages.size(); i++) {
            afterimages.get(i).update(delta);
            if (afterimages.get(i).isDead()) {
                afterimages.remove(i);
                i--;
            }
        }

    }
    public boolean isPhase2() {
        return isPhase2;
    }
    public void fireTouhouSpiral() {
        float centerX = position.x + 128;
        float centerY = position.y + 128;
        int bulletsPerFrame = 4; // bắn liên tục từng chút

        for (int i = 0; i < bulletsPerFrame; i++) {
            float angle = spiralAngle + i * 180; // hai viên đối xứng
            float rad = MathUtils.degreesToRadians * angle;
            Vector2 dir = new Vector2(MathUtils.cos(rad), MathUtils.sin(rad)).nor().scl(220f);
            bullets.add(new Bullet(centerX, centerY, centerX + dir.x, centerY + dir.y, 250f, touhouAnim, 16, 16, 8));
            soundManager.playWindShot();
        }

        spiralAngle += 8f; // tăng theo thời gian → xoay đều
        if (spiralAngle > 360f) spiralAngle -= 360f;
    }
    public void fireDoubleSpiral() {
        float centerX = position.x + 128;
        float centerY = position.y + 128;

        int numBullets = 36;
        float speed = 250f;
        soundManager.playBomberThrow();
        for (int i = 0; i < numBullets; i++) {
            float angle1 = i * 10 + time * 100; // xoắn thuận
            float angle2 = i * 10 - time * 100; // xoắn ngược

            float rad1 = MathUtils.degreesToRadians * angle1;
            float rad2 = MathUtils.degreesToRadians * angle2;

            Vector2 dir1 = new Vector2(MathUtils.cos(rad1), MathUtils.sin(rad1)).nor().scl(speed);
            Vector2 dir2 = new Vector2(MathUtils.cos(rad2), MathUtils.sin(rad2)).nor().scl(speed);
            bullets.add(new Bullet(centerX, centerY, centerX + dir1.x, centerY + dir1.y, speed, doubleSpiral, 16, 16, 8));
            bullets.add(new Bullet(centerX + 300, centerY, centerX + dir2.x, centerY + dir2.y, speed, doubleSpiral2, 16, 16, 8));
        }
    }
    public void fireRainBullets() {
        soundManager.playSonicBoom();
        for (int i = 0; i < 20; i++) {
            float x = MathUtils.random(0, Gdx.graphics.getWidth());
            float y = Gdx.graphics.getHeight() + 50;
            bullets.add(new Bullet(x, y, x, y - 100, 400f, rainAnim, 16, 16, 8));
        }
    }
    public void fireSpiralBurst() {
        float centerX = position.x + 128;
        float centerY = position.y + 128;
        int arms = 5; // số nhánh spiral
        int bulletsPerArm = 10;
        float angleOffset = (time * 120f) % 360; // xoay đều theo thời gian
        soundManager.playExplosion();
        for (int arm = 0; arm < arms; arm++) {
            float baseAngle = arm * (360f / arms) + angleOffset;
            for (int i = 0; i < bulletsPerArm; i++) {
                float angle = baseAngle + i * 5f;
                float rad = MathUtils.degreesToRadians * angle;
                float speed = 150f + i * 15f; // càng xa càng nhanh

                Vector2 dir = new Vector2(MathUtils.cos(rad), MathUtils.sin(rad)).nor().scl(speed);
                bullets.add(new Bullet(centerX, centerY, centerX + dir.x, centerY + dir.y, speed, something, 16, 16, 8));
                bullets.add(new Bullet(centerX + 200, centerY, centerX + dir.x, centerY + dir.y, speed, something, 16, 16, 8));
            }
        }
    }

    public void spawnMeteor() {
        float startX = MathUtils.random(0, Gdx.graphics.getWidth() - 256);
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
    public void stopAllMusic() {
        soundManager.stopAll();
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

    public int getCurrentHp() {
        return currentHp;
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
        if (DuAnh){
            for (AfterImage a : afterimages) {
                batch.setColor(1f, 0.5f, 1f, a.alpha);
                batch.draw(BossOne, a.position.x, a.position.y, 256, 256);
            }
        }
        if (isFlashing) {
            batch.setColor(1f, 1f, 1f, 0.875f); // alpha thấp -> mờ
        } else {
            batch.setColor(1f, 1f, 1f, 1f);   // bình thường
        }
        batch.draw(BossOne, position.x, position.y, 256, 256);
        batch.setColor(1f, 1f, 1f, 1f);
        batch.end();


//        Boss Health Bar
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

