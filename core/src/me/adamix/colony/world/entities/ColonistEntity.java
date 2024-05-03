package me.adamix.colony.world.entities;

import me.adamix.colony.Game;
import me.adamix.colony.math.Vector2;
import me.adamix.colony.preferences.Preferences;
import me.adamix.colony.world.entities.ai.PathFindingEntity;


public class ColonistEntity extends PathFindingEntity {

	private Vector2 animationOffset = new Vector2(0, 0);

	public ColonistEntity(Vector2 position, int textureId) {
		super(position, textureId);
	}

	@Override
	public void update() {
		offset.x += (int) (-1 * Math.signum(offset.x));
		offset.y += (int) (-1 * Math.signum(offset.y));
	}


	// UP = 0
	// RIGHT = 1
	// DOWN = 2
	// LEFT = 3
	public Vector2 getDirection(Vector2 newPosition) {
		Vector2 direction = new Vector2(0, 0);
		if (position.x < newPosition.x) {
			direction.x = 1;
		} else if (position.x > newPosition.x){
			direction.x = -1;
		}
		if (position.y < newPosition.y) {
			direction.y = 1;
		} else if (position.y > newPosition.y){
			direction.y = -1;
		}
		return direction;
	}
//
//	@Override
//	public void move(Vector2 newPosition) {
//		Vector2 direction = getDirection(newPosition);
//
//		this.position = newPosition;
//		offset.x = -1 * direction.x * Preferences.worldScale * Preferences.tileSize;
//		offset.y = -1 * direction.y * Preferences.worldScale * Preferences.tileSize;
//
//	}
}
