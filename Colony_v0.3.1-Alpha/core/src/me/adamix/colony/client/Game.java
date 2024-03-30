package me.adamix.colony.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import me.adamix.colony.client.socket.Client;
import me.adamix.colony.client.world.ClientWorld;
import me.adamix.colony.packet.server.LimboAuthenticationDataPacket;
import me.adamix.colony.server.Server;
import me.adamix.colony.util.Vector2;

import java.util.HashMap;

public class Game extends ApplicationAdapter {
	public SpriteBatch batch;
	public Client client;

	private final boolean isHost = true;
	private final String hostAddress = "127.0.0.1";
	private final int hostPort = 5000;
	private final String username = "AdamIx";

	public Vector2 offset = new Vector2(0, 0);
	public Vector2 playerPosition = new Vector2(0, 0);
	public Vector2 playerGridPos = new Vector2(99999, 9999);
	public ClientWorld world;
	private final HashMap<String, Texture> textures = new HashMap<>();
	public final float zoom = 2f;

	private final byte cameraSpeed = 5;
	private boolean isSprinting = false;

	private void loadResources() {
		textures.put("colony:grass_tile", new Texture("grass_tile.png"));
		textures.put("colony:water_tile", new Texture("water_tile.png"));
		textures.put("colony:debug_tile", new Texture("debug_tile.png"));
	}

	@Override
	public void create () {
		loadResources();
		batch = new SpriteBatch();

		world = new ClientWorld(this);

		if (isHost) {
			Server.startServer(hostPort);
		}

		client = new Client(this);
		client.connect(hostAddress, hostPort);

		client.sendPacket(new LimboAuthenticationDataPacket(username, 69));
		world.onPlayerMove(playerPosition);
	}

	private void update() {
		handleInput();

		playerPosition.x = Gdx.graphics.getWidth() / 2 + offset.x ;
		playerPosition.y = Gdx.graphics.getHeight() / 2 + offset.y;

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
			movement.y = 1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			movement.y = -1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			movement.x = -1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			movement.x = 1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
			isSprinting = true;
		} else {
			isSprinting = false;
		}
//		if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
//
//			Vector2 mousePos = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
//
//			Vector2 chunkGridPos = world.getChunkGridPos(mousePos);
//			ClientChunk chunk = world.getChunk(chunkGridPos);
//
//			Vector2 screenPositionRelativeToChunk = new Vector2(
//					mousePos.x - world.getChunkScreenPos(chunkGridPos).x,
//					mousePos.y - world.getChunkScreenPos(chunkGridPos).y
//			);
//
//			Vector2 tileGridPos = world.getTileGridPos(screenPositionRelativeToChunk);
//			ClientTile tile = chunk.getTile(tileGridPos);
//
//			client.sendPacket(new TileUpdatePacket(chunkGridPos, tileGridPos, "colony:water_tile"));
//		}

		if (movement.x != 0 || movement.y != 0) {

			int boost = 1;
			if (isSprinting) {
				boost = 2;
			}
			offset.x += movement.x * (cameraSpeed * boost);
			offset.y += movement.y * (cameraSpeed * boost);

			world.onPlayerMove(playerPosition);
		}


	}

	public void exit() {
		Gdx.app.exit();
	}

	@Override
	public void dispose () {
		batch.dispose();
		client.disconnect();
		if (isHost) {
			Server.stopServer();
		}
	}
	public Texture getTexture(String id) {
		return textures.get(id);
	}

}
