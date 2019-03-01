package com.rear_admirals.york_pirates.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.rear_admirals.york_pirates.PirateGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "York Pirates!";
		config.addIcon("icon128.png", Files.FileType.Internal);
		config.addIcon("icon32.png", Files.FileType.Internal);
		config.addIcon("icon16.png", Files.FileType.Internal);
		config.foregroundFPS = 60;
		config.backgroundFPS = config.foregroundFPS;
		config.forceExit = false;

		config.setFromDisplayMode(LwjglApplicationConfiguration.getDesktopDisplayMode());
		boolean fullscreen = false; // Set this to true to enable fullscreen by default.
		if (!fullscreen) {
			config.fullscreen = false;
			config.width *= 0.95f;
			config.height *= 0.95f;
			if ((config.width / 16) * 9 > config.height) {
				config.width = (config.height / 9) * 16;
			} else { config.height = (config.width / 16) * 9; }
		} else {
			config.fullscreen = true;
		}

		new LwjglApplication(new PirateGame(), config);
	}
}
