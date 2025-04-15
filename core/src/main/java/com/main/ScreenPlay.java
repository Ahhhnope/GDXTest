package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

public class ScreenPlay {

    private Texture BackgroundScreen;
    private Texture nenMapLevel;
    private Texture title;

    private Texture btnPlay;
    private Rectangle btnPlay1Hitbox;
    private Rectangle btnPlay2Hitbox;

    private Texture btnbackicon;
    private Rectangle btnBackHitbox;

    private ShapeRenderer shapeRenderer;


    public ScreenPlay(){
        SoundManager.load();
        //load ảnh background
        BackgroundScreen = new Texture("Stuffs/backgroundScreen.jpeg");
        //Load ảnh nền Chọn Level
        nenMapLevel = new Texture("Stuffs/nenGameMap.png");
        //load ảnh chữ ( tên game )
        title = new Texture("Stuffs/Buttons/TenDanhBoss.png");
        //Load ảnh các nút
        btnPlay = new Texture("Stuffs/Buttons/Play.png");
        System.out.println(btnPlay.getWidth() / 3 + " | " + btnPlay.getHeight() / 3);
        System.out.println((btnPlay.getWidth() / 2 + 12) + " | " + btnPlay.getHeight() / 2);
        btnPlay1Hitbox = new Rectangle(530, 328, btnPlay.getWidth() / 2 + 11, btnPlay.getHeight() / 2);
        btnPlay2Hitbox = new Rectangle(879, 328, btnPlay.getWidth() / 2 + 11, btnPlay.getHeight() / 2);

        btnbackicon = new Texture("Stuffs/Buttons/backicon.png");
        btnBackHitbox = new Rectangle(10, 420, btnbackicon.getWidth() / 2, btnbackicon.getHeight() / 2);

        shapeRenderer = new ShapeRenderer();

    }

    public void update() {
        // Xử lý click
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            //s/das
            int touchX = Gdx.input.getX();
            int touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (btnBackHitbox.contains(touchX, touchY)) {
                SoundManager.play("click");
                Main.gm.changeScreenWithFade("menu", 0.5f);
            }

            if (btnPlay1Hitbox.contains(touchX, touchY)) {
                SoundManager.play("click2");
                Main.gm.changeScreenWithFade("MapBossOne", 2f);
            }

            if (btnPlay2Hitbox.contains(touchX, touchY)) {
                SoundManager.play("click");
//                GameManager.currScreen = "MapBossTwo";
            }
        }
    }

    public void renderHitbox() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);

        shapeRenderer.rect(btnBackHitbox.x, btnBackHitbox.y, btnBackHitbox.width, btnBackHitbox.height);
        shapeRenderer.rect(btnPlay1Hitbox.x, btnPlay1Hitbox.y, btnPlay1Hitbox.width, btnPlay1Hitbox.height);
        shapeRenderer.rect(btnPlay2Hitbox.x, btnPlay2Hitbox.y, btnPlay2Hitbox.width, btnPlay2Hitbox.height);

        shapeRenderer.end();
    }

    public void render(SpriteBatch batch){
        batch.begin();
        //nền Background
        batch.draw(BackgroundScreen, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // Vẽ trên nền background
        batch.draw(nenMapLevel,
            (Gdx.graphics.getWidth() - nenMapLevel.getWidth() * 1.5f) / 2,
            (Gdx.graphics.getHeight() - nenMapLevel.getHeight() * 1.5f) / 2,
            nenMapLevel.getWidth() * 1.5f,
            nenMapLevel.getHeight() * 1.5f);

        // vẽ chữ hiện trên nền background
        batch.draw(title, 600, Gdx.graphics.getHeight() - 300, title.getWidth() * 0.5f, title.getHeight() * 0.5f);

        // Vẽ nút PLAY bên phải
        batch.draw(btnPlay, btnPlay1Hitbox.x + 35, btnPlay1Hitbox.y + 10, (float) btnPlay1Hitbox.getWidth() - 70, (float) btnPlay1Hitbox.getHeight() - 17);

        // Vẽ nút PLAY bên trái
        batch.draw(btnPlay, btnPlay2Hitbox.x + 36, btnPlay2Hitbox.y + 10, (float) btnPlay2Hitbox.getWidth() - 70, (float) btnPlay2Hitbox.getHeight() - 17);

        //vẽ nút backicon
        batch.draw(btnbackicon, btnBackHitbox.x, btnBackHitbox.y, (float) btnBackHitbox.getWidth(), (float) btnBackHitbox.getHeight());
        batch.end();

//        renderHitbox();
    }

    public void dispose(){
        BackgroundScreen.dispose();
        nenMapLevel.dispose();
        title.dispose();
        btnPlay.dispose();
        btnbackicon.dispose();
    }
}
