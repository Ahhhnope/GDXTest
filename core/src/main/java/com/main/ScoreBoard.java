package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.main.Service.Services;

import java.awt.*;
import java.util.ArrayList;

public class ScoreBoard {

    private Texture BackgroundScreen;
    private Texture backgroudDiem;


    private Texture btnbackicon;
    private Rectangle btnBackHitbox;

    private ShapeRenderer shapeRenderer;
    private BitmapFont font;

    private Services services;
    private ArrayList<MatchModel> matchList;

    public ScoreBoard(){
        //uisound
        SoundManager.load();
//        Load ảnh & hitbox
        BackgroundScreen = new Texture("Stuffs/backgroundScreen.jpeg");
        backgroudDiem = new Texture("Stuffs/ScoreBoard.png");

        btnbackicon = new Texture("Stuffs/Buttons/backicon.png");
        btnBackHitbox = new Rectangle(10, 395, btnbackicon.getWidth() / 2, btnbackicon.getHeight() / 2);

        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        services = new Services();
        matchList = services.getAllHighScore();
    }

    public void update() {
        matchList = new ArrayList<>();
        matchList = services.getAllHighScore();

        // Xử lý click
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {

            int touchX = Gdx.input.getX();
            int touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (btnBackHitbox.contains(touchX, touchY)) {
                SoundManager.play("click");
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

    public void renderList(SpriteBatch batch) {
        batch.begin();

        int rows = matchList.size();
        if (rows > 8) {rows = 8;} // Maximum row for the score board (I ain't doing multiple pages ;-;)

        for (int i = 0; i < rows; i++) {
            MatchModel currMatch = matchList.get(i);
            font.getData().setScale(2f);
            font.draw(batch, currMatch.getScore()+"", 440, 550 - (i * 50));
            font.draw(batch, currMatch.getTime(), 745, 550 - (i * 50));
            font.draw(batch, currMatch.getDate(), 1024, 550 - (i * 50));
        }
        batch.end();
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
        renderList(batch);
    }

    public void dispose(){
        BackgroundScreen.dispose();
        backgroudDiem.dispose();
        btnbackicon.dispose();
    }
}
