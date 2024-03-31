package me.adamix.colony;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import me.adamix.colony.client.Game;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(120);
		config.useVsync(false);
		config.setTitle("Colony");
		config.setWindowedMode(1600, 900);
		config.setResizable(false);
		new Lwjgl3Application(new Game(), config);
	}
}
