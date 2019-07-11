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

import static com.tcg.platformer.GameData.LASER_SPEED;
import static com.tcg.platformer.GameData.METERS_PER_PIXEL;

public class Laser extends AbstractB2DSpriteEntity {

    private Animation<TextureRegion> animation;
    private float stateTime;

    public Laser(World world, AbstractB2DSpriteEntity source) {
        super();
        initAnim(source);
        initB2DBody(world, new Vector2(source.body.getPosition().x, source.body.getPosition().y + MathUtils.random(-GameData.LASER_ERROR, GameData.LASER_ERROR)));
    }

    private void initAnim(AbstractB2DSpriteEntity source) {
        TextureRegion[] frames = MyHelpers.splitSpriteSheet(Platformer.content.getTexture(ContentManager.Image.LASER), 1, 5)[0];
        animation = new Animation<TextureRegion>(GameData.LASER_ANIM_DURATION, frames);
        setImage(frames[0], true);
        setSize(imageWidth, imageHeight);
        this.scaleX = source.scaleX;
        stateTime = 0;
        centerOrigin();
    }

    @Override
    protected void initB2DBody(World world, Vector2 spawnPoint) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(spawnPoint);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.gravityScale = 0;
        bodyDef.fixedRotation = true;
        bodyDef.linearVelocity.set(LASER_SPEED * scaleX, 0);
        body = world.createBody(bodyDef);
        body.setUserData(this);

        float hWidth = (imageWidth * METERS_PER_PIXEL) * 0.5f;
        float hHeight = (imageHeight * METERS_PER_PIXEL) * 0.5f;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(hWidth, hHeight);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = GameData.PhysicsLayers.LASER;
        fixtureDef.filter.maskBits = GameData.PhysicsLayers.GROUND | GameData.PhysicsLayers.FLY;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(GameData.B2DUserData.LASER);

        shape.dispose();
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
