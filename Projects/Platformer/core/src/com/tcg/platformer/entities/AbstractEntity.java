package com.tcg.platformer.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public abstract class AbstractEntity implements Disposable, Comparable<AbstractEntity> {

    public static final String TAG = AbstractEntity.class.getSimpleName();

    private static int entityCount = 0;

    private Rectangle bounds;
    private int id;

    public AbstractEntity() {
        this.bounds = new Rectangle();
        AbstractEntity.entityCount++;
        this.id = AbstractEntity.entityCount;
        Gdx.app.debug(TAG, "Created entity: " + this.toString());
    }

    public abstract void update(float dt);

    public abstract void draw(float dt, SpriteBatch sb, ShapeRenderer sr);

    public float getX() {
        return this.bounds.x;
    }

    public void setX(float x) {
        this.bounds.x = x;
    }

    public float getY() {
        return this.bounds.y;
    }

    public void setY(float y) {
        this.bounds.y = y;
    }

    public Vector2 getPosition() {
        return new Vector2(getX(), getY());
    }

    public void setPosition(float x, float y) {
        setX(x);
        setY(y);
    }

    public void setPosition(Vector2 point) {
        setPosition(point.x, point.y);
    }

    public float getCenterX() {
        return getX() + (getWidth() * .5f);
    }

    public void setCenterX(float x) {
        setX(x - (getWidth() * .5f));
    }

    public float getCenterY() {
        return getY() + (getHeight() * .5f);
    }

    public void setCenterY(float y) {
        setY(y - (getHeight() * .5f));
    }

    public void setCenter(float x, float y) {
        setCenterX(x);
        setCenterY(y);
    }

    public void setCenter(Vector2 center) {
        setCenter(center.x, center.y);
    }

    public Vector2 getCenter() {
        return new Vector2(getCenterX(), getCenterY());
    }

    public float getWidth() {
        return this.bounds.width;
    }

    public void setWidth(float width) {
        this.bounds.width = width;
    }

    public float getHeight() {
        return this.bounds.height;
    }

    public void setHeight(float height) {
        this.bounds.height = height;
    }

    public Vector2 getSize() {
        return new Vector2(getWidth(), getHeight());
    }

    public void setSize(float width, float height) {
        setWidth(width);
        setHeight(height);
    }

    public void setSize(Vector2 size) {
        setSize(size.x, size.y);
    }

    public boolean collidingWith(Rectangle rectangle) {
        return this.bounds.overlaps(rectangle);
    }

    public boolean collidingWith(AbstractEntity entity) {
        return collidingWith(entity.bounds);
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if (obj == null || obj.getClass() != this.getClass()) {
            result = false;
        } else {
            AbstractEntity other = (AbstractEntity) obj;
            result = this.id == other.id;
        }
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "@id=" + this.id;
    }

    @Override
    public int compareTo(AbstractEntity other) {
        return this.id - other.id;
    }
}
