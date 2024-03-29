package me.adamix.colony.packet.client;

import me.adamix.colony.packet.Packet;
import me.adamix.colony.util.Vector2;

import java.util.ArrayList;

public class ChunkPacket implements Packet {

    private int gridPosX;
    private int gridPosY;
    private ArrayList<String> tileIds;

    public ChunkPacket(int gridPosX, int gridPosY, ArrayList<String> tileIds) {
        this.gridPosX = gridPosX;
        this.gridPosY = gridPosY;
        this.tileIds = tileIds;
    }

    public int getGridPosX() {
        return gridPosX;
    }

    public int getGridPosY() {
        return gridPosY;
    }

    public ArrayList<String> getTileIds() {
        return tileIds;
    }

}
