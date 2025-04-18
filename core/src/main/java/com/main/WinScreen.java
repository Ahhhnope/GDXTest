package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

public class WinScreen {

    private Texture panel;
    private Texture btnExit;
    private Rectangle btnExitHitbox;
    private ShapeRenderer shapeRenderer;

    private float panelWidth;
    private float panelHeight;

    private float time;
    private float score;
    private String date;
    private BitmapFont font;

    public WinScreen() {
        panel = new Texture("Stuffs/WinScreen.png");
        btnExit = new Texture("Stuffs/Buttons/Exit.png");
        btnExitHitbox = new Rectangle(720, 314, 162, 42);

        shapeRenderer = new ShapeRenderer();

        panelWidth = panel.getWidth() * 1.5f;
        panelHeight = panel.getHeight() * 1.5f;
        font = new BitmapFont();
    }

    public void update() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (btnExitHitbox.contains(mouseX, mouseY)) {
                SoundManager.play("click");
                MusicManager.playMenuMusic();
                GameManager.currScreen = "menu";
            }
        }
    }

    public void insertTimeAndStuff(float time, float score, String date) {
        this.time = time;
        this.score = score;
        this.date = date;
    }

    public void renderTimeAndStuff(SpriteBatch batch) {
        int totalSeconds = (int) time;
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
        String timeBeLike = String.format("%02d:%02d:%02d", hours, minutes, seconds);

        batch.begin();

        font.getData().setScale(2f);
        font.draw(batch, score+"", 670, 515);
        font.draw(batch, timeBeLike, 670, 465);
        font.draw(batch, date, 670, 415);

        batch.end();
    }

    public void renderHitbox() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(btnExitHitbox.x, btnExitHitbox.y, btnExitHitbox.width, btnExitHitbox.height);
        shapeRenderer.end();
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(panel, Gdx.graphics.getWidth() / 2f - panelWidth / 2f, Gdx.graphics.getHeight() / 2f - panelHeight / 2f, panelWidth, panelHeight);
        batch.draw(btnExit, btnExitHitbox.x + 28, btnExitHitbox.y + 5, btnExitHitbox.width / 1.5f, btnExitHitbox.height / 1.5f);
        batch.end();

        renderTimeAndStuff(batch);
    }

    public void dispose() {
        panel.dispose();
        btnExit.dispose();
    }
}
