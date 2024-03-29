package me.adamix.colony.server;

import me.adamix.colony.server.world.ServerWorld;
import me.adamix.colony.util.Vector2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.UUID;

public class Server {

    private static ServerSocket serverSocket;
    private static boolean isListening = true;
    private static final short maxClients = 5;
    private static HashMap<UUID, SocketClientHandler> connectedClients = new HashMap<>();
    private static HashMap<String, UUID> connectedClientIps = new HashMap<>();
    private static HashMap<String, SocketClientHandler> limboClientIps = new HashMap<>();
    public static ServerWorld world;

    public static void main(String[] args) {
        startServer(8000);
    }

    public static void startServer(int port) {
        try {
            serverSocket = new ServerSocket(port);

            world = new ServerWorld(10);

            listenToNewConnections();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void stopServer() {
        if (!isListening) {
            return;
        }
        log("Stopping server!");
        isListening = false;

        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

    public static void addLimboClient(SocketClientHandler clientHandler) {
        limboClientIps.put(clientHandler.getIp(), clientHandler);
        clientHandler.log("connected to limbo! waiting for data packet...");
    }

    public static void removeLimboClient(SocketClientHandler clientHandler) {
        limboClientIps.remove(clientHandler.getIp());
    }

    public static void addClient(SocketClientHandler clientHandler) {
        connectedClients.put(clientHandler.getUUID(), clientHandler);
        connectedClientIps.put(clientHandler.getIp(), clientHandler.getUUID());
        clientHandler.log("has connected!");
    }

    public static void removeClient(SocketClientHandler clientHandler) {
        connectedClients.remove(clientHandler.getUUID());
        connectedClientIps.remove(clientHandler.getIp());
        clientHandler.log("has disconnected!");
        if (connectedClients.size() == 0) {
            stopServer();
        }
    }

    public static void listenToNewConnections() {
        Thread newConnectionsThread = new Thread(() -> {
            try {
                log("Started listening to new connections on ip: " + serverSocket.getInetAddress().getHostAddress() + ":" + serverSocket.getLocalPort());
                while (isListening) {
                    Socket socket = serverSocket.accept();

                    SocketClientHandler clientHandler = new SocketClientHandler(socket);

                    clientHandler.listenToClient();
                    addLimboClient(clientHandler);

                }
            } catch (IOException e) {
                stopServer();
            }

        });
        newConnectionsThread.start();

    }

    public static void log(String message) {
        System.out.println("[SERVER] " + message);
    }

    private static int customRound(float x) {
        if (x >= 0) {
            return (int) Math.floor(x);
        }
        else {
            return (int) Math.ceil(x - 1);
        }
    }

    public static Vector2 getChunkGridPos(Vector2 screenPos) {
        return new Vector2(
                customRound((float) screenPos.x / (world.chunkSize * world.tileSize)),
                customRound((float) screenPos.y / (world.chunkSize * world.tileSize))
        );
    }

    public static int getPort() {
        return serverSocket.getLocalPort();
    }

}
