package com.snl.application.snakenladder.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snl.application.snakenladder.entity.dice.ClockDice;
import com.snl.application.snakenladder.entity.dice.Dice;
import com.snl.application.snakenladder.entity.dice.DiceType;
import com.snl.application.snakenladder.entity.dice.RandomNumberDice;
import com.snl.application.snakenladder.exceptionhandling.exception.InvalidInputException;

public class DiceFactory {
	private static DiceFactory instance;
	private static Logger logger = LoggerFactory.getLogger(DiceFactory.class);

	private DiceFactory() {
	}

	public static DiceFactory getInstance() {
		if (instance == null)
			instance = new DiceFactory();
		return instance;
	}

	public Dice createDice(int maxDiceValue, DiceType diceType) {
		Dice dice = null;
		if (maxDiceValue < 1) {
			String err = "Maximum dice value cannot be less than 1.";
			logger.error(err);
			throw new InvalidInputException(err);
		}

		if (diceType == DiceType.CLOCK_DICE)
			dice = new ClockDice(maxDiceValue);
		else if (diceType == DiceType.RANDOM_NUMBER_DICE)
			dice = new RandomNumberDice(maxDiceValue);
		else {
			logger.info("Dice type not provided, setting random-number dice.");
			dice = new RandomNumberDice(maxDiceValue);
		}

		return dice;
	}

}