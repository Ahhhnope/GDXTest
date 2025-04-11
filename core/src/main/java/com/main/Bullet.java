package com.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    private Texture bulletTexture;
    private Vector2 position;
    private Vector2 velocity;
    private float speed = 400f;

    public Bullet(float startX, float startY, float targetX, float targetY){
        bulletTexture = new Texture("Bosses/Ship6/Exhaust/Turbo_flight/Exhaust3/exhaust4.png");
        position = new Vector2(startX, startY);
        Vector2 direction = new Vector2(targetX - startX, targetY - startY).nor();
        velocity = direction.scl(speed);
    }

    public void update(float delta){
        position.add(velocity.x * delta, velocity.y * delta);

    }

    public void render(SpriteBatch batch){
        batch.draw(bulletTexture,position.x,position.y,32,32);
    }

    public boolean isOutOfScreen(){
        return position.x < -50 || position.x > 2000 || position.y < -50 || position.y > 2000;
    }

    public void dispose(){
        bulletTexture.dispose();
    }
}
