package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.main.Service.Services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class MapBossOne {
    private boolean hasStartedTimer = false;
    private ScreenShake screenShake = new ScreenShake();

    private Player player;
    private Boss BossOne;
    private float middleScreen;
    private ArrayList<Bullet> bossBullets;
    private ArrayList<Bullet> enemyBullets;
    private ArrayList<FragmentBullet> fragmentBullets;
    private ArrayList<Enemy> enemies;


    private boolean bossInitialized = false;

    private float spawnEnemyTimer = 0f;
    private float spawnEnemyInterval = 10f;

    private ArrayList<HitEffect> hitEffects = new ArrayList<>();
    private boolean scoreSubmitted;

    //Enemy Death Effect
    ArrayList<DeathExplosionEffect> deathEffects = new ArrayList<>();

    private HUD hud;
    private Services services;
    private PauseScreen pauseScreen;

    private boolean paused;


    private int hitOnBoss = 0;
    private int mobKilled = 0;

    public MapBossOne(){
        middleScreen = (Gdx.graphics.getHeight() / 2f) - 120;
        player = new Player();
        enemies = new ArrayList<>();
        hud = new HUD();
        services = new Services();

        pauseScreen = new PauseScreen();
        paused = false;

        scoreSubmitted = false;
    }

    public void update(float deltaTime) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (BossOne.getCurrentHp() > 0) {
                paused = !paused;
            } else {
//                Nothing happen (So you can't pause during the win screen) =w=
            }
        }


        if (paused) {
            pauseScreen.update();

        } else {
            if (!bossInitialized) {
                MusicManager.stopMenuMusic();
                BossOne = new Boss(1400, middleScreen, screenShake);
                bossInitialized = true;
                System.out.println("Boss actually spawned — NOW music should start!");
            }


            if (BossOne.getCurrentHp() > 0) {
                if (!hasStartedTimer) {
                    hud.start();            // bắt đầu timer
                    hud.show();
                    hasStartedTimer = true;
                }
                hud.update(deltaTime);

                player.update();
                BossOne.update(deltaTime, player);


                //        spawn mobs
                if (!BossOne.isPhase2()) {  // <-- Thêm điều kiện check phase
                    spawnEnemyTimer += deltaTime;
                    if (spawnEnemyTimer >= spawnEnemyInterval) {
                        enemies.add(new Enemy(BossOne.getPositionX(), BossOne.getPositionY()));
                        spawnEnemyTimer = 0f;
                    }
                }

                for (Enemy enemy : enemies) {
                    enemy.update(deltaTime);
                }


                //        Heal the plyer to max HP (for testing purposes)
                if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
                    player.heal(2000);
                }


                //        Check if player get hit by the boss's bullet
                bossBullets = BossOne.getBullets();


                for (int i = 0; i < bossBullets.size(); i++) {
                    Bullet b = BossOne.getBullets().get(i);


                    //            Normal bullet hit detection
                    if (bulletHit(b.getBulletHitbox(), player.getHitbox())) {
                        if (!player.isInvincible()) {
                            player.takeDamage(b.getDamage());
                            bossBullets.remove(i);
                            i--;
                        }

                    }

                    //            Check if the bullet being checked is a Explosion bullet (Cuz the fragment they explode is a different bullet class)
                    fragmentBullets = BossOne.getFragmentBullets();
                    for (int j = 0; j < fragmentBullets.size(); j++) {
                        FragmentBullet fb = fragmentBullets.get(j);
                        if (!player.isInvincible()) {
                            if (bulletHit(fb.getBulletHitbox(), player.getHitbox())) {
                                player.takeDamage(fb.getDamage());
                                fragmentBullets.remove(j);
                                j--;
                            }
                        }

                    }

                }

                for (int j = 0; j < enemies.size(); j++) {
                    enemyBullets = enemies.get(j).getBullets();

                    for (int i = 0; i < enemyBullets.size(); i++) {
                        Bullet bullet = enemyBullets.get(i);
                        if (bulletHit(bullet.getHitbox(), player.getHitbox())) {
                            if (!player.isInvincible()) {
                                player.takeDamage(bullet.getDamage());
                                enemyBullets.remove(i);
                                i--;
                            }

                        }
                    }
                }


                ArrayList<Bullet> playerBullets = player.getBullets();
                Rectangle bossHitbox = BossOne.getHitbox();


                for (int i = 0; i < playerBullets.size(); i++) {
                    Bullet bullet = playerBullets.get(i);
                    if (bullet.getBulletHitbox() != null && bulletHit(bullet.getBulletHitbox(), bossHitbox)) {
                        BossOne.takeDamage(bullet.getDamage());

                        hitEffects.add(new HitEffect(
                            bullet.getBulletHitbox().x,
                            bullet.getBulletHitbox().y
                        ));

                        hitOnBoss++;
                        playerBullets.remove(i);
                        i--;
                    }
                }
                for (int i = 0; i < hitEffects.size(); i++) {
                    HitEffect e = hitEffects.get(i);
                    e.update(deltaTime);
                    if (e.isFinished()) {
                        hitEffects.remove(i);
                        i--;
                    }
                }

                for (int i = 0; i < enemies.size(); i++) {
                    Enemy enemy = enemies.get(i);
                    Rectangle enemyHitbox = enemy.getHitbox();

                    for (int j = 0; j < playerBullets.size(); j++) {
                        Bullet bullet = playerBullets.get(j);
                        if (bullet.getBulletHitbox() != null && bulletHit(bullet.getBulletHitbox(), enemyHitbox)) {
                            enemy.takeDamage(bullet.getDamage());
                            hitEffects.add(new HitEffect(bullet.getBulletHitbox().x, bullet.getBulletHitbox().y));
                            playerBullets.remove(j);
                            j--;
                        }
                    }
                    if (enemy.getCurrentHP() <= 0) {
                        deathEffects.add(new DeathExplosionEffect(enemy.getX(), enemy.getY()));
                        enemies.remove(i);
                        i--;
                    }
                }
                //enemy death effect
                for (int i = 0; i < deathEffects.size(); i++) {
                    DeathExplosionEffect effect = deathEffects.get(i);
                    effect.update(deltaTime);
                    if (effect.isFinished()) {
                        deathEffects.remove(i);

                        mobKilled++;
                        i--;
                    }
                }
                screenShake.update(deltaTime);
            } else {
                if (!scoreSubmitted) {
                    //                SUBMIT DAT SCORE
                    float time = hud.getTime();
                    int score = ((hitOnBoss + 50) + (mobKilled + 100)) * ((player.getCurrentHP() / player.getMaxHP()) * 100);
                    LocalDate date = LocalDate.now();
                    String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    System.out.println(score);

                    if (!scoreSubmitted) {
                        if (submitScore(1, score, time, formattedDate)) {
                        }
                        scoreSubmitted = true;
                    }
                }
            }
        }



    }

    public boolean submitScore(int level, int score, float time, String date) {
        return services.addMatch(level, score, time, date);
    }


    public void renderHitbox() {
        player.renderHitbox();
        player.renderHitbox();
        if (bossInitialized) {
            BossOne.renderHitbox();
        }
    }

    public void render(SpriteBatch batch){

            hud.render(batch);
            player.render(batch);
            if (bossInitialized) {
                BossOne.render(batch);
            }
            for (Enemy enemy : enemies){
                enemy.render(batch);
            }
            for (HitEffect e : hitEffects) {
                e.render(batch);
            }
            for (DeathExplosionEffect effect : deathEffects) {
                effect.render(batch);
            }
            float shakeX = screenShake.getOffsetX();
            float shakeY = screenShake.getOffsetY();

            batch.setTransformMatrix(batch.getTransformMatrix().idt().translate(shakeX, shakeY, 0));
//            Draw paused shit
        if (paused) {
            pauseScreen.render(batch);

        }


//        renderHitbox();
    }


//    Check if bullet is inside a hitbox
    // oh my god you have to use pythagorean theorem, lmao
    public boolean bulletHit(Circle bulletHitbox, Rectangle playerHitbox) {
        float closetXtoBullet = MathUtils.clamp(bulletHitbox.x, playerHitbox.x, playerHitbox.x + playerHitbox.width);
        float closetYtoBullet = MathUtils.clamp(bulletHitbox.y, playerHitbox.y, playerHitbox.y + playerHitbox.height);

        float bulletXtoPlayer = bulletHitbox.x - closetXtoBullet;
        float bulletYtoPlayer = bulletHitbox.y - closetYtoBullet;

        float distanceSquared = (bulletXtoPlayer * bulletXtoPlayer) + (bulletYtoPlayer * bulletYtoPlayer);

        return distanceSquared <= (bulletHitbox.radius * bulletHitbox.radius);
    }

    public void dispose(){
        hud.dispose();
        player.dispose();
        pauseScreen.dispose();
        if (bossInitialized) {
            BossOne.dispose();
        }

    }
}
