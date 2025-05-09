package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

public class LoseScreen {

    private Texture panel;

    private Texture btnBack;
    private Rectangle btnBackHitbox;

    private Texture btnReplay;
    private Rectangle btnReplayHitbox;

    private ShapeRenderer shapeRenderer;

    private float panelWidth;
    private float panelHeight;

    private float time;
    private float score;
    private BitmapFont font;

    public static boolean reset;

    public LoseScreen() {
        panel = new Texture("Stuffs/LoseScreenBeLike.png");
//        panel = new Texture("Stuffs/paused.png");

        btnBack = new Texture("Stuffs/Buttons/BackButton.png");
        btnBackHitbox = new Rectangle(540, 280, 362, 142);

        btnReplay = new Texture("Stuffs/Buttons/ReplayButton.png");
        btnReplayHitbox = new Rectangle(830, 276, 368, 146);

        reset = false;

        shapeRenderer = new ShapeRenderer();

        panelWidth = panel.getWidth() * 1.5f;
        panelHeight = panel.getHeight() * 1.5f;
        font = new BitmapFont();
    }

    public void update() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            if (btnBackHitbox.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                SoundManager.play("click");
                GameManager.currScreen = "menu";
                MusicManager.playMenuMusic();
            }
            if (btnReplayHitbox.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                SoundManager.play("click");
                reset = true;
            }
        }
    }

    public void renderHitbox() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(btnBackHitbox.x, btnBackHitbox.y, btnBackHitbox.width, btnBackHitbox.height);
        shapeRenderer.rect(btnReplayHitbox.x, btnReplayHitbox.y, btnReplayHitbox.width, btnReplayHitbox.height);
        shapeRenderer.end();
    }

    public void insertTimeAndStuff(float time, float score) {
        this.time = time;
        this.score = score;
    }

    public void renderTimeAndStuff(SpriteBatch batch) {
        int totalSeconds = (int) time;
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
        String timeBeLike = String.format("%02d:%02d:%02d", hours, minutes, seconds);

        batch.begin();

        font.getData().setScale(2.5f);
        font.draw(batch, timeBeLike, 670, 515);
        font.draw(batch, score+"", 670, 435);

        batch.end();
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(panel, Gdx.graphics.getWidth() / 2f - panelWidth / 4f, Gdx.graphics.getHeight() / 2f - panelHeight / 4f, panelWidth / 2f, panelHeight / 2f);
        batch.draw(btnBack, btnBackHitbox.x, btnBackHitbox.y, btnBackHitbox.width / 1.5f, btnBackHitbox.height / 1.5f);
        batch.draw(btnReplay, btnReplayHitbox.x, btnReplayHitbox.y, btnReplayHitbox.width / 1.5f, btnReplayHitbox.height / 1.5f);
        batch.end();

        renderTimeAndStuff(batch);
    }

    public void dispose() {
        panel.dispose();
        btnBack.dispose();
    }
}
