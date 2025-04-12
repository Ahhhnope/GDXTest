package com.main.Inputs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class InputHandler extends InputAdapter {
    public static boolean isMouseDown;

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        isMouseDown = false;
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        isMouseDown = true;
        return true;
    }
}
