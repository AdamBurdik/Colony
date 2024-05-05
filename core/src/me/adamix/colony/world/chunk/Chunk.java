package me.adamix.colony.world.chunk;

import me.adamix.colony.world.tile.Tile;

import java.io.Serializable;

public class Chunk implements Serializable {

	private final Tile[][][] tiles;
	private final short chunkX;
	private final short chunkY;

	public Chunk(short chunkX, short chunkY, Tile[][][] tiles) {
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
		for (Tile[][] layer : tiles) {
			for (Tile[] column : layer) {
				for (Tile tile : column) {
					tile.render();
				}
			}
		}
	}

	public Tile getTile(int x, int y, int z) {
		return tiles[x][y][z];
	}
}
