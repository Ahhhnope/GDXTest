package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScreenPlay {
    private SpriteBatch batch;

    private Texture BackgroundScreen;

    public ScreenPlay(){

        //load ảnh từ file
        BackgroundScreen = new Texture("Stuffs/backgroundScreen.jpeg");

        // cấu hình
        batch = new SpriteBatch();
    }

    public void Draw(){
        batch.begin();
        batch.draw(BackgroundScreen, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    public void dispose(){
        BackgroundScreen.dispose();
        batch.dispose();
    }
}
