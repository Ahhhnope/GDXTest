package com.main;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;


public class PauseScreen {
    private Texture pauseScreen;
    private Rectangle btnHome;
    private Rectangle btnContinue;

    private ShapeRenderer shapeRenderer;
    public static boolean paused = false;
    public static boolean homePressed = false;

    public PauseScreen() {
//        pauseScreen = new Texture("Stuffs/Paused.png");
        pauseScreen = new Texture("Stuffs/RealPauseScreen.png");
        btnHome = new Rectangle(608, 355, 158, 50);
        btnContinue = new Rectangle(818, 355, 165, 50);

        shapeRenderer = new ShapeRenderer();
    }

    public void update() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (btnHome.contains(mouseX, mouseY)) {
                homePressed = true;
                GameManager.currScreen = "menu";
            }

            if (btnContinue.contains(mouseX, mouseY)) {
                unpause();
            }
        }
    }

    public void unpause() {
        paused = false;
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
