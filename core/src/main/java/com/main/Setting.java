package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Setting {

    private Texture BackgroundScreen;
    private Texture NenManHinh;

    public Setting(){
        //load ảnh background
        BackgroundScreen = new Texture("Stuffs/backgroundScreen.jpeg");
        //load ảnh nền màn hình
        NenManHinh = new Texture("Stuffs/anhnenSetting.png");

    }

    public void update() {

    }

    public void render(SpriteBatch batch){
        //nền Background
        batch.draw(BackgroundScreen, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // hiện ảnh trên nền background
        batch.draw(NenManHinh,
            (Gdx.graphics.getWidth() - NenManHinh.getWidth() * 1.5f) / 2,
            (Gdx.graphics.getHeight() - NenManHinh.getHeight() * 1.5f) / 2,
            NenManHinh.getWidth() * 1.5f,
            NenManHinh.getHeight() * 1.5f);
        
    }

    public void Dispose(){
        BackgroundScreen.dispose();
        NenManHinh.dispose();
    }
}
