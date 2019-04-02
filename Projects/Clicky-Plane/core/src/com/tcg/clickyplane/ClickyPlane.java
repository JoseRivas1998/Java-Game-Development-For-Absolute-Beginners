package com.tcg.clickyplane;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tcg.clickyplane.gamestates.GameStateType;
import com.tcg.clickyplane.managers.GameStateManager;
import com.tcg.clickyplane.managers.input.MyInput;
import com.tcg.clickyplane.managers.input.MyInputProcessor;

public class ClickyPlane extends ApplicationAdapter {

    public static int WORLD_WIDTH = 800;
    public static int WORLD_HEIGHT = 480;

    private GameStateManager gameStateManager;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        gameStateManager = new GameStateManager(GameStateType.PLAY);
        Gdx.input.setInputProcessor(new MyInputProcessor());
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float dt = Gdx.graphics.getDeltaTime();
        gameStateManager.step(dt);

        MyInput.update();

    }

    @Override
    public void resize(int width, int height) {
        gameStateManager.resize(width, height);
    }

    @Override
    public void dispose() {
        gameStateManager.dispose();
    }
}
