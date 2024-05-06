package me.adamix.colony.world.generators;

import me.adamix.colony.math.Vector2;
import me.adamix.colony.world.chunk.Chunk;

public interface Generator {

	Chunk generateChunk(Vector2 gridPosition);


}
