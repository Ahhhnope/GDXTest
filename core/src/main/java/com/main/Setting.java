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

    private Texture AnhMauDen;

    private Texture btnbackicon;
    private Rectangle btnBackHitbox;

    //Theo dõi trạng thái âm lượng
    private int musicVolume = 1; // Giá trị mặc định, từ 0-9
    private int sfxVolume = 1; // Giá trị mặc định, từ 0-9
    private boolean isMusicMuted = false;
    private boolean isSfxMuted = false;

    public Setting(){
        //load ảnh background
        BackgroundScreen = new Texture("Stuffs/backgroundScreen.jpeg");

        //load ảnh nền màn hình
        NenManHinh = new Texture("Stuffs/anhnenSetting.png");

        //Load ảnh âm lượng
        iconAmLuong = new Texture("Stuffs/Buttons/BatAmThanh.png");
        btnAmluong1 = new Rectangle(328, 360, iconAmLuong.getWidth() / 4, iconAmLuong.getHeight() / 4);
        btnAmluong2 = new Rectangle(330, 238, iconAmLuong.getWidth() / 4, iconAmLuong.getHeight() / 4);

        iconAmLuongTat = new Texture("Stuffs/Buttons/TatAmThanh.png");
        btnAmluongTat1 = new Rectangle(440, 225, iconAmLuongTat.getWidth() / 4, iconAmLuongTat.getHeight() / 4);
        btnAmluongTat2 = new Rectangle(775, 225, iconAmLuongTat.getWidth() / 4, iconAmLuongTat.getHeight() / 4);

        //Load ảnh các nút
        btnTang = new Texture("Stuffs/Buttons/thanhTang.png");
        btnTang1Hitbox = new Rectangle(956, 365, btnTang.getWidth() / 9, btnTang.getHeight() / 9);
        btnTang2Hitbox = new Rectangle(956, 243, btnTang.getWidth() / 9, btnTang.getHeight() / 9);

        btnGiam = new Texture("Stuffs/Buttons/thanhGiam.png");
        btnGiam1Hitbox = new Rectangle(428, 385, btnGiam.getWidth() / 9, btnGiam.getHeight() / 9);
        btnGiam2Hitbox = new Rectangle(428, 260, btnGiam.getWidth() / 9, btnGiam.getHeight() / 9);

        //Load ảnh đen âm lượng
        AnhMauDen = new Texture("Stuffs/Buttons/pixelMauden.png");

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

            //Tăng âm Lượng
            if(btnTang1Hitbox.contains(touchX, touchY)){
                if(musicVolume < 9) {
                    musicVolume++;
                }
                System.out.print("Music volume: " + musicVolume + "\n");
            }

            if(btnTang2Hitbox.contains(touchX, touchY)){
                if(sfxVolume < 9) {
                    sfxVolume++;
                }
                System.out.print("SFX volume: " + sfxVolume + "\n");
            }

            //Giảm âm lượng
            if(btnGiam1Hitbox.contains(touchX, touchY)){
                if(musicVolume > 0) {
                    musicVolume--;
                }
                System.out.print("Music volume: " + musicVolume + "\n");
            }

            if(btnGiam2Hitbox.contains(touchX, touchY)){
                if(sfxVolume > 0) {
                    sfxVolume--;
                }
                System.out.print("SFX volume: " + sfxVolume + "\n");
            }

            //Thay đổi trạng thái âm thanh
            if(btnAmluong1.contains(touchX, touchY)){
                isMusicMuted = !isMusicMuted;
                System.out.print("Music muted: " + isMusicMuted + "\n");
            }

            if(btnAmluong2.contains(touchX, touchY)){
                isSfxMuted = !isSfxMuted;
                System.out.print("SFX muted: " + isSfxMuted + "\n");
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

        // Vẽ icon âm thanh phù hợp với trạng thái
        if (isMusicMuted) {
            batch.draw(iconAmLuongTat, 328, 360, (float) btnAmluong1.getWidth(), (float) btnAmluong1.getHeight());
        } else {
            batch.draw(iconAmLuong, 328, 360, (float) btnAmluong1.getWidth(), (float) btnAmluong1.getHeight());
        }

        if (isSfxMuted) {
            batch.draw(iconAmLuongTat, 330, 238, (float) btnAmluong2.getWidth(), (float) btnAmluong2.getHeight());
        } else {
            batch.draw(iconAmLuong, 330, 238, (float) btnAmluong2.getWidth(), (float) btnAmluong2.getHeight());
        }

        // Vẽ các ô màu đen cho thanh Music - chỉ vẽ khi không bị tắt tiếng
        if (!isMusicMuted) {
            for (int i = 0; i < musicVolume; i++) {
                // Điều chỉnh vị trí để phù hợp với các ô trắng trong hình
                batch.draw(AnhMauDen, 485 + i * 50, 360, 48, 49);
            }
        }

        // Vẽ các ô màu đen cho thanh SFX - chỉ vẽ khi không bị tắt tiếng
        if (!isSfxMuted) {
            for (int i = 0; i < sfxVolume; i++) {
                // Điều chỉnh vị trí để phù hợp với các ô trắng trong hình
                batch.draw(AnhMauDen, 485 + i * 50, 240, 48, 49);
            }
        }

        batch.end();
    }

    public void Dispose(){
        BackgroundScreen.dispose();
        NenManHinh.dispose();
        btnTang.dispose();
        iconAmLuong.dispose();
        iconAmLuongTat.dispose();
        btnGiam.dispose();
        AnhMauDen.dispose();
        btnbackicon.dispose();
    }
}
