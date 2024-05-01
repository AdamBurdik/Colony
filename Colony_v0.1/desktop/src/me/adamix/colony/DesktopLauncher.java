package me.adamix.colony;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import me.adamix.colony.preferences.Preferences;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {

		Preferences.loadPreferences();

		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.useVsync(Preferences.vSync);
		config.setForegroundFPS(Preferences.maxFps);
		config.setTitle(Preferences.windowTitle + " " + Preferences.gameVersion);
		config.setWindowedMode(Preferences.windowWidth, Preferences.windowHeight);
		new Lwjgl3Application(new Game(), config);
	}
}
