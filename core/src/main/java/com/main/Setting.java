package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Setting {

    private Texture BackgroundScreen;

    public Setting(){
        //load ảnh background
        BackgroundScreen = new Texture("Stuffs/backgroundScreen.jpeg");

    }

    public void update() {

    }

    public void render(SpriteBatch batch){
        //nền Background
        batch.draw(BackgroundScreen, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void Dispose(){
        BackgroundScreen.dispose();
    }
}
