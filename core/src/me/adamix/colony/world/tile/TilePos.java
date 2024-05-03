package me.adamix.colony.world.tile;

import java.io.Serializable;

public class TilePos implements Serializable {

	public int x;
	public int y;
	public int z;

	public TilePos(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

//	public TilePos(int x, int y, int z) {
//		this.x = (int) x;
//		this.y = (int) y;
//		this.z = (int) z;
//	}

	@Override
	public String toString() {
		return "[x: " + x + ", y: " + y + ", z: " + z + "]";
	}

	public TilePos add(int x, int y, int z) {
		return new TilePos(this.x + x, this.y + y, this.z + z);
	}
//
//	public TilePos add(int x, int y, int z) {
//		return new TilePos(this.x + x, this.y + y, this.z + z);
//	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof TilePos)) {
			return false;
		}

		TilePos other = (TilePos) obj;

		return this.x == other.x && this.y == other.y && this.z == other.z;
	}

	@Override
	public int hashCode() {
		return 31 * x + 17 * y + 31 * z;
	}
}
