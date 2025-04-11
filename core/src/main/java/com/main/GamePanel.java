package com.main;

import com.badlogic.gdx.Gdx;
import com.main.Inputs.KeyHandler;

import javax.swing.*;
import java.awt.*;

public class GamePanel  {

    Player player;

    public GamePanel() {
        player = new Player();

    }

    public void update(float delta) {
        player.update();
    }

    public void render() {
        player.render();
    }

    public void dispose() {
        player.dispose();
    }
}
