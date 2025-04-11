package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import javax.swing.*;
import java.awt.*;

public class GameManager {
    private SpriteBatch batch;
    Menu menu;
    GamePanel gamePanel;
    public static String currScreen;


    public GameManager(String screen){

        menu = new Menu();
        gamePanel = new GamePanel();


        currScreen = screen;
    }

    public void update() {

    }

    public void render(SpriteBatch batch) {

        switch (currScreen) {
            case "menu":
//                vẽ menu
                menu.render();
                menu.update();
                break;

            case "game":
//                vẽ game

                gamePanel.render(batch);
                gamePanel.update(delta);

                break;
        }
    }

    public void dispose() {
        menu.dispose();
        gamePanel.dispose();
    }


}
