package com.main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameManager {
    Menu menu;
    ScreenPlay manhinh;
    ScoreBoard BangDiem;
    Setting caidat;
    MapBossOne MapOne;
    MapBossTwo MapTwo;
    public static String currScreen;


    public GameManager(String screen){
        menu = new Menu();
        manhinh = new ScreenPlay();
        BangDiem = new ScoreBoard();
        caidat = new Setting();
        currScreen = screen;
        MapOne = new MapBossOne();
        MapTwo = new MapBossTwo();
    }

    public void update(float deltaTime) {
        switch (currScreen) {
            case "menu":
                menu.update();
                break;

            case "game":
                manhinh.update();
                break;

            case "scoreboard":
                BangDiem.update();
                break;

            case "setting":
                caidat.update();
                break;

            case "MapBossOne":
                MapOne.update(deltaTime);
                break;

            case "MapBossTwo":
                MapTwo.update();
                break;

            case "quit":
                Gdx.app.exit();
                break;

        }
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
            case "scoreboard":
//               bảng điểm
                BangDiem.render(batch);
                break;
            case "setting":
//               Cài đặt
                caidat.render(batch);
                break;
            case "MapBossOne":
//               bảng điểm
                MapOne.render(batch);
                break;
            case "MapBossTwo":
//               Cài đặt
                MapTwo.render(batch);
                break;
        }
    }

    public void dispose() {
        menu.dispose();
        manhinh.dispose();
        BangDiem.dispose();
        caidat.Dispose();

//        gamePanel.dispose();
    }


}
