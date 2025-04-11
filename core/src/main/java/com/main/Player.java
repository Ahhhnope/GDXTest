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
    private SpriteBatch batch;

    public Player() {
        texture = new Texture("Stuffs/Player/lvl2.png");  // Load ảnh từ thư mục assets
        x = 100;   // vị trí ban đầu
        y = 100;

        batch = new SpriteBatch();
    }

    public void update() {
        float delta = Gdx.graphics.getDeltaTime();
        // delta là thời gian giữa mỗi frame → giúp chuyển động mượt

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            y += speed * delta;  // Di chuyển lên
            System.out.println(delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            y -= speed * delta;  // Di chuyển xuống
            System.out.println(delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            x -= speed * delta;  // Di chuyển trái
            System.out.println(delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            x += speed * delta;  // Di chuyển phải
            System.out.println(delta);
        }



//        Đổi góc nhìn của Player hướng vào vị trí của chuột so với player

    }

    public void render() {
        batch.begin();
        batch.draw(texture, x, y, 64, 64);  // Vẽ ảnh tại vị trí x, y với size 64x64
        batch.end();
    }

    public void dispose() {
        texture.dispose();  // Giải phóng bộ nhớ khi thoát game
    }
}
