package me.adamix.colony.pathfinder;

import me.adamix.colony.math.Vector2;
import me.adamix.colony.world.World;
import me.adamix.colony.world.tile.Tile;

import java.util.*;

public class PathFinder {

	private static final long maxDistance = 100_000_000L;
	private static final boolean diagonal = false;

	public static Queue<Vector2> startSearch(Vector2 startTilePos, Vector2 endTilePos) {

////		Map<Vector2, SearchTile> searchedTiles = new HashMap<>();
////		Map<Vector2, SearchTile> exploredTiles = new HashMap<>();
////		boolean isFound = false;
////		long distance = 0;
////
////		searchedTiles.put(
////				endTilePos,
////				new SearchTile(endTilePos, 0, 0, null)
////		);
////
////		while (!isFound && !searchedTiles.isEmpty()) {
////			distance++;
////			if (distance > maxDistance) {
////				System.out.println("Max distance");
////				return null;
////			}
////
////			SearchTile lowestFCostTile = getLowestFCostTile(searchedTiles.values());
////			if (lowestFCostTile == null) {
////				continue;
////			}
////
////
////			List<Vector2> surroundingPositions = getSurroundingPositions(lowestFCostTile.getPos());
////			for (Vector2 pos : surroundingPositions) {
////				Tile surroundingTile = World.getTileFromGrid(pos);
////
////				if (surroundingTile == null) {
////					continue;
////				}
////
////				if (surroundingTile.isWall()) {
////					continue;
////				}
////
////				if (surroundingTile.getPosition() == startTilePos) {
////					exploredTiles.put(startTilePos, new SearchTile(startTilePos, 0, 0, lowestFCostTile.getPos()));
////					isFound = true;
////					continue;
////				}
////
////				if (!searchedTiles.containsKey(surroundingTile.getPosition()) && !exploredTiles.containsKey(surroundingTile.getPosition())) {
////					SearchTile searchTile = new SearchTile(
////							surroundingTile.getPosition(),
////							lowestFCostTile.getGCost() + calculateDistance(lowestFCostTile.getPos(), surroundingTile.getPosition()),
////							calculateDistance(surroundingTile.getPosition(),startTilePos),
////							lowestFCostTile.getPos());
////					searchedTiles.put(searchTile.getPos(), searchTile);
////				} else {
////					lowestFCostTile.update(lowestFCostTile.getGCost() + calculateDistance(lowestFCostTile.getPos(), surroundingTile.getPosition()), lowestFCostTile.getPos());
////				}
////			}
////
////			exploredTiles.put(lowestFCostTile.getPos(), lowestFCostTile);
////			searchedTiles.remove(lowestFCostTile.getPos());
////
////		}
//
//		return retracePath(exploredTiles, startTilePos, endTilePos);
		return null;
	}

	private static Queue<Vector2> retracePath(Map<Vector2, SearchTile> exploredTiles, Vector2 endTilePos, Vector2 startTilePos) {
		Queue<Vector2> retracedPath = new LinkedList<>();

		Vector2 currentTilePos = endTilePos;

		while (!exploredTiles.values().isEmpty()) {

			if (currentTilePos == startTilePos) {
				retracedPath.add(currentTilePos);
				break;
			}

			SearchTile searchTile = exploredTiles.get(currentTilePos);
			retracedPath.add(searchTile.getPos());
			exploredTiles.remove(currentTilePos);
			currentTilePos = searchTile.getParentTilePos();
		}

		return retracedPath;
	}

	private static List<Vector2> getSurroundingPositions(Vector2 pos) {
		List<Vector2> surroundingPos = new ArrayList<>();

		surroundingPos.add(new Vector2(pos.x + 1, pos.y));
		surroundingPos.add(new Vector2(pos.x - 1, pos.y));
		surroundingPos.add(new Vector2(pos.x, pos.y + 1));
		surroundingPos.add(new Vector2(pos.x, pos.y - 1));
		if (diagonal) {
			surroundingPos.add(new Vector2(pos.x + 1, pos.y - 1));
			surroundingPos.add(new Vector2(pos.x - 1, pos.y + 1));
			surroundingPos.add(new Vector2(pos.x + 1, pos.y + 1));
			surroundingPos.add(new Vector2(pos.x - 1, pos.y - 1));
		}

		return surroundingPos;
	}

	private static SearchTile getLowestFCostTile(Collection<SearchTile> searchedTiles) {
		SearchTile lowestFCostTile = null;
		for (SearchTile searchTile : searchedTiles) {
			if (lowestFCostTile == null) {
				lowestFCostTile = searchTile;
				continue;
			}

			if (searchTile.getFCost() < lowestFCostTile.getFCost()) {
				lowestFCostTile = searchTile;
			}
		}

		return lowestFCostTile;
	}

	private static int calculateDistance(Vector2 vector1, Vector2 vector2) {

		float distanceX = Math.abs(vector1.x - vector2.x);
		float distanceY = Math.abs(vector1.y - vector2.y);
		float diagonalDistance = Math.min(distanceX, distanceY);
		float straightDistance = Math.abs(distanceX - distanceY);

		return (int) ((diagonalDistance * Math.sqrt(2) + straightDistance) * 10);
	}

}
