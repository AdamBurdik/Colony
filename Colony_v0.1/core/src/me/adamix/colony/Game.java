package me.adamix.colony;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.ScreenUtils;
import me.adamix.camera.Camera;
import me.adamix.colony.math.Vector2;
import me.adamix.colony.preferences.Preferences;
import me.adamix.colony.resources.Resources;
import me.adamix.colony.world.World;
import me.adamix.colony.world.generators.OverworldGenerator;

public class Game extends ApplicationAdapter {
	public static boolean isRunning = false;
	private static World currentWorld;
	@Override
	public void create () {
		Camera.create();
		Resources.loadAllTextures();

		currentWorld = new World(new OverworldGenerator(69L));

		for (int y = -10; y < 10; y++) {
			for (int x = -10; x < 10; x++) {
				currentWorld.generateChunk(new Vector2(x, y));
			}
		}

		isRunning = true;
	}

	private void handleInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			Camera.offset.y += (int) (-1000 * Gdx.graphics.getDeltaTime());
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			Camera.offset.y += (int) (1000 * Gdx.graphics.getDeltaTime());
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			Camera.offset.x += (int) (1000 * Gdx.graphics.getDeltaTime());
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			Camera.offset.x += (int) (-1000 * Gdx.graphics.getDeltaTime());
		}
	}

	private void update() {
		handleInput();
		Camera.update();
		if (Preferences.showFpsInTitle) {
			Gdx.graphics.setTitle(Preferences.windowTitle + " " + Preferences.gameVersion + "     FPS: " + Gdx.graphics.getFramesPerSecond());
		}
	}

	@Override
	public void render () {
		update();
		ScreenUtils.clear(1, 1, 1, 1);
		Camera.render();
	}
	
	@Override
	public void dispose () {
		isRunning = false;
		Camera.dispose();
	}

	public static World getCurrentWorld() {
		return currentWorld;
	}

}
