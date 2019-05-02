package com.tcg.platformer.managers.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class MyInputProcessor extends InputAdapter {

    @Override
    public boolean keyDown(int keycode) {
        boolean processed = false;
        if(keycode == Input.Keys.SPACE) {
            MyInput.setKey(MyInput.JUMP, true);
            processed = true;
        }
        if(keycode == Input.Keys.LEFT || keycode == Input.Keys.A ) {
            MyInput.setKey(MyInput.LEFT, true);
            processed = true;
        }
        if(keycode == Input.Keys.RIGHT || keycode == Input.Keys.D ) {
            MyInput.setKey(MyInput.RIGHT, true);
            processed = true;
        }
        if(keycode == Input.Keys.ENTER) {
            MyInput.setKey(MyInput.SHOOT, true);
            processed = true;
        }
        return processed;
    }

    @Override
    public boolean keyUp(int keycode) {
        boolean processed = false;
        if(keycode == Input.Keys.SPACE) {
            MyInput.setKey(MyInput.JUMP, false);
            processed = true;
        }
        if(keycode == Input.Keys.LEFT || keycode == Input.Keys.A ) {
            MyInput.setKey(MyInput.LEFT, false);
            processed = true;
        }
        if(keycode == Input.Keys.RIGHT || keycode == Input.Keys.D ) {
            MyInput.setKey(MyInput.RIGHT, false);
            processed = true;
        }
        if(keycode == Input.Keys.ENTER) {
            MyInput.setKey(MyInput.SHOOT, false);
            processed = true;
        }
        return processed;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        MyInput.mouse.set(screenX, screenY);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        MyInput.mouse.set(screenX, screenY);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        MyInput.mouse.set(screenX, screenY);
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        MyInput.mouse.set(screenX, screenY);
        return true;
    }
}
