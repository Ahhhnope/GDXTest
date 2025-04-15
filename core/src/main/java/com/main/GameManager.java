package com.main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameManager {
    private HUD hud;
    Menu menu;
    ScreenPlay manhinh;
    ScoreBoard BangDiem;
    Setting caidat;
    MapBossOne MapOne;
    MapBossTwo MapTwo;
    public static String currScreen;
    public boolean bossFightHasData;
    public FadeTransitionManager fade;
    public boolean isActive;

    public GameManager(String screen){
        fade = new FadeTransitionManager(this);
        menu = new Menu();
        manhinh = new ScreenPlay();
        BangDiem = new ScoreBoard();
        caidat = new Setting();
        currScreen = screen;
        MapOne = new MapBossOne();
        MapTwo = new MapBossTwo();
        isActive = false;
        bossFightHasData = false;


    }
    public void changeScreenWithFade(String targetScreen, float duration) {
        if (!fade.isActive) {
            fade.start(targetScreen, duration);
        }
    }
    public void update(float deltaTime) {
        if (fade.isActive) {
            fade.update(deltaTime);
            return;
        }
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
                if (bossFightHasData) {
                    MapOne = null;
                    bossFightHasData = false;
                }
                menu.render(batch);
                break;
            case "game": manhinh.render(batch); break;
            case "scoreboard": BangDiem.render(batch); break;
            case "setting": caidat.render(batch); break;
            case "MapBossOne":
                if (!bossFightHasData) {
                    MapOne = new MapBossOne();
                    bossFightHasData = true;
                }
                MapOne.render(batch);
                break;
            case "MapBossTwo": MapTwo.render(batch); break;
        }


        if (fade.isActive) {
            fade.render(batch);
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
