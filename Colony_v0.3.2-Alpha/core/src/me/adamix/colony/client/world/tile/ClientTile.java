package me.adamix.colony.client.world.tile;

import me.adamix.colony.client.Game;
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

    public void render(Game game, Vector2 chunkScreenPos) {
        game.batch.draw(
                game.getTexture(getTileId()),
                (gridPos.x * game.world.tileSize) + chunkScreenPos.x - game.offset.x,
                (gridPos.y * game.world.tileSize) + chunkScreenPos.y - game.offset.y,
                game.world.tileSize,
                game.world.tileSize
        );
    }
}
