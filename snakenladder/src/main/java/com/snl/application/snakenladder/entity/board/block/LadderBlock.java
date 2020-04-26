package com.snl.application.snakenladder.entity.board.block;

public class LadderBlock extends BoardBlock {

	public LadderBlock(Integer blockValue, Integer destinationValue) {
		super(Math.min(blockValue, destinationValue), Math.max(blockValue, destinationValue));
	}

}
