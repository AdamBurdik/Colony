package me.adamix.colony.packet.server;

import me.adamix.colony.packet.Packet;
import me.adamix.colony.util.Vector2;

public class ChunkRequestPacket implements Packet {

	public Vector2 chunkGridPos;

	public ChunkRequestPacket() {
	}

	public ChunkRequestPacket(Vector2 chunkGridPos) {
		this.chunkGridPos = chunkGridPos;
	}

}
