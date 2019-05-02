package com.tcg.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tcg.platformer.entities.LabelEntity;
import com.tcg.platformer.managers.ContentManager;

import static com.tcg.platformer.GameData.WORLD_HEIGHT;
import static com.tcg.platformer.GameData.WORLD_WIDTH;

public class HUD {

    private static final int SPACING = 10;

    private Viewport hudView;
    private LabelEntity coinLabel;

    public HUD() {
        hudView = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        coinLabel = new LabelEntity();
        coinLabel.setPosition(SPACING, WORLD_HEIGHT - SPACING);
        coinLabel.setFont(ContentManager.Font.MAIN);
    }

    public void update(float dt, int coins) {
        coinLabel.setText(String.format("Coins: %d", coins));
        coinLabel.update(dt);
        hudView.apply(true);
    }

    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        sb.setProjectionMatrix(hudView.getCamera().combined);
        sb.begin();
        coinLabel.draw(dt, sb, sr);
        sb.end();
    }

    public void resize(int width, int height) {
        hudView.update(width, height, true);
    }

}
