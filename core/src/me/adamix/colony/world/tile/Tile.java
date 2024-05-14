package me.adamix.colony.world.tile;

import com.badlogic.gdx.Gdx;
import me.adamix.colony.camera.Camera;
import me.adamix.colony.math.Vector2;
import me.adamix.colony.preferences.Preferences;
import me.adamix.colony.resources.Resources;
import me.adamix.colony.utils.Isometric;

import java.io.Serializable;

import static me.adamix.colony.preferences.Preferences.tileSize;

public class Tile implements Serializable {
	private short tileX;
	private short tileY;
	private String textureId;
	private boolean render = true;

	public Tile(short tileX, short tileY, String textureId) {
		this.tileX = tileX;
		this.tileY = tileY;
		this.textureId = textureId;
	}
	public void render() {
		if (!this.render) {
			return;
		}

		Vector2 screenPos = Isometric.getTileScreenPos(tileX, tileY);

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
				Gdx.graphics.getHeight() - screenPos.y - tileSize + Camera.offset.y,
				Preferences.tileSize,
				Preferences.tileSize
		);
	}

	public void setPos(short tileX, short tileY) {
		this.tileX = tileX;
		this.tileY = tileY;
	}

	public String getTextureId() {
		return textureId;
	}

	public void setTextureId(String textureId) {
		this.textureId = textureId;
	}

	public void setRender(boolean render) {
		this.render = render;
	}

	public short getX() {
		return tileX;
	}

	public short getY() {
		return tileY;
	}

}