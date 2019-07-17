package com.tcg.asteroids.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tcg.asteroids.Asteroids;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Asteroids.WORLD_WIDTH;
		config.height = Asteroids.WORLD_HEIGHT;
		config.foregroundFPS = 0;
		new LwjglApplication(new Asteroids(), config);
	}
}
