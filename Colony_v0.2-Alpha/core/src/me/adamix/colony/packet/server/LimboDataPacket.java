package me.adamix.colony.packet.server;

import me.adamix.colony.packet.Packet;

public class LimboDataPacket implements Packet {

    private String username;

    public LimboDataPacket(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
