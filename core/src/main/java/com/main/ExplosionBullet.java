package com.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Circle;

public class ExplosionBullet extends Bullet {
    public ExplosionBullet(float startX, float startY, float targetX, float targetY) {
        super(startX, startY, targetX, targetY); // bay về hướng bất kỳ

    }
}
