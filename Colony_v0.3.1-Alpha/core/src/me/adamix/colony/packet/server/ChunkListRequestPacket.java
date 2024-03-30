package me.adamix.colony.packet.server;

import me.adamix.colony.packet.Packet;
import me.adamix.colony.util.Vector2;

import java.util.ArrayList;
import java.util.List;

public class ChunkListRequestPacket implements Packet {

	private final List<Vector2> gridPosList;

	public ChunkListRequestPacket(List<Vector2> chunkGriPosList) {
		this.gridPosList = chunkGriPosList;
	}

	public List<Vector2> getGridPosList() {
		return gridPosList;
	}

}
