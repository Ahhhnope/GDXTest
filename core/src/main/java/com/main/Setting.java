package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;

public class Setting {

    private Texture BackgroundScreen;
    private Texture NenManHinh;

    private Texture btnTang;
    private Rectangle btnTang1Hitbox;
    private Rectangle btnTang2Hitbox;

    private Texture btnGiam;
    private Rectangle btnGiam1Hitbox;
    private Rectangle btnGiam2Hitbox;

    private Texture TatAmLuong;

    private Texture btnbackicon;
    private Rectangle btnBackHitbox;

    public Setting(){
        //load ảnh background
        BackgroundScreen = new Texture("Stuffs/backgroundScreen.jpeg");
        //load ảnh nền màn hình
        NenManHinh = new Texture("Stuffs/anhnenSetting.png");
        //Load ảnh tắt âm thanh
        TatAmLuong = new Texture("Stuffs/Buttons/TatAmThanh.png");
        //Load ảnh các nút
        btnTang = new Texture("Stuffs/Buttons/thanhTang.png");
        btnTang1Hitbox = new Rectangle(440, 225, btnTang.getWidth() / 9, btnTang.getHeight() / 9);
        btnTang2Hitbox = new Rectangle(775, 225, btnTang.getWidth() / 9, btnTang.getHeight() / 9);

        btnGiam = new Texture("Stuffs/Buttons/thanhGiam.png");
        btnGiam1Hitbox = new Rectangle(440, 225, btnGiam.getWidth() / 9, btnGiam.getHeight() / 9);
        btnGiam2Hitbox = new Rectangle(775, 225, btnGiam.getWidth() / 9, btnGiam.getHeight() / 9);

        btnbackicon = new Texture("Stuffs/Buttons/backicon.png");
        btnBackHitbox = new Rectangle(10, 292, btnbackicon.getWidth() / 2, btnbackicon.getHeight() / 2);
    }

    public void update() {
        // Xử lý click
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            int touchX = Gdx.input.getX();
            int touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (btnBackHitbox.contains(touchX, touchY)) {
                GameManager.currScreen = "menu";
            }


        }
    }

    public void render(SpriteBatch batch){
        batch.begin();

        batch.draw(BackgroundScreen, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // hiện ảnh trên nền background
        batch.draw(NenManHinh,
            (Gdx.graphics.getWidth() - NenManHinh.getWidth() * 1.5f) / 2,
            (Gdx.graphics.getHeight() - NenManHinh.getHeight() * 1.5f) / 2,
            NenManHinh.getWidth() * 1.5f,
            NenManHinh.getHeight() * 1.5f);
        // Vẽ nút Giảm trên bên phải trên
        batch.draw(btnGiam, 440, 395, (float) btnGiam1Hitbox.getWidth(), (float) btnGiam1Hitbox.getHeight());

        // Vẽ nút Tăng trên bên trái trên
        batch.draw(btnTang, 966, 380, (float) btnTang1Hitbox.getWidth(), (float) btnTang1Hitbox.getHeight());

        // Vẽ nút Giảm trên bên bên phải dưới
        batch.draw(btnGiam, 442, 275, (float) btnGiam2Hitbox.getWidth(), (float) btnGiam2Hitbox.getHeight());

        // Vẽ nút Tăng trên bên trái dưới
        batch.draw(btnTang, 969, 260, (float) btnTang2Hitbox.getWidth(), (float) btnTang2Hitbox.getHeight());

        //vẽ nút backicon
        batch.draw(btnbackicon, 10, 292, (float) btnBackHitbox.getWidth(), (float) btnBackHitbox.getHeight());
        batch.end();
    }

    public void Dispose(){
        BackgroundScreen.dispose();
        NenManHinh.dispose();
        btnTang.dispose();
    }
}
