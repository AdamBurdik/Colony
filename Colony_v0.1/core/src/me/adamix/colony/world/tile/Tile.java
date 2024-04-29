package me.adamix.colony.world.tile;

import me.adamix.colony.Game;
import me.adamix.colony.math.Vector2;

public class Tile {

	private Vector2 position;
	private int textureId;

	public Tile(Vector2 position, int textureId) {
		this.position = position;
		this.textureId = textureId;
	}

	public void render() {
		Game.batch.draw(Game.getTexture(textureId), position.x * Game.tileSize * Game.scale, position.y * Game.tileSize * Game.scale, Game.tileSize * Game.scale, Game.tileSize * Game.scale);
	}

	public Vector2 getPosition() {
		return this.position;
	}

	public boolean isWall() {
		return false;
	}

}
