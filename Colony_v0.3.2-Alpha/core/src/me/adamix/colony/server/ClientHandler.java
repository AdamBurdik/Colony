package me.adamix.colony.server;

import com.esotericsoftware.kryonet.Connection;
import me.adamix.colony.packet.Packet;
import me.adamix.colony.packet.client.ChunkPacket;
import me.adamix.colony.packet.general.TileUpdatePacket;
import me.adamix.colony.packet.server.ChunkRequestPacket;
import me.adamix.colony.packet.server.LimboDataPacket;
import me.adamix.colony.server.world.chunk.ServerChunk;
import me.adamix.colony.util.Vector2;

import java.util.UUID;

public class ClientHandler {

    private boolean isListening = true;
    private final UUID uuid;
    private String username = null;
	private int clientId;
    private boolean isLimbo = true;
	private final Connection connection;

    public ClientHandler(Connection connection) {
	    this.uuid = UUID.randomUUID();
		this.connection = connection;
    }

	public void onConnect() {
		ColonyServer.addClient(this);
		ColonyServer.info(getPrefix(), "connected to limbo!");
	}

	public void onDisconnect() {
		disconnect();
	}

	public void onReceive(Packet packet) {
		if (packet instanceof LimboDataPacket) {
			LimboDataPacket limboDataPacket = (LimboDataPacket) packet;
			this.username = limboDataPacket.username;
			isLimbo = false;
			ColonyServer.removeLimboClient(this);
			ColonyServer.addClient(this);
			ColonyServer.info(getPrefix(), "passed limbo verification!");
		}
		if (packet instanceof ChunkRequestPacket) {
			onPlayerChunkRequest((ChunkRequestPacket) packet);
		}
		if (packet instanceof TileUpdatePacket) {
			TileUpdatePacket tileUpdatePacket = (TileUpdatePacket) packet;
//			ColonyServer.info(getPrefix(), "received tile update packet! Chunk at: " + tileUpdatePacket.chunkGridPos + ", Tile at: " + tileUpdatePacket.tileGridPos + " with id: " + tileUpdatePacket.tileId);
			ServerChunk chunk = ColonyServer.world.getChunk(tileUpdatePacket.chunkGridPos);
			chunk.addTile(tileUpdatePacket.tileGridPos, tileUpdatePacket.tileId);
			ColonyServer.sendPacketToALlClients(tileUpdatePacket);
		}
	}

	private void onPlayerChunkRequest(ChunkRequestPacket packet) {
		Vector2 chunkGridPos = new Vector2(packet.chunkGridPos.x, packet.chunkGridPos.y);

		Thread thread = new Thread(() -> {
			if (!ColonyServer.world.containsChunk(chunkGridPos)) {
				ColonyServer.world.generateChunk(chunkGridPos);
			}
			ServerChunk chunk = ColonyServer.world.getChunk(chunkGridPos);

			if (!isListening) {
				return;
			}

			sendPacket(chunk.getPacket());
		});
		thread.start();
	}

	public void onIdle() {
		ColonyServer.info(getPrefix(), "is idle!");
	}











//
//	private void onLimboAuthenticationPacket(LimboAuthenticationDataPacket packet) throws SocketException {
//
//		this.isLimbo = false;
//		this.username = packet.username;
//
//		ColonyServer.addClient(this);
//		ColonyServer.success(getPrefix(), "Passed limbo authentication process!");
//
//	}

//	private void onPlayerChunkRequest(ChunkRequestPacket packet) {
//		Vector2 chunkGridPos = new Vector2(packet.getGridPosX(), packet.getGridPosY());
//
//		Thread thread = new Thread(() -> {
//			if (!ColonyServer.world.containsChunk(chunkGridPos)) {
//				ColonyServer.world.generateChunk(chunkGridPos);
//			}
//			ServerChunk chunk = ColonyServer.world.getChunk(chunkGridPos);
//
//			if (!isListening) {
//				return;
//			}
//
//			sendPacket(chunk.getPacket());
//
//		});
//		thread.start();
//	}

//    private void onPacketReceived(Packet packet) throws SocketException {
//		if (isLimbo) {
//			if (packet instanceof LimboAuthenticationDataPacket) {
//				onLimboAuthenticationPacket((LimboAuthenticationDataPacket) packet);
//			} else {
//				sendPacket(new KickPacket("Server is expecting limbo authentication packet!"));
//				disconnect();
//				return;
//			}
//		}
//	    if (packet instanceof ChunkRequestPacket) {
//		    onPlayerChunkRequest((ChunkRequestPacket) packet);
//	    }
//		if (packet instanceof TileUpdatePacket) {
//			TileUpdatePacket tileUpdatePacket = (TileUpdatePacket) packet;
//			Logger.info("server", "received tile update packet! Chunk at: " + tileUpdatePacket.getChunkGridPos() + ", Tile at: " + tileUpdatePacket.getTileGridPos() + " with id: " + tileUpdatePacket.getTileId());
//			ServerChunk chunk = ColonyServer.world.getChunk(tileUpdatePacket.getChunkGridPos());
//			chunk.addTile(tileUpdatePacket.getTileGridPos(), tileUpdatePacket.getTileId());
//			ColonyServer.sendPacketToAllClients(
//					tileUpdatePacket);
//		}
//    }

    public void disconnect() {
		if (!isListening) {
			return;
		}
	    ColonyServer.info(getPrefix(), "disconnected");
	    ColonyServer.removeClient(this);
	    isListening = false;

	    connection.close();
    }

	public void sendPacket(Packet packet) {
		connection.sendTCP(packet);
	}

    public int getId() {
		return connection.getID();
    }

    public String getPrefix() {
        if (username != null) {
            return username;
        }
        return Integer.toString(getId());
    }

}
