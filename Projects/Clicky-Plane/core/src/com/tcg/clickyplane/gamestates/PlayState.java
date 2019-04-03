package com.tcg.clickyplane.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tcg.clickyplane.ClickyPlane;
import com.tcg.clickyplane.entities.*;
import com.tcg.clickyplane.managers.ClickListener;
import com.tcg.clickyplane.managers.ContentManager;
import com.tcg.clickyplane.managers.GameStateManager;
import com.tcg.clickyplane.managers.input.MyInput;
import com.tcg.clickyplane.ui.GameOver;
import com.tcg.clickyplane.ui.GetReady;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlayState extends AbstractGameState {

    private static final String TAG = PlayState.class.getSimpleName();
    public static final float PIPE_SPAWN_TIME = 1f;
    private static final float DIE_SOUND_DELAY = 0.25f;

    private Viewport viewport;
    private List<DualPipe> pipes;
    private Plane plane;
    private float pipeSpawnTimer;
    private Texture background;
    private Score score;
    private boolean isAlive;
    private Ground ground;
    private GameOver gameOver;
    private float dieTime;
    private boolean playedDieSound;
    private boolean started;
    private GetReady getReady;

    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    protected void init() {
        viewport = new FitViewport(ClickyPlane.WORLD_WIDTH, ClickyPlane.WORLD_HEIGHT);

        pipes = new ArrayList<DualPipe>();
        pipeSpawnTimer = 0;
        plane = new Plane();
        background = ClickyPlane.content.getTexture(ContentManager.Image.BACKGROUND);
        score = new Score();
        score.setAlign(Score.MIDDLE_CENTER);
        score.setFont(ContentManager.Font.SCORE);
        score.setPosition(ClickyPlane.WORLD_WIDTH * .5f, ClickyPlane.WORLD_HEIGHT * .75f);
        isAlive = true;
        ground = new Ground();
        gameOver = new GameOver();
        started = false;
        getReady = new GetReady();
        gameOver.setOnBack(new ClickListener() {
            @Override
            public void onClick() {
                switchState(GameStateType.TITLE);
            }
        });
        gameOver.setOnReplay(new ClickListener() {
            @Override
            public void onClick() {
                switchState(GameStateType.PLAY);
            }
        });
    }

    @Override
    public void handleInput(float dt) {
        if (isAlive && started){
            plane.handleInput();
        } else if(started) {
            gameOver.handleInput(MyInput.worldMouse(viewport));
        } else{
            if(MyInput.keyCheckPressed(MyInput.CLICK)) {
                started = true;
                plane.jump();
            }
        }
    }

    @Override
    public void update(float dt) {
        if ((isAlive || !plane.collidingWith(ground)) && started) {
            plane.update(dt);
            if (plane.collidingWith(ground)) {
                if(isAlive) ClickyPlane.content.playSound(ContentManager.SoundEffect.HIT);
                isAlive = false;
                playedDieSound = true; // DOnt play falling sound
            }
        }
        if (isAlive && started) {
            updatePipes(dt);
            spawnPipes(dt);
            ground.update(dt);
        } else if(started) {
            gameOver.setScore(score.getScore());
            gameOver.update(dt);
            dieTime += dt;
            if(dieTime > DIE_SOUND_DELAY && !playedDieSound) {
                playedDieSound = true;
                ClickyPlane.content.playSound(ContentManager.SoundEffect.DIE);
            }
        } else {
            ground.update(dt);
            getReady.update();
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
                ClickyPlane.content.playSound(ContentManager.SoundEffect.HIT);
                dieTime = 0;
                playedDieSound = false;
                isAlive = false;
            }
            if (plane.getCenterX() > pipe.getCenterX() && !pipe.hasGotPoint()) {
                pipe.point();
                score.incrementScore();
                ClickyPlane.content.playSound(ContentManager.SoundEffect.POINT);
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
        if(isAlive && started) {
            score.draw(dt, sb, sr);
        } else if(started) {
            gameOver.draw(dt, sb, sr);
        } else {
            getReady.draw(dt, sb);
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
        getReady.dispose();
    }
}
