package me.adamix.colony.client.world;

import com.badlogic.gdx.Gdx;
import me.adamix.colony.client.Game;
import me.adamix.colony.client.world.chunk.ClientChunk;
import me.adamix.colony.packet.server.ChunkRequestPacket;
import me.adamix.colony.util.Vector2;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ClientWorld {

    // Chunk width and height in tiles
    public byte chunkSize = 16;
    // Tile width and height in pixels
    public final int originalTileSize = 32;
    public int tileSize = 32;
    private final ConcurrentHashMap<Vector2, ClientChunk> chunks = new ConcurrentHashMap<>();
    private Game game;

    public ClientWorld(Game client) {
        this.game = client;
        this.tileSize = (int) (originalTileSize * client.zoom);
    };

    public void addChunk(Vector2 gridPos, HashMap<Vector2, String> tiles) {
        if (chunks.containsKey(gridPos)) {
            return;
        }
        ClientChunk chunk = new ClientChunk(gridPos);
        chunk.addTiles(tiles, this);
        chunks.put(gridPos, chunk);
    }

    private List<Vector2> getVisibleChunks() {

        Vector2 maxVisibleChunkCount = new Vector2(
                Math.round((float) Gdx.graphics.getWidth() / (chunkSize * tileSize)) + 2,
                Math.round((float) Gdx.graphics.getHeight() / (chunkSize * tileSize)) + 2
        );

        List<Vector2> visibleChunks = new ArrayList<>();

        for (int y = -2; y < maxVisibleChunkCount.y; y++) {
            for (int x = -2; x < maxVisibleChunkCount.x - 1; x++) {
                Vector2 chunkScreenPos = new Vector2(x * chunkSize * tileSize + game.playerPosition.x, y * chunkSize * tileSize + game.playerPosition.y);
                visibleChunks.add(getChunkGridPos(chunkScreenPos));
            }
        }
        return visibleChunks;

    }

    public void render() {
        List<Vector2> visibleChunks = getVisibleChunks();
        for (Vector2 chunkGridPos : visibleChunks) {
            ClientChunk chunk = chunks.get(chunkGridPos);
            if (chunk != null) {;
                chunk.render(game);
            }
        }
    }

    public Vector2 getChunkScreenPos(Vector2 gridPos) {
        return new Vector2(
                gridPos.x * chunkSize * tileSize,
                gridPos.y * chunkSize * tileSize
        );
    }

//    public List<Vector2> getCircularChunkGridPos(int x, int y, int range) {
//        List<Vector2> neighbors = new ArrayList<>();
//        for (int i = -range; i <= range; i++) {
//            for (int j = -range; j <= range; j++) {
//                if (i * i + j * j <= range * range) {
//                    neighbors.add(new Vector2(x + i, y + j));
//                }
//            }
//        }
//        return neighbors;
//    }
//
    public void onPlayerMove(Vector2 playerScreenPos) {

        Vector2 playerGridPos = getChunkGridPos(playerScreenPos);

        if (playerGridPos.x == game.playerGridPos.x && playerGridPos.y == game.playerGridPos.y) {
            return;
        }

        game.playerGridPos = playerGridPos;

        List<Vector2> circularChunks = getVisibleChunks();
        for (Vector2 chunkGridPos : circularChunks) {
            game.colonyClient.sendPacket(new ChunkRequestPacket(chunkGridPos));
        }
    }

    public ClientChunk getChunk(Vector2 gridPos) {
        return chunks.get(gridPos);
    }


    private int customRound(float x) {
        if (x >= 0) {
            return (int) Math.floor(x);
        }
        else {
            return (int) Math.ceil(x - 1);
        }
    }

    public Vector2 getChunkGridPos(Vector2 screenPos) {
        return new Vector2(
                customRound((float) screenPos.x / (chunkSize * tileSize)),
                customRound((float) screenPos.y / (chunkSize * tileSize))
        );
    }

    public Vector2 getTileGridPos(Vector2 screenPos) {
        return new Vector2(
                customRound((float) screenPos.x / tileSize),
                customRound((float) screenPos.y / tileSize)
        );
    }

    public int getChunkCount() {
        return chunks.size();
    }

}
