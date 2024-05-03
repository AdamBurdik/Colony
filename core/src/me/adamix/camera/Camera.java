package me.adamix.camera;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.adamix.colony.Game;
import me.adamix.colony.math.Vector2;

public class Camera {

	public static SpriteBatch batch;
	public static final Vector2 offset = new Vector2(0, 0);

	public static void create() {
		batch = new SpriteBatch();
	}

	public static void update() {

	}

	public static void render() {
		batch.begin();

		Game.getCurrentWorld().render();

		batch.end();
	}

	public static void drawGameObject(Texture texture, float x, float y, int width, int height) {
		batch.draw(texture, x + offset.x, y + offset.y, width, height);
	}

	public static void draw(Texture texture, Vector2 pos, int width, int height) {
		batch.draw(texture, pos.x, pos.y, width, height);
	}

	public static void draw(Texture texture, float x, float y, int width, int height) {
		batch.draw(texture, x, y, width, height);
	}

	public static void dispose() {
		batch.dispose();
	}

}
