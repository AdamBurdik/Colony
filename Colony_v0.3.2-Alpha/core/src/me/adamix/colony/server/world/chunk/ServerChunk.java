package me.adamix.colony.server.world.chunk;

import me.adamix.colony.packet.client.ChunkPacket;
import me.adamix.colony.server.world.ServerWorld;
import me.adamix.colony.server.world.tile.ServerTile;
import me.adamix.colony.util.Logger;
import me.adamix.colony.util.Vector2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ServerChunk {

    private Vector2 gridPos;
    private ConcurrentHashMap<Vector2, ServerTile> tiles = new ConcurrentHashMap<>();

    public ServerChunk(Vector2 gridPos) {
        this.gridPos = gridPos;
    }

    // TODO: Better generating
    public void generate(ServerWorld world) {
        for (int y = 0; y < world.chunkSize; y++) {
            for (int x = 0; x < world.chunkSize; x++) {
                Vector2 tilePosition = new Vector2(x, y);
                ServerTile tile = new ServerTile(tilePosition, "colony:grass_tile");
                tiles.put(tilePosition, tile);
            }
        }
    }

    private HashMap<Vector2, String> getTiles() {
        HashMap<Vector2, String> tilesTexture = new HashMap<>();
        for (ServerTile tile : tiles.values()) {
            tilesTexture.put(tile.getGridPos(), tile.getTileId());
        }
        return tilesTexture;
    }

    public void addTile(Vector2 gridPos, String tileId) {
        tiles.remove(gridPos);
        tiles.put(gridPos, new ServerTile(gridPos, tileId));
    }

    public ChunkPacket getPacket() {
        return new ChunkPacket(gridPos.x, gridPos.y, getTiles());
    }

}
