package me.adamix.colony.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Resources {

	private static final List<Texture> tileTextures = new ArrayList<>();

	public static void loadAllTextures() {
		loadTileTextures();
	}

	public static void loadTileTextures() {
		String path = Gdx.files.getLocalStoragePath() + "assets/tiles";
		File file = new File(path);
		for (String tile : Objects.requireNonNull(file.list())) {
			tileTextures.add(new Texture(path + "/" + tile));
		}
	}


	public static Texture getTileTextures(int tileId) {
		return tileTextures.get(tileId);
	}


}
