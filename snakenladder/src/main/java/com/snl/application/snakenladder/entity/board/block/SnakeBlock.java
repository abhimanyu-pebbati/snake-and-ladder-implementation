package com.snl.application.snakenladder.entity.board.block;

public class SnakeBlock extends BoardBlock {

	public SnakeBlock(Integer blockValue, Integer destinationValue) {
		super(Math.max(blockValue, destinationValue), Math.min(blockValue, destinationValue));
	}

}
