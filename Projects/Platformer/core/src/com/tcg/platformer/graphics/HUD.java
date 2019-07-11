package com.tcg.platformer.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tcg.platformer.GameData;
import com.tcg.platformer.entities.LabelEntity;
import com.tcg.platformer.managers.ContentManager;

import static com.tcg.platformer.GameData.WORLD_HEIGHT;
import static com.tcg.platformer.GameData.WORLD_WIDTH;

public class HUD {

    private static final int SPACING = 10;

    private Viewport hudView;
    private LabelEntity coinLabel;
    private LabelEntity healthLabel;
    private Rectangle healthBar;
    private float healthPercentage;

    public HUD() {
        hudView = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        coinLabel = new LabelEntity();
        coinLabel.setPosition(SPACING, WORLD_HEIGHT - SPACING);
        coinLabel.setFont(ContentManager.Font.MAIN);
        healthLabel = new LabelEntity();
        healthLabel.setFont(ContentManager.Font.MAIN);
        healthLabel.setText("Health:");
        healthBar = new Rectangle();
    }

    public void update(float dt, int coins, int playerHealth) {
        coinLabel.setText(String.format("Coins: %d", coins));
        coinLabel.update(dt);
        healthLabel.setPosition(SPACING, WORLD_HEIGHT - SPACING - coinLabel.getHeight() - SPACING);
        healthLabel.update(dt);

        healthBar.setX(healthLabel.getX() + healthLabel.getWidth() + SPACING);
        healthBar.setY(healthLabel.getY() - healthLabel.getHeight());
        healthBar.setHeight(healthLabel.getHeight());
        healthBar.setWidth(200f);

        healthPercentage = (float) playerHealth / GameData.PLAYER_MAX_HEALTH;

        hudView.apply(true);
    }

    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        sb.setProjectionMatrix(hudView.getCamera().combined);
        sb.begin();
        coinLabel.draw(dt, sb, sr);
        healthLabel.draw(dt, sb, sr);
        sb.end();

        sr.setProjectionMatrix(hudView.getCamera().combined);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(Color.RED);
        sr.rect(healthBar.x, healthBar.y, healthBar.width, healthBar.height);
        sr.setColor(Color.GREEN);
        sr.rect(healthBar.x, healthBar.y, healthBar.width * healthPercentage, healthBar.height);
        sr.end();

        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.WHITE);
        sr.rect(healthBar.x, healthBar.y, healthBar.width, healthBar.height);
        sr.end();
    }

    public void resize(int width, int height) {
        hudView.update(width, height, true);
    }

}
