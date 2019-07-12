package com.tcg.asteroids;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tcg.asteroids.gamestates.GameStateType;
import com.tcg.asteroids.managers.ContentManager;
import com.tcg.asteroids.managers.GameStateManager;
import com.tcg.asteroids.managers.input.MyInput;
import com.tcg.asteroids.managers.input.MyInputProcessor;

public class Asteroids extends ApplicationAdapter {

	public static final int WORLD_WIDTH = 800;
	public static final int WORLD_HEIGHT = 600;

	private GameStateManager gsm;
	public static ContentManager content;

	@Override
	public void create () {
		content = new ContentManager();
		gsm = new GameStateManager(GameStateType.PLAY);
		Gdx.input.setInputProcessor(new MyInputProcessor());
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.step(Gdx.graphics.getDeltaTime());
		MyInput.update();
	}

	@Override
	public void resize(int width, int height) {
		gsm.resize(width, height);
	}

	@Override
	public void dispose () {
		gsm.dispose();
		content.dispose();
	}
}
