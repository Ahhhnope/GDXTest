package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
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

    public WinScreen() {
        panel = new Texture("Stuffs/WinScreen.png");
        btnExit = new Texture("Stuffs/Buttons/Exit.png");
        btnExitHitbox = new Rectangle(720, 314, 162, 42);

        shapeRenderer = new ShapeRenderer();

        panelWidth = panel.getWidth() * 1.5f;
        panelHeight = panel.getHeight() * 1.5f;
    }

    public void update() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            SoundManager.play("click");
            GameManager.currScreen = "menu";
        }
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
    }

    public void dispose() {
        panel.dispose();
        btnExit.dispose();
    }
}
