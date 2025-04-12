package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;

public class ScreenPlay {

    private Texture BackgroundScreen;
    private Texture nenMapLevel;
    private Texture title;

    private Texture btnPlay;
    private Rectangle btnPlay1Hitbox;
    private Rectangle btnPlay2Hitbox;

    private Texture btnbackicon;
    private Rectangle btnBackHitbox;


    public ScreenPlay(){

        //load ảnh background
        BackgroundScreen = new Texture("Stuffs/backgroundScreen.jpeg");
        //Load ảnh nền Chọn Level
        nenMapLevel = new Texture("Stuffs/nenGameMap.png");
        //load ảnh chữ ( tên game )
        title = new Texture("Stuffs/Buttons/TenDanhBoss.png");
        //Load ảnh các nút
        btnPlay = new Texture("Stuffs/Buttons/Play.png");
        btnPlay1Hitbox = new Rectangle(440, 225, btnPlay.getWidth() / 3, btnPlay.getHeight() / 3);
        btnPlay2Hitbox = new Rectangle(775, 225, btnPlay.getWidth() / 3, btnPlay.getHeight() / 3);

        btnbackicon = new Texture("Stuffs/Buttons/backicon.png");
        btnBackHitbox = new Rectangle(10, 292, btnbackicon.getWidth() / 2, btnbackicon.getHeight() / 2);
    }

    public void update() {
        // Xử lý click
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            int touchX = Gdx.input.getX();
            int touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (btnBackHitbox.contains(touchX, touchY)) {
                GameManager.currScreen = "menu";
            }

            if (btnPlay1Hitbox.contains(touchX, touchY)) {
                GameManager.currScreen = "MapBossOne";
            }

            if (btnPlay2Hitbox.contains(touchX, touchY)) {
                GameManager.currScreen = "MapBossTwo";
            }
        }
    }

    public void render(SpriteBatch batch){
        //nền Background
        batch.draw(BackgroundScreen, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // Vẽ trên nền background
        batch.draw(nenMapLevel,
            (Gdx.graphics.getWidth() - nenMapLevel.getWidth() * 1.5f) / 2,
            (Gdx.graphics.getHeight() - nenMapLevel.getHeight() * 1.5f) / 2,
            nenMapLevel.getWidth() * 1.5f,
            nenMapLevel.getHeight() * 1.5f);

        // vẽ chữ hiện trên nền background
        batch.draw(title, 500, Gdx.graphics.getHeight() - 200, title.getWidth() * 0.5f, title.getHeight() * 0.5f);

        // Vẽ nút PLAY bên phải
        batch.draw(btnPlay, 440, 220, (float) btnPlay1Hitbox.getWidth(), (float) btnPlay1Hitbox.getHeight());

        // Vẽ nút PLAY bên trái
        batch.draw(btnPlay, 780, 220, (float) btnPlay2Hitbox.getWidth(), (float) btnPlay2Hitbox.getHeight());

        //vẽ nút backicon
        batch.draw(btnbackicon, 10, 292, (float) btnBackHitbox.getWidth(), (float) btnBackHitbox.getHeight());

    }

    public void dispose(){
        BackgroundScreen.dispose();
        nenMapLevel.dispose();
        title.dispose();
        btnPlay.dispose();
        btnbackicon.dispose();
    }
}
