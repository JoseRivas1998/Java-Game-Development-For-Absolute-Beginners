package com.tcg.asteroids.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.tcg.asteroids.MyHelpers;

public class Particle extends AbstractEntity {

    private static final float PARTICLE_SPEED = 250f;
    private static final float PARTICLE_RADIUS = 1f;
    private static final float PARTICLE_TIME = 0.5f;

    private float stateTime;

    public Particle(AbstractEntity spawnEntity) {
        this(spawnEntity.getPosition());
    }

    private Particle(Vector2 spawnPoint) {
        super();
        initializeVertices(4);
        setVelocity(MyHelpers.randomVector(PARTICLE_SPEED));
        setPosition(spawnPoint);
        setRadius(PARTICLE_RADIUS);
        stateTime = 0;
        setShape();
    }

    public boolean shouldRemove() {
        return stateTime >= PARTICLE_TIME;
    }

    @Override
    protected void setShape() {
        setSquare();
    }

    @Override
    public void update(float dt) {
        applyVelocity(dt);
        setSquare();
        stateTime += dt;
    }

    @Override
    public void draw(float dt, ShapeRenderer sr) {
        sr.setColor(MyHelpers.randomColor());
        drawPoints(sr);
        sr.setColor(Color.WHITE);
    }
}
