package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Jetrix;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Jetrix.WIDTH;
		config.height = Jetrix.HEIGHT;
		config.title = Jetrix.TITLE;
		new LwjglApplication(new Jetrix(), config);
	}
}
