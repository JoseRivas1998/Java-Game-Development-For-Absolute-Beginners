package com.tcg.platformer.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.tcg.platformer.GameData;
import com.tcg.platformer.Platformer;
import com.tcg.platformer.managers.ContentManager;

import static com.tcg.platformer.GameData.METERS_PER_PIXEL;
import static com.tcg.platformer.GameData.PIXELS_PER_METER;

public class Coin extends AbstractB2DSpriteEntity {

    public Coin(World world, Vector2 spawnPoint) {
        super();
        setImage(Platformer.content.getTextureRegion(ContentManager.Image.COIN), true);
        centerOrigin();
        initB2DBody(world, spawnPoint);
    }

    @Override
    protected void initB2DBody(World world, Vector2 spawnPoint) {
        BodyDef coinBody = new BodyDef();
        coinBody.fixedRotation = true;
        coinBody.type = BodyDef.BodyType.StaticBody;
        coinBody.position.set(spawnPoint);
        body = world.createBody(coinBody);
        body.setUserData(this);

        float radius = (imageWidth * METERS_PER_PIXEL) * 0.5f;

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(radius);

        FixtureDef coinFixtureDef = new FixtureDef();
        coinFixtureDef.isSensor = true;
        coinFixtureDef.shape = circleShape;
        coinFixtureDef.filter.categoryBits = GameData.PhysicsLayers.COIN;
        coinFixtureDef.filter.maskBits = GameData.PhysicsLayers.PLAYER;
        Fixture coinFixture = body.createFixture(coinFixtureDef);
        coinFixture.setUserData(GameData.B2DUserData.COIN);
        circleShape.dispose();
    }

    @Override
    public void dispose() {
        /*Empty*/
    }


}
