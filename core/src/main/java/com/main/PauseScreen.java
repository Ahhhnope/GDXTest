package com.main;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;


public class PauseScreen {
    private Texture pauseScreen;
    private Rectangle btnHome;
    private Rectangle btnContinue;

    private ShapeRenderer shapeRenderer;

    public PauseScreen() {
//        pauseScreen = new Texture("Stuffs/Paused.png");
        pauseScreen = new Texture("Stuffs/RealPauseScreen.png");
        btnHome = new Rectangle(700, 420, 100, 30);
        btnContinue = new Rectangle(790, 420, 100, 30);

        shapeRenderer = new ShapeRenderer();
    }

    public void update() {

    }

    public void renderHitbox() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(btnHome.x, btnHome.y, btnHome.width, btnHome.height);
        shapeRenderer.rect(btnContinue.x, btnContinue.y, btnContinue.width, btnContinue.height);
        shapeRenderer.end();
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(pauseScreen, Gdx.graphics.getWidth() / 2f - pauseScreen.getWidth() / 2f - 150, Gdx.graphics.getHeight() / 2f - pauseScreen.getHeight() / 2f - 150, pauseScreen.getWidth() + 300, pauseScreen.getHeight() + 200);
        batch.end();

        renderHitbox();
    }

    public void dispose() {
        pauseScreen.dispose();
    }
}
