package com.snl.application.snakenladder.factory;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snl.application.snakenladder.entity.board.Board;
import com.snl.application.snakenladder.exceptionhandling.exception.InvalidInputException;
import com.snl.application.snakenladder.model.BoardModel;
import com.snl.application.snakenladder.request.BoardRequest;
import com.snl.application.snakenladder.request.TransferPoint;
import com.snl.application.snakenladder.request.TransferType;

public class BoardFactory {
	private static BoardFactory instance;
	private static Logger logger = LoggerFactory.getLogger(BoardFactory.class);

	private BoardFactory() {
	}

	public static BoardFactory getInstance() {
		if (instance == null)
			instance = new BoardFactory();
		return instance;
	}

	public Board createBoard(BoardRequest boardRequest) {
		Board board = null;
		if (!validateRequest(boardRequest))
			return null;

		board = BoardModel.getInstance().getBoard(boardRequest.getBoardId());
		if (board != null) {
			String err = "Board '" + boardRequest.getBoardId() + "' already exists.";
			logger.error(err);
			throw new InvalidInputException(err);
		}

		board = new Board(boardRequest.getBoardId(), boardRequest.getLength(), boardRequest.getBreadth());

		for (TransferPoint transferPoint : boardRequest.getTransferPoints()) {
			if (TransferType.SNAKE == transferPoint.getTransferType()) {
				board.insertSnake(transferPoint.getPosition1(), transferPoint.getPosition2(), false);
			} else if (TransferType.LADDER == transferPoint.getTransferType()) {
				board.insertLadder(transferPoint.getPosition1(), transferPoint.getPosition2(), false);
			} else {
				String err = "Transfer type inputted is invalid for points " + transferPoint.getPosition1() + " and "
						+ transferPoint.getPosition2() + ".";
				logger.error(err);
				throw new InvalidInputException(err);
			}
		}

		if (board.validateBoard()) {
			BoardModel.getInstance().addBoard(board);
			return board;
		} else
			return null;
	}

	private boolean validateRequest(BoardRequest boardRequest) {
		if (boardRequest.getLength() < 1) {
			String err = "Length of the board has to be greater than 0.";
			logger.error(err);
			throw new InvalidInputException(err);
		}

		if (boardRequest.getBreadth() < 1) {
			String err = "Breadth of the board has to be greater than 0.";
			logger.error(err);
			throw new InvalidInputException(err);
		}

		if (boardRequest.getLength() * boardRequest.getBreadth() < 2) {
			String err = "Size of the board has to be greater than 1.";
			logger.error(err);
			throw new InvalidInputException(err);
		}

		if (boardRequest.getBoardId() == null || boardRequest.getBoardId().trim().isEmpty()) {
			String msg = "Board ID not entered, setting a random UUID.";
			logger.error(msg);
			boardRequest.setBoardId(UUID.randomUUID().toString());
		}

		return true;
	}

}