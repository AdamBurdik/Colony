package me.adamix.colony.utils;

import com.badlogic.gdx.Gdx;
import me.adamix.colony.math.Vector2;
import me.adamix.colony.math.Vector3;
import me.adamix.colony.preferences.Preferences;

import static me.adamix.colony.preferences.Preferences.tileSize;
import static me.adamix.colony.preferences.Preferences.chunkSize;

public class Isometric {
	private static final float i_x = 1f;
	private static final float i_y = 0.5f;
	private static final float j_x = -1f;
	private static final float j_y = 0.5f;

	public static Vector2 getChunkScreenPos(Vector2 chunkGridPos) {
		return new Vector2(
				chunkGridPos.x * i_x * ((float) tileSize * chunkSize / 2) + chunkGridPos.y * j_x * ((float) tileSize * chunkSize / 2),
				chunkGridPos.x * i_y * ((float) tileSize * chunkSize / 2) + chunkGridPos.y * j_y * ((float) tileSize * chunkSize / 2)
		);
	}

	public static Vector2 getTileScreenPos(Vector3 tileGridPos, Vector2 chunkGridPos) {
		Vector2 chunkScreenPos = getChunkScreenPos(chunkGridPos);
		return new Vector2(
				tileGridPos.x * i_x * ((float) tileSize / 2) + tileGridPos.y * j_x * ((float) tileSize / 2) + chunkScreenPos.x,
				tileGridPos.x * i_y * ((float) tileSize / 2) + tileGridPos.y * j_y * ((float) tileSize / 2) + chunkScreenPos.y
		);
	}

	public static Vector3 getTileGridPos(Vector2 screenPos) {
		return null;
	}

}
