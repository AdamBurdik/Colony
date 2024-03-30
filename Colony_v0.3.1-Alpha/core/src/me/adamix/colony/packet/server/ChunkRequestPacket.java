package me.adamix.colony.packet.server;

import me.adamix.colony.packet.Packet;
import me.adamix.colony.util.Vector2;

import java.util.ArrayList;
import java.util.List;

public class ChunkRequestPacket implements Packet {

	private final int gridPosX;
	private final int gridPosY;

	public ChunkRequestPacket(int gridPosX, int gridPosY) {
		this.gridPosX = gridPosX;
		this.gridPosY = gridPosY;
	}

	public int getGridPosX() {
		return gridPosX;
	}

	public int getGridPosY() {
		return gridPosY;
	}
}
