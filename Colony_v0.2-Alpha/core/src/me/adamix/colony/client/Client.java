package me.adamix.colony.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import me.adamix.colony.client.world.ClientWorld;
import me.adamix.colony.packet.server.UpdatePositionPacket;
import me.adamix.colony.server.Server;
import me.adamix.colony.util.Vector2;
import java.util.HashMap;

public class Client extends ApplicationAdapter {
	public SpriteBatch batch;
	public SocketClient socketClient;
	public Vector2 position = new Vector2(0, 0);
	public Vector2 playerCenterPos = new Vector2(0, 0);
	private final byte cameraSpeed = 10;
	private HashMap<String, Texture> textures = new HashMap<>();
	public float scale = 1.2f;
	public ClientWorld world;

	private final boolean isServer = true;

	private void loadTextures() {
		textures.put("colony:player", new Texture("player.png"));
		textures.put("colony:grass_tile", new Texture("grass_tile.png"));
		textures.put("colony:dirt_tile", new Texture("tile2.png"));
	}

	@Override
	public void create () {
		loadTextures();
		world = new ClientWorld(this);

		playerCenterPos.x = world.chunkSize * world.tileSize / 2;
		playerCenterPos.y = world.chunkSize * world.tileSize / 2;

		playerCenterPos.x = Gdx.graphics.getWidth() / 2 - 32 + position.x + 32;
		playerCenterPos.y = Gdx.graphics.getHeight() / 2 - 32 + position.y + 32;

		if (isServer) {
			Server.startServer(8000);
		}


        socketClient = new SocketClient(this, "127.0.0.1", 8000, "AdamIx");
		batch = new SpriteBatch();

		socketClient.connect();

		socketClient.sendPacket(new UpdatePositionPacket(playerCenterPos.x, playerCenterPos.y));
	}

	private void update() {
		handleInput();

		playerCenterPos.x = Gdx.graphics.getWidth() / 2 + position.x ;
		playerCenterPos.y = Gdx.graphics.getHeight() / 2 + position.y;
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		update();
		batch.begin();

		world.render(this);

		batch.draw(getTexture("colony:player"), Gdx.graphics.getWidth() / 2- 32, Gdx.graphics.getHeight() / 2 - 32, 64, 64);
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

		if (movement.x != 0 || movement.y != 0) {

			position.x += movement.x * cameraSpeed;
			position.y += movement.y * cameraSpeed;

			socketClient.sendPacket(new UpdatePositionPacket(playerCenterPos.x, playerCenterPos.y));
		}


	}

	@Override
	public void dispose () {
		batch.dispose();
		socketClient.disconnect();
		if (isServer) {
			Server.stopServer();
		}
	}

	public Texture getTexture(String id) {
		return textures.get(id);
	}

}
