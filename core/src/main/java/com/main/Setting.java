package com.main;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Setting {
    private SpriteBatch batch;

    public Setting(){
        // cấu hình
        batch = new SpriteBatch();
    }

    public void Draw(){
        batch.begin();
        batch.end();
    }

    public void Dispose(){
        batch.dispose();
    }
}
