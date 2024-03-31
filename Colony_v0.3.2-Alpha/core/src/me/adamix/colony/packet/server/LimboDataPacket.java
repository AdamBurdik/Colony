package me.adamix.colony.packet.server;

import me.adamix.colony.packet.Packet;

public class LimboDataPacket implements Packet {

	public String username;

	public LimboDataPacket() {

	}

	public LimboDataPacket(String username) {
		this.username = username;
	}

}
