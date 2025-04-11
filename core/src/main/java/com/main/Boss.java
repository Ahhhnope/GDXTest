package com.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.main.Bullet;

import java.util.ArrayList;

public class Boss {
     private Texture BossOne;
     private Vector2 position;
     private ArrayList<Bullet> bullets;
     private float shootTimer = 0f;
     private float shootInterval = 0.25f;

     public Boss (float x, float y){
         BossOne = new Texture("Bosses/Ship6/Ship6.png");
         position = new Vector2(x,y);
         bullets = new ArrayList<>();
     }

     public void update(float delta, Player player){
        shootTimer += delta;
        if (shootTimer >= shootInterval){
            shoot(player.getX(), player.getY());
            shootTimer = 0f;
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
     }

     public void shoot(float targetX, float targetY){
         float centerX = position.x + 64 - 8;
         float centerY = position.y + 64 - 8;
         bullets.add(new Bullet(centerX, centerY, targetX, targetY));
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

