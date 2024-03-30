package me.adamix.colony.packet.server;

import me.adamix.colony.packet.Packet;
import me.adamix.colony.util.Vector2;

public class UpdatePositionPacket implements Packet {

    private int x;
    private int y;

    public UpdatePositionPacket(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
