package com.tcg.asteroids.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.tcg.asteroids.Asteroids;
import com.tcg.asteroids.MyHelpers;

public class Star extends AbstractEntity {

    public Star() {
        super();
        setX(MathUtils.random(0, Asteroids.WORLD_WIDTH));
        setY(MathUtils.random(0, Asteroids.WORLD_HEIGHT));
        setRadius(0.5f);
        initializeVertices(4);
        setShape();
    }

    @Override
    protected void setShape() {
        setSquare();
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void draw(float dt, ShapeRenderer sr) {
        sr.setColor(MyHelpers.randomColor());
        drawPoints(sr);
        sr.setColor(Color.WHITE);
    }
}
