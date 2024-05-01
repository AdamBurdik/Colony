package me.adamix.colony.world.colony;

import me.adamix.colony.math.Vector2;

import java.util.HashMap;
import java.util.Map;

public class ColonyManager {

	private static final Map<Vector2, Colony> colonies = new HashMap<>();

	public static void createColony(String name, Vector2 bannerPosition) {
		colonies.put(
				bannerPosition,
				new Colony(name, bannerPosition)
		);
	}

	public static void updateColonies() {

	}

	public static void renderColonies() {

	}

}
