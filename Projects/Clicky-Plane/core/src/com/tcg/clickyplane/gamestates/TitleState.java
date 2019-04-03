package com.tcg.clickyplane.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tcg.clickyplane.ClickyPlane;
import com.tcg.clickyplane.MyHelpers;
import com.tcg.clickyplane.entities.Ground;
import com.tcg.clickyplane.entities.Plane;
import com.tcg.clickyplane.managers.ContentManager;
import com.tcg.clickyplane.managers.GameStateManager;
import com.tcg.clickyplane.managers.input.MyInput;

public class TitleState extends AbstractGameState {

    public static final String TITLE = "Clicky Plane";

    private Viewport viewPort;
    private Animation<TextureRegion> tapAnim;
    private Texture background;

    private Rectangle titleText;
    private Rectangle tapAnimRect;

    private Plane plane;
    private Ground ground;

    private float stateTime;

    public TitleState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    protected void init() {
        viewPort = new FitViewport(ClickyPlane.WORLD_WIDTH, ClickyPlane.WORLD_HEIGHT);
        background = ClickyPlane.content.getTexture(ContentManager.Image.BACKGROUND);
        TextureRegion[] frames = MyHelpers.splitSpriteSheet(ClickyPlane.content.getTexture(ContentManager.Image.TAP_ANIMATION), 1, 2)[0];
        tapAnim = new Animation<TextureRegion>(0.5f, frames);
        titleText = new Rectangle();

        titleText.width = ClickyPlane.content.getWidth(ContentManager.Font.SCORE, TITLE);
        titleText.height = ClickyPlane.content.getHeight(ContentManager.Font.SCORE, TITLE);
        titleText.x = (ClickyPlane.WORLD_WIDTH * .5f) - (titleText.width * .5f);
        titleText.y = (ClickyPlane.WORLD_HEIGHT * .75f) + (titleText.height * .5f);

        tapAnimRect = new Rectangle();
        tapAnimRect.width = frames[0].getRegionWidth();
        tapAnimRect.height = frames[1].getRegionHeight();
        tapAnimRect.x = (ClickyPlane.WORLD_WIDTH * .5f) - (tapAnimRect.width * .5f);
        tapAnimRect.y = (ClickyPlane.WORLD_HEIGHT * .25f) - (tapAnimRect.height * .5f);
        stateTime = 0;

        plane = new Plane();
        ground = new Ground();

    }

    @Override
    public void handleInput(float dt) {
        if (MyInput.keyCheckPressed(MyInput.CLICK)) {
            switchState(GameStateType.PLAY);
        }
        if(MyInput.keyCheckPressed(MyInput.BACK)) {
            Gdx.app.exit();
        }
    }

    @Override
    public void update(float dt) {
        ground.update(dt);
        stateTime += dt;
        viewPort.apply(true);
    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {

        sb.begin();
        sb.draw(background, 0, 0, ClickyPlane.WORLD_WIDTH, ClickyPlane.WORLD_HEIGHT);
        sb.draw(tapAnim.getKeyFrame(stateTime, true), tapAnimRect.x, tapAnimRect.y);
        plane.draw(dt, sb, sr);
        ground.draw(dt, sb, sr);
        ClickyPlane.content.getFont(ContentManager.Font.SCORE).draw(sb, TITLE, titleText.x, titleText.y);
        sb.end();

    }

    @Override
    public void resize(int width, int height) {
        viewPort.update(width, height, true);
    }

    @Override
    public void dispose() {

    }
}
