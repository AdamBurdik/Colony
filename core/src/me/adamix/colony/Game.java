package me.adamix.colony;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.ScreenUtils;
import me.adamix.colony.camera.Camera;
import me.adamix.colony.math.Vector2;
import me.adamix.colony.preferences.Preferences;
import me.adamix.colony.resources.Resources;
import me.adamix.colony.utils.Isometric;
import me.adamix.colony.world.World;
import me.adamix.colony.world.generators.OverworldGenerator;
import me.adamix.colony.world.tile.Tile;

import static me.adamix.colony.preferences.Preferences.*;

public class Game extends ApplicationAdapter {
	public static boolean isRunning = false;
	private static World currentWorld;
	@Override
	public void create () {
		Isometric.precalculateInvertedMatrix();
		Camera.create();
		Resources.loadAllTextures();

		currentWorld = new World(new OverworldGenerator(69L));

		isRunning = true;
//
//		currentWorld.generateChunk(new Vector2(0, 0));
//		currentWorld.generateChunk(new Vector2(1, 0));
	}

	private void handleInput() {
		Vector2 move = new Vector2(0, 0);
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			move.y -= cameraSpeed;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			move.y += cameraSpeed;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			move.x += cameraSpeed;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			move.x -= cameraSpeed;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
			move.x *= cameraSpeedBoost;
			move.y *= cameraSpeedBoost;
		}
		if (move.x != 0 && move.y != 0) {
			move.y /= 2;
		}
		Camera.offset.x += move.x;
		Camera.offset.y += move.y;
	}

	private void update() {
		handleInput();
		Camera.update();
		if (Preferences.showFpsInTitle) {
			Gdx.graphics.setTitle(Preferences.windowTitle + "     FPS: " + Gdx.graphics.getFramesPerSecond());
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
