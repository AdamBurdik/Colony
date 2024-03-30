package me.adamix.colony.client.socket;

import me.adamix.colony.client.Game;
import me.adamix.colony.packet.Packet;
import me.adamix.colony.packet.client.ChunkPacket;
import me.adamix.colony.packet.client.KickPacket;
import me.adamix.colony.util.Logger;
import me.adamix.colony.util.Vector2;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

	private final Game game;
	private Socket socket;
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;
	private boolean isListening = true;

	public Client(Game game) {
		this.game = game;
	}



	public void connect(String hostAddress, int hostPort) {
		try {
			socket = new Socket(hostAddress, hostPort);


			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectInputStream = new ObjectInputStream(socket.getInputStream());

			listenToServer();
		} catch (Exception e) {
			Logger.error("client", "Cannot connect to server! Error: " + e.getMessage());
		}
	}

	private void onPacketReceived(Packet packet) {
//		Logger.success("client", "Received packet: " + packet);
		if (packet instanceof KickPacket) {
			KickPacket kickPacket = (KickPacket) packet;
			Logger.error("client", "Server has kicked you! Reason: " + kickPacket.getReason());
		}
		if (packet instanceof ChunkPacket) {
			ChunkPacket chunkPacket = (ChunkPacket) packet;
			game.world.addChunk(new Vector2(chunkPacket.getGridPosX(), chunkPacket.getGridPosY()), chunkPacket.getTileIds());
		}
	}


	private void listenToServer() {
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
		Logger.error("client", "disconnected!");
		isListening = false;

		game.exit();

		try {
			objectInputStream.close();
			objectOutputStream.close();
			socket.close();
		} catch (IOException e) {
			Logger.error("client", e.getMessage());
		}
	}

	public void sendPacket(Packet packet) {
		try {
			objectOutputStream.writeObject(packet);
			objectOutputStream.flush();
		} catch (IOException e) {
			Logger.error("client", e.getMessage());
		}
	}

}
