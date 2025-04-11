package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;
import java.util.ArrayList;

public class Player extends InputAdapter {
    private Texture texture;
    private float x, y, width, height;
    private float speed = 200;
    private float rotation = 0f;

    private Vector2 position;
    private ArrayList<Bullet> bullets;
    private float shootTimer = 0f;
    private float shootInterval = 0.2f;
    private boolean shooting = false;

    boolean isMouseDown;

    public Player() {
        texture = new Texture("Stuffs/Player/lvl2.png");  // Load ảnh từ thư mục assets
        x = 100;   // vị trí ban đầu
        y = 100;
        width = 64;
        height = 64;

        position = new Vector2(x, y);
        bullets = new ArrayList<>();
    }

    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }

    public void shoot(float targetX, float targetY){
        float centerX = position.x;
        float centerY = position.y + 32;
        bullets.add(new Bullet(centerX, centerY, targetX, targetY));
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

//        Nhấn chuột sẽ bắn đạn
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            shooting = true;
        }

//        Update đạn
        shootTimer += delta;
        if (shooting) {
            System.out.println(shooting);
            if (shootTimer >= shootInterval) {
                shoot(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
                shootTimer = 0;
            }
            for (int i = 0 ; i < bullets.size(); i++) {
                Bullet b = bullets.get(i);
                b.update(delta);
                if (b.isOutOfScreen()) {
                    b.dispose();
                    bullets.remove(b);
                    i--;
                }
            }
        }



//        Đổi góc nhìn của Player hướng vào vị trí của chuột so với player
        // Get mouse position
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // flip Y

        // Find center of player
        float deltaX = mouseX - x;
        float deltaY = mouseY - y;

//      Calculate angle in radians and convert to degrees
        float angleRad = (float)Math.atan2(deltaY, deltaX);
        float angleDeg = (float)Math.toDegrees(angleRad);

//      Apply rotation to your player
        rotation = angleDeg - 90; //- 90 để làm cái ảnh quay sang phải

    }

    public void render(SpriteBatch batch) {

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
    }

    public void dispose() {
        texture.dispose();  // Giải phóng bộ nhớ khi thoát game
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            isMouseDown = true;
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            isMouseDown = false;
        }
        return true;
    }
}
