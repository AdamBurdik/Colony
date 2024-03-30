package me.adamix.colony.server;

import me.adamix.colony.packet.Packet;
import me.adamix.colony.packet.client.KickPacket;
import me.adamix.colony.packet.general.PingPacket;
import me.adamix.colony.packet.server.ChunkRequestPacket;
import me.adamix.colony.packet.server.LimboAuthenticationDataPacket;
import me.adamix.colony.server.world.chunk.ServerChunk;
import me.adamix.colony.util.Logger;
import me.adamix.colony.util.Vector2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.UUID;

public class ClientHandler {

    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private boolean isListening = true;
    private UUID uuid;
    private String username = null;
	private int clientId;
    private boolean isLimbo = true;

    public ClientHandler(Socket socket) {
		try {
			this.socket = socket;
			this.uuid = UUID.randomUUID();

			this.objectInputStream = new ObjectInputStream(socket.getInputStream());
			this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

		} catch (Exception e) {
			Server.error(getPrefix(), "Failed to create client handler! Error: " + e.getMessage());
		}
    }

	private void onLimboAuthenticationPacket(LimboAuthenticationDataPacket packet) throws SocketException {

		this.isLimbo = false;
		socket.setSoTimeout(0);
		this.username = packet.getUsername();
		this.clientId = packet.getClientId();

		Server.addClient(this);
		Server.success(getPrefix(), "Passed limbo authentication process!");

	}

	private void onPlayerChunkRequest(ChunkRequestPacket packet) {
		Vector2 chunkGridPos = new Vector2(packet.getGridPosX(), packet.getGridPosY());

		Thread thread = new Thread(() -> {
			if (!Server.world.containsChunk(chunkGridPos)) {
				Server.world.generateChunk(chunkGridPos);
			}
			ServerChunk chunk = Server.world.getChunk(chunkGridPos);

			if (!isListening) {
				return;
			}

			// Synchronize access to ObjectOutputStream
			synchronized (objectOutputStream) {
				sendPacket(chunk.getPacket());
			}
		});
		thread.start();
	}

    private void onPacketReceived(Packet packet) throws SocketException {
		if (isLimbo) {
			if (packet instanceof LimboAuthenticationDataPacket) {
				onLimboAuthenticationPacket((LimboAuthenticationDataPacket) packet);
			} else {
				sendPacket(new KickPacket("Server is expecting limbo authentication packet!"));
				disconnect();
				return;
			}
		}
	    if (packet instanceof ChunkRequestPacket) {
		    onPlayerChunkRequest((ChunkRequestPacket) packet);
	    }
    }

    public void listenToClient() {
        Thread thread = new Thread(() -> {
			try {
				while (isListening) {
					Object object = objectInputStream.readObject();

					if (object instanceof Packet) {
						onPacketReceived((Packet) object);
					}
				}
			} catch (IOException | ClassNotFoundException e) {
				disconnect();
			}
        });
	    thread.start();
    }

    public void disconnect() {
		if (!isListening) {
			return;
		}
	    Server.info(getPrefix(), "disconnected");
	    Server.removeClient(this);
	    isListening = false;

	    try {
		    objectInputStream.close();
            objectOutputStream.close();
            socket.close();
	    } catch (Exception e) {
		    Logger.error(getPrefix(), e.getMessage());
	    }
    }

    public void sendPacket(Packet packet) {
        try {
            objectOutputStream.writeObject(packet);
			objectOutputStream.flush();
        } catch (Exception e) {
	        Logger.error(getPrefix(), e.getMessage());
        }
    }

    public String getIp() {
        return socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public String getPrefix() {
        if (username != null) {
            return username;
        }
        return getIp();
    }

}
