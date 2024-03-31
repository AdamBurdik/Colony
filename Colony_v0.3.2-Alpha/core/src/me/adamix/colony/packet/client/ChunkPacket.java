package me.adamix.colony.packet.client;

import me.adamix.colony.packet.Packet;
import me.adamix.colony.util.Vector2;

import java.util.ArrayList;
import java.util.HashMap;

public class ChunkPacket implements Packet {

	public int gridPosX;
	public int gridPosY;
	public HashMap<Vector2, String> tiles;

	public ChunkPacket() {

	}

	public ChunkPacket(int gridPosX, int gridPosY, HashMap<Vector2, String> tiles) {
		this.gridPosX = gridPosX;
		this.gridPosY = gridPosY;
		this.tiles = tiles;
	}
}