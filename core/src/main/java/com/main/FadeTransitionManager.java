package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class FadeTransitionManager {
    public boolean isActive = false;

    private String nextScreen;
    private float duration;
    private float timer = 0f;
    private boolean isFading = false;
    private boolean fadingOut = true;
    private float defaultTransitionTime = 0.5f;
    private ShapeRenderer shapeRenderer;
    private GameManager gameManager;

    public FadeTransitionManager(GameManager gameManager) {
        this.gameManager = gameManager;
        this.shapeRenderer = new ShapeRenderer();
    }

    public void start(String nextScreen, float duration) {
        this.nextScreen = nextScreen;
        this.duration = duration / 2f; // mỗi giai đoạn: fadeOut, fadeIn
        this.timer = 0f;
        this.isFading = true;
        this.fadingOut = true;
        this.isActive = true;
    }

    public void update(float deltaTime) {
        if (!isFading) return;

        timer += deltaTime;
        if (timer >= duration) {
            timer = 0f;
            if (fadingOut) {
                GameManager.currScreen = nextScreen; // 🌙 đổi màn hình ở giữa
                fadingOut = false;
            } else {
                isFading = false;
                isActive = false;
            }
        }
    }

    public void render(SpriteBatch batch) {
        if (!isFading) return;

        float alpha = timer / duration;
        if (!fadingOut) alpha = 1f - alpha;

        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, alpha);
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
