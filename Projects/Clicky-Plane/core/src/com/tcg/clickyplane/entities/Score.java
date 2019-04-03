package com.tcg.clickyplane.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.tcg.clickyplane.ClickyPlane;
import com.tcg.clickyplane.managers.ContentManager;

public class Score extends AbstractEntity {

    public static final byte TOP = 1 << 0;
    public static final byte MIDDLE = 1 << 1;
    public static final byte BOTTOM = 1 << 2;

    public static final byte LEFT = 1 << 3;
    public static final byte CENTER = 1 << 4;
    public static final byte RIGHT = 1 << 5;

    public static final byte TOP_LEFT = TOP | LEFT;
    public static final byte TOP_RIGHT = TOP | RIGHT;
    public static final byte BOTTOM_LEFT = BOTTOM | LEFT;
    public static final byte BOTTOM_RIGHT = BOTTOM | RIGHT;
    public static final byte MIDDLE_CENTER = MIDDLE | CENTER;

    private int score;
    private byte align;
    private ContentManager.Font font;
    private Vector2 textPos;

    public Score() {
        this.score = 0;
        textPos = new Vector2();
        align = TOP_LEFT;
    }

    @Override
    public void update(float dt) {
        setWidth(ClickyPlane.content.getWidth(font, scoreText()));
        setHeight(ClickyPlane.content.getHeight(font, scoreText()));
        if((align & LEFT) != 0) {
            textPos.x = getX();
        } else if((align & CENTER) != 0) {
            textPos.x = getX() - (getWidth() * .5f);
        } else if((align & RIGHT) != 0) {
            textPos.x = getX() - getWidth();
        }
        if((align & TOP) != 0) {
            textPos.y = getY();
        } else if((align & MIDDLE) != 0) {
            textPos.y = getY() + (getHeight() * .5f);
        } else if((align & BOTTOM) != 0) {
            textPos.y = getY() + getHeight();
        }
    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        ClickyPlane.content.getFont(font).draw(sb, scoreText(), textPos.x, textPos.y);
    }

    public void setAlign(byte align) {
        this.align = align;
    }

    public void setFont(ContentManager.Font font) {
        this.font = font;
    }

    public int getScore() {
        return score;
    }

    public String scoreText() {
        return String.valueOf(score);
    }

    public void incrementScore(int amount) {
        this.score += amount;
    }

    public void incrementScore() {
        incrementScore(1);
    }

    public void decrementScore(int amount) {
        this.score -= amount;
    }

    public void decrementScore() {
        decrementScore(1);
    }

    public void reset() {
        this.score = 0;
    }

    @Override
    public void dispose() {

    }
}
