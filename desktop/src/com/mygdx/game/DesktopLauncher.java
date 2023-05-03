package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(144);
		config.setWindowedMode(1600,1000);
		config.setResizable(false);
		config.setTitle("SlidePuzzle");
		new Lwjgl3Application(new SlidePuzzle(), config);
	}
}
