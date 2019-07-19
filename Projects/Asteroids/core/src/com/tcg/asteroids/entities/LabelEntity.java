package com.tcg.asteroids.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.tcg.asteroids.Asteroids;
import com.tcg.asteroids.managers.ContentManager;

public class LabelEntity {

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

    private String text;
    private byte align;
    private ContentManager.Font font;
    private Vector2 textPos;
    private Rectangle bounds;

    public LabelEntity() {
        this.text = "";
        textPos = new Vector2();
        this.bounds = new Rectangle();
        align = TOP_LEFT;
    }

    public void update(float dt) {
        this.bounds.setWidth(Asteroids.content.getWidth(font, this.text));
        this.bounds.setHeight(Asteroids.content.getHeight(font, this.text));
        if ((align & LEFT) != 0) {
            textPos.x = this.bounds.getX();
        } else if ((align & CENTER) != 0) {
            textPos.x = this.bounds.getX() - (this.bounds.getWidth() * .5f);
        } else if ((align & RIGHT) != 0) {
            textPos.x = this.bounds.getX() - this.bounds.getWidth();
        }
        if ((align & TOP) != 0) {
            textPos.y = this.bounds.getY();
        } else if ((align & MIDDLE) != 0) {
            textPos.y = this.bounds.getY() + (this.bounds.getHeight() * .5f);
        } else if ((align & BOTTOM) != 0) {
            textPos.y = this.bounds.getY() + this.bounds.getHeight();
        }
    }


    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        Asteroids.content.getFont(font).draw(sb, this.text, textPos.x, textPos.y);
    }

    public void setAlign(byte align) {
        this.align = align;
    }

    public void setFont(ContentManager.Font font) {
        this.font = font;
    }

    public void setText(String text) {
        this.text = text;
    }

}
