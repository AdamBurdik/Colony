package me.adamix.colony.world.generators;

import me.adamix.colony.math.Vector2;
import me.adamix.colony.preferences.Preferences;
import me.adamix.colony.world.chunk.Chunk;
import me.adamix.colony.world.tile.Tile;

import java.util.List;
import java.util.Objects;

import static me.adamix.colony.preferences.Preferences.*;

public class OverworldGenerator implements Generator {

	private final long seed;
	private PerlinNoise perlinNoise;

	public OverworldGenerator(long seed) {
		this.seed = seed;
		this.perlinNoise = new PerlinNoise(69);

	}

	public short convertToRange(double value, int minRange, int maxRange) {
		double scaledValue = (value + 1) * 0.5 * (maxRange - minRange);
		return (short) (scaledValue + minRange);
	}

	@Override
	public Chunk generateChunk(Vector2 chunkGridPos) {
		// ToDo Better generation
		List<String> stringList = List.of(
				"colony:dirt_tile",
				"colony:grass_tile",
				"colony:sand_tile",
				"colony:water_tile"
		);

		Tile[][] tiles = new Tile[chunkSize][chunkSize];
		for (short y = 0; y < chunkSize; y++) {
			for (short x = 0; x < chunkSize; x++) {
				short gridY = (short) (y + chunkGridPos.y * chunkSize);
				short gridX = (short) (x + chunkGridPos.x * chunkSize);

				double noiseValue = perlinNoise.noise((float) gridX, 0.1f, (float) gridY);
				int rangeValue = convertToRange(noiseValue, 0, 4);

				String tileId = stringList.get(Math.min(3, rangeValue));

				tiles[y][x] = new Tile(gridX, gridY, tileId);



//					double noiseValue = perlinNoise.noise(gridX, gridY, 0);
//					short gridZ = convertToRange(noiseValue, 1, chunkMaxHeight - 1);
//					if (z <= gridZ) {
//					}
			}
		}

//		for (short y = 0; y < chunkSize; y++) {
//			for (short x = 0; x < chunkSize; x++) {
//				Tile tile = tiles[y][x];
//				Tile tileAbove = tiles[y][x];
//				if (tileAbove == null) {
//					continue;
//				}
//				if (Objects.equals(tileAbove.getTextureId(), "colony:grass_tile")) {
//					tile.setTextureId("colony:dirt_tile");
//				}
//				if (tileAbove.getTextureId().equals("colony:water_top_tile")) {
//					tile.setRender(false);
//				}
////					if (tileAbove.getTextureId().equals("colony:sand_tile")) {
////						tile.setTextureId("colony:dirt_tile");
//////						tile.setRender(false);
////					}
//
//
//			}
//		}

		return new Chunk((short) chunkGridPos.x, (short) chunkGridPos.y, tiles);
	}
}
