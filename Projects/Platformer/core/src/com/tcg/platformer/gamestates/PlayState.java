package com.tcg.platformer.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tcg.platformer.entities.Level;
import com.tcg.platformer.managers.GameStateManager;

import static com.tcg.platformer.GameData.*;

public class PlayState extends AbstractGameState {

    private World world;
    private float accumulator;
    private Viewport b2dView;
    private Box2DDebugRenderer b2dRenderer;

    private Level map;
    private Viewport gameView;

    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    protected void init() {
        initPhys();
        map = new Level(0, world);
        gameView = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        gameView.getCamera().position.set(WORLD_WIDTH * 0.5f, WORLD_HEIGHT * 0.5f, 0);
        gameView.getCamera().update();
    }

    private void initPhys() {
        accumulator = 0;
        world = new World(new Vector2(0, GRAVITY), true);
        b2dRenderer = new Box2DDebugRenderer();
        b2dView = new FitViewport(WORLD_WIDTH * METERS_PER_PIXEL, WORLD_HEIGHT * METERS_PER_PIXEL);
    }

    @Override
    public void handleInput(float dt) {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            gameView.getCamera().position.add(-PIXELS_PER_METER * 5 * dt, 0, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            gameView.getCamera().position.add(PIXELS_PER_METER * 5 * dt, 0, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            gameView.getCamera().position.add(0, -PIXELS_PER_METER * 5 * dt, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            gameView.getCamera().position.add(0, PIXELS_PER_METER * 5 * dt, 0);
        }
    }

    @Override
    public void update(float dt) {
        b2dView.getCamera().position.set(gameView.getCamera().position.x * METERS_PER_PIXEL, gameView.getCamera().position.y * METERS_PER_PIXEL, 0);
        b2dView.apply();
        gameView.apply();
        physicsStep(dt);
    }

    private void physicsStep(float dt) {
        accumulator += Math.min(dt, 0.25f);
        while(accumulator >= TIME_STEP) {
            world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
            accumulator -= TIME_STEP;
        }
    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        map.render(gameView);
        if(DEBUG) {
            b2dRenderer.render(world, b2dView.getCamera().combined);
        }
    }

    @Override
    public void resize(int width, int height) {
        b2dView.update(width, height);
        gameView.update(width, height);
    }

    @Override
    public void dispose() {
        world.dispose();
    }
}
