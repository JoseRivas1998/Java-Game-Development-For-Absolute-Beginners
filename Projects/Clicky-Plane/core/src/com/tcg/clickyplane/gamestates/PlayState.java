package com.tcg.clickyplane.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tcg.clickyplane.ClickyPlane;
import com.tcg.clickyplane.entities.*;
import com.tcg.clickyplane.managers.ContentManager;
import com.tcg.clickyplane.managers.GameStateManager;
import com.tcg.clickyplane.ui.GameOver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlayState extends AbstractGameState {

    private static final String TAG = PlayState.class.getSimpleName();
    public static final float PIPE_SPAWN_TIME = 1f;

    private Viewport viewport;
    private List<DualPipe> pipes;
    private Plane plane;
    private float pipeSpawnTimer;
    private Texture background;
    private Score score;
    private boolean isAlive;
    private Ground ground;
    private GameOver gameOver;

    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    protected void init() {
        viewport = new FitViewport(ClickyPlane.WORLD_WIDTH, ClickyPlane.WORLD_HEIGHT);
        pipes = new ArrayList<DualPipe>();
        pipeSpawnTimer = 0;
        plane = new Plane();
        background = new Texture("img/background.png");
        score = new Score();
        score.setAlign(Score.MIDDLE_CENTER);
        score.setFont(ContentManager.Font.SCORE);
        score.setPosition(ClickyPlane.WORLD_WIDTH * .5f, ClickyPlane.WORLD_HEIGHT * .75f);
        isAlive = true;
        ground = new Ground();
        gameOver = new GameOver();
    }

    @Override
    public void handleInput(float dt) {
        if (isAlive) plane.handleInput();
    }

    @Override
    public void update(float dt) {
        if (isAlive || !plane.collidingWith(ground)) {
            plane.update(dt);
            if (plane.collidingWith(ground)) {
                isAlive = false;
            }
        }
        if (isAlive) {
            updatePipes(dt);
            spawnPipes(dt);
            ground.update(dt);
        } else {
            gameOver.update(dt);
        }
        score.update(dt);
        viewport.apply(true);
    }

    private void spawnPipes(float dt) {
        pipeSpawnTimer += dt;
        if (pipeSpawnTimer >= PIPE_SPAWN_TIME) {
            pipeSpawnTimer = 0;
            pipes.add(new DualPipe());
        }
    }

    private void updatePipes(float dt) {
        Iterator<DualPipe> pipeIterator = pipes.iterator();
        while (pipeIterator.hasNext()) {
            DualPipe pipe = pipeIterator.next();
            pipe.update(dt);
            if (pipe.collidingWith(plane)) {
                isAlive = false;
            }
            if (plane.getCenterX() > pipe.getCenterX() && !pipe.hasGotPoint()) {
                pipe.point();
                score.incrementScore();
            }
            if (pipe.getX() + pipe.getWidth() < 0) {
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
        ground.draw(dt, sb, sr);
        plane.draw(dt, sb, sr);
        if(isAlive) {
            score.draw(dt, sb, sr);
        } else {
            gameOver.draw(dt, sb);
        }
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
        gameOver.dispose();
        background.dispose();
    }
}
