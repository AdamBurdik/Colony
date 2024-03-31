package me.adamix.colony.client.world.chunk;

import me.adamix.colony.client.Game;
import me.adamix.colony.client.world.ClientWorld;
import me.adamix.colony.client.world.tile.ClientTile;
import me.adamix.colony.util.Vector2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientChunk {

    public Vector2 gridPos;
    private HashMap<Vector2, ClientTile> tiles = new HashMap<>();

    public ClientChunk(Vector2 gridPos) {
        this.gridPos = gridPos;
    }

    public void addTiles(HashMap<Vector2, String> tilesTextures, ClientWorld world) {
        for (Map.Entry<Vector2, String> entry : tilesTextures.entrySet()) {
            ClientTile tile = new ClientTile(entry.getKey(), entry.getValue());
            tiles.put(entry.getKey(), tile);
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
