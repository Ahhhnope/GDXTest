package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

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

    public MapBossOne(){
        middleScreen = (Gdx.graphics.getHeight() / 2f) - 60;
        player = new Player();
        BossOne = new Boss(1150,middleScreen);
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
            if (bulletHit(b.getHitbox(), player.getHitbox())) {
                player.takeDamage(b.getDamage());
                bossBullets.remove(i);
                i--;
            }

//            Check if the bullet being checked is a Explosion bullet (Cuz the fragment they explode is a different bullet class)
            if (b.getClass() == ExplosionBullet.class) {
                fragmentBullets = ((ExplosionBullet) b).getfragments();
                for (int j = 0; j < fragmentBullets.size(); j++) {
                    if (bulletHit(b.getHitbox(), player.getHitbox())) {
                        player.takeDamage(b.getDamage());
                        bossBullets.remove(j);
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
                    player.takeDamage(bullet.getDamage());
                    enemyBullets.remove(i);
                    i--;
                }
            }
        }


    }

    public void renderHitbox() {
        player.renderHitbox();
        BossOne.renderHitbox();


        if (enemyBullets != null) {
            for(Bullet b : enemyBullets) {
                b.renderHitbox();
            }
        }

        if (fragmentBullets != null) {
            for(FragmentBullet b : fragmentBullets) {
                b.renderHitbox();
            }
        }
    }

    public void render(SpriteBatch batch){
        player.render(batch);
        BossOne.render(batch);

        for (Enemy enemy : enemies){
            enemy.render(batch);
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
