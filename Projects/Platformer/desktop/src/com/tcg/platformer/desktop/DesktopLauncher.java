package com.tcg.platformer.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tcg.platformer.GameData;
import com.tcg.platformer.Platformer;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = GameData.WORLD_WIDTH;
		config.height = GameData.WORLD_HEIGHT;
		config.vSyncEnabled = false;
		config.foregroundFPS = 0;
		new LwjglApplication(new Platformer(), config);
	}
}
