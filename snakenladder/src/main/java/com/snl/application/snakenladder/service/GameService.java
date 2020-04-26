package com.snl.application.snakenladder.service;

import java.util.List;

import com.snl.application.snakenladder.entity.board.Board;
import com.snl.application.snakenladder.entity.game.Game;
import com.snl.application.snakenladder.entity.player.Player;
import com.snl.application.snakenladder.request.BoardRequest;
import com.snl.application.snakenladder.request.GameRequest;
import com.snl.application.snakenladder.request.TransferPoint;

public interface GameService {

	public Player createPlayer(String playerName);

	public Board createBoard(BoardRequest boardRequest);

	public Game createGame(GameRequest gameRequest);

	public Board createSnakeLadders(List<TransferPoint> snakeLadders);

	public String rollDice(String gameId);

	public String runGame(String gameId);

}