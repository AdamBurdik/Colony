package me.adamix.colony.world.entities.ai;

import me.adamix.colony.Game;
import me.adamix.colony.math.Vector2;
import me.adamix.colony.pathfinder.PathFinder;
import me.adamix.colony.world.World;
import me.adamix.colony.world.entities.Entity;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PathFindingEntity extends Entity {

	private Queue<Vector2> path = new ConcurrentLinkedQueue<>();

	public PathFindingEntity(Vector2 position, int textureId) {
		super(position, textureId);
	}

	public void followPath() {
//		if (path == null) {
//			return;
//		}
//
//		for (Vector2 pos : path) {
//			move(World.convertGridToScreen(pos));
//			try {
//				if (!Game.isRunning) {
//					return;
//				}
//				Thread.sleep(500);
//			} catch (InterruptedException e) {
//				throw new RuntimeException(e);
//			}
//		}
	}

	public void goTo(Vector2 tilePosition) {
		Thread thread = new Thread(() -> {
			path.clear();
//			path = PathFinder.startSearch(this.getGridPosition(), tilePosition);
			followPath();
		});
		thread.start();

	}

}
