package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

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

    private Texture AnhMauDen;

    private Texture btnbackicon;
    private Rectangle btnBackHitbox;

    //Theo dõi trạng thái âm lượng
    private int musicVolume = 1; // Giá trị mặc định, từ 0-9
    private int sfxVolume = 1; // Giá trị mặc định, từ 0-9
    private boolean isMusicMuted = false;
    private boolean isSfxMuted = false;

    private ShapeRenderer shapeRenderer;

    public Setting(){
        //ui sound
        SoundManager.load();
        //load ảnh background
        BackgroundScreen = new Texture("Stuffs/backgroundScreen.jpeg");

        //load ảnh nền màn hình
        NenManHinh = new Texture("Stuffs/anhnenSetting.png");

        //Load ảnh âm lượng
        iconAmLuong = new Texture("Stuffs/Buttons/BatAmThanh.png");
        btnAmluong1 = new Rectangle(456, 476, iconAmLuong.getWidth() / 4, iconAmLuong.getHeight() / 4);
        btnAmluong2 = new Rectangle(458, 352, iconAmLuong.getWidth() / 4, iconAmLuong.getHeight() / 4);

        iconAmLuongTat = new Texture("Stuffs/Buttons/TatAmThanh.png");

        //Load ảnh các nút
        btnTang = new Texture("Stuffs/Buttons/thanhTang.png");
        btnTang1Hitbox = new Rectangle(1084, 480, btnTang.getWidth() / 9, btnTang.getHeight() / 9);
        btnTang2Hitbox = new Rectangle(1084, 356, btnTang.getWidth() / 9, btnTang.getHeight() / 9);

        btnGiam = new Texture("Stuffs/Buttons/thanhGiam.png");
        btnGiam1Hitbox = new Rectangle(555, 496, btnGiam.getWidth() / 9, btnGiam.getHeight() / 9);
        btnGiam2Hitbox = new Rectangle(555, 372, btnGiam.getWidth() / 9, btnGiam.getHeight() / 9);

        //Load ảnh đen âm lượng
        AnhMauDen = new Texture("Stuffs/Buttons/pixelMauden.png");

        btnbackicon = new Texture("Stuffs/Buttons/backicon.png");
        btnBackHitbox = new Rectangle(10, 392, btnbackicon.getWidth() / 2, btnbackicon.getHeight() / 2);


        shapeRenderer = new ShapeRenderer();
    }

    public void update() {
        // Xử lý click
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            SoundManager.play("click");
            int touchX = Gdx.input.getX();
            int touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (btnBackHitbox.contains(touchX, touchY)) {
                SoundManager.play("click");
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

    public void renderHitbox() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);

        shapeRenderer.rect(btnBackHitbox.x, btnBackHitbox.y, btnBackHitbox.width, btnBackHitbox.height);
        shapeRenderer.rect(btnAmluong1.x, btnAmluong1.y, btnAmluong1.width, btnAmluong1.height);
        shapeRenderer.rect(btnAmluong2.x, btnAmluong2.y, btnAmluong2.width, btnAmluong2.height);
        shapeRenderer.rect(btnGiam1Hitbox.x, btnGiam1Hitbox.y, btnGiam1Hitbox.width, btnGiam1Hitbox.height);
        shapeRenderer.rect(btnGiam2Hitbox.x, btnGiam2Hitbox.y, btnGiam2Hitbox.width, btnGiam2Hitbox.height);
        shapeRenderer.rect(btnTang1Hitbox.x, btnTang1Hitbox.y, btnTang1Hitbox.width, btnTang1Hitbox.height);
        shapeRenderer.rect(btnTang2Hitbox.x, btnTang2Hitbox.y, btnTang2Hitbox.width, btnTang2Hitbox.height);

        shapeRenderer.end();
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
        batch.draw(btnGiam, btnGiam1Hitbox.x, btnGiam1Hitbox.y, (float) btnGiam1Hitbox.getWidth(), (float) btnGiam1Hitbox.getHeight());

        // Vẽ nút Tăng trên bên trái trên
        batch.draw(btnTang, btnTang1Hitbox.x, btnTang1Hitbox.y, (float) btnTang1Hitbox.getWidth(), (float) btnTang1Hitbox.getHeight());

        // Vẽ nút Giảm trên bên bên phải dưới
        batch.draw(btnGiam, btnGiam2Hitbox.x, btnGiam2Hitbox.y, (float) btnGiam2Hitbox.getWidth(), (float) btnGiam2Hitbox.getHeight());

        // Vẽ nút Tăng trên bên trái dưới
        batch.draw(btnTang, btnTang2Hitbox.x, btnTang2Hitbox.y, (float) btnTang2Hitbox.getWidth(), (float) btnTang2Hitbox.getHeight());

        //vẽ nút backicon
        batch.draw(btnbackicon, btnBackHitbox.x, btnBackHitbox.y, (float) btnBackHitbox.getWidth(), (float) btnBackHitbox.getHeight());

        // Vẽ icon âm thanh phù hợp với trạng thái
        if (isMusicMuted) {
            batch.draw(iconAmLuongTat, btnAmluong1.x, btnAmluong1.y, (float) btnAmluong1.getWidth(), (float) btnAmluong1.getHeight());
        } else {
            batch.draw(iconAmLuong, btnAmluong1.x, btnAmluong1.y, (float) btnAmluong1.getWidth(), (float) btnAmluong1.getHeight());
        }

        if (isSfxMuted) {
            batch.draw(iconAmLuongTat, btnAmluong2.x, btnAmluong2.y, (float) btnAmluong2.getWidth(), (float) btnAmluong2.getHeight());
        } else {
            batch.draw(iconAmLuong, btnAmluong2.x, btnAmluong2.y, (float) btnAmluong2.getWidth(), (float) btnAmluong2.getHeight());
        }

        // Vẽ các ô màu đen cho thanh Music - chỉ vẽ khi không bị tắt tiếng
        if (!isMusicMuted) {
            for (int i = 0; i < musicVolume; i++) {
                // Điều chỉnh vị trí để phù hợp với các ô trắng trong hình
                batch.draw(AnhMauDen, 614 + i * 50, 475, 48, 49);
            }
        }

        // Vẽ các ô màu đen cho thanh SFX - chỉ vẽ khi không bị tắt tiếng
        if (!isSfxMuted) {
            for (int i = 0; i < sfxVolume; i++) {
                // Điều chỉnh vị trí để phù hợp với các ô trắng trong hình
                batch.draw(AnhMauDen, 614 + i * 50, 351, 48, 49);
            }
        }

        batch.end();


//        renderHitbox();
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
