package com.snl.application.snakenladder.model;

import java.util.HashMap;
import java.util.Map;

import com.snl.application.snakenladder.entity.player.Player;

public class PlayerModel {
	private static PlayerModel instance;

	private Map<String, Player> players;

	private PlayerModel() {
		players = new HashMap<String, Player>();
	}

	public static PlayerModel getInstance() {
		if (instance == null)
			instance = new PlayerModel();
		return instance;
	}

	public Player getPlayer(String playerId) {
		playerId = playerId.trim().toLowerCase();
		return players.get(playerId);
	}

	public void addPlayer(Player player) {
		players.put(player.getPlayerId(), player);
	}
}
