package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import javax.swing.*;
import java.awt.*;

public class GameManager {
    Menu menu;
    GamePanel gamePanel;
    public static String currScreen;
    float delta;

    public GameManager(String screen){
        menu = new Menu();
        gamePanel = new GamePanel();


        delta = Gdx.graphics.getDeltaTime();
        currScreen = screen;
    }

    public void update() {

    }

    public void render() {

        switch (currScreen) {
            case "menu":
//                vẽ menu
                menu.render();
                menu.update();
                break;

            case "game":
//                vẽ game
                gamePanel.render();
                gamePanel.update(delta);
                break;
        }
    }

    public void dispose() {
        menu.dispose();
        gamePanel.dispose();
    }


}
