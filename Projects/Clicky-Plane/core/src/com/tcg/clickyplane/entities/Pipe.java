package com.tcg.clickyplane.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tcg.clickyplane.ClickyPlane;

public class Pipe extends AbstractEntity {

    public static final int TOP_HEIGHT = 73;
    public static final float PIPE_SPEED = 100f;

    private Texture top;
    private Texture body;

    public enum PipeType {
        TOP, BOTTOM
    }

    private PipeType pipeType;

    public Pipe(float y, PipeType pipeType) {
        super();
        setX(ClickyPlane.WORLD_WIDTH);

        top = new Texture("img/pipe_top.png");
        body = new Texture("img/pipe_body.png");

        setWidth(top.getWidth());

        this.pipeType = pipeType;
        switch (pipeType) {
            case TOP:
                setHeight(ClickyPlane.WORLD_HEIGHT - y);
                setY(y);
                break;
            case BOTTOM:
                setHeight(y);
                setY(0);
                break;
        }

        setVelocity(-PIPE_SPEED, 0);

    }

    @Override
    public void update(float dt) {
        applyVelocity(dt);
    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {

        switch (pipeType) {
            case TOP:
                drawUpperBody(sb);
                break;
            case BOTTOM:
                drawBottomPipe(sb);
                break;
        }

    }

    private void drawBottomPipe(SpriteBatch sb) {
        for (float y = 0; y < getHeight() - TOP_HEIGHT; y++) {
            sb.draw(body, getX() + 1, y);
        }
        sb.draw(top, getX(), getHeight() - TOP_HEIGHT);
    }

    private void drawUpperBody(SpriteBatch sb) {

        sb.draw(top, getX(), getY());
        for(float y = getY() + TOP_HEIGHT; y < ClickyPlane.WORLD_HEIGHT; y++) {
            sb.draw(body, getX() + 1, y);
        }

    }

    @Override
    public void dispose() {
        top.dispose();
    }
}
