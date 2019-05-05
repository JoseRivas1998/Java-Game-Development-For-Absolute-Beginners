package com.tcg.platformer.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tcg.platformer.managers.GameStateManager;
import com.tcg.platformer.managers.input.MyInput;

public class TitleState extends AbstractGameState {

    public TitleState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    protected void init() {

    }

    @Override
    public void handleInput(float dt) {
        if (MyInput.keyCheckPressed(MyInput.SHOOT)) {
            switchState(GameStateType.PLAY);
        }
        if (MyInput.keyCheckPressed(MyInput.BACK)) {
            Gdx.app.exit();
        }
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {

    }
}
