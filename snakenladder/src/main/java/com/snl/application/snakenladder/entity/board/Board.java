package com.snl.application.snakenladder.entity.board;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snl.application.snakenladder.entity.board.block.BoardBlock;
import com.snl.application.snakenladder.entity.board.block.LadderBlock;
import com.snl.application.snakenladder.entity.board.block.RegularBlock;
import com.snl.application.snakenladder.entity.board.block.SnakeBlock;
import com.snl.application.snakenladder.entity.board.block.WinnerBlock;
import com.snl.application.snakenladder.entity.player.GamePlayer;
import com.snl.application.snakenladder.exceptionhandling.exception.BoardLoopException;
import com.snl.application.snakenladder.exceptionhandling.exception.InvalidInputException;

import lombok.Getter;

@Getter
public class Board {
	private String boardId;
	private int length;
	private int breadth;
	private int size;
	private Map<Integer, BoardBlock> boardBlocks;
	private String locationTraverse = " -> ";

	private static Logger logger = LoggerFactory.getLogger(Board.class);

	public Board(String boardId, int length, int breadth) {
		this.boardId = boardId;
		this.length = length;
		this.breadth = breadth;
		if (this.length < 1)
			throw new InvalidInputException("Length cannot be negative.");
		if (this.breadth < 1)
			throw new InvalidInputException("Breadth cannot be negative.");
		this.size = length * breadth;
		this.boardBlocks = new HashMap<Integer, BoardBlock>();
		init();
	}

	public String getBoardId() {
		return boardId;
	}

	public int getLength() {
		return length;
	}

	public int getBreadth() {
		return breadth;
	}

	public int getSize() {
		return size;
	}

	private void init() {
		for (int i = 1; i < this.size; i++)
			boardBlocks.put(i, new RegularBlock(i));
		boardBlocks.put(size, new WinnerBlock(size));
	}

	public BoardBlock getBlockAt(int location) {
		return this.boardBlocks.get(location);
	}

	public BoardBlock getStartBlock() {
		return this.getBlockAt(1);
	}

	protected void validateTransferPoints(int point1, int point2) {
		this.validateLocation(point1);
		this.validateLocation(point2);

		if (point1 == point2)
			throw new InvalidInputException("Source and destination cannot be equal for a snake or ladder.");

	}

	private void validateLocation(Integer location) {
		if (location < 1)
			throw new InvalidInputException("Location " + location + "cannot be less than 1.");
		if (location > size)
			throw new InvalidInputException(
					"Location " + location + "cannot be greater than size of the board - " + this.size + ".");
		if (location == 1 || location == size)
			throw new InvalidInputException("Point " + location + " cannot be start or end points for snake or ladder");
	}

	public String movePlayer(GamePlayer gamePlayer, int moves) {
		BoardBlock currentBlock = gamePlayer.getCurrentBlock();
		BoardBlock destinationBlock;
		if (!this.boardBlocks.containsValue(currentBlock)) {
			return "Player in the wrong game.";
		}

		int location = currentBlock.getBlockValue();
		destinationBlock = this.getBlockAt(location + moves);
		if (destinationBlock == null) {
			return "Player '" + gamePlayer.getPlayer().getPlayerName() + "' can make a maximum of "
					+ (this.size - location) + " moves from the current location.\n";
		}
		StringBuffer path = new StringBuffer(location);
		path.append(currentBlock.getBlockValue());
		path.append(locationTraverse);
		path.append(destinationBlock.getBlockValue());

		while (destinationBlock.getBlockValue() != destinationBlock.getDestinationValue()) {
			path.append(locationTraverse);
			destinationBlock = this.getBlockAt(destinationBlock.getDestinationValue());
			path.append(destinationBlock.getBlockValue());
		}
		path.append(System.lineSeparator());

		gamePlayer.setCurrentBlock(destinationBlock);

		return path.toString();
	}

	public boolean insertBlockOnBoard(BoardBlock boardBlock) {
		return this.insertBlockOnBoard(boardBlock, true);
	}

	public boolean insertBlockOnBoard(BoardBlock boardBlock, boolean doValidateBoard) {
		if (boardBlock == null) {
			return false;
		}

		this.validateLocation(boardBlock.getBlockValue());

		BoardBlock oldBlock = this.getBlockAt(boardBlock.getBlockValue());
		this.boardBlocks.put(boardBlock.getBlockValue(), boardBlock);
		if (doValidateBoard) {
			if (this.validateBoard())
				return true;
			else {
				this.boardBlocks.put(oldBlock.getBlockValue(), oldBlock);
				return false;
			}
		} else {
			return true;
		}
	}

	public boolean insertSnake(int point1, int point2) {
		return this.insertSnake(point1, point2, true);
	}

	public boolean insertSnake(int point1, int point2, boolean doValidateBoard) {
		SnakeBlock snakeBlock = new SnakeBlock(point1, point2);
		return this.insertBlockOnBoard(snakeBlock, doValidateBoard);
	}

	public boolean insertLadder(int point1, int point2) {
		return this.insertLadder(point1, point2, true);
	}

	public boolean insertLadder(int point1, int point2, boolean doValidateBoard) {
		LadderBlock ladderBlock = new LadderBlock(point1, point2);
		return this.insertBlockOnBoard(ladderBlock, doValidateBoard);
	}

	public boolean validateBoard() {
		Set<Integer> visited = new HashSet<Integer>();
		Set<Integer> cycleVisited = new HashSet<Integer>();
		BoardBlock boardBlock;
		StringBuffer path;
		for (int blockNumber = 1; blockNumber <= this.size; blockNumber++) {
			if (!visited.contains(blockNumber)) {
				path = new StringBuffer();
				boardBlock = this.getBlockAt(blockNumber);
				cycleVisited.clear();

				visited.add(boardBlock.getBlockValue());
				cycleVisited.add(boardBlock.getBlockValue());
				path.append(boardBlock.getBlockValue());

				while (boardBlock.getBlockValue() != boardBlock.getDestinationValue()) {
					boardBlock = this.getBlockAt(boardBlock.getDestinationValue());
					if (cycleVisited.contains(boardBlock.getBlockValue())) {
						String err = "Board has a cyclic loop of snakes and ladder with the following path: "
								+ path.toString();
						logger.error(err);
						throw new BoardLoopException(err);
					}

					visited.add(boardBlock.getBlockValue());
					cycleVisited.add(boardBlock.getBlockValue());
					path.append(locationTraverse);
					path.append(boardBlock.getBlockValue());
				}
			}

		}
		return true;
	}

}
