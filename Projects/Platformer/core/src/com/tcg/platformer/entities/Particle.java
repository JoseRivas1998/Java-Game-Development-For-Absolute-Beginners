package com.tcg.platformer.entities;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.tcg.platformer.MyHelpers;
import com.tcg.platformer.Platformer;
import com.tcg.platformer.managers.ContentManager;

import static com.tcg.platformer.GameData.*;

public class Particle extends AbstractB2DSpriteEntity {

    public Particle(World world, Vector2 spawnPoint) {
        super();
        setImage(Platformer.content.getTexture(ContentManager.Image.PARTICLE), true);
        centerOrigin();
        initB2DBody(world, spawnPoint);
    }

    @Override
    protected void initB2DBody(World world, Vector2 spawnPoint) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(spawnPoint);
        float angle = MathUtils.random(MathUtils.PI2);
        bodyDef.linearVelocity.set(MyHelpers.polarVectorRadians(PARTICLE_SPEED, angle));
        bodyDef.angularVelocity = PARTICLE_ANGLUAR_SPEED * MyHelpers.sign(bodyDef.linearVelocity.x) * -1;
        body = world.createBody(bodyDef);
        body.setUserData(this);

        float hWidth = (imageWidth * METERS_PER_PIXEL) * 0.5f;
        float hHeight = (imageHeight * METERS_PER_PIXEL) * 0.5f;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(hWidth, hHeight);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.maskBits = 0;
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);


    }

    @Override
    public void update(float dt) {
        this.imageAngle = body.getAngle() * MathUtils.radiansToDegrees;
        super.update(dt);
    }

    @Override
    public void dispose() {

    }
}
