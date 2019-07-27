package com.tcg.asteroids.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.tcg.asteroids.Asteroids;
import com.tcg.asteroids.MyHelpers;

public class Asteroid extends AbstractEntity {

    public enum AsteroidType {
        LARGE(100.0f,
                45 * MathUtils.degreesToRadians,
                40.0f,
                12),
        MEDIUM(125.0f,
                90 * MathUtils.degreesToRadians,
                20.0f,
                10),
        SMALL(150.0f,
                180 * MathUtils.degreesToRadians,
                12.0f,
                8),
        ;
        public final float speed;
        public final float maxRotationSpeed;
        public final float radius;
        public final int numPoints;

        AsteroidType(float speed, float maxRotationSpeed, float radius, int numPoints) {
            this.speed = speed;
            this.maxRotationSpeed = maxRotationSpeed;
            this.radius = radius;
            this.numPoints = numPoints;
        }
    }

    private AsteroidType asteroidType;
    private float rotationSpeed;
    private int numPoints;
    private float[] distances;
    private static final Color ASTEROID_COLOR = new Color(0xD2D2D2FF);

    public Asteroid(float x, float y, AsteroidType type) {
        super();
        asteroidType = type;
        setVelocity(MyHelpers.randomVector(asteroidType.speed));
        rotationSpeed = MathUtils.random(-asteroidType.maxRotationSpeed, asteroidType.maxRotationSpeed);
        angle = 0;
        setPosition(x, y);
        setRadius(asteroidType.radius);
        this.numPoints = asteroidType.numPoints;
        distances = new float[this.numPoints];
        for (int i = 0; i < this.numPoints; i++) {
            distances[i] = MathUtils.random(getRadius() * 0.5f, getRadius());
        }
        initializeVertices(this.numPoints);
        setShape();
    }

    public Asteroid(float x, float y) {
        this(x, y, MyHelpers.choose(AsteroidType.class));
    }

    public AsteroidType getAsteroidType() {
        return asteroidType;
    }

    @Override
    protected void setShape() {
        for (int i = 0; i < numPoints; i++) {
            float pointAngle = (i / (float) this.numPoints) * MathUtils.PI2;
            setPointRadians(i, distances[i], pointAngle);
        }
    }

    @Override
    public void update(float dt) {
        applyVelocity(dt);
        wrapPosition(0, Asteroids.WORLD_WIDTH, 0, Asteroids.WORLD_HEIGHT);
        angle += rotationSpeed * dt;
        setShape();
    }

    @Override
    public void draw(float dt, ShapeRenderer sr) {
        sr.setColor(ASTEROID_COLOR);
        drawPoints(sr);
        sr.setColor(Color.WHITE);
    }
}
