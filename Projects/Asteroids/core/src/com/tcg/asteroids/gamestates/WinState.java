package com.tcg.asteroids.gamestates;

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

public class WinState extends AbstractGameState {

    private Viewport viewport;

    private LabelEntity winLabel;
    private LabelEntity enterLabel;

    public WinState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    protected void init() {
        Asteroids.content.stopAllSound();
        viewport = new FitViewport(Asteroids.WORLD_WIDTH, Asteroids.WORLD_HEIGHT);

        winLabel = new LabelEntity("You Win!");
        winLabel.setFont(ContentManager.Font.WIN);
        winLabel.setAlign(LabelEntity.MIDDLE_CENTER);
        winLabel.setPosition(Asteroids.WORLD_WIDTH * 0.5f, Asteroids.WORLD_HEIGHT * 0.75f);

        enterLabel = new LabelEntity("Press Enter to Return to Title Screen");
        enterLabel.setFont(ContentManager.Font.MAIN);
        enterLabel.setAlign(LabelEntity.MIDDLE_CENTER);
        enterLabel.setPosition(Asteroids.WORLD_WIDTH * 0.5f, Asteroids.WORLD_HEIGHT * 0.5f);

    }

    @Override
    public void handleInput(float dt) {
        if(MyInput.keyCheckPressed(MyInput.ENTER)) {
            switchState(GameStateType.TITLE);
        }
    }

    @Override
    public void update(float dt) {
        winLabel.update();
        enterLabel.update();
        viewport.apply(true);
    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        sb.begin();
        sb.setProjectionMatrix(viewport.getCamera().combined);
        winLabel.draw(sb);
        enterLabel.draw(sb);
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
