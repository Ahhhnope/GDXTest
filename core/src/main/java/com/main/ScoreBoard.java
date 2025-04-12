package com.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScoreBoard {
    private SpriteBatch batch;
    private Texture backgroudDiem;

    public ScoreBoard(){
        batch = new SpriteBatch();
    }

    public void Draw(){
        batch.begin();
        batch.end();
    }

    public void dispose(){
        batch.dispose();
    }
}
