package me.adamix.colony.world.tile;

import com.badlogic.gdx.Gdx;
import me.adamix.colony.Game;
import me.adamix.colony.math.Vector2;
import me.adamix.colony.math.Vector3;
import me.adamix.colony.preferences.Preferences;
import me.adamix.colony.resources.Resources;
import me.adamix.colony.utils.Isometric;

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

	public void render() {
		Vector2 pos = Isometric.getTileScreenPos(this.gridPos, this.chunkGridPos);
		Game.batch.draw(
				Resources.getTileTextures(textureId),
				(float) Gdx.graphics.getWidth() / 2 + pos.x - (float) Preferences.tileSize / 2 + Game.offset.x,
				(Gdx.graphics.getHeight() - pos.y - Preferences.tileSize) + Game.offset.y + (float) (gridPos.z * tileSize / 2),
				Preferences.tileSize,
				Preferences.tileSize
		);
	}

	public Vector3 getPosition() {
		return this.gridPos;
	}

	public boolean isWall() {
		return false;
	}

}
