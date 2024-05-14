package me.adamix.colony.preferences;

public class Preferences {

	public static final int windowWidth = 480 * 3; // pixels
	public static final int windowHeight = 270 * 3; // pixels
	public static final int worldSizeX = 2000;
	public static final int worldSizeY = 2000;
	public static final int worldScale = 1;
	public static final int tileSize = 32 * worldScale; // pixels
	public static final int chunkSize = 16; // tiles
	public static final int renderDistance = 3; // chunks
	public static final int maxFps = 120;
	public static final String windowTitle = "Colony";
	public static final boolean showFpsInTitle = true;
	public static final boolean vSync = true;
	public static final int cameraSpeed = 50;
	public static final int cameraSpeedBoost = 2;


	public static void loadPreferences() {
		// ToDo
	}

}
