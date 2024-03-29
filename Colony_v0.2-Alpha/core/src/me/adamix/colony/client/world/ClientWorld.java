package me.adamix.colony.client.world;

import me.adamix.colony.client.Client;
import me.adamix.colony.client.world.chunk.ClientChunk;
import me.adamix.colony.server.world.chunk.ServerChunk;
import me.adamix.colony.util.Vector2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientWorld {

    // Chunk width and height in tiles
    public byte chunkSize = 16;
    // Tile width and height in pixels
    public int tileSize = 48;
    private ConcurrentHashMap<Vector2, ClientChunk> chunks = new ConcurrentHashMap<>();
    private Client client;

    public ClientWorld(Client client) {
        this.client = client;
        this.tileSize *= client.scale;
    };

    public void addChunk(Vector2 gridPos, ArrayList<String> tileIds) {
        if (chunks.containsKey(gridPos)) {
            return;
        }
        ClientChunk chunk = new ClientChunk(gridPos);
        chunk.addTiles(tileIds, this);
        chunks.put(gridPos, chunk);
    }

    public void render(Client client) {
        for (ClientChunk chunk : chunks.values()) {
            chunk.render(client);
        }
    }

    public Vector2 getChunkScreenPos(Vector2 gridPos) {
        return new Vector2(
                gridPos.x * chunkSize * tileSize,
                gridPos.y * chunkSize * tileSize
        );
    }
//
//    private int customRound(float x) {
//        if (x >= 0) {
//            return (int) Math.floor(x);
//        }
//        else {
//            return (int) Math.ceil(x - 1);
//        }
//    }
//
//    public Vector2 getChunkGridPos(Vector2 screenPos) {
//        return new Vector2(
//                customRound((float) screenPos.x / (16 * 16)),
//                customRound((float) screenPos.y / (16 * 16))
//        );
//    }

}
