package me.adamix.colony.math;

public class Vector3 {

	public int x;
	public int y;
	public int z;

	public Vector3(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3(float x, float y, float z) {
		this.x = (int) x;
		this.y = (int) y;
		this.z = (int) z;
	}

	@Override
	public String toString() {
		return "[x: " + x + ", y: " + y + ", z: " + z + "]";

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Vector3)) {
			return false;
		}

		Vector3 other = (Vector3) obj;

		return this.x == other.x && this.y == other.y && this.z == other.z;
	}

	@Override
	public int hashCode() {
		return 31 * x + 17 * y;
	}
}
