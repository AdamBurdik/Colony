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
import java.time.chrono.IsoEra;
import java.util.concurrent.ThreadLocalRandom;

import static me.adamix.colony.preferences.Preferences.tileSize;

public class Tile implements Serializable {
	private final short tileX;
	private final short tileY;
	private final short tileZ;
	private final short textureId;

	public Tile(short tileX, short tileY, short tileZ, short textureId) {
		this.tileX = tileX;
		this.tileY = tileY;
		this.tileZ = tileZ;
		this.textureId = textureId;
	}
	public void render() {

		Vector2 screenPos = Isometric.getTileScreenPos(tileX, tileY, tileZ);

		// ToDo: Reimplement this function
		// Make texture id to dirt if block above is solid
//		if (textureId != 0 && textureId != 1) {
//			Chunk chunk = Game.getCurrentWorld().getChunkByGrid(chunkGridPos);
//			if (chunk.getTileByGrid(gridPos.add(0, 0, 1)) != null) {
//				textureId = ThreadLocalRandom.current().nextInt(0, 2);
//			}
//		}

		Camera.draw(
				Resources.getTileTextures(textureId),
				screenPos.x - (float) tileSize / 2 + Camera.offset.x,
				Gdx.graphics.getHeight() - screenPos.y - tileSize + (float) (tileZ * tileSize) / 2 + Camera.offset.y,
				Preferences.tileSize,
				Preferences.tileSize
		);
	}

}