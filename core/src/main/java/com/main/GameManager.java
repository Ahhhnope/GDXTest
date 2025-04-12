package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import javax.swing.*;
import java.awt.*;

public class GameManager {
    Menu menu;
    String currScreen;

    public GameManager(String screen){
        menu = new Menu();



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
                break;
        }
    }


}
