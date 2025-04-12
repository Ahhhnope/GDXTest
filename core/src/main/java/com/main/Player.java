package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private Rectangle hitbox;
    private Texture texture;
    private Vector2 position;
    private Vector2 velocity;

    private float width, height;
    private float speed = 300f;
    private float friction = 0.95f;
    private float rotation = 0f;

    private int maxHP = 100;
    private int currentHP = 50;

    private ShapeRenderer shapeRenderer;

    public Player() {
        texture = new Texture("Stuffs/Player/lvl2.png");
        position = new Vector2(100, 100);
        velocity = new Vector2(0, 0);
        width = 64;
        height = 64;
        shapeRenderer = new ShapeRenderer();
    }

    public float getX() { return position.x; }
    public float getY() { return position.y; }

    public void update() {
        float delta = Gdx.graphics.getDeltaTime();

        // Di chuyển trơn mượt bằng velocity và friction
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            velocity.y = MathUtils.lerp(velocity.y, speed, 0.2f);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            velocity.y = MathUtils.lerp(velocity.y, -speed, 0.2f);
        } else {
            velocity.y *= friction;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocity.x = MathUtils.lerp(velocity.x, -speed, 0.2f);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocity.x = MathUtils.lerp(velocity.x, speed, 0.2f);
        } else {
            velocity.x *= friction;
        }

        position.add(velocity.x * delta, velocity.y * delta);

        // Xoay theo chuột
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        float deltaX = mouseX - position.x;
        float deltaY = mouseY - position.y;
        rotation = (float) Math.toDegrees(Math.atan2(deltaY, deltaX)) - 90;

        // Cập nhật hitbox
        float hitboxSize = 40f;
        float offset = (width - hitboxSize) / 2f;
        hitbox = new Rectangle(position.x - width / 2 + offset, position.y - height / 2 + offset, hitboxSize, hitboxSize);
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void takeDamage(int damage) {
        currentHP -= damage;
        if (currentHP < 0) currentHP = 0;
    }

    public void render(SpriteBatch batch) {
        // Thanh máu
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        float barX = (Gdx.graphics.getWidth() / 2) - 600f;
        float barY = 20f;
        float healthbarWidth = 300f;
        float healthbarHeight = 20f;
        float radius = healthbarHeight / 2;

        float percent = (float) currentHP / maxHP;
        float filledWidth = healthbarWidth * percent;
        float fillRight = barX + filledWidth;

        shapeRenderer.setColor(0.3f, 0.3f, 0.3f, 1f);
        shapeRenderer.rect(barX + radius, barY, healthbarWidth - radius * 2, healthbarHeight);
        shapeRenderer.circle(barX + radius, barY + radius, radius);
        shapeRenderer.circle(barX + healthbarWidth - radius, barY + radius, radius);

        shapeRenderer.setColor(1f, 0f, 0f, 1f);
        if (filledWidth > radius * 2) {
            shapeRenderer.rect(barX + radius, barY, filledWidth - radius * 2, healthbarHeight);
            shapeRenderer.circle(barX + radius, barY + radius, radius);
            shapeRenderer.circle(fillRight - radius, barY + radius, radius);
        } else {
            shapeRenderer.circle(barX + radius, barY + radius, filledWidth / 2f);
        }
        shapeRenderer.end();

        // Hitbox
        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 0, 0, 1f);
        shapeRenderer.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
        shapeRenderer.end();

        // Vẽ player
        batch.begin();
        batch.setColor(1f, 1f, 1f, 1f);
        batch.draw(
            texture,
            position.x - width / 2,
            position.y - height / 2,
            width / 2,
            height / 2,
            width,
            height,
            1f,
            1f,
            rotation,
            0,
            0,
            texture.getWidth(),
            texture.getHeight(),
            false,
            false
        );
        batch.end();
    }

    public void dispose() {
        texture.dispose();
    }
}
