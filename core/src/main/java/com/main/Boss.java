package com.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;


import java.util.ArrayList;

public class Boss {
     private Texture BossOne;
     private Vector2 position;
     private ArrayList<Bullet> bullets;
     private float shootTimer = 0f;
     private float shootInterval = 0.2f;
     private Player player;
     //sin wave movement

    private float trackingShootTimer = 0f;
    private float trackingShootInterval = 3f;

    private float time = 0f;
    private float baseY;
    private float amplitude = 200f;
    private float frequency = 1f;


     public Boss (float x, float y){
         BossOne = new Texture("Bosses/Ship6/Ship6.png");
         position = new Vector2(x,y);
         bullets = new ArrayList<>();
         baseY = y;

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
             shootTracking(player);
             trackingShootTimer = 0;
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
         time += delta;
         position.y = baseY + MathUtils.sin(time * frequency) * amplitude;
     }

     public void shoot(float targetX, float targetY){
         float centerX = position.x;
         float centerY = position.y + 64 - 15;
         bullets.add(new Bullet(centerX, centerY, targetX, targetY));

     }
    public void shootTracking(Player player) {
        float centerX = position.x;
        float centerY = position.y + 64 - 15;

        bullets.add(new Bullet(centerX, centerY, player)); // đạn tracking
    }

     public void render(SpriteBatch batch){
         batch.draw(BossOne, position.x, position.y, 128, 128);

         for (Bullet bullet : bullets){
             bullet.render(batch);
         }
     }
     public void dispose(){
         for (Bullet bullet : bullets){
             bullet.dispose();
         }
     }
}

