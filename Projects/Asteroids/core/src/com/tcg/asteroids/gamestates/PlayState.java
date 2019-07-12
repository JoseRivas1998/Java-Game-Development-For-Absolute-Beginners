package com.tcg.asteroids.gamestates;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tcg.asteroids.Asteroids;
import com.tcg.asteroids.entities.Ship;
import com.tcg.asteroids.managers.GameStateManager;

public class PlayState extends AbstractGameState {

    private Viewport viewport;
    private Ship ship;

    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    protected void init() {
        viewport = new FitViewport(Asteroids.WORLD_WIDTH, Asteroids.WORLD_HEIGHT);
        ship = new Ship();
    }

    @Override
    public void handleInput(float dt) {
        ship.handleInput(dt);
    }

    @Override
    public void update(float dt) {
        viewport.apply(true);
        ship.update(dt);
    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setProjectionMatrix(viewport.getCamera().combined);
        ship.draw(dt, sr);
        sr.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {

    }
}
