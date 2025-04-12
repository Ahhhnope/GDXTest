package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScoreBoard {

    private Texture BackgroundScreen;
    private Texture backgroudDiem;
    private Texture title;

    private Texture btnbackicon;

    public String clickedButton = "";

    public ScoreBoard(){
        //load ảnh background
        BackgroundScreen = new Texture("Stuffs/backgroundScreen.jpeg");
        //load ảnh nền
        backgroudDiem = new Texture("Stuffs/ScoreBoard.png");
        //load ảnh chữ ( tên game )
        title = new Texture("Stuffs/Buttons/Score.png");
        //Load ảnh các nút
        btnbackicon = new Texture("Stuffs/Buttons/backicon.png");
        //Cấu hình
    }

    public void update() {

    }

    public void render(SpriteBatch batch){
        //nền Background
        batch.draw(BackgroundScreen, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // Vẽ trên nền background
        batch.draw(backgroudDiem,
            (Gdx.graphics.getWidth() - backgroudDiem.getWidth() * 1.3f) / 2,
            (Gdx.graphics.getHeight() - backgroudDiem.getHeight() * 1.3f) / 2,
            backgroudDiem.getWidth() * 1.3f,
            backgroudDiem.getHeight() * 1.3f);
        // vẽ chữ hiện trên nền background
        batch.draw(title, 500, Gdx.graphics.getHeight() - 70, title.getWidth() * 0.5f, title.getHeight() * 0.5f);

        // vị trí ban đầu để vẽ các nút
        int btnX = 10; // Vị trí x của nút
        int currentY = Gdx.graphics.getHeight() - 250; // Bắt đầu từ trên cao, dưới tiêu đề

        //vẽ nút backicon
        int Backicon = currentY; //lưu vị trí
        batch.draw(btnbackicon, btnX , currentY - 100, btnbackicon.getWidth() / 2, btnbackicon.getHeight() / 2);

        // Xử lý click
        if (Gdx.input.justTouched()) {
            int touchX = Gdx.input.getX();
            int touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            int width = btnbackicon.getWidth() / 2;
            int height = btnbackicon.getHeight() / 2;
            int backY = currentY - 100;

            if (touchX >= btnX && touchX <= btnX + width &&
                touchY >= backY && touchY <= backY + height) {
                clickedButton = "back";
            }
        }

    }

    public void dispose(){
        BackgroundScreen.dispose();
        backgroudDiem.dispose();
        title.dispose();
        btnbackicon.dispose();
    }
}
