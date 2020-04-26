package com.snl.application.snakenladder.entity.dice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class Dice {

	private final int maxDiceValue;

	public int getMaxFaceValue() {
		return maxDiceValue;
	}

	public int rollDice(Object input) {
		int randomInt = this.generateRandomNumber();
		randomInt = (int) (randomInt % (this.getMaxFaceValue() - 1));
		randomInt++;
		return randomInt;
	}

	protected abstract int generateRandomNumber();
}