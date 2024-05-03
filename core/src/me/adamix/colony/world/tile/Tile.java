package me.adamix.colony.world.tile;

import com.badlogic.gdx.Gdx;
import me.adamix.colony.camera.Camera;
import me.adamix.colony.Game;
import me.adamix.colony.math.Vector2;
import me.adamix.colony.preferences.Preferences;
import me.adamix.colony.resources.Resources;
import me.adamix.colony.utils.Isometric;
import me.adamix.colony.world.chunk.Chunk;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

import static me.adamix.colony.preferences.Preferences.tileSize;

public class Tile implements Serializable {

	private final TilePos gridPos;;
	private final Vector2 chunkGridPos;
	private int textureId;

	public Tile(TilePos gridPos, Vector2 chunkGridPos, int textureId) {
		this.gridPos = gridPos;
		this.chunkGridPos = chunkGridPos;
		this.textureId = textureId;
	}

	private boolean isVisible() {
		Chunk chunk = Game.getCurrentWorld().getChunkByGrid(chunkGridPos);
		return chunk.getTileByGrid(gridPos.add(0, 0, 1)) == null || chunk.getTileByGrid(gridPos.add(1, 0, 0)) == null || chunk.getTileByGrid(gridPos.add(0, 1, 0)) == null;
	}

	public void render(Vector2 chunkScreenPos, Vector2 chunkSize) {
//		if (!isVisible()) {
//			return;
//		}
		Vector2 pos = Isometric.getTileScreenPos(this.gridPos);
		Vector2 screenPos = new Vector2(
				pos.x + chunkScreenPos.x + (float) chunkSize.x / 2 - (float) tileSize / 2,
				(Gdx.graphics.getHeight() - pos.y - Preferences.tileSize) - chunkScreenPos.y
		);
//		if (screenPos.x > Gdx.graphics.getWidth() || screenPos.x + tileSize < 0 || screenPos.y > Gdx.graphics.getHeight() || screenPos.y + tileSize < 0) {
//			return;
//		}

		// ToDo: Reimplement this function
		// Make texture id to dirt if block above is solid
		if (textureId != 0 && textureId != 1) {
			Chunk chunk = Game.getCurrentWorld().getChunkByGrid(chunkGridPos);
			if (chunk.getTileByGrid(gridPos.add(0, 0, 1)) != null) {
				textureId = ThreadLocalRandom.current().nextInt(0, 2);
			}
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