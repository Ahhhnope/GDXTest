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
    Setting caidat;
    String currScreen;

    public GameManager(String screen){
        menu = new Menu();
        manhinh = new ScreenPlay();
        BangDiem = new ScoreBoard();
        caidat = new Setting();
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
            case "ScoreBoard":
//               bảng điểm
                BangDiem.Draw();
                break;
            case "Setting":
//               Cài đặt
                caidat.Draw();
                break;
        }

        // Xử lý sau khi vẽ
        if (currScreen.equals("menu")) {
            if (menu.clickedButton.equals("play")) { // chạy màn hình Play
                currScreen = "game";
                menu.clickedButton = "";
            } else if (menu.clickedButton.equals("score")) { //Chạy màn hình Score Board
                currScreen = "ScoreBoard";
                menu.clickedButton = "";
            } else if (menu.clickedButton.equals("setting")) { //Chạy màn hình setting
                currScreen = "Setting";
                menu.clickedButton = "";
            } else if (menu.clickedButton.equals("exit")) { // Thoát màn hình
                Gdx.app.exit();
            }
        } else if (currScreen.equals("game")) {
            if (manhinh.clickedButton.equals("back")) { // quay lại màn hình chính của PLay
                currScreen = "menu";
                manhinh.clickedButton = "";
            }
        } else if (currScreen.equals("ScoreBoard")) {
            if (BangDiem.clickedButton.equals("back")) { // quay lại màn hình chính của Score Board
                currScreen = "menu";
                BangDiem.clickedButton = "";
            }
        }
    }

}
