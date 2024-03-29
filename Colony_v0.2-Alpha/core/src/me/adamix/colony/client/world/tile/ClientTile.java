package me.adamix.colony.client.world.tile;

import me.adamix.colony.client.Client;
import me.adamix.colony.client.world.chunk.ClientChunk;
import me.adamix.colony.util.Vector2;

public class ClientTile {

    private Vector2 gridPos;
    private String tileId;

    public ClientTile(Vector2 gridPos, String tileId) {
        this.gridPos = gridPos;
        this.tileId = tileId;
    }

    public String getTileId() {
        return tileId;
    }

    public void render(Client game, Vector2 chunkScreenPos) {
//        System.out.println((gridPos.x * game.world.tileSize) + chunkScreenPos.x - game.position.x);
        game.batch.draw(
                game.getTexture(getTileId()),
                (gridPos.x * game.world.tileSize) + chunkScreenPos.x - game.position.x,
                (gridPos.y * game.world.tileSize) + chunkScreenPos.y - game.position.y,
                game.world.tileSize,
                game.world.tileSize
        );
    }

}
