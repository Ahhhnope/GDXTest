package com.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import javax.swing.*;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    Texture background;
    SpriteBatch batch;
    GameManager gm;
    int screenWidth;
    int screenHeight;

    Player player;

    @Override
    public void create() {
        background = new Texture("Stuffs/background.png");
        player = new Player();
        batch = new SpriteBatch();
        gm = new GameManager();


        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();


    }

    public void inputs() {

    }

    public void update() {
    }

    public void draw() {
//        batch.setColor(Color.BLACK);
    }


    @Override
    public void render() {

        Gdx.gl.glClearColor(0, 0, 0, 1);  // Đặt màu nền (đen)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float delta = Gdx.graphics.getDeltaTime();


        //gm.render(0); render map
        inputs();
        update();
        draw();
        player.update(delta);
        //        Draw stuff inside begin and end
        batch.begin();
//        batch.draw(background, 0, 0, screenWidth, screenHeight);
        player.render(batch);
        batch.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        player.dispose();
    }


}
