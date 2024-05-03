package me.adamix.colony.world.generators;

import me.adamix.colony.math.Vector2;
import me.adamix.colony.math.Vector3;
import me.adamix.colony.preferences.Preferences;
import me.adamix.colony.world.chunk.Chunk;
import me.adamix.colony.world.tile.Tile;
import me.adamix.colony.world.tile.TilePos;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class OverworldGenerator implements Generator {

	private final long seed;

	public OverworldGenerator(long seed) {
		this.seed = seed;
	}

	@Override
	public Chunk generateChunk(Vector2 chunkGridPos) {
		// ToDo
		Map<TilePos, Tile> tileMap = new LinkedHashMap<>();
		for (int z = 0; z < Preferences.chunkHeight; z++) {
			for (int y = 0; y < Preferences.chunkSize; y++) {
				for (int x = 0; x < Preferences.chunkSize; x++) {
					TilePos pos = new TilePos(x, y, z);
					tileMap.put(
							pos, new Tile(pos, chunkGridPos, ThreadLocalRandom.current().nextInt(2, 5))
					);
				}
			}
		}

		return new Chunk(chunkGridPos, tileMap);
	}
}
