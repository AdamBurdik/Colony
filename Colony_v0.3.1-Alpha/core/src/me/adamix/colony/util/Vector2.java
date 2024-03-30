package me.adamix.colony.util;

import java.io.Serializable;

public class Vector2 implements Serializable {

    public int x;
    public int y;

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(float x, float y) {
        this.x = (int) x;
        this.y = (int) y;
    }

    @Override
    public String toString() {
        return "[x: " + x + ", y: " + y + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Vector2)) {
            return false;
        }

        Vector2 other = (Vector2) obj;

        return this.x == other.x && this.y == other.y;
    }

    @Override
    public int hashCode() {
        // Use the prime numbers 31 and 17 to calculate a hash code
        return 31 * x + 17 * y;
    }
}
