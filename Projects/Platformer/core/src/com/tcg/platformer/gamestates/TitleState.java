package com.tcg.platformer.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tcg.platformer.entities.LabelEntity;
import com.tcg.platformer.entities.TilingBackground;
import com.tcg.platformer.managers.ContentManager;
import com.tcg.platformer.managers.GameStateManager;
import com.tcg.platformer.managers.input.MyInput;

import static com.tcg.platformer.GameData.WORLD_HEIGHT;
import static com.tcg.platformer.GameData.WORLD_WIDTH;

public class TitleState extends AbstractGameState {

    private Viewport viewport;
    private TilingBackground background;

    private LabelEntity title;
    private LabelEntity pressStart;

    public TitleState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    protected void init() {
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        background = new TilingBackground(ContentManager.Image.LEVEL_BG, WORLD_WIDTH, WORLD_HEIGHT);
        title = new LabelEntity();
        title.setText("Platformer");
        title.setAlign(LabelEntity.MIDDLE_CENTER);
        title.setFont(ContentManager.Font.TITLE);
        title.setPosition(WORLD_WIDTH * 0.5f, WORLD_HEIGHT * 0.75f);

        pressStart = new LabelEntity();
        pressStart.setText("Press Start to Begin!");
        pressStart.setAlign(LabelEntity.MIDDLE_CENTER);
        pressStart.setFont(ContentManager.Font.MAIN);
        pressStart.setPosition(WORLD_WIDTH * 0.5f, WORLD_HEIGHT * 0.25f);

    }

    @Override
    public void handleInput(float dt) {
        if (MyInput.keyCheckPressed(MyInput.SHOOT)) {
            switchState(GameStateType.PLAY);
        }
        if (MyInput.keyCheckPressed(MyInput.BACK)) {
            Gdx.app.exit();
        }
    }

    @Override
    public void update(float dt) {
        viewport.apply(true);
        title.update(dt);
        pressStart.update(dt);
    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        sb.begin();
        sb.setProjectionMatrix(viewport.getCamera().combined);
        background.draw(sb);
        title.draw(dt, sb, sr);
        pressStart.draw(dt, sb, sr);
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
