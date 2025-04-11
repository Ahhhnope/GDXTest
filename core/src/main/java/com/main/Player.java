package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

public class Player {
    String playerAction = "Idle";
    int x = 20;
    int y = 20;
    int width = 100, height = 100;
    int speed = 4;
    float delta;

    SpriteBatch batch;
    Texture playerSprite;


    public Player(int playerX, int playerY, int playerWidth, int playerHeight, float delta) {
        this.x = playerX;
        this.y = playerY;
        this.width = playerWidth;
        this.height = playerHeight;
        this.delta = delta;

        playerSprite = new Texture("Stuffs/Player/lvl2.png");
        batch = new SpriteBatch();
    }

    public void update() {
        updatePosition();
    }

    public void draw() {
        batch.begin();
        batch.draw(playerSprite, x, y, width, height);
        batch.end();
    }

    public void updatePosition() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isButtonPressed(Input.Buttons.FORWARD)) {
            y += (speed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isButtonPressed(Input.Buttons.BACK)) {
            y -= (speed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            x -= (speed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            x += (speed * delta);
        }
    }

}
