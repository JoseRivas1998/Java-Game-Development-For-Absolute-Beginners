package com.tcg.clickyplane.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.tcg.clickyplane.ClickyPlane;
import com.tcg.clickyplane.MyHelpers;

public class GetReady implements Disposable {

    private static final float PADDING = 10f;
    private static final float ALHPA_SMOOTHING = 10f;

    private Texture getReady;
    private Texture tapAnimTexture;
    private Animation<TextureRegion> tapAnim;
    private Texture tapLeft;
    private Texture tapRight;

    public Vector2 getReadyPos;
    public Vector2 tapPos;
    public Vector2 tapLeftPos;
    public Vector2 tapRightPos;

    private float alpha;
    private float stateTime = 0;

    public GetReady() {
        getReady = new Texture("img/textGetReady.png");
        tapAnimTexture = new Texture("img/tapAnimation.png");
        TextureRegion[] tapAnimFrames = MyHelpers.splitSpriteSheet(tapAnimTexture, 1, 2)[0];
        tapAnim = new Animation<TextureRegion>(0.5f, tapAnimFrames);
        tapLeft = new Texture("img/tapLeft.png");
        tapRight = new Texture("img/tapRight.png");

        getReadyPos = new Vector2();
        getReadyPos.x = (ClickyPlane.WORLD_WIDTH * .5f) - (getReady.getWidth() * .5f);
        getReadyPos.y = (ClickyPlane.WORLD_HEIGHT * .75f) - (getReady.getHeight() * .5f);

        tapPos = new Vector2();
        tapPos.x = (ClickyPlane.WORLD_WIDTH * .5f) - (tapAnimFrames[0].getRegionWidth() * .5f);
        tapPos.y = (ClickyPlane.WORLD_HEIGHT * .5f) - (tapAnimFrames[0].getRegionHeight() * .5f);

        tapLeftPos = new Vector2();
        tapLeftPos.x = tapPos.x + tapAnimFrames[0].getRegionWidth() + PADDING;
        tapLeftPos.y = (tapPos.y + (tapAnimFrames[0].getRegionHeight() * .5f)) - (tapLeft.getHeight() * .5f);

        tapRightPos = new Vector2();
        tapRightPos.x = tapPos.x - tapRight.getWidth() - PADDING;
        tapRightPos.y = (tapPos.y + (tapAnimFrames[0].getRegionHeight() * .5f)) - (tapRight.getHeight() * .5f);

        resetAlpha();
    }

    public void resetAlpha() {
        alpha = 0;
        stateTime = 0;
    }

    public void update() {
        alpha += (1 - alpha) / ALHPA_SMOOTHING;
    }

    public void draw(float dt, SpriteBatch sb) {
        stateTime += dt;

        sb.setColor(1, 1, 1, alpha);
        sb.draw(getReady, getReadyPos.x, getReadyPos.y);
        sb.draw(tapAnim.getKeyFrame(stateTime, true), tapPos.x, tapPos.y);
        sb.draw(tapLeft, tapLeftPos.x, tapLeftPos.y);
        sb.draw(tapRight, tapRightPos.x, tapRightPos.y);
        sb.setColor(1, 1, 1, 1);
    }

    @Override
    public void dispose() {
        getReady.dispose();
        tapAnimTexture.dispose();
        tapLeft.dispose();
        tapRight.dispose();
    }
}
