package com.snl.application.snakenladder.entity.dice;

public class ClockDice extends Dice {

	public ClockDice(int maxFaceValue) {
		super(maxFaceValue);
	}

	@Override
	protected int generateRandomNumber() {
		int time = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
		return time;
	}

}
