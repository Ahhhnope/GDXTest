package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

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

    private ShapeRenderer shapeRenderer;

    public Menu(){
        SoundManager.load();
        //load ảnh từ file
        background = new Texture("Stuffs/background.png");

        //load ảnh chữ ( tên game )
        title = new Texture("Stuffs/Title.png");

        //Load ảnh các nút và hitbox của nút
        btnPlay = new Texture("Stuffs/Buttons/Play.png");
        btnPlayHitbox = new Rectangle(10, 570, btnPlay.getWidth() / 2, btnPlay.getHeight() / 2);

        btnSetting = new Texture("Stuffs/Buttons/Setting.png");
        btnSettingHitbox = new Rectangle(10, 470, btnSetting.getWidth() / 2, btnSetting.getHeight() / 2);

        btnScore = new Texture("Stuffs/Buttons/Score.png");
        btnScoreHitbox = new Rectangle(10, 370, btnScore.getWidth() / 2, btnScore.getHeight() / 2);

        btnQuit = new Texture("Stuffs/Buttons/Exit.png");
        btnQuitHitbox = new Rectangle(10, 270, btnQuit.getWidth() / 2, btnQuit.getHeight() / 2);

        shapeRenderer = new ShapeRenderer();
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
                SoundManager.play("click");
                GameManager.currScreen = "game";
            }

            if (btnSettingHitbox.contains(x, y)) {
                SoundManager.play("click");
                GameManager.currScreen = "setting";
            }

            if (btnScoreHitbox.contains(x, y)) {
                SoundManager.play("click");
                GameManager.currScreen = "scoreboard";

            }

            if (btnQuitHitbox.contains(x, y)) {
                SoundManager.play("click");
                GameManager.currScreen = "quit";


            }
        }
    }

    public void renderHitbox() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        shapeRenderer.setColor(com.badlogic.gdx.graphics.Color.RED);
        shapeRenderer.rect(btnPlayHitbox.x, btnPlayHitbox.y, btnPlayHitbox.width, btnPlayHitbox.height);
        shapeRenderer.rect(btnSettingHitbox.x, btnSettingHitbox.y, btnSettingHitbox.width, btnSettingHitbox.height);
        shapeRenderer.rect(btnScoreHitbox.x, btnScoreHitbox.y, btnScoreHitbox.width, btnScoreHitbox.height);
        shapeRenderer.rect(btnQuitHitbox.x, btnQuitHitbox.y, btnQuitHitbox.width, btnQuitHitbox.height);

        shapeRenderer.end();
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        // vẽ nền background
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // vẽ chữ hiện trên nền background
        batch.draw(title, 100, Gdx.graphics.getHeight() - title.getHeight() - 20);

        // Vẽ nút PLAY
        batch.draw(btnPlay, btnPlayHitbox.x, btnPlayHitbox.y, (float) (btnPlayHitbox.getWidth()), (float) (btnPlayHitbox.getHeight()));

        // Vẽ nút SETTING
        batch.draw(btnSetting, btnSettingHitbox.x, btnSettingHitbox.y, (float) (btnSettingHitbox.getWidth()), (float) (btnSettingHitbox.getHeight()));

        // Vẽ nút SCORE
        batch.draw(btnScore, btnScoreHitbox.x, btnScoreHitbox.y, (float) (btnScoreHitbox.getWidth()), (float) (btnScoreHitbox.getHeight()));

        // Vẽ nút EXIT
        batch.draw(btnQuit, btnQuitHitbox.x, btnQuitHitbox.y, (float) (btnQuitHitbox.getWidth()), (float) (btnQuitHitbox.getHeight()));
        batch.end();


//        renderHitbox();
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
