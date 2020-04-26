package com.snl.application.snakenladder.entity.dice;

import java.util.Random;

public class RandomNumberDice extends Dice {

	Random random;

	public RandomNumberDice(int maxFaceValue) {
		super(maxFaceValue);
		random = new Random();
	}

	@Override
	protected int generateRandomNumber() {
		int randomInt = Math.abs(random.nextInt());
		return randomInt;
	}

}
