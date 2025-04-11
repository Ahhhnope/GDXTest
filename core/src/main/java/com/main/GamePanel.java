package com.main;

import com.main.Inputs.KeyHandler;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    public int fps = 120;
    public int ups = 200;

    int x = 0;
    int y = 0;
    int speed = 4;

    KeyHandler kh = new KeyHandler();

    Thread gameThread;

    public GamePanel() {
        gameThread = new Thread(this);
        this.addKeyListener(kh);
    }

    public void update() {
        if (kh.upressed) {
            y += speed;
        }
        if (kh.downPressed) {
            y -= speed;
        }
        if (kh.leftPressed) {
            x -= speed;
        }
        if (kh.rightPressed) {
            x += speed;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//        Draw shits
        g.fillRect(x, y, 100, 100);
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
