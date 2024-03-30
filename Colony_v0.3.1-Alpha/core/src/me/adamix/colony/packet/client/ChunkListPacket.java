package me.adamix.colony.packet.client;

import me.adamix.colony.packet.Packet;
import me.adamix.colony.util.Vector2;

import java.util.ArrayList;
import java.util.List;

public class ChunkListPacket implements Packet {

	private final List<ChunkPacket> chunkPackets;

	public ChunkListPacket(List<ChunkPacket> chunkPackets) {
		this.chunkPackets = chunkPackets;
	}

	public List<ChunkPacket> getChunkPackets() {
		return chunkPackets;
	}

}
