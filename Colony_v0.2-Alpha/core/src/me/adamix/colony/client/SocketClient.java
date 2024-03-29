package me.adamix.colony.client;

import me.adamix.colony.packet.Packet;
import me.adamix.colony.packet.client.ChunkPacket;
import me.adamix.colony.packet.general.PingPacket;
import me.adamix.colony.packet.server.LimboDataPacket;
import me.adamix.colony.packet.server.UpdatePositionPacket;
import me.adamix.colony.util.Vector2;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SocketClient {

    private Socket socket;
    private final int port;
    private final String hostAddress;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private boolean isListening = true;
    private String username;
    private int ping = -1;
    private Client client;

    public SocketClient(Client client, String hostAddress, int port, String username) {
        this.client = client;
        this.port = port;
        this.hostAddress = hostAddress;
        this.username = username;
    }

    public void connect() {
        try {
            this.socket = new Socket(hostAddress, port);

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            InputStream inputStream = socket.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);

            listenToServer();
            sendPacket(new LimboDataPacket(username));
            pingTask();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void disconnect() {
        if (!isListening) {
            return;
        }

        log("disconnected!");
        isListening = false;
        try {
            objectOutputStream.close();
            objectInputStream.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void onPacketReceived(Packet packet) {
//        System.out.println("[CLIENT] received packet " + packet);
        if (packet instanceof PingPacket) {
            PingPacket pingPacket = (PingPacket) packet;
            ping = (int) (System.currentTimeMillis() - pingPacket.getTime());
            System.out.println("Current Ping: " + ping);
        }
        if (packet instanceof ChunkPacket) {
            ChunkPacket chunkPacket = (ChunkPacket) packet;
            log("RECEIVED CHUNK: " + chunkPacket.getGridPosX() + " " + chunkPacket.getGridPosY());
            client.world.addChunk(new Vector2(chunkPacket.getGridPosX(), chunkPacket.getGridPosY()), chunkPacket.getTileIds());
        }
    }

    private void listenToServer() {
        Thread serverThread = new Thread(() -> {
            try {
                while (isListening) {
                    Object object = objectInputStream.readObject();
                    if (object instanceof Packet) {
                        onPacketReceived((Packet) object);
                    }
                }
            } catch (IOException e) {
                disconnect();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        serverThread.start();
    }

    public void pingTask() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (!isListening) {
                    scheduler.shutdown();
                }
                sendPacket(new PingPacket(System.currentTimeMillis()));
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void sendPacket(Packet packet) {
        try {
            objectOutputStream.writeObject(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void log(String message) {
        System.out.println("[CLIENT] " + message);
    }

}
