package me.adamix.colony.preferences;

public class Preferences {

	public static int windowWidth = 480 * 3; // pixels
	public static int windowHeight = 270 * 3; // pixels
	public static int worldSizeX = 500;
	public static int worldSizeY = 500;
	public static int worldScale = 2;
	public static int tileSize = 32 * worldScale; // pixels
	public static int chunkSize = 16; // tiles
	public static int chunkHeight = 1;
	public static int chunkMaxHeight = 10;
	public static int renderDistance = 2;
	public static int maxFps = 120;
	public static String windowTitle = "Colony";
	public static boolean showFpsInTitle = true;
	public static boolean vSync = true;
	public static int cameraSpeed = 5;
	public static int cameraSpeedBoost = 2;


	public static void loadPreferences() {
		// ToDo
	}

}
