package com.main;

public class GamePanel implements Runnable {
    public int fps = 120;
    public int ups = 200;

    Thread gameThread;

    public GamePanel() {
        gameThread = new Thread(this);
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / fps;
        double updateInterval = 1000000000 / ups;

        long currTime = System.nanoTime();

        int fpsCount = 0;
        int updateCount = 0;
        long lastUpdate = System.currentTimeMillis();

        double drawTime = 0;
        doutble updateTime = 0;

        while (true) {
            long currTime =
        }

    }
}
