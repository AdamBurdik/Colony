package me.adamix.colony.world.chunk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.ScreenUtils;
import me.adamix.colony.Game;
import me.adamix.colony.camera.Camera;
import me.adamix.colony.math.Vector2;
import me.adamix.colony.preferences.Preferences;
import me.adamix.colony.resources.Resources;
import me.adamix.colony.utils.Isometric;
import me.adamix.colony.world.tile.Tile;
import com.badlogic.gdx.graphics.Pixmap;

import java.io.Serializable;

import static me.adamix.colony.preferences.Preferences.chunkSize;

public class Chunk implements Serializable {

	private final Tile[][] tiles;
	private final short chunkX;
	private final short chunkY;

	public Chunk(short chunkX, short chunkY, Tile[][] tiles) {
		this.chunkX = chunkX;
		this.chunkY = chunkY;
		this.tiles = tiles;
	}

	public short getChunkX() {
		return this.chunkX;
	}

	public short getChunkY() {
		return chunkY;
	}

	public void render() {
		for (Tile[] column : tiles) {
			for (Tile tile : column) {
				if (tile == null) {
					continue;
				}
				tile.render();
			}
		}
	}

	public void removeTile(short x, short y) {
		int tileX = x - (x / chunkSize) * chunkSize;
		int tileY = y - (y / chunkSize) * chunkSize;

		if (tileX < 0 || tileY < 0) {
			return;
		}

		tiles[tileY][tileX] = null;
	}

	public Tile getTile(int x, int y, int z) {
		return tiles[y][x];
	}
}
