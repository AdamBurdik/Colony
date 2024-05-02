package me.adamix.colony.world.tile;

import com.badlogic.gdx.Gdx;
import me.adamix.camera.Camera;
import me.adamix.colony.Game;
import me.adamix.colony.math.Vector2;
import me.adamix.colony.math.Vector3;
import me.adamix.colony.preferences.Preferences;
import me.adamix.colony.resources.Resources;
import me.adamix.colony.utils.Isometric;
import me.adamix.colony.world.chunk.Chunk;

import static me.adamix.colony.preferences.Preferences.tileSize;

public class Tile {

	private final Vector3 gridPos;
	private final Vector2 chunkGridPos;
	private int textureId;

	public Tile(Vector3 gridPos, Vector2 chunkGridPos, int textureId) {
		this.gridPos = gridPos;
		this.chunkGridPos = chunkGridPos;
		this.textureId = textureId;
	}

	private boolean isVisible() {
		Chunk chunk = Game.getCurrentWorld().getChunkByGrid(chunkGridPos);
		return chunk.getTileByGrid(gridPos.add(0, 0, 1)) == null || chunk.getTileByGrid(gridPos.add(1, 0, 0)) == null || chunk.getTileByGrid(gridPos.add(0, 1, 0)) == null;
	}

	public void render() {
		if (!isVisible()) {
			return;
		}
		Vector2 pos = Isometric.getTileScreenPos(this.gridPos, this.chunkGridPos);
		Vector2 screenPos = new Vector2(
				(float) Gdx.graphics.getWidth() / 2 - pos.x - (float) Preferences.tileSize / 2 + Camera.offset.x,
				(Gdx.graphics.getHeight() - pos.y - Preferences.tileSize) + (float) (gridPos.z * tileSize / 2) - Preferences.worldScale * gridPos.z + Camera.offset.y
		);
		if (screenPos.x > Gdx.graphics.getWidth() || screenPos.x + tileSize < 0 || screenPos.y > Gdx.graphics.getHeight() || screenPos.y + tileSize < 0) {
			return;
		}

		Chunk chunk = Game.getCurrentWorld().getChunkByGrid(chunkGridPos);
		if (chunk.getTileByGrid(gridPos.add(0, 0, 1)) != null) {
			textureId = 0;
		}

		Camera.draw(
				Resources.getTileTextures(textureId),
				screenPos.x,
				screenPos.y,
				Preferences.tileSize,
				Preferences.tileSize
		);
	}

}
