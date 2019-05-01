package com.tcg.platformer.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import static com.tcg.platformer.GameData.PIXELS_PER_METER;

public abstract class AbstractB2DSpriteEntity extends AbstractSpriteEntity {

    protected Body body;

    public Body getBody() {
        return body;
    }

    public void update(float dt) {
        setPosition(
                (body.getPosition().x * PIXELS_PER_METER) - (imageWidth * .5f),
                (body.getPosition().y * PIXELS_PER_METER) - (imageHeight * .5f)
        );
    }

    protected abstract void initB2DBody(World world, Vector2 spawnPoint);
}
