package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.main.Inputs.InputHandler;

import java.awt.*;
import java.util.ArrayList;

public class Player {
    private Rectangle hitbox;
    private Texture texture;
    private float x, y, width, height;
    private float speed = 220;
    private float rotation = 0f;

    private Vector2 position;
    private ArrayList<Bullet> bullets;


    private int maxHP = 100;
    private int currentHP = 50;

    private float shootTimer = 0f;
    private float shootInterval = 0.3f;

    private ShapeRenderer shapeRenderer;

    public Player() {
        texture = new Texture("Stuffs/Player/lvl2.png");  // Load ảnh từ thư mục assets
        x = 100;   // vị trí ban đầu
        y = 100;
        width = 64;
        height = 64;
        shapeRenderer = new ShapeRenderer();

        position = new Vector2(x, y);
        bullets = new ArrayList<>();
    }

    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }

    public void update() {
        float delta = Gdx.graphics.getDeltaTime();
        // delta là thời gian giữa mỗi frame → giúp chuyển động mượt

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            y += speed * delta;  // Di chuyển lên
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            y -= speed * delta;  // Di chuyển xuống
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            x -= speed * delta;  // Di chuyển trái
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            x += speed * delta;  // Di chuyển phải
        }


        // Get mouse position
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // flip Y

//          Ấn chuột để bắn
        position.x = x;
        position.y = y;
        shootTimer += delta;
        if (InputHandler.isMouseDown) {
            if (shootTimer >= shootInterval) {
                shoot(mouseX, mouseY); // đạn thường
                shootTimer = 0;
            }
        }

//        Update đạn
        for (int i = 0; i < bullets.size();i++){
            Bullet b = bullets.get(i);
            b.update(delta);
            if (b.isOutOfScreen()){
                b.dispose();
                bullets.remove(i);
                i--;
            }
        }

        // Đổi góc nhìn của Player hướng vào vị trí của chuột so với player

        // Find center of player
        float deltaX = mouseX - x;
        float deltaY = mouseY - y;

// Calculate angle in radians and convert to degrees
        float angleRad = (float)Math.atan2(deltaY, deltaX);
        float angleDeg = (float)Math.toDegrees(angleRad);

// Apply rotation to your player
        rotation = angleDeg - 90;

        //hitbox
        float hitboxSize = 40f;
        float offset = (width - hitboxSize) / 2f;
        hitbox = new Rectangle(x - width / 2 + offset, y - height / 2 + offset, hitboxSize, hitboxSize);
    }

    public void shoot(float targetX, float targetY) {
        float centerX = position.x;
        float centerY = position.y;
        System.out.println(position.x + " | " + position.y);
        bullets.add(new Bullet(centerX, centerY, targetX, targetY));
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void takeDamage(int Damage){
        currentHP -= Damage;
        if (currentHP < 0){
            currentHP = 0;
        }
    }

    public void render(SpriteBatch batch) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        float barX = (Gdx.graphics.getWidth() / 2) - 600f;
        float barY = 20f;
        float healthbarWidth = 300f;
        float healthbarHeight = 20f;
        float padding = 20f;
        float radius = healthbarHeight / 2;

        float percent = (float) currentHP / maxHP;
        float filledWidth = healthbarWidth * percent;
        float fillRight = barX + filledWidth;

        shapeRenderer.setColor(0.3f, 0.3f, 0.3f, 1f);
        shapeRenderer.rect(barX + radius, barY, healthbarWidth - radius * 2, healthbarHeight);
        shapeRenderer.circle(barX + radius, barY + radius, radius);
        shapeRenderer.circle(barX + healthbarWidth - radius, barY + radius, radius);

// 🔴 Thanh máu (đỏ bo tròn theo phần trăm máu)
        shapeRenderer.setColor(1f, 0f, 0f, 1f);
        if (filledWidth > radius * 2) {
            shapeRenderer.rect(barX + radius, barY, filledWidth - radius * 2, healthbarHeight);
            shapeRenderer.circle(barX + radius, barY + radius, radius);                // đầu trái
            shapeRenderer.circle(fillRight - radius, barY + radius, radius);           // đầu phải
        } else {
            // Nếu thanh máu quá ngắn, vẽ nửa vòng tròn
            shapeRenderer.circle(barX + radius, barY + radius, filledWidth / 2f);
        }

        shapeRenderer.end();


        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 0, 0, 1f); // đỏ hitbox
        shapeRenderer.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
        shapeRenderer.end();
        Gdx.gl.glEnable(GL20.GL_BLEND);

       /* shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1f, 1f, 1f, 0.3f);
        shapeRenderer.rect(x - width / 2, y - height / 2, width, height);
        shapeRenderer.end();*/




        batch.begin();

        for (Bullet bullet : bullets){
            bullet.render(batch);
        }

        batch.setColor(1f, 1f, 1f, 1f);
        batch.draw(
                texture,
                x - width / 2,       // X position (adjust to center the texture)
                y - height / 2,      // Y position
                width / 2,           // Origin X (rotation center)
                height / 2,          // Origin Y
                width,               // Width
                height,              // Height
                1f,                  // Scale X
                1f,                  // Scale Y
                rotation,         // Rotation in degrees
                0,                   // Src X (texture region)
                0,                   // Src Y
                texture.getWidth(),
                texture.getHeight(),
                false,               // Flip X
                false                // Flip Y
        );



        batch.end();
    }

    public void dispose() {
        // Giải phóng bộ nhớ khi thoát game
        texture.dispose();
        for (Bullet bullet : bullets){
            bullet.dispose();
        }
    }
}
