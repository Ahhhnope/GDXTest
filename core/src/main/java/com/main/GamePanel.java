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

    }
}
