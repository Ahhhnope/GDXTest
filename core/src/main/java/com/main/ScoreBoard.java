package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;

public class ScoreBoard {

    private Texture BackgroundScreen;
    private Texture backgroudDiem;


    private Texture btnbackicon;
    private Rectangle btnBackHitbox;


    public ScoreBoard(){
//        Load ảnh & hitbox
        BackgroundScreen = new Texture("Stuffs/backgroundScreen.jpeg");
        backgroudDiem = new Texture("Stuffs/ScoreBoard.png");

        btnbackicon = new Texture("Stuffs/Buttons/backicon.png");
        btnBackHitbox = new Rectangle(10, 322, btnbackicon.getWidth() / 2, btnbackicon.getHeight() / 2);
    }

    public void update() {
        // Xử lý click
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            int touchX = Gdx.input.getX();
            int touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (btnBackHitbox.contains(touchX, touchY)) {
                GameManager.currScreen = "menu";
            }
        }
    }

    public void render(SpriteBatch batch){
        batch.begin();
        //nền Background
        batch.draw(BackgroundScreen, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // Vẽ trên nền background
        batch.draw(backgroudDiem,
            (Gdx.graphics.getWidth() - backgroudDiem.getWidth() * 1.5f) / 2,
            (Gdx.graphics.getHeight() - backgroudDiem.getHeight() * 1.5f) / 2,
            backgroudDiem.getWidth() * 1.5f,
            backgroudDiem.getHeight() * 1.5f);

        //vẽ nút backicon
        batch.draw(btnbackicon, 10 , 322, (float) btnBackHitbox.getWidth(), (float) btnBackHitbox.getHeight());

        batch.end();
    }

    public void dispose(){
        BackgroundScreen.dispose();
        backgroudDiem.dispose();
        btnbackicon.dispose();
    }
}
