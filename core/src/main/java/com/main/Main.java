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

    private Player player;
    private Boss BossOne;
    @Override
    public void create() {

        background = new Texture("Stuffs/background.png");
        batch = new SpriteBatch();
        gm = new GameManager("menu");
        player = new Player();
        BossOne = new Boss(1000,500);

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

    }

    @Override
    public void render() {
        float deltatime = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0, 0, 0, 1);  // Đặt màu nền (đen)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //player
        batch.begin();
        player.render(batch);
        player.update();
        //boss
        BossOne.render(batch);
        BossOne.update(deltatime, player);
        batch.end();
        //gm.render();
        //gm.update();
    }

    @Override
    public void dispose() {
        background.dispose();
        player.dispose();
        gm.dispose();
        BossOne.dispose();
    }


}
