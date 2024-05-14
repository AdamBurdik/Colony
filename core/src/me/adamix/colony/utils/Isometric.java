package me.adamix.colony.utils;

import me.adamix.colony.math.Vector2;
import me.adamix.colony.math.Vector3;
import me.adamix.colony.preferences.Preferences;

import java.util.Map;

import static me.adamix.colony.preferences.Preferences.*;

public class Isometric {
	private static final float i_x = 1f;
	private static final float i_y = 0.5f;
	private static final float j_x = -1f;
	private static final float j_y = 0.5f;
	private static Map<String, Float> invertedMatrix;
	private static Vector2 chunkSize;

	public static Map<String, Float> invertMatrix(float a, float b, float c, float d) {
		float det = (1 / (a * d - b * c));

		return Map.of(
				"a", det * d,
				"b", det * -b,
				"c", det * -c,
				"d", det * a
		);
	}

	public static void precalculateAll() {
		precalculateInvertedMatrix();
		precalculateChunkSize();
	}

	public static void precalculateInvertedMatrix() {
		float a = (float) (i_x * 0.5 * tileSize);
		float b = (float) (j_x * 0.5 * tileSize);
		float c = (float) (i_y * 0.5 * tileSize);
		float d = (float) (j_y * 0.5 * tileSize);

		invertedMatrix = invertMatrix(a, b, c, d);
	}

	public static void precalculateChunkSize() {
		chunkSize = getChunkSize();
	}

	public static Vector2 getTileScreenPos(short tileX, short tileY) {
		return new Vector2(
				tileX * i_x * ((float) tileSize / 2) + tileY * j_x * ((float) tileSize / 2),
				(tileX * i_y * ((float) tileSize / 2) + tileY * j_y * ((float) tileSize / 2)) // - ((float) (tileSize / 2 - worldScale) * tileZ)
		);
	}

	public static Vector3 getTileGridPos(int screenX, int screenY) {
		return new Vector3(
				(int) (screenX * invertedMatrix.get("a") + screenY * invertedMatrix.get("b")),
				(int) (screenX * invertedMatrix.get("c") + screenY * invertedMatrix.get("d")),
				0
		);
	}

	public static Vector2 getRelativeTileGridPos(short tileGridX, short tileGridY) {
		return new Vector2(
				tileGridX - Preferences.chunkSize * (tileGridX / Preferences.chunkSize),
				tileGridY - Preferences.chunkSize * (tileGridY / Preferences.chunkSize)

		);
	}

	public static Vector2 getChunkScreenPos(short chunkX, short chunkY) {
		return new Vector2(
				chunkX * i_x * ((float) chunkSize.x / 2) + chunkY * j_x * ((float) chunkSize.x / 2),
				chunkX * i_y * ((float) chunkSize.y / 2) + chunkY * j_y * ((float) chunkSize.y / 2)
		);
	}

	public static Vector2 getChunkSize() {
		return new Vector2(
				tileSize + tileSize * (Preferences.chunkSize - 1),
				tileSize + tileSize / 2 * (Preferences.chunkSize - 1)
		);
	}

	public static Vector2 getChunkGridPos(Vector2 chunkScreenPos) {
		float a = (i_x * 0.5f * chunkSize.x);
		float b = (j_x * 0.5f * chunkSize.x);
		float c = (i_y * 0.5f * chunkSize.y);
		float d = (j_y * 0.5f * chunkSize.y);

		Map<String, Float> invertedMatrix = invertMatrix(a, b, c, d);

		return new Vector2(
				chunkScreenPos.x * invertedMatrix.get("a") + chunkScreenPos.y * invertedMatrix.get("b"),
				chunkScreenPos.x * invertedMatrix.get("c") + chunkScreenPos.y * invertedMatrix.get("d")
		);
	}

	public static Vector2 getChunkPos(int tileX, int tileY) {
		return new Vector2(
				tileX / Preferences.chunkSize,
				tileY / Preferences.chunkSize
		);
	}
}
