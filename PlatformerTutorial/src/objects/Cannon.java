package objects;

import main.FlappyGame;

public class Cannon extends GameObject {

	private int tileY;

	public Cannon(int x, int y, int objType) {
		super(x, y, objType);
		tileY = y / FlappyGame.TILES_SIZE;
		initHitbox(40, 26);
//		hitbox.x -= (int) (1 * FlappyGame.SCALE);
		hitbox.y += (int) (6 * FlappyGame.SCALE);
	}

	public void update() {
		if (doAnimation)
			updateAnimationTick();
	}

	public int getTileY() {
		return tileY;
	}

}
