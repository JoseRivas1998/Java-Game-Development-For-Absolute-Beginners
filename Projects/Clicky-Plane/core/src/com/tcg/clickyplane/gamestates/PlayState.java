package com.tcg.clickyplane.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tcg.clickyplane.ClickyPlane;
import com.tcg.clickyplane.entities.DualPipe;
import com.tcg.clickyplane.entities.Pipe;
import com.tcg.clickyplane.entities.Plane;
import com.tcg.clickyplane.managers.GameStateManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlayState extends AbstractGameState {

    private static final String TAG = PlayState.class.getSimpleName();
    public static final float PIPE_SPAWN_TIME = 3.15f;

    private Viewport viewport;
    private List<DualPipe> pipes;
    private Plane plane;
    private float pipeSpawnTimer;
    private Texture background;

    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    protected void init() {
        viewport = new FitViewport(ClickyPlane.WORLD_WIDTH, ClickyPlane.WORLD_HEIGHT);
        pipes = new ArrayList<DualPipe>();
        pipeSpawnTimer  = 0;
        plane = new Plane();
        background = new Texture("img/background.png");
    }

    @Override
    public void handleInput(float dt) {
        plane.handleInput();
    }

    @Override
    public void update(float dt) {
        plane.update(dt);
        updatePipes(dt);
        spawnPipes(dt);
        viewport.apply(true);
    }

    private void spawnPipes(float dt) {
        pipeSpawnTimer += dt;
        if(pipeSpawnTimer >= PIPE_SPAWN_TIME) {
            pipeSpawnTimer = 0;
            pipes.add(new DualPipe());
        }
    }

    private void updatePipes(float dt) {
        Iterator<DualPipe> pipeIterator = pipes.iterator();
        while (pipeIterator.hasNext()) {
            DualPipe pipe = pipeIterator.next();
            pipe.update(dt);
            if(pipe.collidingWith(plane)) {
                Gdx.app.debug(TAG, "HIT!");
            }
            if(plane.getCenterX() > pipe.getCenterX() && !pipe.hasGotPoint()) {
                pipe.point();
                Gdx.app.debug(TAG, "POINT!");
            }
            if(pipe.getX() + pipe.getWidth() < 0) {
                Gdx.app.debug(TAG, "Disposing pipe: " + pipe);
                pipe.dispose();
                pipeIterator.remove();
            }
        }
    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        sb.begin();
        sb.setProjectionMatrix(viewport.getCamera().combined);
        sb.draw(background, 0, 0, ClickyPlane.WORLD_WIDTH, ClickyPlane.WORLD_HEIGHT);
        for (DualPipe pipe : pipes) {
            pipe.draw(dt, sb, sr);
        }
        plane.draw(dt, sb, sr);
        sb.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        for (DualPipe pipe : pipes) {
            pipe.dispose();
        }
        background.dispose();
    }
}
