package objects;

import static utils.Constants.ObjectConstants.*;

import main.FlappyGame;

public class GameContainer extends GameObject {

	public GameContainer(int x, int y, int objType) {
		super(x, y, objType);
		createHitbox();
	}

	private void createHitbox() {
		if (objType == BOX) {
			initHitbox(25, 18);

			xDrawOffset = (int) (7 * FlappyGame.SCALE);
			yDrawOffset = (int) (12 * FlappyGame.SCALE);

		} else {
			initHitbox(23, 25);
			xDrawOffset = (int) (8 * FlappyGame.SCALE);
			yDrawOffset = (int) (5 * FlappyGame.SCALE);
		}

		hitbox.y += yDrawOffset + (int) (FlappyGame.SCALE * 2);
		hitbox.x += (float) xDrawOffset / 2;
	}

	public void update() {
		if (doAnimation)
			updateAnimationTick();
	}
}
