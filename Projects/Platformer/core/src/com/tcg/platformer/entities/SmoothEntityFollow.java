package com.tcg.platformer.entities;

import com.badlogic.gdx.math.Vector2;

public class SmoothEntityFollow {

    private float smoothing;
    private Vector2 pos;
    private boolean followCenter;

    private AbstractEntity toFollow;
    private Vector2 target;

    public SmoothEntityFollow(float smoothing, Vector2 initialPos, boolean followCenter) {
        this.smoothing = smoothing;
        this.pos = new Vector2(initialPos);
        this.followCenter = followCenter;
        target = new Vector2();
        toFollow = null;
    }
    public void update() {
        if(toFollow == null) {
            return;
        }
        target.set(followCenter ? toFollow.getCenter() : toFollow.getPosition());
        pos.x += (target.x - pos.x) / smoothing;
        pos.y += (target.y - pos.y) / smoothing;
    }

    public Vector2 getPos() {
        return new Vector2(pos);
    }

    public void setPos(Vector2 pos) {
        this.pos.set(pos);
    }

    public void setFollowCenter(boolean followCenter) {
        this.followCenter = followCenter;
    }

    public void setToFollow(AbstractEntity toFollow) {
        this.toFollow = toFollow;
    }
}
