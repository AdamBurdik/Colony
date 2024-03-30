package me.adamix.colony.server;

import me.adamix.colony.client.socket.Client;
import me.adamix.colony.server.world.ServerWorld;
import me.adamix.colony.util.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.UUID;

public class Server {

    private static ServerSocket serverSocket;
    private static boolean isListening = true;
    private static final HashMap<UUID, ClientHandler> connectedClients = new HashMap<>();
    private static final HashMap<String, UUID> connectedIps = new HashMap<>();
    private static final HashMap<String, ClientHandler> connectedLimboIps = new HashMap<>();
    private static final int limboClientTimeout = 1000 * 60; // 60 seconds timeout
    public static ServerWorld world;

    public static void startServer(int port) {
        try {
            world = new ServerWorld(100);
            serverSocket = new ServerSocket(port);

            listenToNewConnections();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void stopServer() {
        try {
            for (ClientHandler client : connectedClients.values()) {
                client.disconnect();
            }

            serverSocket.close();
            isListening = false;
        } catch (IOException e) {
            Logger.error("server", e.getMessage());
        }
    }

    public static void addClient(ClientHandler clientHandler) {
        connectedLimboIps.remove(clientHandler.getIp());
        connectedIps.put(clientHandler.getIp(), clientHandler.getUUID());
        connectedClients.put(clientHandler.getUUID(), clientHandler);
    }

    public static void removeClient(ClientHandler clientHandler) {
        connectedLimboIps.remove(clientHandler.getIp());
        connectedClients.remove(clientHandler.getUUID());
        connectedIps.remove(clientHandler.getIp());
    }

    public static void listenToNewConnections() {
        Thread thread = new Thread(() -> {
            try {
                Logger.info("server", "Started listening to new connections on ip: " + serverSocket.getInetAddress().getHostAddress() + ":" + serverSocket.getLocalPort());
                while (isListening) {
                    Socket socket = serverSocket.accept();

                    Logger.info("server", "New client connected to limbo!");

                    socket.setSoTimeout(limboClientTimeout);
                    ClientHandler clientHandler = new ClientHandler(socket);

                    clientHandler.listenToClient();


                }
            } catch (Exception e) {
				Logger.error("server", "Sever stopped!");
            }

        });
	    thread.start();
    }

    public static void info(String prefix, Object message) {
        Logger.info("server] [" + prefix, message.toString());
    }

    public static void success(String prefix, Object message) {
        Logger.success("server] [" + prefix, message.toString());
    }

    public static void error(String prefix, Object message) {
        Logger.error("server] [" + prefix, message.toString());
    }

    public static void warning(String prefix, Object message) {
        Logger.warning("server] [" + prefix, message.toString());
    }

}
