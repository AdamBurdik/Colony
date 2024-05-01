package me.adamix.colony.world;

import me.adamix.colony.Game;
import me.adamix.colony.math.Vector2;
import me.adamix.colony.preferences.Preferences;
import me.adamix.colony.world.chunk.Chunk;
import me.adamix.colony.world.generators.Generator;
import me.adamix.colony.world.tile.Tile;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class World {
	private Generator generator;
	private final Map<Vector2, Chunk> chunkMap = new LinkedHashMap<>();

	public World(Generator generator) {
		this.generator = generator;
	}

	public void generateChunk(Vector2 chunkGridPos) {
		getChunkByGrid(chunkGridPos);
	}

	public void render() {
		for (Chunk chunk : chunkMap.values()) {
			chunk.render();
		}
	}

	public Chunk getChunkByGrid(Vector2 chunkGridPos) {
		if (!chunkMap.containsKey(chunkGridPos)) {
			chunkMap.put(chunkGridPos, generator.generateChunk(chunkGridPos));
		}
		return chunkMap.get(chunkGridPos);
	}


	public Chunk getChunkByScreen(Vector2 screenPosition) {
		return null;
	}

}
