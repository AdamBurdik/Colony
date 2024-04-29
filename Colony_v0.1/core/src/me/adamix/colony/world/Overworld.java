package me.adamix.colony.world;

import me.adamix.colony.Game;
import me.adamix.colony.math.Vector2;
import me.adamix.colony.world.entities.ColonistEntity;
import me.adamix.colony.world.entities.Entity;
import me.adamix.colony.world.tile.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Overworld {

	private static final Map<Vector2, Tile> tiles = new HashMap<>();
	private static final List<Entity> entities = new ArrayList<>();

	public static Vector2 getTileCount() {
		return tileCount;
	}

	private static Vector2 tileCount;

	public static void init(Vector2 worldTileCount) {
		tileCount = worldTileCount;
	}

	public static void generate() {
		for (int y = 0; y < tileCount.y; y++) {
			for (int x = 0; x < tileCount.x; x++) {
				Vector2 position = new Vector2(x, y);
				tiles.put(position, new Tile(position, 0));
			}
		}
	}

	public static void render() {
		for (Tile tile : tiles.values()) {
			tile.render();
		}
		for (Entity entity : entities) {
			entity.render();
		}
	}

	public static void update() {
		for (Entity entity : entities) {
			Thread thread = new Thread(entity::update);
			thread.start();
		}
	}

	public static Tile getTileFromScreen(int screenX, int screenY) {
		int tileGridX = screenX / Game.tileSize;
		int tileGridY = screenY / Game.tileSize;
		return tiles.get(new Vector2(tileGridX, tileGridY));
	}

	public static Tile getTileFromGrid(int gridX, int gridY) {
		return tiles.get(new Vector2(gridX, gridY));
	}

	public static Tile getTileFromGrid(Vector2 pos) {
		return tiles.get(pos);
	}

	public static void addEntity(Entity entity) {
		entities.add(entity);
	}

	public static void addColonist(Vector2 gridPosition) {
		addEntity(
				new ColonistEntity(convertGridToScreen(gridPosition), 1)
		);
	}

	public static Entity getEntity(int index) {
		return entities.get(index);
	}

	public static List<Entity> getAllEntities() {
		return entities;
	}

	public static Vector2 convertGridToScreen(Vector2 gridPosition) {
		return new Vector2(
				gridPosition.x * Game.tileSize * Game.scale,
				gridPosition.y * Game.tileSize * Game.scale
		);
	}

	public static Vector2 convertScreenToGrid(Vector2 screenPosition) {
		return new Vector2(
				screenPosition.x / (Game.tileSize * Game.scale),
				screenPosition.y / (Game.tileSize * Game.scale)
		);
	}

	public static Vector2 convertScreenToGrid(int screenPositionX, int screenPositionY) {
		return new Vector2(
				screenPositionX / (Game.tileSize * Game.scale),
				screenPositionY / (Game.tileSize * Game.scale)
		);
	}

}
