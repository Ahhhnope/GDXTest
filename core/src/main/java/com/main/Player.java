package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
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
    private Texture playerBulletTexture;

    private Vector2 velocity;


    private float width, height;
    private float speed = 300f;
    private float friction = 0.975f;
    private float rotation = 0f;

    private Vector2 position;

    private ArrayList<Bullet> bullets;
    private float x = 100;
    private float y = 100;



    private int maxHP = 100;
    private int currentHP = 50;

    private float shootTimer = 0f;

    private float shootInterval = 0.1f;
    float hitboxSize;
    float offset;

    private ShapeRenderer shapeRenderer;

    public Player() {
        texture = new Texture("Stuffs/Player/lvl2.png");
        position = new Vector2(100, 100);
        velocity = new Vector2(0, 0);
        width = 64;
        height = 64;
        shapeRenderer = new ShapeRenderer();


        position = new Vector2(x, y);
        bullets = new ArrayList<>();


        //hitbox
        hitboxSize = 40f;
        offset = (width - hitboxSize) / 2f;
        hitbox = new Rectangle(position.x - width / 2 + offset, position.y - height / 2 + offset, hitboxSize, hitboxSize);
    }

    public float getX() { return position.x; }
    public float getY() { return position.y; }


    public boolean isInsideScreen(float x, float y) {
        return position.x < -200 || position.x > 2000 || position.y < -50 || position.y > 2000;
    }

    public void update() {
        float delta = Gdx.graphics.getDeltaTime();

        // Di chuyển trơn mượt bằng velocity và friction
        Vector2 input = new Vector2(0, 0);

// Lấy input từ bàn phím
        if (Gdx.input.isKeyPressed(Input.Keys.W)) input.y += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) input.y -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) input.x -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) input.x += 1;

// Nếu đang ấn phím nào đó → chuẩn hóa và nhân với tốc độ
        if (input.len() > 0) {
            input.nor().scl(speed);  // tốc độ đều nhau
            velocity.lerp(input, 0.2f);  // làm mượt
        } else {
            velocity.scl(friction);  // giảm dần
        }

// Cập nhật vị trí

        position.x = MathUtils.clamp(position.x, width / 2 - 10, Gdx.graphics.getWidth() - width / 2 + 14);
        position.y = MathUtils.clamp(position.y, height / 2 - 10, Gdx.graphics.getHeight() - height / 2 + 14);
        position.add(velocity.x * delta, velocity.y * delta);

        // Xoay theo chuột

        // Get mouse position
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        float deltaX = mouseX - position.x;
        float deltaY = mouseY - position.y;
        rotation = (float) Math.toDegrees(Math.atan2(deltaY, deltaX)) - 90;


        // Cập nhật hitbox

//          Ấn chuột để bắn

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
        deltaX = mouseX - position.x;
        deltaY = mouseY - position.y;

// Calculate angle in radians and convert to degrees
        float angleRad = (float)Math.atan2(deltaY, deltaX);
        float angleDeg = (float)Math.toDegrees(angleRad);

// Apply rotation to your player
        rotation = angleDeg - 90;

//        Update hitbox
        hitbox = new Rectangle(position.x - width / 2 + offset, position.y - height / 2 + offset, hitboxSize, hitboxSize);
    }


    public void shoot(float targetX, float targetY) {
        playerBulletTexture = new Texture("Stuffs/Player/playerbullet.png");

        float centerX = hitbox.x + hitbox.width / 2f;
        float centerY = hitbox.y + hitbox.height / 2f;
        bullets.add(new Bullet(centerX, centerY, targetX, targetY,2500,playerBulletTexture, 8, 8, 5));

    }


    public Rectangle getHitbox() {
        return hitbox;
    }

    public void takeDamage(int damage) {
        currentHP -= damage;
        if (currentHP < 0) currentHP = 0;
    }

    public void render(SpriteBatch batch) {
        // Thanh máu
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        float barX = (Gdx.graphics.getWidth() / 2) - 600f;
        float barY = 20f;
        float healthbarWidth = 300f;
        float healthbarHeight = 20f;
        float radius = healthbarHeight / 2;

        float percent = (float) currentHP / maxHP;
        float filledWidth = healthbarWidth * percent;
        float fillRight = barX + filledWidth;

        shapeRenderer.setColor(0.3f, 0.3f, 0.3f, 1f);
        shapeRenderer.rect(barX + radius, barY, healthbarWidth - radius * 2, healthbarHeight);
        shapeRenderer.circle(barX + radius, barY + radius, radius);
        shapeRenderer.circle(barX + healthbarWidth - radius, barY + radius, radius);

        shapeRenderer.setColor(1f, 0f, 0f, 1f);
        if (filledWidth > radius * 2) {
            shapeRenderer.rect(barX + radius, barY, filledWidth - radius * 2, healthbarHeight);
            shapeRenderer.circle(barX + radius, barY + radius, radius);
            shapeRenderer.circle(fillRight - radius, barY + radius, radius);
        } else {
            shapeRenderer.circle(barX + radius, barY + radius, filledWidth / 2f);
        }

        shapeRenderer.end();

        // Hitbox

        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 0, 0, 1f);
        shapeRenderer.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
        shapeRenderer.end();

        batch.begin();
        // Vẽ player
        batch.setColor(1f, 1f, 1f, 1f);
        batch.draw(
            texture,
            position.x - width / 2f,
            position.y - height / 2f,
            width / 2f,
            height / 2f,
            width,
            height,
            1f,
            1f,
            rotation,
            0,
            0,
            texture.getWidth(),
            texture.getHeight(),
            false,
            false
        );
        batch.end();

        for (Bullet bullet : bullets){
            bullet.render(batch);
        }

        for (Bullet b : bullets) {
            b.renderHitbox();
        }
    }

    public void dispose() {
        // Giải phóng bộ nhớ khi thoát game
        texture.dispose();
        for (Bullet bullet : bullets){
            bullet.dispose();
        }
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }
}
