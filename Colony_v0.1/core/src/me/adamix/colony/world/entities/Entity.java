package me.adamix.colony.world.entities;

import com.badlogic.gdx.graphics.Texture;
import me.adamix.camera.Camera;
import me.adamix.colony.math.Vector2;
import me.adamix.colony.preferences.Preferences;
import me.adamix.colony.resources.Resources;

public class Entity {

	protected Vector2 position;
	protected Vector2 offset = new Vector2(0, 0);
	private final int textureId;

	public Entity(Vector2 position, int textureId) {
		this.position = position;
		this.textureId = textureId;
	}

	public void update() {

	}

//	public void render() {
//		Texture texture = Resources.getTileTextures(textureId);
//		Camera.draw(
//				texture,
//				position.x + offset.x,
//				position.y + offset.y,
//				texture.getWidth() * Preferences.worldScale,
//				texture.getHeight() * Preferences.worldScale);
//
//	}
//
//	public void move(Vector2 newPosition) {
//		this.position = newPosition;
//	}
//
//	public Vector2 getScreenPosition() {
//		return position;
//	}

}
