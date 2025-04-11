package com.main;

import java.awt.*;

public class Player {
    String playerAction = "Idle";
    int x, y;
    int width, height;

    public Player(int playerX, int playerY, int playerWidth, int playerHeight) {
        this.x = playerX;
        this.y = playerY;
        this.width = playerWidth;
        this.height = playerHeight;
    }

    public void update() {
        updatePosition();
    }

    public void draw(Graphics g) {
        g.fillRect(x, y, width, height);
    }

    public void updatePosition() {


    }
}
