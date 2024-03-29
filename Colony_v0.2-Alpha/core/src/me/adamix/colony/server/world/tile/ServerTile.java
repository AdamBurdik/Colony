package me.adamix.colony.server.world.tile;

import me.adamix.colony.util.Vector2;

public class ServerTile {

    private Vector2 gridPos;
    private String tileId;

    public ServerTile(Vector2 gridPos, String tileId) {
        this.gridPos = gridPos;
        this.tileId = tileId;
    }

    public String getTileId() {
        return tileId;
    }

}