package me.adamix.colony.world.generators;

import me.adamix.colony.math.Vector2;
import me.adamix.colony.preferences.Preferences;
import me.adamix.colony.world.chunk.Chunk;
import me.adamix.colony.world.tile.Tile;

import java.util.Objects;

import static me.adamix.colony.preferences.Preferences.*;

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
		Tile[][][] tiles = new Tile[chunkMaxHeight][chunkSize][chunkSize];
		for (short z = 0; z < Preferences.chunkMaxHeight; z++) {
			for (short y = 0; y < chunkSize; y++) {
				for (short x = 0; x < chunkSize; x++) {
					if (z >= chunkHeight) {
						tiles[z][y][x] = null;
						continue;
					}
					int gridY = y + chunkGridPos.y * chunkSize;
					int gridX = x + chunkGridPos.x * chunkSize;
					double noiseValue = perlinNoise.noise(gridX, gridY, 0);
					String tileId = "colony:water_top_tile";
					if (noiseValue > 0.09 - 0.15) {
						tileId = "colony:sand_tile";
					}
					if (noiseValue > 0.3 - 0.2) {
						tileId = "colony:grass_tile";
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
					if (tileAbove == null) {
						continue;
					}
					if (Objects.equals(tileAbove.getTextureId(), "colony:grass_tile")) {
						tile.setTextureId("colony:dirt_tile");
						tile.setRender(false);
					} else if (tileAbove.getTextureId().equals("colony:water_top_tile")) {
						tile.setTextureId("colony:sand_tile");
					}
				}
			}
		}

		return new Chunk((short) chunkGridPos.x, (short) chunkGridPos.y, tiles);
	}
}
