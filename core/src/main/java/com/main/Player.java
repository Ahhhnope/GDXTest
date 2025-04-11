package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

public class Player {
    private Texture texture;
    private float x, y;
    private float speed = 200;

    public Player() {
        texture = new Texture("player.png");  // Load ảnh từ thư mục assets
        x = 100;   // vị trí ban đầu
        y = 100;
    }

    public void update(float delta) {
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
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, 64, 64);  // Vẽ ảnh tại vị trí x, y với size 64x64
    }

    public void dispose() {
        texture.dispose();  // Giải phóng bộ nhớ khi thoát game
    }
}
