package com.tcg.asteroids.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends AbstractEntity {

    public enum BulletType {
        PLAYER(Color.RED), ENEMY(Color.GREEN);
        final Color color;

        BulletType(Color color) {
            this.color = color;
        }
    }

    private static final float BULLET_SPEED = 600f;
    private static final float BULLET_RADIUS = 1f;

    private BulletType bulletType;

    public Bullet(Ship ship) {
        this(ship.getPosition(), ship.getAngle(), BulletType.PLAYER);
    }

    public Bullet(Vector2 pos, float angle, BulletType type) {
        super();
        this.bulletType = type;
        this.angle = angle;
        this.setPosition(pos);
        this.setRadius(BULLET_RADIUS);
        this.setVelocityPolar(BULLET_SPEED, this.angle);
        this.initializeVertices(4);
        this.setShape();
    }

    @Override
    protected void setShape() {
        setSquare();
    }

    @Override
    public void update(float dt) {
        this.applyVelocity(dt);
        this.setShape();
    }

    @Override
    public void draw(float dt, ShapeRenderer sr) {
        sr.setColor(this.bulletType.color);
        this.drawPoints(sr);
        sr.setColor(Color.WHITE);
    }
}
