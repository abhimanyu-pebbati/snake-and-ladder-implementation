package com.snl.application.snakenladder.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snl.application.snakenladder.entity.player.Player;
import com.snl.application.snakenladder.model.PlayerModel;

public class PlayerFactory {
	private static PlayerFactory instance;
	private static Logger logger = LoggerFactory.getLogger(PlayerFactory.class);

	private PlayerFactory() {
	}

	public static PlayerFactory getInstance() {
		if (instance == null)
			instance = new PlayerFactory();
		return instance;
	}

	public Player createPlayer(String playerName) {
		Player player = new Player(playerName);

		if (PlayerModel.getInstance().getPlayer(player.getPlayerId()) != null) {
			logger.error("Player " + playerName + " already exists.");
			return player;
		} else {
			PlayerModel.getInstance().addPlayer(player);
			logger.info("Player " + playerName + " created.");
			return player;
		}
	}

}