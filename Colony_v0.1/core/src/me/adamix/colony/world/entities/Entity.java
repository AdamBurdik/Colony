package me.adamix.colony.world.entities;

import com.badlogic.gdx.graphics.Texture;
import me.adamix.colony.Game;
import me.adamix.colony.math.Vector2;
import me.adamix.colony.world.Overworld;

public class Entity {

	protected Vector2 position;
	protected Vector2 offset = new Vector2(0, 0);
	private int textureId;

	public Entity(Vector2 position, int textureId) {
		this.position = position;
		this.textureId = textureId;
	}

	public void update() {

	}

	public void render() {
		Texture texture = Game.getTexture(textureId);
		Game.batch.draw(
				texture,
				position.x + offset.x,
				position.y + offset.y,
				texture.getWidth() * Game.scale,
				texture.getHeight() * Game.scale);

	};

	public void move(Vector2 newPosition) {
		this.position = newPosition;
	}

	public Vector2 getGridPosition() {
		return Overworld.convertScreenToGrid(position);
	}

	public Vector2 getScreenPosition() {
		return position;
	}

}
