package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

import java.util.ArrayList;


public class MapBossOne {

    private Player player;
    private Boss BossOne;
    private float middleScreen;

    private ArrayList<Bullet> bossBullets;
    private ArrayList<Bullet> enemyBullets;
    private ArrayList<FragmentBullet> fragmentBullets;
    private ArrayList<Enemy> enemies;


    private float spawnEnemyTimer = 0f;
    private float spawnEnemyInterval = 10f;

    private ArrayList<HitEffect> hitEffects = new ArrayList<>();

    //Enemy Death Effect
    ArrayList<DeathExplosionEffect> deathEffects = new ArrayList<>();

    public MapBossOne(){
        middleScreen = (Gdx.graphics.getHeight() / 2f) - 120;
        player = new Player();
        BossOne = new Boss(1400,middleScreen);
        enemies = new ArrayList<>();
    }

    public void update(float deltaTime) {
        player.update();
        BossOne.update(deltaTime, player);


//        spawn mobs
        spawnEnemyTimer += deltaTime;
        if (spawnEnemyTimer >= spawnEnemyInterval) {
            enemies.add(new Enemy(BossOne.getPositionX(), BossOne.getPositionY())); // spawn gần boss
            spawnEnemyTimer = 0f;
        }

        for (Enemy enemy : enemies){
            enemy.update(deltaTime);
        }


//        Heal the plyer to max HP (for testing purposes)
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            player.heal(100);
        }


//        Check if player get hit by the boss's bullet
        bossBullets = BossOne.getBullets();


        for (int i = 0 ; i < bossBullets.size(); i++) {
            Bullet b = BossOne.getBullets().get(i);


//            Normal bullet hit detection
            if (bulletHit(b.getBulletHitbox(), player.getHitbox())) {
                if (!player.isInvincible()){
                    player.takeDamage(b.getDamage());
                    bossBullets.remove(i);
                    i--;
                }

            }

//            Check if the bullet being checked is a Explosion bullet (Cuz the fragment they explode is a different bullet class)
            fragmentBullets = BossOne.getFragmentBullets();
            for (int j = 0; j < fragmentBullets.size(); j++) {
                FragmentBullet fb = fragmentBullets.get(j);
                if (!player.isInvincible()){
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
                if(bulletHit(bullet.getHitbox(), player.getHitbox())) {
                        if (!player.isInvincible()){
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
                i--;
            }
        }
    }


    public void renderHitbox() {
        player.renderHitbox();
        BossOne.renderHitbox();
    }

    public void render(SpriteBatch batch){
        player.render(batch);
        BossOne.render(batch);

        for (Enemy enemy : enemies){
            enemy.render(batch);
        }
        for (HitEffect e : hitEffects) {
            e.render(batch);
        }
        for (DeathExplosionEffect effect : deathEffects) {
            effect.render(batch);
        }
        renderHitbox();
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
        player.dispose();
        BossOne.dispose();

    }
}
