package me.adamix.colony.server;

import me.adamix.colony.packet.Packet;
import me.adamix.colony.packet.general.PingPacket;
import me.adamix.colony.packet.server.LimboDataPacket;
import me.adamix.colony.packet.server.UpdatePositionPacket;
import me.adamix.colony.server.world.chunk.ServerChunk;
import me.adamix.colony.util.Vector2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SocketClientHandler {

    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private boolean isListening = true;
    private UUID uuid;
    private String username = null;
    private boolean isLimbo;
    private Vector2 clientPos = new Vector2(-100, -100);
    private Vector2 clientGridPos = new Vector2(-100, -100);
    private List<Vector2> clientChunks = new ArrayList<>();

    public SocketClientHandler(Socket socket) throws IOException {
        this.isLimbo = true;
        this.socket = socket;
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.uuid = UUID.randomUUID();
    }

    private void onPacketReceived(Packet packet) {
        if (packet instanceof LimboDataPacket) {
            onLimboPacketReceived((LimboDataPacket) packet);
        }
        if (packet instanceof UpdatePositionPacket) {
            onPlayerMove((UpdatePositionPacket) packet);
        }
        if (packet instanceof PingPacket) {
            sendPacket(new PingPacket(System.currentTimeMillis()));
        }
    }

    private void onPlayerMove(UpdatePositionPacket packet) {
        clientPos = new Vector2(packet.getX(), packet.getY());
        Vector2 newClientGridPos = Server.getChunkGridPos(clientPos);
        if (newClientGridPos.x != clientGridPos.x || newClientGridPos.y != clientGridPos.y) {
            clientGridPos = newClientGridPos;
            List<Vector2> circularNeighbors = Server.world.getCircularChunkGridPos(newClientGridPos.x, newClientGridPos.y, 2);
            ServerChunk chunk;
            for (Vector2 chunkGridPos : circularNeighbors) {
                if (!clientChunks.contains(chunkGridPos)) {
                    chunk = Server.world.getChunk(chunkGridPos);
                    sendPacket(chunk.getPacket());
                }
            }
            clientChunks.addAll(circularNeighbors);
        }

    }

    private void onLimboPacketReceived(LimboDataPacket packet) {
        this.username = packet.getUsername();
        this.isLimbo = false;
        Server.removeLimboClient(this);
        Server.addClient(this);
    }

    public void listenToClient() {
        Thread clientThread = new Thread(() -> {
            try {
                while (isListening && !Thread.currentThread().isInterrupted()) {
                    Object object = objectInputStream.readObject();
                    if (object == null) {
                        continue;
                    }

                    if (object instanceof Packet) {
                        onPacketReceived((Packet) object);
                    }
                }
            } catch (IOException e) {
                // Handle IO exceptions (e.g., connection closed)
                disconnect();
            } catch (ClassNotFoundException e) {
                // Handle class not found exception
                // Consider logging the error or taking other appropriate action
                e.printStackTrace();
            } finally {
                // Close the ObjectInputStream to release resources
                try {
                    objectInputStream.close();
                    objectOutputStream.close();
                } catch (IOException e) {
                    // Handle IOException when closing the stream
                    e.printStackTrace();
                }
            }
        });
        clientThread.start();
    }

    public void disconnect() {
        if (isLimbo) {
            Server.removeLimboClient(this);
        }
        else {
            Server.removeClient(this);
        }
	    try {
		    objectInputStream.close();
            objectOutputStream.close();
            socket.close();
	    } catch (IOException e) {
		    e.printStackTrace();
	    }
    }

    public void sendPacket(Packet packet) {
        try {
            objectOutputStream.writeObject(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    public void log(String message) {
        Server.log("(" + getPrefix() + ") " + message);
    }

}
