package com.main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    public int fps = 120;
    public int ups = 200;

    Thread gameThread;

    public GamePanel() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / fps;
        double updateInterval = 1000000000 / ups;


        int fpsCount = 0;
        int updateCount = 0;
        long lastUpdate = System.currentTimeMillis();

        double drawTime = 0;
        double updateTime = 0;

        while (true) {
            long currTime = System.nanoTime();
            updateTime = (currTime - lastUpdate) / updateInterval;
            drawInterval = (currTime - lastUpdate) / drawInterval;

            if (updateTime >= 1) {
                update();
            }

            if (drawTime >= 1) {
                repaint();
            }
        }

    }

    public void update() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
