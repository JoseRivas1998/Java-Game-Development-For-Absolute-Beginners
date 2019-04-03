package com.tcg.clickyplane.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.tcg.clickyplane.ClickyPlane;

public class GameOver implements Disposable {

    private Texture gameOver;
    private float gameOverAlpha;

    private final float GAME_OVER_SMOOTHING = 10f;
    private final float GAME_OVER_DIFF = 25f;

    private Vector2 gameOverPos;
    private float gameOverTarget;


    public GameOver() {
        gameOver = new Texture("img/textGameOver.png");
        gameOverAlpha = 0;
        gameOverTarget = ClickyPlane.WORLD_HEIGHT * .75f - (gameOver.getHeight() * .5f);
        gameOverPos = new Vector2((ClickyPlane.WORLD_WIDTH * .5f) - (gameOver.getWidth() * .5f), gameOverTarget - GAME_OVER_DIFF);
    }

    public void handleInput(Vector2 mouseUnprojected) {

    }

    public void update(float dt) {
        gameOverAlpha += (1 - gameOverAlpha) / GAME_OVER_SMOOTHING;
        gameOverPos.y += (gameOverTarget - gameOverPos.y) / GAME_OVER_SMOOTHING;
    }

    public void draw(float dt, SpriteBatch sb) {
        sb.setColor(1, 1, 1, gameOverAlpha);
        sb.draw(gameOver, gameOverPos.x, gameOverPos.y);
        sb.setColor(1, 1, 1, 1);
    }

    @Override
    public void dispose() {
        gameOver.dispose();
    }

}