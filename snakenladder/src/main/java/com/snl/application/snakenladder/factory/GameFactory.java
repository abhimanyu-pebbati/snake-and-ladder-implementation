package com.snl.application.snakenladder.factory;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snl.application.snakenladder.entity.board.Board;
import com.snl.application.snakenladder.entity.dice.Dice;
import com.snl.application.snakenladder.entity.game.Game;
import com.snl.application.snakenladder.entity.player.Player;
import com.snl.application.snakenladder.exceptionhandling.exception.BoardNotFoundException;
import com.snl.application.snakenladder.exceptionhandling.exception.InvalidGameException;
import com.snl.application.snakenladder.exceptionhandling.exception.InvalidInputException;
import com.snl.application.snakenladder.model.BoardModel;
import com.snl.application.snakenladder.model.GameModel;
import com.snl.application.snakenladder.model.PlayerModel;
import com.snl.application.snakenladder.request.GameRequest;

public class GameFactory {
	private static GameFactory instance;
	private PlayerFactory playerFactory;
	private static Logger logger = LoggerFactory.getLogger(GameFactory.class);

	GameFactory() {
		this.playerFactory = PlayerFactory.getInstance();
	}

	public static GameFactory getInstance() {
		if (instance == null)
			instance = new GameFactory();
		return instance;
	}

	public Game createGame(GameRequest gameRequest) {
		Game game = null;
		if (!validateRequest(gameRequest))
			return null;

		Board board = BoardModel.getInstance().getBoard(gameRequest.getBoardId());

		// Setting dice
		Dice dice = DiceFactory.getInstance().createDice(gameRequest.getMaxDiceValue(), gameRequest.getDiceType());

		game = new Game(gameRequest.getGameId(), board, dice);

		PlayerModel playerModel = PlayerModel.getInstance();
		Player player;
		// Setting players
		for (String playerId : gameRequest.getPlayerIds()) {
			player = playerModel.getPlayer(playerId);
			if (player == null) {
				String info = "Creating player '" + playerId + "' as such a user does not exist.";
				logger.info(info);
				player = playerFactory.createPlayer(playerId);
			}
			game.addPlayer(player);
		}

		if (game.getNumberOfPlayers() > 1) {
			GameModel.getInstance().addGame(game);
			return game;
		} else {
			String err = "Game requires at least two or more players to play.";
			logger.error(err);
			throw new InvalidGameException(err);
		}
	}

	private boolean validateRequest(GameRequest gameRequest) {

		Board board = BoardModel.getInstance().getBoard(gameRequest.getBoardId());
		if (board == null) {
			String err = "Board with board ID " + gameRequest.getBoardId() + " does not exist.";
			logger.error(err);
			throw new BoardNotFoundException(err);
		}

		if (gameRequest.getGameId() != null && !gameRequest.getGameId().trim().isEmpty()) {
			if (GameModel.getInstance().getGame(gameRequest.getGameId()) != null) {
				String err = "Game with game id " + gameRequest.getGameId() + " already exists.";
				logger.error(err);
				throw new InvalidInputException(err);
			}
		} else {
			logger.info("Game ID not entered, setting a random UUID.");
			gameRequest.setGameId(UUID.randomUUID().toString());
		}

		return true;
	}

}