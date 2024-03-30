package me.adamix.colony.packet.general;

import me.adamix.colony.packet.Packet;

public class PingPacket implements Packet {

    private long time;

    public PingPacket(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }


}
