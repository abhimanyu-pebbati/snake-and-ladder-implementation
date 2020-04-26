package com.snl.application.snakenladder.model;

import java.util.HashMap;
import java.util.Map;

import com.snl.application.snakenladder.entity.game.Game;

public class GameModel {
	private static GameModel instance;

	private Map<String, Game> games;

	private GameModel() {
		games = new HashMap<String, Game>();
	}

	public static GameModel getInstance() {
		if (instance == null)
			instance = new GameModel();
		return instance;
	}

	public Game getGame(String gameId) {
		return games.get(gameId);
	}

	public void addGame(Game game) {
		games.put(game.getGameId(), game);
	}
}
