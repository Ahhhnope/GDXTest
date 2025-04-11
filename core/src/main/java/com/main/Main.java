package com.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import org.w3c.dom.Text;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    Texture playerTexture;
    SpriteBatch batch;

    @Override
    public void create() {
        playerTexture = new Texture("Spaceships/spaceships.png");
        batch = new SpriteBatch();
    }

    @Override
    public void render() {
        batch.begin();
        batch.draw(playerTexture, 100, 100, 64, 64);
        batch.end();
    }

    @Override
    public void dispose() {
        playerTexture.dispose();
    }
}
