package com.snl.application.snakenladder.entity.board.block;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class BoardBlock {
	private final Integer blockValue;
	private final Integer destinationValue;
}