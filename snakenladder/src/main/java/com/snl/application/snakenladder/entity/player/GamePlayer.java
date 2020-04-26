package com.snl.application.snakenladder.entity.player;

import com.snl.application.snakenladder.entity.board.block.BoardBlock;
import com.snl.application.snakenladder.entity.board.block.WinnerBlock;

import lombok.Getter;

@Getter
public class GamePlayer {
	private Player player;
	private BoardBlock currentBlock;

	public GamePlayer(Player player, BoardBlock location) {
		this.player = player;
		this.currentBlock = location;
	}

	public void setCurrentBlock(BoardBlock currentBlock) {
		this.currentBlock = currentBlock;
	}

	public boolean isWinner() {
		if (currentBlock instanceof WinnerBlock)
			return true;
		else
			return false;
	}

}