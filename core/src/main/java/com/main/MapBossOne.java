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

    public MapBossOne(){
        middleScreen = (Gdx.graphics.getHeight() / 2f) - 60;
        player = new Player();
        BossOne = new Boss(1150,middleScreen);
    }

    public void update(float deltaTime) {
        player.update();
        BossOne.update(deltaTime, player);


//        Heal the plyer to max HP (for testing purposes)
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            player.heal(100);
        }


//        Check if player get hit by the boss's bullet
        bossBullets = BossOne.getBullets();
        for (int i = 0 ; i < bossBullets.size(); i++) {
            Bullet b = BossOne.getBullets().get(i);
            if (bulletHit(b.getHitbox(), player.getHitbox())) {
                player.takeDamage(b.getDamage());

                bossBullets.remove(i);
                i--;
            }
        }

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


    public void render(SpriteBatch batch){
        player.render(batch);
        BossOne.render(batch);
    }

    public void dispose(){
        player.dispose();
        BossOne.dispose();
    }
}
