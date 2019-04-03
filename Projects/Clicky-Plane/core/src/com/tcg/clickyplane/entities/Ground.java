package com.tcg.clickyplane.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tcg.clickyplane.ClickyPlane;

public class Ground extends AbstractEntity {

    private Texture ground;
    private float drawX;

    public Ground() {
        setPosition(0, 0);
        ground = new Texture("img/ground_grass.png");
        setSize(ground.getWidth(), ground.getHeight());
        setVelocityX(-Pipe.PIPE_SPEED);
        drawX = 0;
    }

    @Override
    public void update(float dt) {
        drawX += getVelocityX() * dt;
        if (drawX < -ground.getWidth()) {
            drawX += ground.getWidth();
        }
    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        float x = drawX;
        while (x <= ClickyPlane.WORLD_WIDTH) {
            sb.draw(ground, x, 0);
            x += ground.getWidth();
        }
    }

    @Override
    public void dispose() {
        ground.dispose();
    }
}
