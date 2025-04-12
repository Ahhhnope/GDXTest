package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScreenPlay {

    private Texture BackgroundScreen;
    private Texture nenMapLevel;
    private Texture title;

    private Texture btnPlay;
    private Texture btnbackicon;

    public String clickedButton = "";

    public ScreenPlay(){

        //load ảnh background
        BackgroundScreen = new Texture("Stuffs/backgroundScreen.jpeg");
        //Load ảnh nền Chọn Level
        nenMapLevel = new Texture("Stuffs/nenGameMap.png");
        //load ảnh chữ ( tên game )
        title = new Texture("Stuffs/Buttons/TenDanhBoss.png");
        //Load ảnh các nút
        btnPlay = new Texture("Stuffs/Buttons/Play.png");
        btnbackicon = new Texture("Stuffs/Buttons/backicon.png");
    }

    public void update() {

    }

    public void render(SpriteBatch batch){
        //nền Background
        batch.draw(BackgroundScreen, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // Vẽ trên nền background
        batch.draw(nenMapLevel,
            (Gdx.graphics.getWidth() - nenMapLevel.getWidth() * 1.5f) / 2,
            (Gdx.graphics.getHeight() - nenMapLevel.getHeight() * 1.5f) / 2,
            nenMapLevel.getWidth() * 1.5f,
            nenMapLevel.getHeight() * 1.5f);
        // vẽ chữ hiện trên nền background
        batch.draw(title, 500, Gdx.graphics.getHeight() - 200, title.getWidth() * 0.5f, title.getHeight() * 0.5f);

        // vị trí ban đầu để vẽ các nút
        int btnX = 10; // Vị trí x của nút
        int currentY = Gdx.graphics.getHeight() - 250; // Bắt đầu từ trên cao, dưới tiêu đề
        int spacing = 10; // Khoảng cách giữa các nút

        // Vẽ nút PLAY bên phải
        int playP = currentY; //lưu vị trí
        batch.draw(btnPlay, btnX + 430, currentY - 197, btnPlay.getWidth() / 3, btnPlay.getHeight() / 3);
        currentY -= 80 + spacing;

        // Vẽ nút PLAY bên trái
        int playT = currentY; //lưu vị trí
        batch.draw(btnPlay, btnX + 765, currentY  - 107, btnPlay.getWidth() / 3, btnPlay.getHeight() / 3);
        currentY -= 80 + spacing;

        //vẽ nút backicon
        int Backicon = currentY; //lưu vị trí
        batch.draw(btnbackicon, btnX, currentY + 50, btnbackicon.getWidth() / 2, btnbackicon.getHeight() / 2);

        // Xử lý click
        if (Gdx.input.justTouched()) {
            int touchX = Gdx.input.getX();
            int touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            int width = btnbackicon.getWidth() / 2;
            int height = btnbackicon.getHeight() / 2;
            int backY = currentY + 50;

            if (touchX >= btnX && touchX <= btnX + width &&
                touchY >= backY && touchY <= backY + height) {
                clickedButton = "back";
            }
        }

    }

    public void dispose(){
        BackgroundScreen.dispose();
        nenMapLevel.dispose();
        title.dispose();
        btnPlay.dispose();
        btnbackicon.dispose();
    }
}
