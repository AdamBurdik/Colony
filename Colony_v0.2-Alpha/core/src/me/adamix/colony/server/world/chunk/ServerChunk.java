package me.adamix.colony.server.world.chunk;

import me.adamix.colony.packet.client.ChunkPacket;
import me.adamix.colony.server.world.ServerWorld;
import me.adamix.colony.server.world.tile.ServerTile;
import me.adamix.colony.util.Vector2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerChunk {

    private Vector2 gridPos;
    private HashMap<Vector2, ServerTile> tiles = new HashMap<>();

    public ServerChunk(Vector2 gridPos) {
        this.gridPos = gridPos;
    }

    // TODO: Better generating
    public void generate(ServerWorld world) {
        world.log("Generating new chunk: " + gridPos);
        Vector2 tilePosition;
        ServerTile tile;
        for (int y = 0; y < world.chunkSize; y++) {
            for (int x = 0; x < world.chunkSize; x++) {
                tilePosition = new Vector2(x, y);
                tile = new ServerTile(tilePosition, "colony:grass_tile");
                tiles.put(tilePosition, tile);

            }
        }
    }

    private ArrayList<String> getTileIds() {
        ArrayList<String> tileIds = new ArrayList<>();
        for (ServerTile tile : tiles.values()) {
            tileIds.add(tile.getTileId());
        }
        return tileIds;
    }

    public ChunkPacket getPacket() {
        return new ChunkPacket(gridPos.x, gridPos.y, getTileIds());
    }

}
