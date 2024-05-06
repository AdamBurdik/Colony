package me.adamix.colony.world.colony;

// Colony Name
// Time
// Bed count
// Colonist Count
// Unemployed colonist Count

// Meal Count
// Colony Points
// Ammo
// Threat Level

// Safe zone
//

import me.adamix.colony.math.Vector2;
import me.adamix.colony.world.entities.Entity;

import java.util.ArrayList;
import java.util.List;

public class Colony {

	private final String name;
	private final int bedCount = 0;
	private final int colonistCount = 0;
	private final int unemployedCount = 0;
	private final int mealCount = 0;
	private final int pointCount = 0;
	private final int threadLevel = 0;
	private final Vector2 bannerPosition;
	private final List<Entity> entityList = new ArrayList<>();
//	private static final Map<Vector2, Tile> jobBlocks = new HashMap<>();



	public Colony(String name, Vector2 bannerPosition) {
		this.name = name;
		this.bannerPosition = bannerPosition;
	}

}
