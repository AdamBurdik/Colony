package me.adamix.colony.packet.general;

import me.adamix.colony.packet.Packet;
import me.adamix.colony.util.Vector2;

public class TileUpdatePacket implements Packet {

	private final Vector2 chunkGridPos;
	private final Vector2 tileGridPos;
	private final String tileId;

	public TileUpdatePacket(Vector2 chunkGridPos, Vector2 tileGridPos, String tileId) {
		this.chunkGridPos = chunkGridPos;
		this.tileGridPos = tileGridPos;
		this.tileId = tileId;
	}

	public Vector2 getChunkGridPos() {
		return chunkGridPos;
	}

	public Vector2 getTileGridPos() {
		return tileGridPos;
	}

	public String getTileId() {
		return tileId;
	}
}
