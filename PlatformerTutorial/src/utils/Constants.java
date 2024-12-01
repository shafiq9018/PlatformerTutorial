package utils;

import main.FlappyGame;

public class Constants {

	public static final float GRAVITY = 0.04f * FlappyGame.SCALE;
	public static final int ANI_SPEED = 25;

	public static class Dialogue {
		public static final int QUESTION = 0;
		public static final int EXCLAMATION = 1;

		public static final int DIALOGUE_WIDTH = (int) (14 * FlappyGame.SCALE);
		public static final int DIALOGUE_HEIGHT = (int) (12 * FlappyGame.SCALE);

		public static int GetSpriteAmount(int type) {
			switch (type) {
				case QUESTION, EXCLAMATION:
					return 5;
			}
			return 0;
		}
	}

	public static class Projectiles {
		public static final int CANNON_BALL_DEFAULT_WIDTH = 15;
		public static final int CANNON_BALL_DEFAULT_HEIGHT = 15;

		public static final int CANNON_BALL_WIDTH = (int) (FlappyGame.SCALE * CANNON_BALL_DEFAULT_WIDTH);
		public static final int CANNON_BALL_HEIGHT = (int) (FlappyGame.SCALE * CANNON_BALL_DEFAULT_HEIGHT);
		public static final float SPEED = 0.75f * FlappyGame.SCALE;
	}

	public static class ObjectConstants {

		public static final int RED_POTION = 0;
		public static final int BLUE_POTION = 1;
		public static final int BARREL = 2;
		public static final int BOX = 3;
		public static final int SPIKE = 4;
		public static final int CANNON_LEFT = 5;
		public static final int CANNON_RIGHT = 6;
		public static final int TREE_ONE = 7;
		public static final int TREE_TWO = 8;
		public static final int TREE_THREE = 9;

		public static final int RED_POTION_VALUE = 15;
		public static final int BLUE_POTION_VALUE = 10;

		public static final int CONTAINER_WIDTH_DEFAULT = 40;
		public static final int CONTAINER_HEIGHT_DEFAULT = 30;
		public static final int CONTAINER_WIDTH = (int) (FlappyGame.SCALE * CONTAINER_WIDTH_DEFAULT);
		public static final int CONTAINER_HEIGHT = (int) (FlappyGame.SCALE * CONTAINER_HEIGHT_DEFAULT);

		public static final int POTION_WIDTH_DEFAULT = 12;
		public static final int POTION_HEIGHT_DEFAULT = 16;
		public static final int POTION_WIDTH = (int) (FlappyGame.SCALE * POTION_WIDTH_DEFAULT);
		public static final int POTION_HEIGHT = (int) (FlappyGame.SCALE * POTION_HEIGHT_DEFAULT);

		public static final int SPIKE_WIDTH_DEFAULT = 32;
		public static final int SPIKE_HEIGHT_DEFAULT = 32;
		public static final int SPIKE_WIDTH = (int) (FlappyGame.SCALE * SPIKE_WIDTH_DEFAULT);
		public static final int SPIKE_HEIGHT = (int) (FlappyGame.SCALE * SPIKE_HEIGHT_DEFAULT);

		public static final int CANNON_WIDTH_DEFAULT = 40;
		public static final int CANNON_HEIGHT_DEFAULT = 26;
		public static final int CANNON_WIDTH = (int) (CANNON_WIDTH_DEFAULT * FlappyGame.SCALE);
		public static final int CANNON_HEIGHT = (int) (CANNON_HEIGHT_DEFAULT * FlappyGame.SCALE);

		public static int GetSpriteAmount(int object_type) {
			switch (object_type) {
				case RED_POTION, BLUE_POTION:
					return 7;
				case BARREL, BOX:
					return 8;
				case CANNON_LEFT, CANNON_RIGHT:
					return 7;
			}
			return 1;
		}

		public static int GetTreeOffsetX(int treeType) {
			switch (treeType) {
				case TREE_ONE:
					return (FlappyGame.TILES_SIZE / 2) - (GetTreeWidth(treeType) / 2);
				case TREE_TWO:
					return (int) (FlappyGame.TILES_SIZE / 2.5f);
				case TREE_THREE:
					return (int) (FlappyGame.TILES_SIZE / 1.65f);
			}

			return 0;
		}

		public static int GetTreeOffsetY(int treeType) {

			switch (treeType) {
				case TREE_ONE:
					return -GetTreeHeight(treeType) + FlappyGame.TILES_SIZE * 2;
				case TREE_TWO, TREE_THREE:
					return -GetTreeHeight(treeType) + (int) (FlappyGame.TILES_SIZE / 1.25f);
			}
			return 0;

		}

		public static int GetTreeWidth(int treeType) {
			switch (treeType) {
				case TREE_ONE:
					return (int) (39 * FlappyGame.SCALE);
				case TREE_TWO:
					return (int) (62 * FlappyGame.SCALE);
				case TREE_THREE:
					return -(int) (62 * FlappyGame.SCALE);

			}
			return 0;
		}

		public static int GetTreeHeight(int treeType) {
			switch (treeType) {
				case TREE_ONE:
					return (int) (int) (92 * FlappyGame.SCALE);
				case TREE_TWO, TREE_THREE:
					return (int) (54 * FlappyGame.SCALE);
			}
			return 0;
		}
	}

	public static class EnemyConstants {
		public static final int CRABBY = 0;
		public static final int PINKSTAR = 1;
		public static final int SHARK = 2;

		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int ATTACK = 2;
		public static final int HIT = 3;
		public static final int DEAD = 4;

		public static final int CRABBY_WIDTH_DEFAULT = 72;
		public static final int CRABBY_HEIGHT_DEFAULT = 32;
		public static final int CRABBY_WIDTH = (int) (CRABBY_WIDTH_DEFAULT * FlappyGame.SCALE);
		public static final int CRABBY_HEIGHT = (int) (CRABBY_HEIGHT_DEFAULT * FlappyGame.SCALE);
		public static final int CRABBY_DRAWOFFSET_X = (int) (26 * FlappyGame.SCALE);
		public static final int CRABBY_DRAWOFFSET_Y = (int) (9 * FlappyGame.SCALE);

		public static final int PINKSTAR_WIDTH_DEFAULT = 34;
		public static final int PINKSTAR_HEIGHT_DEFAULT = 30;
		public static final int PINKSTAR_WIDTH = (int) (PINKSTAR_WIDTH_DEFAULT * FlappyGame.SCALE);
		public static final int PINKSTAR_HEIGHT = (int) (PINKSTAR_HEIGHT_DEFAULT * FlappyGame.SCALE);
		public static final int PINKSTAR_DRAWOFFSET_X = (int) (9 * FlappyGame.SCALE);
		public static final int PINKSTAR_DRAWOFFSET_Y = (int) (7 * FlappyGame.SCALE);

		public static final int SHARK_WIDTH_DEFAULT = 34;
		public static final int SHARK_HEIGHT_DEFAULT = 30;
		public static final int SHARK_WIDTH = (int) (SHARK_WIDTH_DEFAULT * FlappyGame.SCALE);
		public static final int SHARK_HEIGHT = (int) (SHARK_HEIGHT_DEFAULT * FlappyGame.SCALE);
		public static final int SHARK_DRAWOFFSET_X = (int) (8 * FlappyGame.SCALE);
		public static final int SHARK_DRAWOFFSET_Y = (int) (6 * FlappyGame.SCALE);

		public static int GetSpriteAmount(int enemy_type, int enemy_state) {
			switch (enemy_state) {

				case IDLE: {
					if (enemy_type == CRABBY)
						return 9;
					else if (enemy_type == PINKSTAR || enemy_type == SHARK)
						return 8;
				}
				case RUNNING:
					return 6;
				case ATTACK:
					if (enemy_type == SHARK)
						return 8;
					return 7;
				case HIT:
					return 4;
				case DEAD:
					return 5;
			}
			return 0;
		}

		public static int GetMaxHealth(int enemy_type) {
			switch (enemy_type) {
				case CRABBY:
					return 50;
				case PINKSTAR, SHARK:
					return 25;
				default:
					return 1;
			}
		}

		public static int GetEnemyDmg(int enemy_type) {
			switch (enemy_type) {
				case CRABBY:
					return 15;
				case PINKSTAR:
					return 20;
				case SHARK:
					return 25;
				default:
					return 0;
			}
		}
	}

	public static class Environment {
		public static final int BIG_CLOUD_WIDTH_DEFAULT = 448;
		public static final int BIG_CLOUD_HEIGHT_DEFAULT = 101;
		public static final int SMALL_CLOUD_WIDTH_DEFAULT = 74;
		public static final int SMALL_CLOUD_HEIGHT_DEFAULT = 24;

		public static final int BIG_CLOUD_WIDTH = (int) (BIG_CLOUD_WIDTH_DEFAULT * FlappyGame.SCALE);
		public static final int BIG_CLOUD_HEIGHT = (int) (BIG_CLOUD_HEIGHT_DEFAULT * FlappyGame.SCALE);
		public static final int SMALL_CLOUD_WIDTH = (int) (SMALL_CLOUD_WIDTH_DEFAULT * FlappyGame.SCALE);
		public static final int SMALL_CLOUD_HEIGHT = (int) (SMALL_CLOUD_HEIGHT_DEFAULT * FlappyGame.SCALE);
	}

	public static class UI {
		public static class Buttons {
			public static final int B_WIDTH_DEFAULT = 140;
			public static final int B_HEIGHT_DEFAULT = 56;
			public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * FlappyGame.SCALE);
			public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * FlappyGame.SCALE);
		}

		public static class PauseButtons {
			public static final int SOUND_SIZE_DEFAULT = 42;
			public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * FlappyGame.SCALE);
		}

		public static class URMButtons {
			public static final int URM_DEFAULT_SIZE = 56;
			public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * FlappyGame.SCALE);

		}

		public static class VolumeButtons {
			public static final int VOLUME_DEFAULT_WIDTH = 28;
			public static final int VOLUME_DEFAULT_HEIGHT = 44;
			public static final int SLIDER_DEFAULT_WIDTH = 215;

			public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * FlappyGame.SCALE);
			public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * FlappyGame.SCALE);
			public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * FlappyGame.SCALE);
		}
	}

	public static class Directions {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}

	public static class PlayerConstants {
		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int JUMP = 2;
		public static final int FALLING = 3;
		public static final int ATTACK = 4;
		public static final int HIT = 5;
		public static final int DEAD = 6;

	}
}