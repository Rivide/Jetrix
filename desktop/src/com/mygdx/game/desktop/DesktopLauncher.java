package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Config;
import com.mygdx.game.Jetrix;
import com.mygdx.game._Jetrix;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Config.width;
		config.height = Config.height;
		config.title = Config.title;
		new LwjglApplication(new Jetrix(), config);
	}
}
