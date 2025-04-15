package com.main;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;

public class HUD {
    private float timer = 0f;
    private boolean running = false;
    private BitmapFont font;
    private boolean HudVisible = false;



    public HUD() {
        font = new BitmapFont(); // Load font mặc định, có thể thay bằng font .fnt sau
    }

    public void show(){
        HudVisible = true;
    }

    public void hide(){
        HudVisible = true;
    }

    public boolean isVisible(){
        return HudVisible;
    }

    public void start() {
        timer = 0f;
        running = true;
    }

    public void stop() {
        running = false;
    }

    public void update(float delta) {
        if (running) {
            timer += delta;
//            System.out.println("Timer: " + timer);
        }
    }

    public void render(SpriteBatch batch) {
        if (!HudVisible) return;
        batch.begin();
        font.draw(batch, String.format("Time: %.1f s", timer), 20, Gdx.graphics.getHeight() - 50);
        batch.end();
    }


    public float getTime() {
        return timer;
    }

    public boolean isRunning() {
        return running;
    }

    public void dispose() {
        font.dispose();
    }
}
