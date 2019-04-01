package com.tcg.clickyplane;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public final class MyHelpers {

    public static Vector2 polarVectorRadians(float magnitude, float angle) {
        // Calculate rectangular x and y components
        float rectX = magnitude * MathUtils.cos(angle);
        float rectY = magnitude * MathUtils.sin(angle);
        return new Vector2(rectX, rectY);
    }

    public static Vector2 polarVectorDegrees(float magnitude, float angle) {
        return polarVectorRadians(magnitude, angle * MathUtils.degreesToRadians);
    }

    public static float magSquared(float x, float y) {
        return (x * x) + (y * y);
    }

    public static float magSquared(Vector2 vector2) {
        return magSquared(vector2.x, vector2.y);
    }

    public static float mag(float x, float y) {
        return (float) Math.sqrt(mag(x, y));
    }

    public static float mag(Vector2 vector2) {
        return mag(vector2.x, vector2.y);
    }

    public static float distSquared(float x1, float y1, float x2, float y2) {
        return magSquared(x2 - x1, y2 - y1);
    }

    public static float distSquared(Vector2 p1, Vector2 p2) {
        return distSquared(p1.x, p1.y, p2.x, p2.y);
    }

    public static float dist(float x1, float y1, float x2, float y2) {
        return mag(x2 - x1, y2 - y1);
    }

    public static float dist(Vector2 p1, Vector2 p2) {
        return dist(p1.x, p1.y, p2.x, p2.y);
    }

}
