package me.adamix.colony.preferences;

import me.adamix.colony.utils.Isometric;

public class Preferences {

	public static int windowWidth = 480 * 2; // pixels
	public static int windowHeight = 270 * 2; // pixels
	public static int worldScale = 2;
	public static int tileSize = 32 * worldScale; // pixels
	public static int chunkSize = 8; // tiles
	public static int maxFps = 60;
	public static String windowTitle = "Colony";
	public static String gameVersion = "v0.1";
	public static boolean showFpsInTitle = true;
	public static boolean vSync = true;


	public static void loadPreferences() {
		// ToDo
	}

}
