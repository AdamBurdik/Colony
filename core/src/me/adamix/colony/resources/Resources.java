package me.adamix.colony.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Resources {

	private static final List<Texture> tileTextures = new ArrayList<>();
	private static final Map<String, Texture> tiles = new HashMap<>();

	public static void loadAll() {
		loadTiles();
	}

	public static void loadTiles() {
		File tileDataDirectory = new File(Gdx.files.getLocalStoragePath() + "/assets/data/tiles");
		if (!tileDataDirectory.exists()) {
			throw new RuntimeException("Tile data directory does not exist!");
		}

		File[] tileDataFiles = tileDataDirectory.listFiles();
		if (tileDataFiles == null) {
			throw new RuntimeException("Tile data directory is empty!");
		}


		for (File file : tileDataFiles) {
			if (file.isDirectory()) {
				continue;
			}

			try (FileReader fileReader = new FileReader(file)) {
				Gson gson = new Gson();
				JsonObject jsonData = gson.fromJson(fileReader, JsonObject.class);

				String tileName = jsonData.get("name").getAsString();
				String tileId = jsonData.get("id").getAsString();
				String texturePath = jsonData.get("texture").getAsString();
				tiles.put(tileId, new Texture("assets/textures/" + texturePath));
				System.out.println("Loaded tile " + tileName);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}


//		String path = Gdx.files.getLocalStoragePath() + "assets/tiles";
//		File file = new File(path);
//		for (String tile : Objects.requireNonNull(file.list())) {
//			tileTextures.add(new Texture(path + "/" + tile));
//		}
	}


	public static Texture getTileTextures(String tileId) {
		return tiles.get(tileId);
	}


}
