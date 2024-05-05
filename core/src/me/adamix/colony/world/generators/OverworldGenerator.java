package me.adamix.colony.world.generators;

import me.adamix.colony.math.Vector2;
import me.adamix.colony.preferences.Preferences;
import me.adamix.colony.world.chunk.Chunk;
import me.adamix.colony.world.tile.Tile;

import java.util.concurrent.ThreadLocalRandom;

import static me.adamix.colony.preferences.Preferences.chunkHeight;
import static me.adamix.colony.preferences.Preferences.chunkSize;

public class OverworldGenerator implements Generator {

	private final long seed;

	public OverworldGenerator(long seed) {
		this.seed = seed;
	}

	@Override
	public Chunk generateChunk(Vector2 chunkGridPos) {
		// ToDo Better generation
		Tile[][][] tiles = new Tile[chunkHeight][chunkSize][chunkSize];
		for (short z = 0; z < Preferences.chunkHeight; z++) {
			for (short y = 0; y < chunkSize; y++) {
				for (short x = 0; x < chunkSize; x++) {
					int gridY = y + chunkGridPos.y * chunkSize;
					int gridX = x + chunkGridPos.x * chunkSize;
					tiles[z][y][x] = new Tile((short) gridX, (short) gridY, z, (short) 0);
				}
			}
		}

		return new Chunk((short) chunkGridPos.x, (short) chunkGridPos.y, tiles);
	}
}
