package com.tcg.platformer;

public class GameData {

    // GRAPHICS
    public static final int WORLD_WIDTH = 1280;
    public static final int WORLD_HEIGHT = 720;

    public static final float PIXELS_PER_METER = 70f;
    public static final float METERS_PER_PIXEL = 1f / PIXELS_PER_METER;

    public static final boolean DEBUG = true;

    // PHYSICS CONSTANTS
    public static final float WORLD_STEP_PER_SECOND = 60f;
    public static final float TIME_STEP = 1f / WORLD_STEP_PER_SECOND;
    public static final float GRAVITY = -9.81f;
    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 2;

    public static final class PhysicsLayers {
        public static final short GROUND = 1 << 0;
        public static final short PLAYER = 1 << 1;
    }

}
