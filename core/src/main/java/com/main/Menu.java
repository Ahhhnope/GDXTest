package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import javax.swing.*;
import java.awt.*;

public class Menu {
    private SpriteBatch batch;

    private Texture background;
    private Texture title;

    private Texture btnPlay;
    private Texture btnSetting;
    private Texture btnScore;
    private Texture btnQuit;

    public String clickedButton = "";

    public Menu(){

        //load ảnh từ file
        background = new Texture("Stuffs/background.png");

        //load ảnh chữ ( tên game )
        title = new Texture("Stuffs/Title.png");

        //Load ảnh các nút
        btnPlay = new Texture("Stuffs/Buttons/Play.png");
        btnSetting = new Texture("Stuffs/Buttons/Setting.png");
        btnScore = new Texture("Stuffs/Buttons/Score.png");
        btnQuit = new Texture("Stuffs/Buttons/Exit.png");

        // cấu hình
        batch = new SpriteBatch();
    }

    public void draw() {
        batch.begin();
        // vẽ nền background
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // vẽ chữ hiện trên nền background
        batch.draw(title, 150, Gdx.graphics.getHeight() - title.getHeight() - 20);

        // vị trí ban đầu để vẽ các nút
        int btnX = 10; // Vị trí x của nút, dịch chuyển sang bên trái hơn
        int currentY = Gdx.graphics.getHeight() - 250; // Bắt đầu từ trên cao, dưới tiêu đề
        int spacing = 20; // Khoảng cách giữa các nút

        // Vẽ nút PLAY
        int playY = currentY; //lưu vị trí
        batch.draw(btnPlay, btnX, currentY, btnPlay.getWidth() / 2, btnPlay.getHeight() / 2);
        currentY -= 80 + spacing;

        // Vẽ nút SETTING
        batch.draw(btnSetting, btnX, currentY, btnSetting.getWidth() / 2, btnSetting.getHeight() / 2);
        currentY -= 80 + spacing;

        // Vẽ nút SCORE
        batch.draw(btnScore, btnX, currentY, btnScore.getWidth() / 2, btnScore.getHeight() / 2);
        currentY -= 80 + spacing;

        // Vẽ nút EXIT
        int quitY = currentY; // lưu vị trí
        batch.draw(btnQuit, btnX, currentY, btnQuit.getWidth() / 2, btnQuit.getHeight() / 2);
        batch.end();

        // Xử lý click
        if (Gdx.input.justTouched()) {
            int touchX = Gdx.input.getX();
            int touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            int width = btnPlay.getWidth() / 2;
            int height = btnPlay.getHeight() / 2;

            if (touchX >= btnX && touchX <= btnX + width) {
                if (touchY >= playY && touchY <= playY + height) {
                    clickedButton = "play";
                } else if (touchY >= quitY && touchY <= quitY + height) {
                    clickedButton = "exit";
                }
            }
        }
    }

    public void dispose() {
        background.dispose();
        title.dispose();
        btnPlay.dispose();
        btnSetting.dispose();
        btnScore.dispose();
        btnQuit.dispose();
        batch.dispose();
    }
}
