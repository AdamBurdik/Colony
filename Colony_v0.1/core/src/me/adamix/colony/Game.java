package me.adamix.colony;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Predicate;
import com.badlogic.gdx.utils.ScreenUtils;
import me.adamix.colony.math.Vector2;
import me.adamix.colony.preferences.Preferences;
import me.adamix.colony.resources.Resources;
import me.adamix.colony.world.World;
import me.adamix.colony.world.entities.ColonistEntity;
import me.adamix.colony.world.entities.Entity;
import me.adamix.colony.world.generators.OverworldGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static me.adamix.colony.preferences.Preferences.tileSize;
import static me.adamix.colony.preferences.Preferences.worldScale;

public class Game extends ApplicationAdapter {
	public static SpriteBatch batch;
	public static boolean isRunning = false;
	private static World overWorld;
	public static Vector2 offset = new Vector2(0, 0);

	@Override
	public void create () {
		isRunning = true;
		batch = new SpriteBatch();
		Resources.loadAllTextures();

		overWorld = new World(new OverworldGenerator(69L));

		for (int y = -5; y < 5; y++) {
			for (int x = -5; x < 5; x++) {
				overWorld.generateChunk(new Vector2(x, y));
			}
		}
	}

	private void handleInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			offset.y += (int) (-1000 * Gdx.graphics.getDeltaTime());
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			offset.y += (int) (1000 * Gdx.graphics.getDeltaTime());
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			offset.x += (int) (1000 * Gdx.graphics.getDeltaTime());
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			offset.x += (int) (-1000 * Gdx.graphics.getDeltaTime());
		}
	}

	private void update() {
		handleInput();
		if (Preferences.showFpsInTitle) {
			Gdx.graphics.setTitle(Preferences.windowTitle + " " + Preferences.gameVersion + "     FPS: " + Gdx.graphics.getFramesPerSecond());
		}
	}

	@Override
	public void render () {
		update();
		ScreenUtils.clear(1, 1, 1, 1);
		batch.begin();
		overWorld.render();
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		isRunning = false;
	}

}
