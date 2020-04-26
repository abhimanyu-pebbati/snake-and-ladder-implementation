package com.snl.application.snakenladder.request;

import java.util.List;

import com.snl.application.snakenladder.entity.dice.DiceType;

import lombok.Data;

@Data
public class GameRequest {
	private String gameId;
	private String boardId;
	private DiceType diceType;
	private int maxDiceValue;
	private List<String> playerIds;
}