package me.adamix.colony.util;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import me.adamix.colony.packet.client.ChunkPacket;
import me.adamix.colony.packet.general.TileUpdatePacket;
import me.adamix.colony.packet.server.ChunkRequestPacket;
import me.adamix.colony.packet.server.LimboDataPacket;

import java.util.ArrayList;
import java.util.HashMap;

public class Network {
	static public final int port = 8000;

	// This registers objects that are going to be sent over the network.
	static public void register (EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(Vector2.class);
		kryo.register(LimboDataPacket.class);
		kryo.register(ChunkPacket.class);
		kryo.register(ChunkRequestPacket.class);
		kryo.register(HashMap.class);
		kryo.register(TileUpdatePacket.class);
		kryo.register(String.class);

	}
}