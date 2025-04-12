package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import javax.swing.*;
import java.awt.*;

public class GameManager {
    Menu menu;
    ScreenPlay manhinh;
    ScoreBoard BangDiem;
    String currScreen;

    public GameManager(String screen){
        menu = new Menu();
        manhinh = new ScreenPlay();
        BangDiem = new ScoreBoard();

        currScreen = screen;
    }

    public void draw() {

        switch (currScreen) {
            case "menu":
//                vẽ menu
                menu.draw();
                break;

            case "game":
//                vẽ game
                manhinh.Draw();
                break;
        }

        // Xử lý sau khi vẽ
        if (currScreen.equals("menu")) {
            if (menu.clickedButton.equals("play")) {
                currScreen = "game";
                menu.clickedButton = "";
            } else if (menu.clickedButton.equals("exit")) {
                Gdx.app.exit();
            }
        }

        if (currScreen.equals("game")) {
            if (manhinh.clickedButton.equals("back")) {
                currScreen = "menu";
                manhinh.clickedButton = "";
            }
        }
    }

}
