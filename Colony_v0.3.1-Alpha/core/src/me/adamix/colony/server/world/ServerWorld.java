package me.adamix.colony.server.world;

import me.adamix.colony.server.Server;
import me.adamix.colony.server.world.chunk.ServerChunk;
import me.adamix.colony.util.Vector2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ServerWorld {

    // Chunk width and height in tiles
    public byte chunkSize = 16;
    // World width and height in chunks
    public int worldSize;
    private ConcurrentHashMap<Vector2, ServerChunk> chunks = new ConcurrentHashMap<>();

    public ServerWorld(int worldSize) {
        this.worldSize = worldSize;
    }

    public void generateChunk(Vector2 chunkGridPos) {
        ServerChunk chunk = new ServerChunk(chunkGridPos);
        chunk.generate(this);
        chunks.put(chunkGridPos, chunk);
    }

    public ServerChunk getChunk(Vector2 chunkGridPos) {
        ServerChunk chunk = chunks.get(chunkGridPos);
        if (chunk != null) {
            return chunk;
        }
        generateChunk(chunkGridPos);
        return null;
    }

    public boolean containsChunk(Vector2 chunkGridPos) {
        return chunks.get(chunkGridPos) != null;
    }

    public void log(Object message) {
        Server.info("world", message);
    }

}
