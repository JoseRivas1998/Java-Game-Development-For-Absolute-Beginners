package com.tcg.platformer;

import com.badlogic.gdx.math.MathUtils;

public class GameData {

    // GRAPHICS
    public static final int WORLD_WIDTH = 1280;
    public static final int WORLD_HEIGHT = 720;

    public static final float PIXELS_PER_METER = 70f;
    public static final float METERS_PER_PIXEL = 1f / PIXELS_PER_METER;

    public static final boolean DEBUG = false;

    // PHYSICS CONSTANTS
    public static final float WORLD_STEP_PER_SECOND = 60f;
    public static final float TIME_STEP = 1f / WORLD_STEP_PER_SECOND;
    public static final float GRAVITY = -9.81f;
    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 2;

    public static final class PhysicsLayers {
        public static final short GROUND = 1 << 0;
        public static final short PLAYER = 1 << 1;
        public static final short COIN = 1 << 2;
        public static final short LASER = 1 << 3;
        public static final short FLY = 1 << 4;
        public static final short LEVEL_END = 1 << 5;
    }

    public enum B2DUserData {
        PLAYER_BODY, PLAYER_FOOT, COIN, LASER, FLY, LEVEL_END
    }

    // PLAYER CONSTANTS
    public static final float PLAYER_ANIM_DURATION = 0.15f; // s / frame
    public static final float PLAYER_FOOT_HEIGHT = 4f; // px
    public static final float PLAYER_SPEED = 5f; // m/s
    public static final float PLAYER_JUMP_FORCE = 500f; // N
    public static final float PLAYER_INVINCIBILITY = 0.3333f; // seconds
    public static final int PLAYER_MAX_HEALTH = 100;

    // LASER CONSTANTS
    public static final float LASER_ANIM_DURATION = 0.05f; // s / frame
    public static final float LASER_SPEED = 8f; // m/s
    public static final float LASER_ERROR = 0.1f; // m

    // FLY CONSTANTS
    public static final float FLY_SPEED = 0.75f; // m/s
    public static final float FLY_ANIM_DURATION = 0.15f; // s / frame

    // PARTICLE CONSTANTS
    public static final float PARTICLE_SPEED = 2; // m/s
    public static final float PARTICLE_ANGLUAR_SPEED = MathUtils.PI; // rads/s

}
