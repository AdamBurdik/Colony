package me.adamix.colony.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import me.adamix.colony.client.network.ColonyClient;
import me.adamix.colony.client.world.ClientWorld;
import me.adamix.colony.client.world.chunk.ClientChunk;
import me.adamix.colony.client.world.tile.ClientTile;
import me.adamix.colony.packet.general.TileUpdatePacket;
import me.adamix.colony.packet.server.LimboDataPacket;
import me.adamix.colony.server.ColonyServer;
import me.adamix.colony.util.Network;
import me.adamix.colony.util.Vector2;

import java.util.HashMap;

public class Game extends ApplicationAdapter {
	public SpriteBatch batch;
	public ColonyClient colonyClient;

	private final boolean isHost = false;
	private final String username = "AdamIx";

	public Vector2 offset = new Vector2(0, 0);
	public Vector2 playerPosition = new Vector2(0, 0);
	public Vector2 playerGridPos = new Vector2(99999, 9999);
	public ClientWorld world;
	private final HashMap<String, Texture> textures = new HashMap<>();
	public final float zoom = 2f;

	private final byte cameraSpeed = 5;
	private boolean isSprinting = false;

	private Vector2 lastClickedTileGridPos;
	private String selectedTileId = "colony:water_tile";

	private void loadResources() {
		textures.put("colony:grass_tile", new Texture("grass_tile.png"));
		textures.put("colony:water_tile", new Texture("water_tile.png"));
		textures.put("colony:debug_tile", new Texture("debug_tile.png"));
	}

	@Override
	public void create () {
//		ObjectSizePrinter.setInstrumentation(new MyIn());
		loadResources();
		batch = new SpriteBatch();

		world = new ClientWorld(this);

		playerPosition.x = Gdx.graphics.getWidth() / 2 + offset.x ;
		playerPosition.y = Gdx.graphics.getHeight() / 2 + offset.y;

		if (isHost) {
			ColonyServer.startServer(Network.port);
		}

		colonyClient = new ColonyClient(this, 5000, "127.0.1.2", Network.port);

		colonyClient.sendPacket(new LimboDataPacket(username));
		world.onPlayerMove(playerPosition);
	}

	private void update() {
		handleInput();

		playerPosition.x = (Gdx.graphics.getWidth() / 2 + offset.x) - 1;
		playerPosition.y = (Gdx.graphics.getHeight() / 2 + offset.y) - 1;

		Gdx.graphics.setTitle("Colony v0.3.1-Alpha     FPS: " + Gdx.graphics.getFramesPerSecond() + "     Client: " + username + "     Server: " + isHost);
	}

	@Override
	public void render () {
		update();
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();

		world.render();

		batch.end();
	}

	private void handleInput() {

		Vector2 movement = new Vector2(0, 0);

		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			movement.y += 1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			movement.y += -1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			movement.x += -1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			movement.x += 1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
			isSprinting = true;
		} else {
			isSprinting = false;
		}

		if (movement.x != 0 || movement.y != 0) {

			int boost = 1;
			if (isSprinting) {
				boost = 2;
			}
			offset.x += movement.x * (cameraSpeed * boost);
			offset.y += movement.y * (cameraSpeed * boost);

			world.onPlayerMove(playerPosition);
		}

		if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {

			Vector2 mousePos = new Vector2(Gdx.input.getX() + offset.x, Gdx.graphics.getHeight() - Gdx.input.getY() + offset.y);

			Vector2 chunkGridPos = world.getChunkGridPos(mousePos);
			ClientChunk chunk = world.getChunk(chunkGridPos);

			if (chunk == null) {
				return;
			}

			Vector2 screenPositionRelativeToChunk = new Vector2(
					mousePos.x - world.getChunkScreenPos(chunkGridPos).x,
					mousePos.y - world.getChunkScreenPos(chunkGridPos).y
			);

			Vector2 tileGridPos = world.getTileGridPos(screenPositionRelativeToChunk);

			if (tileGridPos.equals(lastClickedTileGridPos)) {
				return;
			}

			lastClickedTileGridPos = tileGridPos;

			ClientTile tile = chunk.getTile(tileGridPos);

			chunk.addTile(tileGridPos, "colony:water_tile");
			colonyClient.sendPacket(new TileUpdatePacket(chunkGridPos, tileGridPos, selectedTileId));

//			colonyClient.sendPacket(new TileUpdatePacket(chunkGridPos, tileGridPos, "colony:water_tile"));
		}

	}

	public void exit() {
		Gdx.app.exit();
	}

	@Override
	public void dispose () {
		batch.dispose();
//		colonyClient.disconnect();
		colonyClient.disconnect();
		if (isHost) {
			ColonyServer.stopServer();
		}
	}
	public Texture getTexture(String id) {
		return textures.get(id);
	}

}
