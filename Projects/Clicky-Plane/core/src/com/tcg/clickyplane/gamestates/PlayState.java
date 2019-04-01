package com.tcg.clickyplane.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tcg.clickyplane.entities.DualPipe;
import com.tcg.clickyplane.entities.Pipe;
import com.tcg.clickyplane.managers.GameStateManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlayState extends AbstractGameState {

    private static final String TAG = PlayState.class.getSimpleName();
    public static final float PIPE_SPAWN_TIME = 3f;

    private List<DualPipe> pipes;

    private float pipeSpawnTimer;

    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    protected void init() {
        pipes = new ArrayList<DualPipe>();
        pipeSpawnTimer  = 0;
    }

    @Override
    public void handleInput(float dt) {

    }

    @Override
    public void update(float dt) {
        updatePipes(dt);
        spawnPipes(dt);
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
        for (DualPipe pipe : pipes) {
            pipe.draw(dt, sb, sr);
        }
        sb.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {
        for (DualPipe pipe : pipes) {
            pipe.dispose();
        }
    }
}
