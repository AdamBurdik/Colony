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
import me.adamix.colony.world.tile.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Colony {

	private String name;
	private int bedCount = 0;
	private int colonistCount = 0;
	private int unemployedCount = 0;
	private int mealCount = 0;
	private int pointCount = 0;
	private int threadLevel = 0;
	private Vector2 bannerPosition;
	private final List<Entity> entityList = new ArrayList<>();
//	private static final Map<Vector2, Tile> jobBlocks = new HashMap<>();



	public Colony(String name, Vector2 bannerPosition) {
		this.name = name;
		this.bannerPosition = bannerPosition;
	}

}
