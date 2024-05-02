package me.adamix.colony.preferences;

import me.adamix.colony.utils.Isometric;

public class Preferences {

	public static int windowWidth = 480 * 3; // pixels
	public static int windowHeight = 270 * 3; // pixels
	public static int worldScale = 3;
	public static int tileSize = 32 * worldScale; // pixels
	public static int chunkSize = 8; // tiles
	public static int maxFps = 120;
	public static String windowTitle = "Colony";
	public static String gameVersion = "v0.1";
	public static boolean showFpsInTitle = true;
	public static boolean vSync = true;


	public static void loadPreferences() {
		// ToDo
	}

}
