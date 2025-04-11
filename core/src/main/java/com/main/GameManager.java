package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import javax.swing.*;
import java.awt.*;

public class GameManager implements Screen {
    private SpriteBatch batch;

    private Texture background;
    private Texture title;

    private Texture btnPlay;
    private Texture btnMenu;
    private Texture btnSetting;
    private Texture btnScore;
    private Texture btnQuit;

    public GameManager(){

        //load ảnh từ file
        background = new Texture("Stuffs/background.png");

        //load ảnh chữ ( tên game )
        title = new Texture("Stuffs/Title.png");

        //Load ảnh các nút
        btnPlay = new Texture("Stuffs/Buttons/PlayButton1.png");
        btnMenu = new Texture("Stuffs/Buttons/Menu1.png");
        btnSetting = new Texture("Stuffs/Buttons/setting1.png");
        btnScore = new Texture("Stuffs/Buttons/SB2.png");
        btnQuit = new Texture("Stuffs/Buttons/Exit.png");

        // cấu hình
        batch = new SpriteBatch();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        batch.begin();
        // vẽ nền background
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // vẽ chữ hiện trên nền background
        batch.draw(title, 150, Gdx.graphics.getHeight() - title.getHeight() - 20);

        // vị trí ban đầu để vẽ các nút
        int btnX = 0;
        int btnYStart = Gdx.graphics.getHeight() - title.getHeight() - 100;
        int spacing = 20;


        int currentY = btnYStart;
        batch.draw(btnPlay, btnX, currentY - btnPlay.getHeight());
        currentY -= btnPlay.getHeight() + spacing;

        batch.draw(btnMenu, btnX, currentY - btnMenu.getHeight());
        currentY -= btnMenu.getHeight() + spacing;

        batch.draw(btnSetting, btnX, currentY - btnSetting.getHeight());
        currentY -= btnSetting.getHeight() + spacing;

        batch.draw(btnScore, btnX, currentY - btnScore.getHeight());
        currentY -= btnScore.getHeight() + spacing;

        batch.draw(btnQuit, btnX, currentY - btnQuit.getHeight());
        batch.end();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        background.dispose();
        title.dispose();
        btnPlay.dispose();
        btnMenu.dispose();
        btnSetting.dispose();
        btnScore.dispose();
        btnQuit.dispose();
        batch.dispose();
    }
}
