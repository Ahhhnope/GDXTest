package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import javax.swing.*;
import java.awt.*;

public class Menu {
    private Texture background;
    private Texture title;

    private Texture btnPlay;
    private Rectangle btnPlayHitbox;

    private Texture btnSetting;
    private Rectangle btnSettingHitbox;

    private Texture btnScore;
    private Rectangle btnScoreHitbox;

    private Texture btnQuit;
    private Rectangle btnQuitHitbox;


    public Menu(){

        //load ảnh từ file
        background = new Texture("Stuffs/background.png");

        //load ảnh chữ ( tên game )
        title = new Texture("Stuffs/Title.png");

        //Load ảnh các nút và hitbox của nút
        btnPlay = new Texture("Stuffs/Buttons/Play.png");
        btnPlayHitbox = new Rectangle(10, 422, btnPlay.getWidth() / 2, btnPlay.getHeight() / 2);

        btnSetting = new Texture("Stuffs/Buttons/Setting.png");
        btnSettingHitbox = new Rectangle(10, 322, btnSetting.getWidth() / 2, btnSetting.getHeight() / 2);

        btnScore = new Texture("Stuffs/Buttons/Score.png");
        btnScoreHitbox = new Rectangle(10, 222, btnScore.getWidth() / 2, btnScore.getHeight() / 2);

        btnQuit = new Texture("Stuffs/Buttons/Exit.png");
        btnQuitHitbox = new Rectangle(10, 122, btnQuit.getWidth() / 2, btnQuit.getHeight() / 2);

    }

    public void update() {
//      Test lệnh vào chế dộ chơi game bằng nút Enter
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            GameManager.currScreen = "testing";
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();
//            Đảo ngược y (Vì một lí do nào đó "Gdx.input.getY()" trả về tọa độ y tính từ trên xuống =w=)

            if (btnPlayHitbox.contains(x, y)) {
                GameManager.currScreen = "game";
            }

            if (btnSettingHitbox.contains(x, y)) {
                GameManager.currScreen = "setting";
            }

            if (btnScoreHitbox.contains(x, y)) {
                GameManager.currScreen = "scoreboard";
            }

            if (btnQuitHitbox.contains(x, y)) {
                GameManager.currScreen = "quit";
            }
        }
    }


    public void render(SpriteBatch batch) {
        // vẽ nền background
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // vẽ chữ hiện trên nền background
        batch.draw(title, 150, Gdx.graphics.getHeight() - title.getHeight() - 20);

        // vị trí ban đầu để vẽ các nút
        int btnX = 10; // Vị trí x của nút, dịch chuyển sang bên trái hơn
        int currentY = Gdx.graphics.getHeight() - 250; // Bắt đầu từ trên cao, dưới tiêu đề
        int spacing = 100; // Khoảng cách giữa các nút

        // Vẽ nút PLAY
        batch.draw(btnPlay, btnX, currentY, (float) (btnPlayHitbox.getWidth()), (float) (btnPlayHitbox.getHeight()));
        currentY -= spacing;

        // Vẽ nút SETTING
        batch.draw(btnSetting, btnX, currentY, (float) (btnSettingHitbox.getWidth()), (float) (btnSettingHitbox.getHeight()));
        currentY -= spacing;

        // Vẽ nút SCORE
        batch.draw(btnScore, btnX, currentY, (float) (btnScoreHitbox.getWidth()), (float) (btnScoreHitbox.getHeight()));
        currentY -= spacing;

        // Vẽ nút EXIT
        batch.draw(btnQuit, btnX, currentY, (float) (btnQuitHitbox.getWidth()), (float) (btnQuitHitbox.getHeight()));
    }

    public void dispose() {
        background.dispose();
        title.dispose();
        btnPlay.dispose();
        btnSetting.dispose();
        btnScore.dispose();
        btnQuit.dispose();
    }
}
