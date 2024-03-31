package me.adamix.colony.client.world.chunk;

import me.adamix.colony.client.Game;
import me.adamix.colony.client.world.ClientWorld;
import me.adamix.colony.client.world.tile.ClientTile;
import me.adamix.colony.util.Vector2;

import java.util.ArrayList;
import java.util.HashMap;

public class ClientChunk {

    public Vector2 gridPos;
    private HashMap<Vector2, ClientTile> tiles = new HashMap<>();

    public ClientChunk(Vector2 gridPos) {
        this.gridPos = gridPos;
    }

    public void addTiles(ArrayList<String> tileIds, ClientWorld world) {
        int i = 0;
        Vector2 tilePosition;
        ClientTile tile;
        for (int y = 0; y < world.chunkSize; y++) {
            for (int x = 0; x < world.chunkSize; x++) {
                tilePosition = new Vector2(x, y);
                tile = new ClientTile(tilePosition, tileIds.get(i));
                tiles.put(tilePosition, tile);
            }
        }
    }

    public void render(Game game) {
        for (ClientTile tile : tiles.values()) {
            tile.render(game, game.world.getChunkScreenPos(gridPos));
        }
    }

    public ClientTile getTile(Vector2 gridPos) {
        return tiles.get(gridPos);
    }

    public void addTile(Vector2 gridPos, String tileId) {
        tiles.put(gridPos, new ClientTile(gridPos, tileId));
    }

}