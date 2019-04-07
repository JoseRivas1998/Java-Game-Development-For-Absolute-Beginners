package com.tcg.platformer.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.tcg.platformer.GameData;
import com.tcg.platformer.MyHelpers;
import com.tcg.platformer.Platformer;
import com.tcg.platformer.managers.ContentManager;
import com.tcg.platformer.managers.input.MyInput;

import static com.tcg.platformer.GameData.*;

public class Player extends AbstractB2DSpriteEntity {

    private Animation<TextureRegion> walkAnim;
    private TextureRegion idle;
    private TextureRegion jump;

    private float stateTime;

    public Player(World world, Vector2 spawnPoint) {
        super();
        initAnim();
        initB2DBody(world, spawnPoint);
        stateTime = 0;
    }

    private void initAnim() {
        TextureRegion[] frames = MyHelpers.splitSpriteSheet(Platformer.content.getTexture(ContentManager.Image.PINK_ALIEN_WALK), 1, 2)[0];
        walkAnim = new Animation<TextureRegion>(PLAYER_ANIM_DURATION, frames);
        idle = Platformer.content.getTextureRegion(ContentManager.Image.PINK_ALIEN_STAND);
        jump = Platformer.content.getTextureRegion(ContentManager.Image.PINK_ALIEN_JUMP);
        setImage(idle, true);
    }

    private void initB2DBody(World world, Vector2 spawnPoint) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(spawnPoint);
        body = world.createBody(bodyDef);

        float bodyWidth = (imageWidth * METERS_PER_PIXEL * 0.5f) * 0.9f;
        float bodyHeight = (imageHeight * METERS_PER_PIXEL * 0.5f) * 0.9f;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(bodyWidth, bodyHeight);

        // Main body fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = GameData.PhysicsLayers.PLAYER;
        fixtureDef.filter.maskBits = GameData.PhysicsLayers.GROUND;
        body.createFixture(fixtureDef);
        shape.dispose();

        // Foot fixture
        shape = new PolygonShape();
        shape.setAsBox(
                (imageWidth * METERS_PER_PIXEL * 0.5f) * 0.9f, PLAYER_FOOT_HEIGHT * METERS_PER_PIXEL,
                new Vector2(0, -bodyHeight - (PLAYER_FOOT_HEIGHT * METERS_PER_PIXEL * 0.5f)),
                0
        );
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = PhysicsLayers.PLAYER;
        fixtureDef.filter.maskBits = PhysicsLayers.GROUND;
        fixtureDef.isSensor = true;
        Fixture foot = body.createFixture(fixtureDef);
        foot.setUserData(B2DUserData.PLAYER_FOOT);
        shape.dispose();

    }

    public void handleInput() {
        if (MyInput.keyCheckPressed(MyInput.JUMP)) {
            body.applyForceToCenter(0, 500, true);
        }
        if (MyInput.keyCheck(MyInput.LEFT)) {
            body.setLinearVelocity(-PLAYER_SPEED, body.getLinearVelocity().y);
            scaleX = -1;
        } else if (MyInput.keyCheck(MyInput.RIGHT)) {
            body.setLinearVelocity(PLAYER_SPEED, body.getLinearVelocity().y);
            scaleX = 1;
        } else {
            body.setLinearVelocity(0, body.getLinearVelocity().y);
        }
    }

    @Override
    public void update(float dt) {
        setPosition(
                (body.getPosition().x * PIXELS_PER_METER) - (imageWidth * .5f),
                (body.getPosition().y * PIXELS_PER_METER) - (imageHeight * .5f)
        );
        setSize(imageWidth, imageHeight);
        centerOrigin(true);
    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        super.draw(dt, sb, sr);
    }

    @Override
    public void dispose() {

    }
}
