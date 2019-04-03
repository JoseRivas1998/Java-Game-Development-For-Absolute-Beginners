package com.tcg.clickyplane.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tcg.clickyplane.ClickyPlane;
import com.tcg.clickyplane.MyHelpers;
import com.tcg.clickyplane.managers.ContentManager;
import com.tcg.clickyplane.managers.input.MyInput;

public class Plane extends AbstractSpriteEntity {

    private static final float ANIM_SPEED = 0.05f;

    private static final float GRAVITY = 1000f;
    private static final float JUMP_SPEED = 500f;
    private static final float ANGLE_SMOOTHING = 7f;

    private Texture spriteSheet;
    private Animation<TextureRegion> anim;
    private float stateTime;

    public Plane() {
        super();
        spriteSheet = ClickyPlane.content.getTexture(ContentManager.Image.PLANE_SPRITESHEET);
        TextureRegion[][] planes = MyHelpers.splitSpriteSheet(spriteSheet, 4, 3);
        TextureRegion[] frames = MyHelpers.choose(planes);
        anim = new Animation<TextureRegion>(ANIM_SPEED, frames);
        anim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        stateTime = 0;
        setSize(frames[0].getRegionWidth(), frames[0].getRegionHeight());
        setCenter(ClickyPlane.WORLD_WIDTH * 0.15f, ClickyPlane.WORLD_HEIGHT * 0.5f);
    }

    public void resetImageAngle() {
        this.imageAngle = 0;
    }

    public void handleInput() {
        if (MyInput.keyCheckPressed(MyInput.CLICK) && getY() < ClickyPlane.WORLD_HEIGHT - getHeight()) {
            jump();
        }
    }

    public void jump() {
        ClickyPlane.content.playSound(ContentManager.SoundEffect.WING);
        setVelocityY(JUMP_SPEED);
    }

    @Override
    public void update(float dt) {
        setVelocityY(getVelocityY() - GRAVITY * dt);
        applyVelocity(dt);
    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        animate(dt);
        rotate();
        super.draw(dt, sb, sr);
    }

    private void rotate() {
        Vector2 simMovement = new Vector2(Pipe.PIPE_SPEED, getVelocityY());
        float targetAngle = simMovement.angle();
        float diff;
        if (getVelocityY() > 0) { // image angle should be below target angle
            if (imageAngle > targetAngle) { // only reason this would happen is angle is pointing down
                if (imageAngle > 270) {
                    diff = targetAngle + (360 - imageAngle);
                    imageAngle += diff / ANGLE_SMOOTHING;
                }
            } else {
                diff = targetAngle - imageAngle;
                imageAngle += diff / ANGLE_SMOOTHING;
                if (imageAngle > targetAngle) {
                    imageAngle = targetAngle;
                }
            }
        } else if (getVelocityY() < 0) { // Image angle should be above target angle
            if (imageAngle > targetAngle) {
                diff = imageAngle - targetAngle;
                imageAngle -= diff / ANGLE_SMOOTHING;
            } else { // target angle is pointing down
                diff = imageAngle + (360 - targetAngle);
                imageAngle -= diff / ANGLE_SMOOTHING;
            }
        }
        if (imageAngle < 0) {
            imageAngle += 360;
        }
        if (imageAngle > 360) {
            imageAngle -= 360;
        }
    }

    private void animate(float dt) {
        stateTime += dt;
        setImage(anim.getKeyFrame(stateTime));
        centerOrigin(true);
    }

    @Override
    public void dispose() {
    }
}
