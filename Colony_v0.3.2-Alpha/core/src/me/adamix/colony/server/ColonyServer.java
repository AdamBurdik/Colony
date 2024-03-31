package me.adamix.colony.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import me.adamix.colony.packet.Packet;
import me.adamix.colony.server.world.ServerWorld;
import me.adamix.colony.util.Logger;
import me.adamix.colony.util.Network;
import me.adamix.colony.util.Vector2;


import java.io.IOException;
import java.util.HashMap;

public class ColonyServer {

    private static boolean isListening = true;
    private static final HashMap<Integer, ClientHandler> connectedClients = new HashMap<>();
    private static final HashMap<Integer, ClientHandler> connectedLimboClients = new HashMap<>();

    public static ServerWorld world;
    private static Server server;

    public static void startServer(int port) {

        world = new ServerWorld(100);

        server = new Server(16384, 2048 * 3);
        server.start();
        Network.register(server);

        try {
            server.bind(port);
            Logger.success("server", "Server started!");
        } catch (IOException e) {
            Logger.error("server", e.getMessage());
        }

        server.addListener(new Listener() {
            @Override
            public void connected (Connection connection) {
                ClientHandler clientHandler = new ClientHandler(connection);
                clientHandler.onConnect();
            }
            public void disconnected (Connection connection) {
                ClientHandler clientHandler = connectedClients.get(connection.getID());
                clientHandler.onDisconnect();
            }
            public void received (Connection connection, Object object) {
                if (!(object instanceof Packet)) {
                    return;
                }
                ClientHandler clientHandler = connectedClients.get(connection.getID());
                clientHandler.onReceive((Packet) object);
            }
//            public void idle (Connection connection) {
//                ClientHandler clientHandler = connectedClients.get(connection.getID());
//                clientHandler.onIdle();
//            }
        });

    }

    public static void addClient(ClientHandler clientHandler) {
        connectedClients.put(clientHandler.getId(), clientHandler);
    }

    public static void removeClient(ClientHandler clientHandler) {
        connectedClients.remove(clientHandler.getId());
    }

    public static void addLimboClient(ClientHandler clientHandler) {
        connectedLimboClients.put(clientHandler.getId(), clientHandler);
    }

    public static void removeLimboClient(ClientHandler clientHandler) {
        connectedLimboClients.remove(clientHandler.getId());
    }

//
//    public static void sendPacket(Packet packet) {
//
//    }

    public static void stopServer() {
        server.stop();
    }

    public static void sendPacketToALlClients(Packet packet) {
        server.sendToAllTCP(packet);
    }

//    public static void addClient(ClientHandler clientHandler) {
//        connectedLimboIps.remove(clientHandler.getIp());
//        connectedIps.put(clientHandler.getIp(), clientHandler.getUUID());
//        connectedClients.put(clientHandler.getUUID(), clientHandler);
//    }
//
//    public static void removeClient(ClientHandler clientHandler) {
//        connectedLimboIps.remove(clientHandler.getIp());
//        connectedClients.remove(clientHandler.getUUID());
//        connectedIps.remove(clientHandler.getIp());
//    }

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
