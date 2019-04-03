package com.tcg.clickyplane.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tcg.clickyplane.ClickyPlane;

import java.io.File;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = ClickyPlane.WORLD_WIDTH;
        config.height = ClickyPlane.WORLD_HEIGHT;
        new LwjglApplication(new ClickyPlane(), config);
    }
}
