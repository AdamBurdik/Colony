package me.adamix.colony.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.adamix.colony.Game;
import me.adamix.colony.math.Vector2;

public class Camera {

	public static final Vector2 offset = new Vector2(Gdx.graphics.getWidth() / 2, 0);

	public static void create() {
	}

	public static void update() {

	}

	public static void render() {
		Game.getCurrentWorld().render();
	}

	public static void drawGameObject(Texture texture, float x, float y, int width, int height) {
		Game.batch.draw(texture, x + offset.x, y + offset.y, width, height);
	}

	public static void draw(Texture texture, Vector2 pos, int width, int height) {
		Game.batch.draw(texture, pos.x, pos.y, width, height);
	}

	public static void draw(Texture texture, Vector2 pos, int width, int height, int u, int v, int u2, int v2) {
		Game.batch.draw(texture, pos.x, pos.y, width, height, u, v, u2, v2);
	}

	public static void draw(Texture texture, int x, int y, int width, int height, int u, int v, int u2, int v2) {
		Game.batch.draw(texture, x, y, width, height, u, v, u2, v2);
	}

	public static void draw(Texture texture, float x, float y, int width, int height) {
		Game.batch.draw(texture, x, y, width, height);
	}

}
