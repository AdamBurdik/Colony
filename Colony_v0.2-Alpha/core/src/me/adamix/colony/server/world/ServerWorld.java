package me.adamix.colony.server.world;

import me.adamix.colony.server.Server;
import me.adamix.colony.server.world.chunk.ServerChunk;
import me.adamix.colony.util.Vector2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServerWorld {

    // Chunk width and height in tiles
    public byte chunkSize = 16;
    // World width and height in chunks
    public int worldSize;
    // Tile width and height in pixels
    public float scale = 1.2f;
    public final int tileSize = (int) (48 * scale);
    private HashMap<Vector2, ServerChunk> chunks = new HashMap<>();

    public ServerWorld(int worldSize) {
        this.chunkSize = chunkSize;
        this.worldSize = worldSize;
    }

    public ServerChunk generateChunk(Vector2 chunkGridPos) {
        ServerChunk chunk = new ServerChunk(chunkGridPos);
        chunk.generate(this);
        chunks.put(chunkGridPos, chunk);
        return chunk;
    }

    public ServerChunk getChunk(Vector2 chunkGridPos) {
        return chunks.getOrDefault(chunks.get(chunkGridPos), generateChunk(chunkGridPos));
    }

    public List<Vector2> getCircularChunkGridPos(int x, int y, int range) {
        List<Vector2> neighbors = new ArrayList<>();
        for (int i = -range; i <= range; i++) {
            for (int j = -range; j <= range; j++) {
                if (i * i + j * j <= range * range) {
                    neighbors.add(new Vector2(x + i, y + j));
                }
            }
        }
        return neighbors;
    }

    public void log(String message) {
        Server.log("[WORLD] " + message);
    }

}
