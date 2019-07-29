package com.tcg.asteroids.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tcg.asteroids.Asteroids;
import com.tcg.asteroids.MyHelpers;
import com.tcg.asteroids.managers.ContentManager;

public class Enemy extends AbstractEntity {

    public enum EnemyType {
        SMALL(150.0f, 12.0f, ContentManager.SoundEffect.SMALL_ENEMY),
        LARGE(100.0f, 20.0f, ContentManager.SoundEffect.LARGE_ENEMY);
        public final float speed;
        public final float radius;
        public final ContentManager.SoundEffect sound;

        EnemyType(float speed, float radius, ContentManager.SoundEffect sound) {
            this.speed = speed;
            this.radius = radius;
            this.sound = sound;
        }
    }

    private EnemyType type;
    private boolean goUp;
    private boolean goRight;
    private Vector2 originalPosition;
    private static final float[] DISTANCES = {
            Asteroids.WORLD_WIDTH * 2.0f / 3.0f,
            Asteroids.WORLD_WIDTH / 3.0f
    };

    private static final Color ENEMY_COLOR = Color.GOLD;

    public Enemy() {
        super();
        type = MyHelpers.choose(EnemyType.class);
        originalPosition = new Vector2();
        goRight = MathUtils.randomBoolean();
        if(goRight) {
            originalPosition.x = 0;
        } else {
            originalPosition.x = Asteroids.WORLD_WIDTH;
        }
        originalPosition.y = MathUtils.random((float) Asteroids.WORLD_HEIGHT);
        goUp = originalPosition.y < Asteroids.WORLD_WIDTH * 0.5f;
        setPosition(originalPosition);
        this.initializeVertices(6);
        setRadius(type.radius);
        setShape();
        Asteroids.content.loopSound(type.sound);
    }

    @Override
    protected void setShape() {
        setPointOffset(0, getRadius() / 3.0f, -getRadius() * 0.5f);
        setPointOffset(1, getRadius(), 0);
        setPointOffset(2, getRadius() / 3.0f, getRadius() * 0.5f);
        setPointOffset(3, -getRadius() / 3.0f, getRadius() * 0.5f);
        setPointOffset(4, -getRadius(), 0);
        setPointOffset(5, -getRadius() / 3.0f, -getRadius() * 0.5f);
    }

    public EnemyType getType() {
        return type;
    }

    @Override
    public void update(float dt) {
        calculateVelocity();
        applyVelocity(dt);
        setShape();
    }

    private void calculateVelocity() {
        float distance = MyHelpers.dist(getPosition(), originalPosition);
        if(distance > DISTANCES[0]) {
            setVelocityY(0);
            setVelocityX(goRight ? type.speed : -type.speed);
        } else if(distance > DISTANCES[1]) {
            float angleDeg;
            if(goUp) {
                angleDeg = goRight ? 45.0f : 135.0f;
            } else {
                angleDeg = goRight ? 315.0f : 225.0f;
            }
            setVelocity(MyHelpers.polarVectorDegrees(type.speed, angleDeg));
        } else {
            setVelocityY(0);
            setVelocityX(goRight ? type.speed : -type.speed);
        }
    }

    @Override
    public void draw(float dt, ShapeRenderer sr) {
        sr.setColor(ENEMY_COLOR);
        drawPoints(sr);
        sr.line(getVertex(1), getVertex(4));
        sr.setColor(Color.WHITE);
    }
}
