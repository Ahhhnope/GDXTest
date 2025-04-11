package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

import java.awt.*;

public class Player {
    private Texture texture;
    private float x, y, width, height;
    private float speed = 200;
    private float rotation = 0f;

    public Player() {
        texture = new Texture("Stuffs/Player/lvl2.png");  // Load ảnh từ thư mục assets
        x = 100;   // vị trí ban đầu
        y = 100;
        width = 64;
        height = 64;

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



//        Đổi góc nhìn của Player hướng vào vị trí của chuột so với player
        // Get mouse position
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // flip Y

        // Find center of player
        float deltaX = mouseX - x;
        float deltaY = mouseY - y;

// Calculate angle in radians and convert to degrees
        float angleRad = (float)Math.atan2(deltaY, deltaX);
        float angleDeg = (float)Math.toDegrees(angleRad);

// Apply rotation to your player
        rotation = angleDeg - 90;

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
}
