package me.adamix.colony.world.chunk;

import me.adamix.colony.math.Vector2;
import me.adamix.colony.math.Vector3;
import me.adamix.colony.world.generators.Generator;
import me.adamix.colony.world.tile.Tile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chunk {

	private final Map<Vector3, Tile> tileMap;
	private final Vector2 gridPosition;

	public Chunk(Vector2 gridPosition, Map<Vector3, Tile> tileMap) {
		this.gridPosition = gridPosition;
		this.tileMap = tileMap;
	}


	public void render() {
		for (Tile tile : tileMap.values()) {
			tile.render();
		}
	}

	public Tile getTileByGrid(Vector3 tileGridPos) {
		return tileMap.get(tileGridPos);
	}
}
