package com.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BulletAssets {
    public static Texture BulletSheet;

    public static TextureRegion trackingBullet;

    public static void load(){
        BulletSheet = new Texture("New_All_Fire_Bullet_Pixel_16x16/All_Fire_Bullet_Pixel_16x16_05.png");
        TextureRegion[][] regions = TextureRegion.split(BulletSheet, 32, 32);
        trackingBullet = regions[2][1];
    }
    public static void dispose(){
        BulletSheet.dispose();
    }
}
