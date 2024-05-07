package me.adamix.colony.world.generators;

import me.adamix.colony.math.Vector2;
import me.adamix.colony.preferences.Preferences;
import me.adamix.colony.world.chunk.Chunk;
import me.adamix.colony.world.tile.Tile;

import static me.adamix.colony.preferences.Preferences.chunkHeight;
import static me.adamix.colony.preferences.Preferences.chunkSize;

public class OverworldGenerator implements Generator {

	private final long seed;
	private PerlinNoise perlinNoise;

	public OverworldGenerator(long seed) {
		this.seed = seed;
		this.perlinNoise = new PerlinNoise();

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
					double noiseValue = perlinNoise.noise(gridX, gridY, 0);
					short tileId = 5;
					if (noiseValue > 0.09 - 0.15) {
						tileId = 2;
					}
					if (noiseValue > 0.3 - 0.2) {
						tileId = 1;
					}
					tiles[z][y][x] = new Tile((short) gridX, (short) gridY, z, tileId);
				}
			}
		}

		for (short z = 0; z < Preferences.chunkHeight; z++) {
			for (short y = 0; y < chunkSize; y++) {
				for (short x = 0; x < chunkSize; x++) {
					if (z != 0) {
						continue;
					}

					Tile tile = tiles[z][y][x];
					Tile tileAbove = tiles[z + 1][y][x];
					if (tileAbove.getTextureId() == 1) {
						tile.setTextureId((short) 0);
						tile.setRender(false);
					} else if (tileAbove.getTextureId() == 5) {
						tile.setTextureId((short) 2);
					}
				}
			}
		}

		return new Chunk((short) chunkGridPos.x, (short) chunkGridPos.y, tiles);
	}
}
