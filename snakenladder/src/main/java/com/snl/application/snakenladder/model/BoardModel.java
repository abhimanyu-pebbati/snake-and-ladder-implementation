package com.snl.application.snakenladder.model;

import java.util.HashMap;
import java.util.Map;

import com.snl.application.snakenladder.entity.board.Board;

public class BoardModel {
	private static BoardModel instance;

	private Map<String, Board> boards;

	private BoardModel() {
		boards = new HashMap<String, Board>();
	}

	public static BoardModel getInstance() {
		if (instance == null)
			instance = new BoardModel();
		return instance;
	}

	public Board getBoard(String boardId) {
		return boards.get(boardId);
	}

	public void addBoard(Board board) {
		boards.put(board.getBoardId(), board);
	}
}
