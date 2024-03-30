package me.adamix.colony.packet.server;

import me.adamix.colony.packet.Packet;

public class LimboAuthenticationDataPacket implements Packet {

	private final String username;
	private final int clientId;

	public LimboAuthenticationDataPacket(String username, int clientId) {
		this.username = username;
		this.clientId = clientId;
	}

	public String getUsername() {
		return username;
	}

	public int getClientId() {
		return clientId;
	}

}
