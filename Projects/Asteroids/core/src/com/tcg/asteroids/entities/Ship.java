package com.tcg.asteroids.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tcg.asteroids.Asteroids;
import com.tcg.asteroids.MyHelpers;
import com.tcg.asteroids.managers.input.MyInput;

public class Ship extends AbstractEntity {

    private static final int NUM_POINTS = 4;
    private static final float RADIUS = 16.0f;
    private static final float ACCELERATION = 300.0f;
    private static final float ROTATION_SPEED = MathUtils.PI2;

    private static final Color SHIP_COLOR = Color.BLUE;
    private static final Color THRUST_COLOR = Color.RED;

    private boolean isThrusting;
    private Vector2[] flame;

    public Ship() {
        super();
        setRadius(RADIUS);
        initializeVertices(NUM_POINTS);
        flame = new Vector2[3];
        reset();
    }

    private void reset() {
        setVelocity(0, 0);
        setX(Asteroids.WORLD_WIDTH * 0.5f);
        setY(Asteroids.WORLD_HEIGHT * 0.5f);
        this.angle = 0;
        isThrusting = false;
        setShape();
        // TODO stop thruster sound
    }

    public void handleInput(float dt) {
        if (MyInput.keyCheck(MyInput.LEFT)) {
            angle += ROTATION_SPEED * dt;
        }
        if (MyInput.keyCheck(MyInput.RIGHT)) {
            angle -= ROTATION_SPEED * dt;
        }
        if (MyInput.keyCheck(MyInput.THRUST)) {
            Vector2 acceleration = MyHelpers.polarVectorRadians(ACCELERATION * dt, this.angle);
            setVelocity(getVelocity().add(acceleration));
            if (!isThrusting) {
                isThrusting = true;
            }
        } else {
            isThrusting = false;
        }
    }

    @Override
    protected void setShape() {
        setPointDegrees(0, getRadius(), 0);
        setPointDegrees(1, getRadius(), 135);
        setPointDegrees(2, getRadius() * 0.25f, 180);
        setPointDegrees(3, getRadius(), 225);
        if(isThrusting) {
            setFlame();
        }
    }

    private void setFlame() {
        setFlamePoint(0, getRadius() / 3f, 150);
        setFlamePoint(1, MathUtils.random(getRadius() / 3f, getRadius()), 180);
        setFlamePoint(2, getRadius() / 3f, 210);
    }

    private void setFlamePoint(int point, float length, float angleDegrees) {
        Vector2 offset = MyHelpers.polarVectorRadians(length, (angleDegrees * MathUtils.degreesToRadians) + this.angle);
        if(flame[point] != null) {
            flame[point].set(getX() + offset.x, getY() + offset.y);
        } else {
            flame[point] = new Vector2(getX() + offset.x, getY() + offset.y);
        }
    }

    @Override
    public void update(float dt) {
        applyVelocity(dt);
        wrapPosition(0, Asteroids.WORLD_WIDTH, 0, Asteroids.WORLD_HEIGHT);
        setShape();

    }

    @Override
    public void draw(float dt, ShapeRenderer sr) {
        sr.setColor(SHIP_COLOR);
        drawPoints(sr);
        if(isThrusting) {
            sr.setColor(THRUST_COLOR);
            AbstractEntity.drawVector(flame, sr, false);
        }
        sr.setColor(Color.WHITE);
    }

}
