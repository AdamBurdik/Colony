package me.adamix.colony.client.network;


import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.adamix.colony.client.Game;
import me.adamix.colony.client.world.chunk.ClientChunk;
import me.adamix.colony.packet.Packet;
import me.adamix.colony.packet.client.ChunkPacket;
import me.adamix.colony.packet.general.TileUpdatePacket;
import me.adamix.colony.packet.server.LimboDataPacket;
import me.adamix.colony.server.ColonyServer;
import me.adamix.colony.util.Logger;
import me.adamix.colony.util.Network;
import me.adamix.colony.util.Vector2;
import java.io.IOException;

public class ColonyClient {

	private final Game game;
	private Client client;
	private boolean isListening = true;

	public ColonyClient(Game game, int timeout, String hostAddress, int hostPort) {
		this.game = game;

		client = new Client(8192, 2048 * 3);
		client.start();
		Network.register(client);

		try {
			client.connect(timeout, hostAddress, hostPort);
			Logger.success("client", "Connected to server!");
		} catch (IOException e) {
			Logger.error("client", e.getMessage());
		}


		client.addListener(new Listener() {
			public void connected (Connection connection) {
				onConnect();
			}

			public void disconnected (Connection connection) {
				onDisconnect();
			}

			public void received (Connection connection, Object object) {
				if (!(object instanceof Packet)) {
					return;
				}
				onReceive((Packet) object);
			}

//			public void idle (Connection connection) {
//				onIdle();
//			}
		});
	}

	public void onConnect() {
	}

	public void onDisconnect() {
	}

	public void onReceive(Packet packet) {
//		Logger.success("client", "Received packet: " + packet);
		if (packet instanceof ChunkPacket) {
			ChunkPacket chunkPacket = (ChunkPacket) packet;
			game.world.addChunk(new Vector2(chunkPacket.gridPosX, chunkPacket.gridPosY), chunkPacket.tiles);
		}
		if (packet instanceof TileUpdatePacket) {
			TileUpdatePacket tileUpdatePacket = (TileUpdatePacket) packet;
//			Logger.info("client", "received tile update packet! Chunk at: " + tileUpdatePacket.chunkGridPos + ", Tile at: " + tileUpdatePacket.tileGridPos + " with id: " + tileUpdatePacket.tileId);
			ClientChunk chunk = game.world.getChunk(tileUpdatePacket.chunkGridPos);
			chunk.addTile(tileUpdatePacket.tileGridPos, tileUpdatePacket.tileId);
		}
	}

	public void onIdle() {
	}



//		if (object instanceof KickPacket) {
//			KickPacket kickPacket = (KickPacket) packet;
//			Logger.error("client", "Server has kicked you! Reason: " + kickPacket.getReason());
//		}
//		if (object instanceof TileUpdatePacket) {
//			TileUpdatePacket tileUpdatePacket = (TileUpdatePacket) packet;
//			Logger.info("client", "received tile update packet! Chunk at: " + tileUpdatePacket.getChunkGridPos() + ", Tile at: " + tileUpdatePacket.getTileGridPos() + " with id: " + tileUpdatePacket.getTileId());
//			ClientChunk chunk = game.world.getChunk(tileUpdatePacket.getChunkGridPos());
//			chunk.addTile(tileUpdatePacket.getTileGridPos(), tileUpdatePacket.getTileId());
//		}
//	}

//
//	private void listenToServer() {
//		Thread thread = new Thread(() -> {
//			try {
//				while (isListening) {
//					String jsonPacket = reader.readLine();
//					Packet packet = gson.fromJson(jsonPacket, Packet.class);
//					Logger.info("client", "received object: " + packet);
//					onPacketReceived(packet);
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//				Logger.error("client", e.getMessage());
////				disconnect();
//			}
//
//		});
//		thread.start();
//	}

	public void sendPacket(Packet packet) {
		client.sendTCP(packet);
	}

	public void disconnect() {
		client.close();
	}

}
