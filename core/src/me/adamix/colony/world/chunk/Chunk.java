package me.adamix.colony.world.chunk;

import com.badlogic.gdx.Gdx;
import me.adamix.colony.camera.Camera;
import me.adamix.colony.math.Vector2;
import me.adamix.colony.utils.Isometric;
import me.adamix.colony.world.tile.Tile;
import me.adamix.colony.world.tile.TilePos;

import java.io.Serializable;
import java.util.Map;

public class Chunk implements Serializable {

	private final Map<TilePos, Tile> tileMap;
	private final Vector2 gridPosition;

	public Chunk(Vector2 gridPosition, Map<TilePos, Tile> tileMap) {
		this.gridPosition = gridPosition;
		this.tileMap = tileMap;
	}

	public void render() {
		Vector2 chunkScreenPos = Isometric.getChunkScreenPos(this.gridPosition);
		Vector2 chunkSize = Isometric.getChunkSize();
		chunkScreenPos.x += Camera.offset.x;
		chunkScreenPos.y -= Camera.offset.y;

		if (chunkScreenPos.x > Gdx.graphics.getWidth() || chunkScreenPos.x + chunkSize.x < 0 || chunkScreenPos.y - 50 > Gdx.graphics.getHeight() || chunkScreenPos.y + chunkSize.y < 0) {
			return;
		}


		for (Tile tile : tileMap.values()) {
			tile.render(chunkScreenPos, chunkSize);
		}
	}

	public Tile getTileByGrid(TilePos tileGridPos) {
//		System.out.println("Tile count: " + tileMap.size());
		return tileMap.get(tileGridPos);
	}
}
