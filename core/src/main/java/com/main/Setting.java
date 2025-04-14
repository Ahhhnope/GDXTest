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

    //Âm lượng
    private Texture iconAmLuong;
    private Rectangle btnAmluong1;
    private Rectangle btnAmluong2;

    private Texture iconAmLuongTat;
    private Rectangle btnAmluongTat1;
    private Rectangle btnAmluongTat2;


    private Texture btnbackicon;
    private Rectangle btnBackHitbox;

    // Biến đếm số lượng ô đen đã hiện
    private int musicVolumeLevel = 0;
    private int sfxVolumeLevel = 0;

    // Biến trạng thái âm lượng
    private boolean isMusicMuted = false;
    private boolean isSfxMuted = false;

    public Setting(){
        //load ảnh background
        BackgroundScreen = new Texture("Stuffs/backgroundScreen.jpeg");

        //load ảnh nền màn hình
        NenManHinh = new Texture("Stuffs/anhnenSetting.png");

        //Load ảnh âm lượng
        iconAmLuong = new Texture("Stuffs/Buttons/BatAmThanh.png");
        btnAmluong1 = new Rectangle(440, 225, iconAmLuong.getWidth() / 4, iconAmLuong.getHeight() / 4);
        btnAmluong2 = new Rectangle(775, 225, iconAmLuong.getWidth() / 4, iconAmLuong.getHeight() / 4);

        iconAmLuongTat = new Texture("Stuffs/Buttons/TatAmThanh.png");
        btnAmluongTat1 = new Rectangle(440, 225, iconAmLuongTat.getWidth() / 4, iconAmLuongTat.getHeight() / 4);
        btnAmluongTat2 = new Rectangle(775, 225, iconAmLuongTat.getWidth() / 4, iconAmLuongTat.getHeight() / 4);

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

            // Xử lý nút tăng âm lượng nhạc
            if (btnTang1Hitbox.contains(touchX, touchY) && !isMusicMuted) {
                if (musicVolumeLevel < 10) {
                    musicVolumeLevel++;
                }
            }

            // Xử lý nút giảm âm lượng nhạc
            if (btnGiam1Hitbox.contains(touchX, touchY) && !isMusicMuted) {
                if (musicVolumeLevel > 0) {
                    musicVolumeLevel--;
                }
            }

            // Xử lý nút tăng âm lượng hiệu ứng
            if (btnTang2Hitbox.contains(touchX, touchY) && !isSfxMuted) {
                if (sfxVolumeLevel < 10) {
                    sfxVolumeLevel++;
                }
            }

            // Xử lý nút giảm âm lượng hiệu ứng
            if (btnGiam2Hitbox.contains(touchX, touchY) && !isSfxMuted) {
                if (sfxVolumeLevel > 0) {
                    sfxVolumeLevel--;
                }
            }

            // Xử lý nút tắt/bật âm lượng nhạc
            if (btnAmluong1.contains(touchX, touchY) || btnAmluongTat1.contains(touchX, touchY)) {
                isMusicMuted = !isMusicMuted;
            }

            // Xử lý nút tắt/bật âm lượng hiệu ứng
            if (btnAmluong2.contains(touchX, touchY) || btnAmluongTat2.contains(touchX, touchY)) {
                isSfxMuted = !isSfxMuted;
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
        batch.draw(btnGiam, 428, 385, (float) btnGiam1Hitbox.getWidth(), (float) btnGiam1Hitbox.getHeight());

        // Vẽ nút Tăng trên bên trái trên
        batch.draw(btnTang, 956, 365, (float) btnTang1Hitbox.getWidth(), (float) btnTang1Hitbox.getHeight());

        // Vẽ nút Giảm trên bên bên phải dưới
        batch.draw(btnGiam, 428, 260, (float) btnGiam2Hitbox.getWidth(), (float) btnGiam2Hitbox.getHeight());

        // Vẽ nút Tăng trên bên trái dưới
        batch.draw(btnTang, 956, 243, (float) btnTang2Hitbox.getWidth(), (float) btnTang2Hitbox.getHeight());

        //vẽ nút backicon
        batch.draw(btnbackicon, 10, 292, (float) btnBackHitbox.getWidth(), (float) btnBackHitbox.getHeight());

        //Vẽ Ảnh Bật Âm thanh - trên
        batch.draw(iconAmLuong, 328, 360, (float) btnAmluong1.getWidth(), (float) btnAmluong1.getHeight());

        //Vẽ Ảnh Bật Âm thanh - dưới
        batch.draw(iconAmLuong, 330, 238, (float) btnAmluong2.getWidth(), (float) btnAmluong2.getHeight());

        // Vẽ biểu tượng âm lượng cho nhạc dựa vào trạng thái
        if (isMusicMuted) {
            batch.draw(iconAmLuongTat, 328, 360, (float) btnAmluongTat1.getWidth(), (float) btnAmluongTat1.getHeight());
        } else {
            batch.draw(iconAmLuong, 328, 360, (float) btnAmluong1.getWidth(), (float) btnAmluong1.getHeight());

            // Vẽ các ô âm lượng nhạc
            for (int i = 0; i < musicVolumeLevel; i++) {
                // Vẽ ô màu đen theo thứ tự từ trái sang phải
                // Bắt đầu từ vị trí đầu tiên sau icon và di chuyển sang phải
                batch.draw(new Texture("pixel.png"), 430 + i * 30, 370, 20, 20);
            }
        }

        // Vẽ biểu tượng âm lượng cho hiệu ứng dựa vào trạng thái
        if (isSfxMuted) {
            batch.draw(iconAmLuongTat, 330, 238, (float) btnAmluongTat2.getWidth(), (float) btnAmluongTat2.getHeight());
        } else {
            batch.draw(iconAmLuong, 330, 238, (float) btnAmluong2.getWidth(), (float) btnAmluong2.getHeight());

            // Vẽ các ô âm lượng hiệu ứng
            for (int i = 0; i < sfxVolumeLevel; i++) {
                // Vẽ ô màu đen theo thứ tự từ trái sang phải
                batch.draw(new Texture("pixel.png"), 430 + i * 30, 248, 20, 20);
            }
        }

        batch.end();
    }

    public void Dispose(){
        BackgroundScreen.dispose();
        NenManHinh.dispose();
        btnTang.dispose();
        btnGiam.dispose();
        iconAmLuong.dispose();
        iconAmLuongTat.dispose();
        btnbackicon.dispose();
    }
}
