package me.adamix.colony.world;

import com.badlogic.gdx.Gdx;
import me.adamix.colony.camera.Camera;
import me.adamix.colony.math.Vector2;
import me.adamix.colony.math.Vector3;
import me.adamix.colony.preferences.Preferences;
import me.adamix.colony.utils.Isometric;
import me.adamix.colony.world.chunk.Chunk;
import me.adamix.colony.world.generators.Generator;
import me.adamix.colony.world.tile.Tile;

import java.util.LinkedList;

import static me.adamix.colony.preferences.Preferences.chunkSize;
import static me.adamix.colony.preferences.Preferences.renderDistance;

public class World {
	private final Generator generator;
	private final LinkedList<LinkedList<Chunk>> chunks = new LinkedList<>();

	public World(Generator generator) {
		this.generator = generator;
		for (int y = 0; y < Preferences.worldSizeY; y++) {
			LinkedList<Chunk> row = new LinkedList<>();
			for (int x = 0; x < Preferences.worldSizeX; x++) {
				row.add(null);
			}
			chunks.add(row);
		}
	}

	public Chunk generateChunk(int x, int y) {
		Chunk chunk = generator.generateChunk(new Vector2(x, y));
		LinkedList<Chunk> row = chunks.get(y);
		row.set(x, chunk);
		return chunk;
	}

	public Chunk[] getSurroundingChunks(int chunkX, int chunkY) {
		Chunk[] surroundingChunks = new Chunk[renderDistance * (2 + 1) * renderDistance * (2 + 1)];
		int i = -1;
		for (int y = chunkY - renderDistance; y < chunkY + renderDistance + 1; y++) {
			for (int x = chunkX - renderDistance; x < chunkX + renderDistance + 1; x++) {
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
		if (x < 0 || y < 0 || y >= Preferences.worldSizeY || x >= Preferences.worldSizeX) {
			return null;
		}
		Chunk chunk = chunks.get(y).get(x);
		if (chunk == null) {
			chunk = generateChunk(x, y);
		}
		return chunk;
	}

}
