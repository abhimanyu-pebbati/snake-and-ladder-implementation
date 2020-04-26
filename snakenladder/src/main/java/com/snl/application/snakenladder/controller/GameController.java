package com.snl.application.snakenladder.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.snl.application.snakenladder.entity.board.Board;
import com.snl.application.snakenladder.entity.game.Game;
import com.snl.application.snakenladder.entity.player.Player;
import com.snl.application.snakenladder.request.BoardRequest;
import com.snl.application.snakenladder.request.GameRequest;
import com.snl.application.snakenladder.request.TransferPoint;
import com.snl.application.snakenladder.service.GameService;

@RestController
public class GameController {

	@Autowired
	GameService gameService;

	@RequestMapping(method = RequestMethod.POST, value = "/player")
	public Player createPlayer(@RequestParam String playerName) {
		return gameService.createPlayer(playerName);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/players")
	public List<Player> createPlayers(@RequestParam List<String> playerNames) {
		List<Player> players = new ArrayList<Player>();
		for (String player : playerNames)
			players.add(gameService.createPlayer(player));

		return players;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/board")
	public Board createBoard(@RequestBody BoardRequest boardRequest) {
		return gameService.createBoard(boardRequest);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/game")
	public Game createGame(@RequestBody GameRequest gameRequest) {
		return gameService.createGame(gameRequest);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/roll-dice")
	public String rollDice(@RequestParam String gameId) {
		return gameService.rollDice(gameId);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/run-game")
	public String runGame(@RequestParam String gameId) {
		return gameService.runGame(gameId);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/snake-ladder")
	public Board createSnakeLadders(@RequestBody List<TransferPoint> snakeLadders) {
		return gameService.createSnakeLadders(snakeLadders);
	}
}