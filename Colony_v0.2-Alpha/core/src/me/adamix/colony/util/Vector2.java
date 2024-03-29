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
}
