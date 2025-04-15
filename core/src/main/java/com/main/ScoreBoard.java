package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

public class ScoreBoard {

    private Texture BackgroundScreen;
    private Texture backgroudDiem;


    private Texture btnbackicon;
    private Rectangle btnBackHitbox;

    private ShapeRenderer shapeRenderer;

    public ScoreBoard(){
        //uisound
        SoundManager.load();
//        Load ảnh & hitbox
        BackgroundScreen = new Texture("Stuffs/backgroundScreen.jpeg");
        backgroudDiem = new Texture("Stuffs/ScoreBoard.png");

        btnbackicon = new Texture("Stuffs/Buttons/backicon.png");
        btnBackHitbox = new Rectangle(10, 395, btnbackicon.getWidth() / 2, btnbackicon.getHeight() / 2);

        shapeRenderer = new ShapeRenderer();
    }

    public void update() {
        // Xử lý click
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            SoundManager.play("click");

            int touchX = Gdx.input.getX();
            int touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (btnBackHitbox.contains(touchX, touchY)) {
                Main.gm.changeScreenWithFade("menu", 0.5f);
            }
        }
    }

    public void renderHitbox() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);

        shapeRenderer.rect(btnBackHitbox.x, btnBackHitbox.y, btnBackHitbox.width, btnBackHitbox.height);

        shapeRenderer.end();
    }

    public void render(SpriteBatch batch){
        batch.begin();
        //nền Background
        batch.draw(BackgroundScreen, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // Vẽ trên nền background
        batch.draw(backgroudDiem,
            (Gdx.graphics.getWidth() - backgroudDiem.getWidth() / 1.5f) / 2,
            (Gdx.graphics.getHeight() - backgroudDiem.getHeight() / 1.5f) / 2,
            backgroudDiem.getWidth() / 1.5f,
            backgroudDiem.getHeight() / 1.5f
        );

        //vẽ nút backicon
        batch.draw(btnbackicon, btnBackHitbox.x , btnBackHitbox.y, (float) btnBackHitbox.getWidth(), (float) btnBackHitbox.getHeight());
        batch.end();

//        renderHitbox();
    }

    public void dispose(){
        BackgroundScreen.dispose();
        backgroudDiem.dispose();
        btnbackicon.dispose();
    }
}
