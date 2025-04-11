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
    int screenWidth;
    int screenHeight;

    GameManager gm;

    @Override
    public void create() {
        background = new Texture("Stuffs/background.png");
        batch = new SpriteBatch();
        gm = new GameManager("menu");


        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();


    }

    @Override
    public void render() {

        Gdx.gl.glClearColor(0, 0, 0, 1);  // Đặt màu nền (đen)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gm.render();
        gm.update();
    }

    @Override
    public void dispose() {
        background.dispose();
        gm.dispose();
    }


}
