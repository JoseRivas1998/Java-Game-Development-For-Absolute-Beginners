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

    private boolean onGround;

    private EntityEvent<Player> shootEvent;

    public Player(World world, Vector2 spawnPoint) {
        super();
        initAnim();
        initB2DBody(world, spawnPoint);
        stateTime = 0;
        onGround = true;
        shootEvent = null;
    }

    private void initAnim() {
        TextureRegion[] frames = MyHelpers.splitSpriteSheet(Platformer.content.getTexture(ContentManager.Image.PINK_ALIEN_WALK), 1, 2)[0];
        walkAnim = new Animation<TextureRegion>(PLAYER_ANIM_DURATION, frames);
        idle = Platformer.content.getTextureRegion(ContentManager.Image.PINK_ALIEN_STAND);
        jump = Platformer.content.getTextureRegion(ContentManager.Image.PINK_ALIEN_JUMP);
        setImage(idle, true);
    }

    @Override
    protected void initB2DBody(World world, Vector2 spawnPoint) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(spawnPoint);
        body = world.createBody(bodyDef);
        body.setUserData(this);

        float bodyWidth = (imageWidth * METERS_PER_PIXEL * 0.5f) * 0.75f;
        float bodyHeight = (imageHeight * METERS_PER_PIXEL * 0.5f) * 0.9f;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(bodyWidth, bodyHeight);

        // Main body fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = PhysicsLayers.PLAYER;
        fixtureDef.filter.maskBits = PhysicsLayers.GROUND | PhysicsLayers.COIN;
        body.createFixture(fixtureDef);
        shape.dispose();

        // Foot fixture
        shape = new PolygonShape();
        shape.setAsBox(
                bodyWidth * 0.9f, PLAYER_FOOT_HEIGHT * METERS_PER_PIXEL,
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
        if (MyInput.keyCheckPressed(MyInput.JUMP) && isOnGround()) {
            Platformer.content.playSound(ContentManager.SoundEffect.JUMP);
            body.applyForceToCenter(0, PLAYER_JUMP_FORCE, true);
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
        if(MyInput.keyCheckPressed(MyInput.SHOOT) && shootEvent != null) {
            shootEvent.accept(this);
        }
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public void setShootEvent(EntityEvent<Player> shootEvent) {
        this.shootEvent = shootEvent;
    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        stateTime += dt;
        if(onGround) {
            if(Float.compare(body.getLinearVelocity().x, 0) == 0) {
                setImage(idle);
            } else {
                setImage(walkAnim.getKeyFrame(stateTime, true));
            }
        } else {
            setImage(jump);
        }
        setSize(imageWidth, imageHeight);
        centerOrigin(true);
        super.draw(dt, sb, sr);
    }

    @Override
    public void dispose() {

    }
}
