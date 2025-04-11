package com.main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    public int fps = 120;
    public int ups = 200;

    Thread gameThread;

    public GamePanel() {
        gameThread = new Thread(this);
    }

    public void update() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//        Draw shits
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000 / fps;
        double timePerUpdate = 1000000000 / ups;

        long previousUpdate = System.nanoTime();

        int frameCount = 0;
        int updateCount = 0;
        long lastCheck = System.currentTimeMillis();

        double updateTime = 0;
        double frameTime = 0;


        while (true) {
            long currentTime = System.nanoTime();
            updateTime += (currentTime - previousUpdate) / timePerUpdate;
            frameTime += (currentTime - previousUpdate) / timePerFrame;

            previousUpdate = currentTime;

            if (updateTime >= 1) {
                update();
                updateCount++;
                updateTime--;
            }

            if (frameTime >= 1) {
                repaint();
                frameCount++;
                frameTime--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("Fps: "+frameCount+" | Updates: "+updateCount);
                frameCount = 0;
                updateCount = 0;
            }
        }
    }
}
