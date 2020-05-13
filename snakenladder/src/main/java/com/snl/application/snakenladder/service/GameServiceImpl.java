package com.snl.application.snakenladder.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.snl.application.snakenladder.entity.board.Board;
import com.snl.application.snakenladder.entity.game.Game;
import com.snl.application.snakenladder.entity.player.Player;
import com.snl.application.snakenladder.exceptionhandling.exception.InvalidGameException;
import com.snl.application.snakenladder.factory.BoardFactory;
import com.snl.application.snakenladder.factory.GameFactory;
import com.snl.application.snakenladder.factory.PlayerFactory;
import com.snl.application.snakenladder.model.GameModel;
import com.snl.application.snakenladder.request.BoardRequest;
import com.snl.application.snakenladder.request.GameRequest;
import com.snl.application.snakenladder.request.TransferPoint;

@Service
public class GameServiceImpl implements GameService {

	private static Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);

	@Override
	public Player createPlayer(String playerName) {
		return PlayerFactory.getInstance().createPlayer(playerName);
	}

	@Override
	public Board createBoard(BoardRequest boardRequest) {
		return BoardFactory.getInstance().createBoard(boardRequest);
	}

	@Override
	public Game createGame(GameRequest gameRequest) {
		return GameFactory.getInstance().createGame(gameRequest);
	}

	@Override
	public Board createSnakeLadders(List<TransferPoint> snakeLadders) {
		return null;
	}

	@Override
	public String rollDice(String gameId) {
		Game game = GameModel.getInstance().getGame(gameId);
		return game.rollDice();
	}

	@Override
	public String runGame(String gameId) {
		Game game = GameModel.getInstance().getGame(gameId);
		if (game == null) {
			String err = "Game with ID '" + gameId + "' does not exist.";
			logger.error(err);
			throw new InvalidGameException(err);
		}

		return game.runGame();
	}

}