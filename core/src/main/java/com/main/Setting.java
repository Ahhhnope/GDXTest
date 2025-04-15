package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public class Setting {

    private Texture BackgroundScreen;
    private Texture NenManHinh;

    private Texture btnTang;
    private Texture btnGiam;

    private Circle btnTang1, btnTang2;
    private Circle btnGiam1, btnGiam2;

    private Texture iconAmLuong;
    private Texture iconAmLuongTat;
    private Texture AnhMauDen;
    private Texture btnbackicon;

    private com.badlogic.gdx.math.Rectangle btnBackHitbox;

    private int musicVolume = 1;
    private int sfxVolume = 1;
    private boolean isMusicMuted = false;
    private boolean isSfxMuted = false;

    private ShapeRenderer shapeRenderer;

    private Circle btnAmluong1;
    private Circle btnAmluong2;

    float iconSize = 64;
    float buttonRadius = 28;

    public Setting() {
        SoundManager.load();

        BackgroundScreen = new Texture("Stuffs/backgroundScreen.jpeg");
        NenManHinh = new Texture("Stuffs/anhnenSetting.png");

        iconAmLuong = new Texture("Stuffs/Buttons/BatAmThanh.png");
        iconAmLuongTat = new Texture("Stuffs/Buttons/TatAmThanh.png");

        btnTang = new Texture("Stuffs/Buttons/thanhTang.png");
        btnGiam = new Texture("Stuffs/Buttons/thanhGiam.png");

        btnTang1 = new Circle(1112, 496, buttonRadius);
        btnTang2 = new Circle(1112, 372, buttonRadius);

        btnGiam1 = new Circle(580, 510, buttonRadius);
        btnGiam2 = new Circle(580, 386, buttonRadius);

        btnAmluong1 = new Circle(456 + iconAmLuong.getWidth() / 8f, 476 + iconAmLuong.getHeight() / 8f, 32);
        btnAmluong2 = new Circle(458 + iconAmLuong.getWidth() / 8f, 352 + iconAmLuong.getHeight() / 8f, 32);

        AnhMauDen = new Texture("Stuffs/Buttons/pixelMauden.png");
        btnbackicon = new Texture("Stuffs/Buttons/backicon.png");
        btnBackHitbox = new com.badlogic.gdx.math.Rectangle(10, 392, btnbackicon.getWidth() / 2, btnbackicon.getHeight() / 2);

        shapeRenderer = new ShapeRenderer();
    }

    public void update() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            int touchX = Gdx.input.getX();
            int touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (btnBackHitbox.contains(touchX, touchY)) {
                SoundManager.play("click");
                Main.gm.changeScreenWithFade("menu", 0.5f);
            }

            if (isInsideCircle(btnAmluong1, touchX, touchY)) {
                SoundManager.play("click");
                isMusicMuted = !isMusicMuted;
                MusicManager.setMuted(isMusicMuted);
            }

            if (isInsideCircle(btnAmluong2, touchX, touchY)) {
                SoundManager.play("click");
                isSfxMuted = !isSfxMuted;
                SoundManager.setMuted(isSfxMuted);
            }

            if (isInsideCircle(btnGiam1, touchX, touchY)) {
                SoundManager.play("click");
                if (musicVolume > 0) {
                    musicVolume--;
                    MusicManager.setVolume(musicVolume / 9f);
                }
            }

            if (isInsideCircle(btnGiam2, touchX, touchY)) {
                SoundManager.play("click");
                if (sfxVolume > 0) {
                    sfxVolume--;
                    SoundManager.setDefaultVolume(sfxVolume / 9f);
                }
            }

            if (isInsideCircle(btnTang1, touchX, touchY)) {
                SoundManager.play("click");
                if (musicVolume < 9) {
                    musicVolume++;
                    MusicManager.setVolume(musicVolume / 9f);
                }
            }

            if (isInsideCircle(btnTang2, touchX, touchY)) {
                SoundManager.play("click");
                if (sfxVolume < 9) {
                    sfxVolume++;
                    SoundManager.setDefaultVolume(sfxVolume / 9f);
                }
            }
        }
    }

    private boolean isInsideCircle(Circle circle, float x, float y) {
        float dx = circle.x - x;
        float dy = circle.y - y;
        return dx * dx + dy * dy <= circle.radius * circle.radius;
    }

    public void render(SpriteBatch batch) {
        float minusWidth = buttonRadius * 1.6f;
        float minusHeight = buttonRadius * 0.5f;

        batch.begin();

        batch.draw(BackgroundScreen, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(NenManHinh,
            (Gdx.graphics.getWidth() - NenManHinh.getWidth() * 1.5f) / 2,
            (Gdx.graphics.getHeight() - NenManHinh.getHeight() * 1.5f) / 2,
            NenManHinh.getWidth() * 1.5f,
            NenManHinh.getHeight() * 1.5f);

        batch.draw(btnGiam, (btnGiam1.x - minusWidth / 2f) - 5, (btnGiam1.y - minusHeight / 2f) - 10, minusWidth, minusHeight);
        batch.draw(btnGiam, (btnGiam2.x - minusWidth / 2f) - 5, (btnGiam2.y - minusHeight / 2f) - 10, minusWidth, minusHeight);
        batch.draw(btnTang, (btnTang1.x - buttonRadius) - 7, (btnTang1.y - buttonRadius) + 3, buttonRadius * 2, buttonRadius * 2);
        batch.draw(btnTang, (btnTang2.x - buttonRadius) - 7, (btnTang2.y - buttonRadius) + 3, buttonRadius * 2, buttonRadius * 2);

        batch.draw(btnbackicon, btnBackHitbox.x, btnBackHitbox.y, btnBackHitbox.width, btnBackHitbox.height);

        batch.draw(isMusicMuted ? iconAmLuongTat : iconAmLuong, btnAmluong1.x - iconSize / 2, btnAmluong1.y - iconSize / 2, iconSize, iconSize);
        batch.draw(isSfxMuted ? iconAmLuongTat : iconAmLuong, btnAmluong2.x - iconSize / 2, btnAmluong2.y - iconSize / 2, iconSize, iconSize);

        if (!isMusicMuted) {
            for (int i = 0; i < musicVolume; i++) {
                batch.draw(AnhMauDen, 614 + i * 50, 475, 48, 49);
            }
        }

        if (!isSfxMuted) {
            for (int i = 0; i < sfxVolume; i++) {
                batch.draw(AnhMauDen, 614 + i * 50, 351, 48, 49);
            }
        }

        batch.end();

        renderHitbox(); // nếu cần debug
    }

    public void renderHitbox() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);

        shapeRenderer.rect(btnBackHitbox.x, btnBackHitbox.y, btnBackHitbox.width, btnBackHitbox.height);

        shapeRenderer.circle(btnTang1.x - 8, btnTang1.y + 5, btnTang1.radius);
        shapeRenderer.circle(btnTang2.x - 8, btnTang2.y + 5, btnTang2.radius);
        shapeRenderer.circle(btnGiam1.x - 5, btnGiam1.y - 10, btnGiam1.radius);
        shapeRenderer.circle(btnGiam2.x - 5, btnGiam2.y - 10, btnGiam2.radius);
        shapeRenderer.circle(btnAmluong1.x, btnAmluong1.y, btnAmluong1.radius);
        shapeRenderer.circle(btnAmluong2.x, btnAmluong2.y, btnAmluong2.radius);

        shapeRenderer.end();
    }

    public void Dispose() {
        BackgroundScreen.dispose();
        NenManHinh.dispose();
        btnTang.dispose();
        iconAmLuong.dispose();
        iconAmLuongTat.dispose();
        btnGiam.dispose();
        AnhMauDen.dispose();
        btnbackicon.dispose();
    }
}
