package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;


public class MapBossOne {

    private Player player;
    private Boss BossOne;
    private float middleScreen;
    private ShapeRenderer shapeRenderer;

    public MapBossOne(){
        middleScreen = (Gdx.graphics.getHeight() / 2f) - 60;
        player = new Player();
        BossOne = new Boss(1150,middleScreen);

        shapeRenderer = new ShapeRenderer();
    }

    public void update(float deltaTime) {
        player.update();
        BossOne.update(deltaTime, player);

        ArrayList<Bullet> playerBullets = player.getBullets();
        for (int i = 0; i < playerBullets.size(); i++) {
            Bullet bullet = playerBullets.get(i);
            Circle bulletHitbox = bullet.getBulletHitbox();
            Circle bossHitbox = BossOne.getHitbox();

            if (bulletHitbox == null || bossHitbox == null) continue;

           /* // DEBUG (tùy chọn: bật/tắt)
            System.out.println(">> Đạn: (" + bulletHitbox.x + ", " + bulletHitbox.y + "), R=" + bulletHitbox.radius);
            System.out.println(">> Boss: (" + bossHitbox.x + ", " + bossHitbox.y + "), R=" + bossHitbox.radius);
*/
            if (bulletHitbox.overlaps(bossHitbox)) {
                BossOne.takeDamage(50);
                bullet.dispose();
                playerBullets.remove(i);
                i--;
            }
        }
    }

    public void render(SpriteBatch batch){
        player.render(batch);
        BossOne.render(batch);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 0, 0, 1); // Đỏ cho boss
        shapeRenderer.circle(
            BossOne.getHitbox().x,
            BossOne.getHitbox().y,
            BossOne.getHitbox().radius
        );
        shapeRenderer.end();
    }

    public void dispose(){
        player.dispose();
        BossOne.dispose();
    }
}
