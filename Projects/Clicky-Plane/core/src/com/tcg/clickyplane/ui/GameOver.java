package com.tcg.clickyplane.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.tcg.clickyplane.ClickyPlane;
import com.tcg.clickyplane.MyHelpers;
import com.tcg.clickyplane.managers.ClickListener;
import com.tcg.clickyplane.managers.ContentManager;
import com.tcg.clickyplane.managers.input.MyInput;

public class GameOver implements Disposable {

    private Texture gameOver;
    private float gameOverAlpha;

    private final float GAME_OVER_SMOOTHING = 10f;
    private final float GAME_OVER_DIFF = 25f;

    private final float PADDING = 10f;

    private Vector2 gameOverPos;
    private float gameOverTarget;

    private Rectangle infoBoard;
    private float infoBoardTargetY;
    private Texture infoBoardTexture;
    private NinePatch infoBoardPatch;

    private float scoreLabelHeight;
    private float scoreY;
    private float medalLabelHeight;

    private Texture medals;
    private TextureRegion toDraw;
    private TextureRegion[] medalRegions;

    private final int[] SCORE_THRESHOLDS = {
            0, 10, 20, 30
    };

    private int score;

    private Texture button;
    private NinePatch buttonPatch;
    private Rectangle backButton;
    private Rectangle replayButton;
    private Texture back;
    private Texture replay;
    private Vector2 backIconPos;
    private Vector2 replayIconPos;
    private ClickListener onBack;
    private ClickListener onReplay;

    public GameOver() {
        createInfoBoard();
        createGameOverText();
        createMedals();
        createButtons();
    }

    private void createButtons() {
        button = new Texture("img/button.png");
        buttonPatch = new NinePatch(button, 45, 45, 26,27);
        back = new Texture("img/back.png");
        replay = new Texture("img/replay.png");
        backButton = new Rectangle();
        replayButton = new Rectangle();
        backButton.x = infoBoard.x + PADDING;
        backButton.y = infoBoard.y + PADDING;
        backButton.height = Math.max(back.getHeight(), replay.getHeight()) + (PADDING * 3);
        backButton.width = (infoBoard.width - (PADDING * 3)) / 2f;

        replayButton.x = backButton.x + backButton.width + PADDING;
        replayButton.y = backButton.y;
        replayButton.width = backButton.width;
        replayButton.height = backButton.height;

        backIconPos = new Vector2();
        backIconPos.x = (backButton.x + (backButton.width * .5f)) - (back.getWidth() * .5f);
        replayIconPos = new Vector2();
        replayIconPos.x = (replayButton.x + (replayButton.width * .5f)) - (replay.getWidth() * .5f);
    }

    private void createMedals() {
        medals = new Texture("img/medals.png");
        medalRegions = MyHelpers.splitSpriteSheet(medals, 1, 4)[0];
        toDraw = medalRegions[0];
    }

    private void createInfoBoard() {
        infoBoardTexture = new Texture("img/ui_bg.png");
        infoBoardPatch = new NinePatch(infoBoardTexture, 88, 88, 88, 88);

        infoBoard = new Rectangle();
        infoBoard.width = ClickyPlane.WORLD_WIDTH * .5f;
        infoBoard.height = ClickyPlane.WORLD_HEIGHT * .5f;
        infoBoard.x = ClickyPlane.WORLD_WIDTH * .5f - infoBoard.width * .5f;
        infoBoardTargetY = ClickyPlane.WORLD_HEIGHT * .5f - infoBoard.height * .5f;
        infoBoard.y = -infoBoard.height;
    }

    private void createGameOverText() {
        gameOver = new Texture("img/textGameOver.png");
        gameOverAlpha = 0;
        gameOverTarget = infoBoardTargetY + infoBoard.height + PADDING;
        gameOverPos = new Vector2((ClickyPlane.WORLD_WIDTH * .5f) - (gameOver.getWidth() * .5f), gameOverTarget - GAME_OVER_DIFF);
    }

    public void handleInput(Vector2 mouseUnprojected) {
        if(MyInput.keyCheckPressed(MyInput.CLICK)) {
            if(backButton.contains(mouseUnprojected)) {
                onBack.onClick();
            }
            if(replayButton.contains(mouseUnprojected)) {
                onReplay.onClick();
            }
        }
    }

    public void reset() {
        infoBoard.y = -infoBoard.height;
        gameOverPos = new Vector2((ClickyPlane.WORLD_WIDTH * .5f) - (gameOver.getWidth() * .5f), gameOverTarget - GAME_OVER_DIFF);
        gameOverAlpha = 0;

    }

    public void update(float dt) {
        gameOverAlpha += (1 - gameOverAlpha) / GAME_OVER_SMOOTHING;
        gameOverPos.y += (gameOverTarget - gameOverPos.y) / GAME_OVER_SMOOTHING;

        infoBoard.y += (infoBoardTargetY - infoBoard.y) / GAME_OVER_SMOOTHING;

        scoreLabelHeight = ClickyPlane.content.getHeight(ContentManager.Font.GAMEOVER_LABEL, "SCORE");
        scoreY = infoBoard.y + infoBoard.height - (PADDING * 2) - scoreLabelHeight - PADDING;

        medalLabelHeight = ClickyPlane.content.getHeight(ContentManager.Font.GAMEOVER_LABEL, "MEDAL");

        for (int i = SCORE_THRESHOLDS.length - 1; i >= 0; i--) {
            if(score >= SCORE_THRESHOLDS[i]) {
                toDraw = medalRegions[i];
                break;
            }
        }

        backButton.y = infoBoard.y + PADDING;
        replayButton.y = backButton.y;

        backIconPos.y = backButton.y + (backButton.height * .5f) - (back.getHeight() * .5f);
        replayIconPos.y = replayButton.y + (replayButton.height * .5f) - (replay.getHeight() * .5f);

    }

    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        sb.setColor(1, 1, 1, gameOverAlpha);
        sb.draw(gameOver, gameOverPos.x, gameOverPos.y);

        infoBoardPatch.draw(sb, infoBoard.x, infoBoard.y, infoBoard.width, infoBoard.height);

        ClickyPlane.content.getFont(ContentManager.Font.GAMEOVER_LABEL).draw(
                sb, "SCORE",
                infoBoard.x + infoBoard.width - (PADDING * 2) - ClickyPlane.content.getWidth(ContentManager.Font.GAMEOVER_LABEL, "SCORE"),
                infoBoard.y + infoBoard.height - (PADDING * 2)
        );

        ClickyPlane.content.getFont(ContentManager.Font.GAMEOVER_SCORE).draw(
                sb, String.valueOf(score),
                infoBoard.x + infoBoard.width - (PADDING * 2) - ClickyPlane.content.getWidth(ContentManager.Font.GAMEOVER_SCORE, String.valueOf(score)),
                scoreY
        );

        ClickyPlane.content.getFont(ContentManager.Font.GAMEOVER_LABEL).draw(
                sb, "MEDAL",
                infoBoard.x + (PADDING * 2),
                infoBoard.y + infoBoard.height - (PADDING * 2)
        );

        sb.draw(toDraw, infoBoard.x + (PADDING * 2), infoBoard.y + infoBoard.height - (PADDING * 2) - medalLabelHeight - (PADDING * 2) - toDraw.getRegionHeight());

        buttonPatch.draw(sb, backButton.x, backButton.y, backButton.width, backButton.height);
        buttonPatch.draw(sb, replayButton.x, replayButton.y, replayButton.width, replayButton.height);
        sb.draw(back, backIconPos.x, backIconPos.y);
        sb.draw(replay, replayIconPos.x, replayIconPos.y);

        sb.setColor(1, 1, 1, 1);
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setOnBack(ClickListener onBack) {
        this.onBack = onBack;
    }

    public void setOnReplay(ClickListener onReplay) {
        this.onReplay = onReplay;
    }

    @Override
    public void dispose() {
        gameOver.dispose();
        infoBoardTexture.dispose();
        medals.dispose();
        button.dispose();
        back.dispose();
        replay.dispose();
    }

}