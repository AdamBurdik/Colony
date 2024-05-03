package me.adamix.colony.preferences;

import me.adamix.colony.utils.Isometric;

public class Preferences {

	public static int windowWidth = 480 * 3; // pixels
	public static int windowHeight = 270 * 3; // pixels
	public static int worldSizeX = 100;
	public static int worldSizeY = 100;
	public static int worldScale = 2;
	public static int tileSize = 32 * worldScale; // pixels
	public static int chunkSize = 16; // tiles
	public static int chunkHeight = 1;
	public static int maxFps = 120;
	public static String windowTitle = "Colony";
	public static String gameVersion = "v0.1";
	public static boolean showFpsInTitle = true;
	public static boolean vSync = true;
	public static int cameraSpeed = 10;
	public static int cameraSpeedBoost = 10;


	public static void loadPreferences() {
		// ToDo
	}

}
