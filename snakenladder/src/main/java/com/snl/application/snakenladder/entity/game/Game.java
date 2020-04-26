package com.snl.application.snakenladder.entity.game;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snl.application.snakenladder.entity.board.Board;
import com.snl.application.snakenladder.entity.dice.Dice;
import com.snl.application.snakenladder.entity.player.GamePlayer;
import com.snl.application.snakenladder.entity.player.Player;

import lombok.Data;

@Data
public class Game {
	private final String gameId;
	private final Board gameBoard;
	private final Dice gameDice;
	private List<GamePlayer> players;
	private int playingPlayer;
	private GamePlayer winner;

	private static Logger logger = LoggerFactory.getLogger(Game.class);

	public Game(String gameId, Board gameBoard, Dice gameDice) {
		this.gameId = gameId;
		this.gameBoard = gameBoard;
		this.gameDice = gameDice;
		players = new ArrayList<GamePlayer>();
		playingPlayer = 1;
	}

	public void addPlayer(Player player) {
		this.players.add(new GamePlayer(player, gameBoard.getStartBlock()));
	}

	public void addPlayers(List<Player> players) {
		for (Player player : players)
			this.addPlayer(player);
	}

	public int getNumberOfPlayers() {
		return this.players.size();
	}

	public String rollDice() {
		return this.rollDice(null);
	}

	public String rollDice(Object diceInput) {
		StringBuffer result = new StringBuffer();
		if (winner != null) {
			result.append("Game already over! Winner is '" + winner.getPlayer().getPlayerName() + "'.");
			return result.toString();
		}
		GamePlayer player = this.players.get(playingPlayer % players.size());
		int diceVal = this.gameDice.rollDice(diceInput);
		result.append("Player '");
		result.append(player.getPlayer().getPlayerName());
		result.append("' rolled the dice - " + diceVal + ".\n");

		if (diceVal < 1) {
			result.append("Dice value has to be greater than/equal to 1 and less than/equal to ");
			result.append(this.gameDice.getMaxDiceValue());
			result.append(System.lineSeparator());
			return result.toString();
		}

		result.append(gameBoard.movePlayer(player, diceVal));
		playingPlayer++;
		if (player.isWinner()) {
			this.winner = player;
			result.append("Game over! Winner is '" + winner.getPlayer().getPlayerName() + "'.\n");
		}

		logger.info(result.toString());

		return result.toString();

	}

	public String runGame() {
		StringBuffer result = new StringBuffer();
		while (winner == null) {
			result.append(rollDice());
		}
		result.append("Game over! Winner is '" + winner.getPlayer().getPlayerName() + "'.\n");

		logger.info(result.toString());
		return result.toString();
	}

}