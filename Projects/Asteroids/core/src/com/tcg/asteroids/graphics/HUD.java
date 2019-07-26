package com.tcg.asteroids.graphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tcg.asteroids.Asteroids;
import com.tcg.asteroids.entities.Asteroid;
import com.tcg.asteroids.entities.LabelEntity;
import com.tcg.asteroids.managers.ContentManager;

public class HUD {

    private static final float PADDING = 10f;

    private LabelEntity scoreLabel;
    private LabelEntity livesLabel;
    private LabelEntity speedLabel;

    public HUD() {
        scoreLabel = new LabelEntity();
        scoreLabel.setFont(ContentManager.Font.MAIN);

        livesLabel = new LabelEntity();
        livesLabel.setFont(ContentManager.Font.MAIN);

        speedLabel = new LabelEntity();
        speedLabel.setFont(ContentManager.Font.MAIN);
    }

    public void update(int score, int lives, float speed) {
        scoreLabel.setText(String.format("Score: %03d", score));
        livesLabel.setText(String.format("Lives: %d", lives));
        speedLabel.setText(getSpeedString(speed));

        scoreLabel.setPosition(PADDING, Asteroids.WORLD_HEIGHT - PADDING);
        scoreLabel.update();

        livesLabel.setUnder(scoreLabel, PADDING);
        livesLabel.update();

        speedLabel.setUnder(livesLabel, PADDING);
        speedLabel.update();

    }

    public void draw(SpriteBatch sb) {
        sb.begin();
        scoreLabel.draw(sb);
        livesLabel.draw(sb);
        speedLabel.draw(sb);
        sb.end();
    }

    private String getSpeedString(float speed) {
        String unit = "m/s";
        float displaySpeed = speed;
        if(speed >= 1000) {
            displaySpeed /= 1000f;
            unit = "km/s";
        }
        return String.format("%.2f %s", displaySpeed, unit);
    }

}
