package com.tcg.asteroids.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tcg.asteroids.Asteroids;
import com.tcg.asteroids.entities.Asteroid;
import com.tcg.asteroids.entities.LabelEntity;
import com.tcg.asteroids.managers.ContentManager;
import com.tcg.asteroids.managers.GameStateManager;
import com.tcg.asteroids.managers.input.MyInput;

public class TitleState extends AbstractGameState {

    private Viewport viewport;

    private LabelEntity titleLabel;
    private LabelEntity enterLabel;
    private LabelEntity exitLabel;

    public TitleState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    protected void init() {

        viewport = new FitViewport(Asteroids.WORLD_WIDTH, Asteroids.WORLD_HEIGHT);

        titleLabel = new LabelEntity("Asteroids");
        titleLabel.setAlign(LabelEntity.MIDDLE_CENTER);
        titleLabel.setFont(ContentManager.Font.TITLE);
        titleLabel.setPosition(Asteroids.WORLD_WIDTH * 0.5f, Asteroids.WORLD_HEIGHT * 0.75f);

        enterLabel = new LabelEntity("Press Enter to Play!");
        enterLabel.setAlign(LabelEntity.MIDDLE_CENTER);
        enterLabel.setFont(ContentManager.Font.MAIN);
        enterLabel.setPosition(Asteroids.WORLD_WIDTH * 0.5f, Asteroids.WORLD_HEIGHT * 0.5f);

        exitLabel = new LabelEntity("Press Escape to Exit!");
        exitLabel.setAlign(LabelEntity.MIDDLE_CENTER);
        exitLabel.setFont(ContentManager.Font.MAIN);
        exitLabel.setPosition(Asteroids.WORLD_WIDTH * 0.5f, Asteroids.WORLD_HEIGHT * 0.25f);

    }

    @Override
    public void handleInput(float dt) {
        if(MyInput.keyCheckPressed(MyInput.ESCAPE)) {
            Gdx.app.exit();
        }
        if(MyInput.keyCheckPressed(MyInput.ENTER)) {
            switchState(GameStateType.PLAY);
        }
    }

    @Override
    public void update(float dt) {
        titleLabel.update();
        enterLabel.update();
        exitLabel.update();

        viewport.apply(true);
    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        sb.begin();
        sb.setProjectionMatrix(viewport.getCamera().combined);
        titleLabel.draw(sb);
        enterLabel.draw(sb);
        exitLabel.draw(sb);
        sb.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {

    }
}
