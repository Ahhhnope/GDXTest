package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;



public class MapBossOne {

    private Player player;
    private Boss BossOne;
    private float middleScreen;

    public MapBossOne(){
        middleScreen = (Gdx.graphics.getHeight() / 2f) - 60;
        player = new Player();
        BossOne = new Boss(1150,middleScreen);
    }

    public void update(float deltaTime) {
        player.update();
        BossOne.update(deltaTime, player);
    }

    public void render(SpriteBatch batch){
        player.render(batch);
        BossOne.render(batch);
    }

    public void dispose(){
        player.dispose();
        BossOne.dispose();
    }
}
