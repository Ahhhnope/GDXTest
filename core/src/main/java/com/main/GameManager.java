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
        int btnX = 10; // Vị trí x của nút, dịch chuyển sang bên trái hơn
        int currentY = Gdx.graphics.getHeight() - 250; // Bắt đầu từ trên cao, dưới tiêu đề
        int spacing = 20; // Khoảng cách giữa các nút
        int buttonWidth = 250; // Chiều rộng nút
        int buttonHeight = 80; // Chiều cao nút

        // Vẽ nút PLAY
        batch.draw(btnPlay, btnX, currentY, buttonWidth, buttonHeight);
        currentY -= buttonHeight + spacing;

        // Vẽ nút SETTING
        batch.draw(btnSetting, btnX, currentY, buttonWidth, buttonHeight);
        currentY -= buttonHeight + spacing;

        // Vẽ nút SCORE
        batch.draw(btnScore, btnX, currentY, buttonWidth, buttonHeight);
        currentY -= buttonHeight + spacing;

        // Vẽ nút EXIT
        batch.draw(btnQuit, btnX, currentY, buttonWidth, buttonHeight);
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
