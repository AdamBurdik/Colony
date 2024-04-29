package me.adamix.colony;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import me.adamix.colony.math.Vector2;
import me.adamix.colony.world.Overworld;
import me.adamix.colony.world.entities.ColonistEntity;
import me.adamix.colony.world.entities.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Game extends ApplicationAdapter {
	public static SpriteBatch batch;
	public static int tileSize = 16;
	public static boolean isRunning = false;
	public static int scale = 2;
	private static final List<Texture> textures = new ArrayList<>();

	private void loadTextures() {
		textures.add(new Texture("tiles/grass.png"));
		textures.add(new Texture("entities/colonist.png"));
	}

	@Override
	public void create () {
		isRunning = true;
		batch = new SpriteBatch();
		loadTextures();
		Overworld.init(new Vector2(Gdx.graphics.getWidth() / (tileSize * scale), Gdx.graphics.getHeight() / (tileSize * scale)));
		Overworld.generate();

		int colonistCount = 10;
		for (int i = 0; i < colonistCount; i++) {

			int randomGridX = ThreadLocalRandom.current().nextInt(0,  Overworld.getTileCount().x);
			int randomGridY = ThreadLocalRandom.current().nextInt(0,  Overworld.getTileCount().y);

			Overworld.addColonist(new Vector2(randomGridX, randomGridY));

		}
	}

	public void handleInput() {

		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {

			for (Entity entity : Overworld.getAllEntities()) {
				if (!(entity instanceof ColonistEntity)) {
					continue;
				}

				ColonistEntity colonist = (ColonistEntity) entity;
				colonist.goTo(Overworld.convertScreenToGrid(new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())));
			}
		}
	}


	@Override
	public void render () {
		handleInput();
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();

		Overworld.update();
		Overworld.render();

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		for (Texture texture : textures) {
			texture.dispose();
		}
		isRunning = false;
	}

	public static Texture getTexture(int tileId) {
		return textures.get(tileId);
	}
}
