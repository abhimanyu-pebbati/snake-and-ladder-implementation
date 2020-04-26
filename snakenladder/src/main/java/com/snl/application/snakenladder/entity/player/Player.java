package com.snl.application.snakenladder.entity.player;

import lombok.Getter;

@Getter
public class Player {
	private String playerId;
	private String playerName;

	public Player(String playerName) {
		this.playerId = playerName.toLowerCase();
		this.playerName = playerName;
	}

}