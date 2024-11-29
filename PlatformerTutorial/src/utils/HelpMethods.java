package utils;

import java.awt.geom.Rectangle2D;

import main.FlappyGame;
import objects.Projectile;

public class HelpMethods {

	public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
		if (!IsSolid(x, y, lvlData))
			if (!IsSolid(x + width, y + height, lvlData))
				if (!IsSolid(x + width, y, lvlData))
					if (!IsSolid(x, y + height, lvlData))
						return true;
		return false;
	}

	private static boolean IsSolid(float x, float y, int[][] lvlData) {
		int maxWidth = lvlData[0].length * FlappyGame.TILES_SIZE;
		if (x < 0 || x >= maxWidth)
			return true;
		if (y < 0 || y >= FlappyGame.GAME_HEIGHT)
			return true;
		float xIndex = x / FlappyGame.TILES_SIZE;
		float yIndex = y / FlappyGame.TILES_SIZE;

		return IsTileSolid((int) xIndex, (int) yIndex, lvlData);
	}

	public static boolean IsProjectileHittingLevel(Projectile p, int[][] lvlData) {
		return IsSolid(p.getHitbox().x + p.getHitbox().width / 2, p.getHitbox().y + p.getHitbox().height / 2, lvlData);
	}

	public static boolean IsEntityInWater(Rectangle2D.Float hitbox, int[][] lvlData) {
		// Will only check if entity touch top water. Can't reach bottom water if not
		// touched top water.
		if (GetTileValue(hitbox.x, hitbox.y + hitbox.height, lvlData) != 48)
			if (GetTileValue(hitbox.x + hitbox.width, hitbox.y + hitbox.height, lvlData) != 48)
				return false;
		return true;
	}

	private static int GetTileValue(float xPos, float yPos, int[][] lvlData) {
		int xCord = (int) (xPos / FlappyGame.TILES_SIZE);
		int yCord = (int) (yPos / FlappyGame.TILES_SIZE);
		return lvlData[yCord][xCord];
	}

//	public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData) {
//		int value = lvlData[yTile][xTile];
//
//		switch (value) {
//		case 11, 48, 49:
//			return true; // I set it to solid for testing .
//
//		default:
//			return true;
//		}
//
//	}

public static boolean IsTileSolid(float x, float y, int[][] lvlData) {
	int maxWidth = lvlData[0].length * FlappyGame.TILES_SIZE;
	// if (x < 0 || x >= FlappyGame.GAME_WIDTH)
	if (x < 0 || x >= maxWidth)
		return true;
	if (y < 0 || y >= FlappyGame.GAME_HEIGHT)
		return true;
	float xIndex = x / FlappyGame.TILES_SIZE;
	float yIndex = y / FlappyGame.TILES_SIZE;
	int value = lvlData[(int) yIndex][(int) xIndex];
	if (value == 23) {
		return false;
	}
	if (value >= 48 || value < 0 || value != 11) {
		return true;
		// return false;   // Set this to false if you wish to pass through pipes.
	}
	return false;
}

	public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
		int currentTile = (int) (hitbox.x / FlappyGame.TILES_SIZE);
		if (xSpeed > 0) {
			// Right
			int tileXPos = currentTile * FlappyGame.TILES_SIZE;
			int xOffset = (int) (FlappyGame.TILES_SIZE - hitbox.width);
			return tileXPos + xOffset - 1;
		} else
			// Left
			return currentTile * FlappyGame.TILES_SIZE;
	}

	public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
		int currentTile = (int) (hitbox.y / FlappyGame.TILES_SIZE);
		if (airSpeed > 0) {
			// Falling - touching floor
			int tileYPos = currentTile * FlappyGame.TILES_SIZE;
			int yOffset = (int) (FlappyGame.TILES_SIZE - hitbox.height);
			return tileYPos + yOffset - 1;
		} else
			// Jumping
			return currentTile * FlappyGame.TILES_SIZE;

	}

	public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
		if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
			if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
				return false;
		return true;
	}

	public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
		if (xSpeed > 0)
			return IsSolid(hitbox.x + hitbox.width + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
		else
			return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
	}

	public static boolean IsFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
		if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
			if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
				return false;
		return true;
	}

	public static boolean CanCannonSeePlayer(int[][] lvlData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int yTile) {
		int firstXTile = (int) (firstHitbox.x / FlappyGame.TILES_SIZE);
		int secondXTile = (int) (secondHitbox.x / FlappyGame.TILES_SIZE);

		if (firstXTile > secondXTile)
			return IsAllTilesClear(secondXTile, firstXTile, yTile, lvlData);
		else
			return IsAllTilesClear(firstXTile, secondXTile, yTile, lvlData);
	}

	public static boolean IsAllTilesClear(int xStart, int xEnd, int y, int[][] lvlData) {
		for (int i = 0; i < xEnd - xStart; i++)
			if (IsTileSolid(xStart + i, y, lvlData))
				return false;
		return true;
	}

	public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
		if (IsAllTilesClear(xStart, xEnd, y, lvlData))
			for (int i = 0; i < xEnd - xStart; i++) {
				if (!IsTileSolid(xStart + i, y + 1, lvlData))
					return false;
			}
		return true;
	}

	// Player can sometimes be on an edge and in sight of enemy.
	// The old method would return false because the player x is not on edge.
	// This method checks both player x and player x + width.
	// If tile under playerBox.x is not solid, we switch to playerBox.x +
	// playerBox.width;
	// One of them will be true, because of prior checks.

	public static boolean IsSightClear(int[][] lvlData, Rectangle2D.Float enemyBox, Rectangle2D.Float playerBox, int yTile) {
		int firstXTile = (int) (enemyBox.x / FlappyGame.TILES_SIZE);

		int secondXTile;
		if (IsSolid(playerBox.x, playerBox.y + playerBox.height + 1, lvlData))
			secondXTile = (int) (playerBox.x / FlappyGame.TILES_SIZE);
		else
			secondXTile = (int) ((playerBox.x + playerBox.width) / FlappyGame.TILES_SIZE);

		if (firstXTile > secondXTile)
			return IsAllTilesWalkable(secondXTile, firstXTile, yTile, lvlData);
		else
			return IsAllTilesWalkable(firstXTile, secondXTile, yTile, lvlData);
	}

	public static boolean IsSightClear_OLD(int[][] lvlData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int yTile) {
		int firstXTile = (int) (firstHitbox.x / FlappyGame.TILES_SIZE);
		int secondXTile = (int) (secondHitbox.x / FlappyGame.TILES_SIZE);

		if (firstXTile > secondXTile)
			return IsAllTilesWalkable(secondXTile, firstXTile, yTile, lvlData);
		else
			return IsAllTilesWalkable(firstXTile, secondXTile, yTile, lvlData);
	}
}