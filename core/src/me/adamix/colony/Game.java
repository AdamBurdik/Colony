package me.adamix.colony;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import me.adamix.colony.camera.Camera;
import me.adamix.colony.math.Vector2;
import me.adamix.colony.math.Vector3;
import me.adamix.colony.preferences.Preferences;
import me.adamix.colony.resources.Resources;
import me.adamix.colony.utils.Isometric;
import me.adamix.colony.world.World;
import me.adamix.colony.world.chunk.Chunk;
import me.adamix.colony.world.generators.OverworldGenerator;
import me.adamix.colony.world.generators.PerlinNoise;
import me.adamix.colony.world.tile.Tile;

import static me.adamix.colony.preferences.Preferences.*;

public class Game extends ApplicationAdapter {
	public static boolean isRunning = false;
	private static World currentWorld;
	private static Tile selectedTile;

	public static SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();

		Isometric.precalculateAll();
		Camera.create();
		Resources.loadAll();

		currentWorld = new World(new OverworldGenerator(69L));

		isRunning = true;
		selectedTile = new Tile((short) 0, (short) 0, "colony:selected_tile");
//
//		currentWorld.generateChunk(new Vector2(0, 0));
//		currentWorld.generateChunk(new Vector2(1, 0));
	}

	private void placeTile(short tileId) {

	}

	private void removeTile() {
		Chunk chunk = currentWorld.getChunk(selectedTile.getX(), selectedTile.getY());
		chunk.removeTile(selectedTile.getX(), selectedTile.getY());
	}

	private void handleInput() {
		// ToDo move to specific class
		Vector2 move = new Vector2(0, 0);
		if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
			placeTile((short) 0);
		}
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			removeTile();
		}


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
		if (Gdx.input.isKeyJustPressed(Input.Keys.F11)){
			boolean fullScreen = Gdx.graphics.isFullscreen();
			Graphics.DisplayMode currentMode = Gdx.graphics.getDisplayMode();
			if (fullScreen)
				Gdx.graphics.setWindowedMode(windowWidth, windowHeight);
			else
				Gdx.graphics.setFullscreenMode(currentMode);
		}
		if (move.x != 0 && move.y != 0) {
			move.y /= 2;
		}
		Camera.offset.x += move.x;
		if (Camera.offset.y + move.y >= 0) {
			Camera.offset.y += move.y;
		}
		Vector3 selectedTilePos = Isometric.getTileGridPos(Gdx.input.getX() - Camera.offset.x, Gdx.input.getY() + Camera.offset.y);
		selectedTile.setPos((short) selectedTilePos.x, (short) selectedTilePos.y);
		selectedTile.setRender(selectedTilePos.x >= 0 && selectedTilePos.y >= 0);
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
		batch.begin();
		ScreenUtils.clear((float) 41 / 255, (float) 40 / 255, (float) 49 / 255, (float) 0 / 255);
		Camera.render();
		selectedTile.render();
		batch.end();

	}
	
	@Override
	public void dispose () {
		isRunning = false;
		batch.dispose();
	}

	public static World getCurrentWorld() {
		return currentWorld;
	}

}
