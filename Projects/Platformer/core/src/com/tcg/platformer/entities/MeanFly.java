package com.tcg.platformer.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.tcg.platformer.GameData;
import com.tcg.platformer.MyHelpers;
import com.tcg.platformer.Platformer;
import com.tcg.platformer.managers.ContentManager;

import static com.tcg.platformer.GameData.*;

public class MeanFly extends AbstractB2DSpriteEntity {

    private float stateTime;
    private Animation<TextureRegion> animation;
    private Vector2 initialPoint;

    public MeanFly(World world, Vector2 spawnPoint) {
        super();
        initAnim();
        initB2DBody(world, spawnPoint);
    }

    private void initAnim() {
        TextureRegion[] frames = MyHelpers.splitSpriteSheet(Platformer.content.getTexture(ContentManager.Image.FLY), 1, 2)[0];
        stateTime = 0;
        animation = new Animation<TextureRegion>(FLY_ANIM_DURATION, frames);
        setImage(frames[0]);
        scaleX = MathUtils.randomBoolean() ? 1 : -1;
        centerOrigin();
    }

    @Override
    protected void initB2DBody(World world, Vector2 spawnPoint) {
        initialPoint = spawnPoint.cpy();
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.gravityScale = 0;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(spawnPoint);
        bodyDef.linearVelocity.set(FLY_SPEED * scaleX * -1, 0);
        body = world.createBody(bodyDef);
        body.setUserData(this);

        float hWidth = (imageWidth * METERS_PER_PIXEL) * 0.5f;
        float hHeight = (imageHeight * METERS_PER_PIXEL) * 0.5f;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(hWidth, hHeight);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = PhysicsLayers.FLY;
        fixtureDef.filter.maskBits = PhysicsLayers.PLAYER | PhysicsLayers.LASER;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(B2DUserData.FLY);
        shape.dispose();

    }

    @Override
    public void update(float dt) {
        Vector2 velocity = body.getLinearVelocity().cpy();
        velocity.y = MathUtils.sin(stateTime);
        if (MyHelpers.dist(body.getPosition(), initialPoint) > 5) {
            velocity.x *= -1;
            scaleX *= -1;
        }
        body.setLinearVelocity(velocity);
        super.update(dt);
    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        stateTime += dt;
        setImage(animation.getKeyFrame(stateTime, true));
        super.draw(dt, sb, sr);
    }

    @Override
    public void dispose() {

    }
}
