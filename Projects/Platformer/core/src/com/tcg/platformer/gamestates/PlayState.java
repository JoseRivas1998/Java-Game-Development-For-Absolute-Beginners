package com.tcg.platformer.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tcg.platformer.GameData;
import com.tcg.platformer.entities.Level;
import com.tcg.platformer.entities.Player;
import com.tcg.platformer.managers.GameStateManager;

import static com.tcg.platformer.GameData.*;

public class PlayState extends AbstractGameState {

    private World world;
    private float accumulator;
    private Viewport b2dView;
    private Box2DDebugRenderer b2dRenderer;

    private Level map;
    private Viewport gameView;

    private Player player;

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

        player = new Player(world, map.getPlayerSpawnPosition());

    }

    private void initPhys() {
        accumulator = 0;
        world = new World(new Vector2(0, GRAVITY), true);
        world.setContactListener(new GameContactListener());
        b2dRenderer = new Box2DDebugRenderer();
        b2dView = new FitViewport(WORLD_WIDTH * METERS_PER_PIXEL, WORLD_HEIGHT * METERS_PER_PIXEL);
    }

    @Override
    public void handleInput(float dt) {
        player.handleInput();
    }

    @Override
    public void update(float dt) {
        b2dView.getCamera().position.set(gameView.getCamera().position.x * METERS_PER_PIXEL, gameView.getCamera().position.y * METERS_PER_PIXEL, 0);
        player.update(dt);
        gameView.getCamera().position.set(player.getCenter(), 0);
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
        sb.begin();
        sb.setProjectionMatrix(gameView.getCamera().combined);
        player.draw(dt, sb, sr);
        sb.end();
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
        player.dispose();
    }

    class GameContactListener implements ContactListener {

        private int playerFootContacts = 0;

        @Override
        public void beginContact(Contact contact) {
            if(contact.getFixtureA().getUserData() != null && contact.getFixtureA().getUserData().equals(B2DUserData.PLAYER_FOOT)) {
                playerFootContacts++;
            }
            if(contact.getFixtureB().getUserData() != null && contact.getFixtureB().getUserData().equals(B2DUserData.PLAYER_FOOT)) {
                playerFootContacts++;
            }
            player.setOnGround(playerFootContacts > 0);
        }

        @Override
        public void endContact(Contact contact) {
            if(contact.getFixtureA().getUserData() != null && contact.getFixtureA().getUserData().equals(B2DUserData.PLAYER_FOOT)) {
                playerFootContacts--;
            }
            if(contact.getFixtureB().getUserData() != null && contact.getFixtureB().getUserData().equals(B2DUserData.PLAYER_FOOT)) {
                playerFootContacts--;
            }
            player.setOnGround(playerFootContacts > 0);
        }

        @Override
        public void preSolve(Contact contact, Manifold oldManifold) {}

        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) {}
    }

}
