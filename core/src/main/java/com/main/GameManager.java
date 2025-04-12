package com.main;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;



public class GameManager {
    Menu menu;
    ScreenPlay manhinh;
    ScoreBoard BangDiem;
    Setting caidat;
    public static String currScreen;


    public GameManager(String screen){
        menu = new Menu();
        manhinh = new ScreenPlay();
        BangDiem = new ScoreBoard();
        caidat = new Setting();
        currScreen = screen;
    }

    public void update() {

    }

    public void render(SpriteBatch batch) {

        switch (currScreen) {
            case "menu":
//                vẽ menu
                menu.render(batch);
                break;
            case "game":
//                vẽ game
                manhinh.render(batch);
                break;
            case "ScoreBoard":
//               bảng điểm
                BangDiem.render(batch);
                break;
            case "Setting":
//               Cài đặt
                caidat.render(batch);
                break;
        }
    }

    public void dispose() {
        menu.dispose();
//        gamePanel.dispose();
    }


}
