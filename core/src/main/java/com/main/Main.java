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
import com.main.Inputs.InputHandler;

import javax.swing.*;
import com.badlogic.gdx.audio.Music;
/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    Texture background;
    SpriteBatch batch;
    int screenWidth;
    int screenHeight;
    float deltatime;

    GameManager gm;
    InputHandler ih;

    private Music backgroundMusic;

    @Override
    public void create() {
        background = new Texture("Stuffs/background.png");
        batch = new SpriteBatch();
        gm = new GameManager("menu");

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        ih = new InputHandler();
        Gdx.input.setInputProcessor(ih);

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Stuffs/Musics/Hokma battle.mp3"));
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();


    }

    @Override
    public void render() {
        deltatime = Gdx.graphics.getDeltaTime();


        Gdx.gl.glClearColor(0, 0, 0, 1);  // Đặt màu nền (đen)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gm.update(deltatime);
        gm.render(batch);


    }

    @Override
    public void dispose() {
        background.dispose();
    }


}
