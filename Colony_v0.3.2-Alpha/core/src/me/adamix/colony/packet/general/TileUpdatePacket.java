package me.adamix.colony.packet.general;

import me.adamix.colony.packet.Packet;
import me.adamix.colony.util.Vector2;

public class TileUpdatePacket implements Packet {

	public Vector2 chunkGridPos;
	public Vector2 tileGridPos;
	public String tileId;

	public TileUpdatePacket() {

	}

	public TileUpdatePacket(Vector2 chunkGridPos, Vector2 tileGridPos, String tileId) {
		this.chunkGridPos = chunkGridPos;
		this.tileGridPos = tileGridPos;
		this.tileId = tileId;
	}
}
