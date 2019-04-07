package com.tcg.platformer.entities;

import com.badlogic.gdx.physics.box2d.Body;

public abstract class AbstractB2DSpriteEntity extends AbstractSpriteEntity {

    protected Body body;

    public Body getBody() {
        return body;
    }
}
