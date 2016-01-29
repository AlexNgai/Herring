package com.alexngai.herring;


public class Difficulty {
	
	public static final int EASY = 1;
	public static final int MEDIUM = 2;
	public static final int HARD = 3;
	
	private static final int EASY_ENEMIES = 10;
	private static final double EASY_SPAWNRATE = .02;
	private static final double EASY_SPEEDMULT = 1.5;
	private static final float EASY_MULT = 1;
	private static final float EASY_SIZEMULT = 1f;
	
	private static final int MEDIUM_ENEMIES = 18;
	private static final double MEDIUM_SPAWNRATE = .03;
	private static final double MEDIUM_SPEEDMULT = 2.5;
	private static final float MEDIUM_MULT = 1.3f;
	private static final float MEDIUM_SIZEMULT = 1.2f;
	
	private static final int HARD_ENEMIES = 25;
	private static final double HARD_SPAWNRATE = .04;
	private static final double HARD_SPEEDMULT = 3.5;
	private static final float HARD_MULT = 2;
	private static final float HARD_SIZEMULT = 1.35f;
	
	public static void setDifficulty(int difficulty, GameInstance gameInstance){
		switch (difficulty) {
		case EASY:
			gameInstance.setEnemies(EASY_ENEMIES);
			gameInstance.setSpawnRate(EASY_SPAWNRATE);
			gameInstance.setSpeedMultiplier(EASY_SPEEDMULT);
			gameInstance.setDifficultyMult(EASY_MULT);
			gameInstance.setSizeMultiplier(EASY_SIZEMULT);
			break;

		case MEDIUM:
			gameInstance.setEnemies(MEDIUM_ENEMIES);
			gameInstance.setSpawnRate(MEDIUM_SPAWNRATE);
			gameInstance.setSpeedMultiplier(MEDIUM_SPEEDMULT);
			gameInstance.setDifficultyMult(MEDIUM_MULT);
			gameInstance.setSizeMultiplier(MEDIUM_SIZEMULT);
			break;
	
		case HARD:
			gameInstance.setEnemies(HARD_ENEMIES);
			gameInstance.setSpawnRate(HARD_SPAWNRATE);
			gameInstance.setSpeedMultiplier(HARD_SPEEDMULT);
			gameInstance.setDifficultyMult(HARD_MULT);
			gameInstance.setSizeMultiplier(HARD_SIZEMULT);
			break;

			
		default:
			break;
		}
	}
	
	public static String increaseLevel(int curLevel, int score){
		if (curLevel == 1 && score > 500){
			return "Level 2";
		}
		else if (curLevel == 2 && score > 1500){
			return "Level 3";
		}
		else if (curLevel == 3 && score > 3000){
			return "Level 4";
		}
		else if (curLevel == 4 && score > 5000){
			return "Level 5";
		}
		else if (curLevel == 5 && score > 7000){
			return "Level 6";
		}
		else if (curLevel == 6 && score > 9000){
			return "Final Stage";
		}
		
		return null;
	}
}
