package com.tcg.clickyplane;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tcg.clickyplane.gamestates.GameStateType;
import com.tcg.clickyplane.managers.GameStateManager;

public class ClickyPlane extends ApplicationAdapter {

    public static int WORLD_WIDTH = 1280;
    public static int WORLD_HEIGHT = 720;

    private GameStateManager gameStateManager;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        gameStateManager = new GameStateManager(GameStateType.PLAY);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float dt = Gdx.graphics.getDeltaTime();
        gameStateManager.step(dt);

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
