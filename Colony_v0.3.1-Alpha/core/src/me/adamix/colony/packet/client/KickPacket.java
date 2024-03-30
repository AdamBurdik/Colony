package me.adamix.colony.packet.client;

import me.adamix.colony.packet.Packet;

public class KickPacket implements Packet {

	private final String reason;

	public KickPacket(String reason) {
		this.reason = reason;
	}

	public String getReason() {
		return reason;
	}

}
