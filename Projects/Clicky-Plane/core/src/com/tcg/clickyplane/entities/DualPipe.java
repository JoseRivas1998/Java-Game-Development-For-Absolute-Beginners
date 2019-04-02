package com.tcg.clickyplane.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.tcg.clickyplane.ClickyPlane;

public class DualPipe extends AbstractEntity {

    private static final float SPACE_BETWEEN_PIPES = 200f;

    private Pipe top;
    private Pipe bottom;
    private boolean gotPoint;

    public DualPipe() {
        super();
        float topY = MathUtils.random(Pipe.TOP_HEIGHT + SPACE_BETWEEN_PIPES, ClickyPlane.WORLD_HEIGHT - Pipe.TOP_HEIGHT);

        top = new Pipe(topY, Pipe.PipeType.TOP);
        bottom = new Pipe(topY - SPACE_BETWEEN_PIPES, Pipe.PipeType.BOTTOM);

        gotPoint = false;

        setSize(top.getSize());

    }

    public void point() {
        gotPoint = true;
    }

    public boolean hasGotPoint() {
        return gotPoint;
    }

    @Override
    public void update(float dt) {
        top.update(dt);
        bottom.update(dt);
        setX(top.getX());
    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        top.draw(dt, sb, sr);
        bottom.draw(dt, sb, sr);
    }

    @Override
    public void dispose() {
        top.dispose();
        bottom.dispose();
    }


    @Override
    public float getX() {
        return top.getX();
    }

    @Override
    public float getY() {
        return top.getY();
    }

    @Override
    public boolean collidingWith(Rectangle rectangle) {
        return top.collidingWith(rectangle) || bottom.collidingWith(rectangle);
    }
}
