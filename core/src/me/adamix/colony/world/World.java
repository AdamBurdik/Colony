package me.adamix.colony.world;

import com.badlogic.gdx.Gdx;
import me.adamix.colony.camera.Camera;
import me.adamix.colony.math.Vector2;
import me.adamix.colony.math.Vector3;
import me.adamix.colony.preferences.Preferences;
import me.adamix.colony.utils.Isometric;
import me.adamix.colony.utils.ObjectSizeCalculator;
import me.adamix.colony.world.chunk.Chunk;
import me.adamix.colony.world.generators.Generator;
import me.adamix.colony.world.tile.Tile;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import static me.adamix.colony.preferences.Preferences.chunkSize;

public class World {
	private Generator generator;
	private final Chunk[][] chunks = new Chunk[999][999];

	public World(Generator generator) {
		this.generator = generator;
	}

	public Chunk generateChunk(int x, int y) {
		Chunk chunk = generator.generateChunk(new Vector2(x, y));
		chunks[y][x] = chunk;
		return chunk;
	}

	public Chunk[] getSurroundingChunks(int chunkX, int chunkY) {
		int radius = 5;
		Chunk[] surroundingChunks = new Chunk[radius * (2 + 1) * radius * (2 + 1)];
		int i = -1;
		for (int y = chunkY - radius; y < chunkY + radius + 1; y++) {
			for (int x = chunkX - radius; x < chunkX + radius + 1; x++) {
				i++;
				if (x < 0 || y < 0) {
					continue;
				}
				surroundingChunks[i] = getChunk(x, y);
			}
		}
		return surroundingChunks;
	}

	public void render() {
		Vector3 centeredTilePos = Isometric.getTileGridPos(Gdx.graphics.getWidth() / 2 - Camera.offset.x, Gdx.graphics.getHeight() / 2 + Camera.offset.y);
		Chunk chunk = getChunk((short) centeredTilePos.x, (short) centeredTilePos.y);
		if (chunk != null) {
			Chunk[] surroundingChunks = getSurroundingChunks(chunk.getChunkX(), chunk.getChunkY());
			for (Chunk surroundingChunk : surroundingChunks) {
				if (surroundingChunk == null) {
					continue;
				}
				surroundingChunk.render();
			}
		}
	}

	public Chunk getChunk(short tileX, short tileY) {
		return getChunk(
				tileX / chunkSize,
				tileY / chunkSize
		);
	}

	public Chunk getChunk(int x, int y) {
		if (x < 0 || y < 0) {
			return null;
		}
		Chunk chunk = chunks[y][x];
		if (chunk == null) {
			chunk = generateChunk(x, y);
		}
		return chunk;
	}

}
