package com.snl.application.snakenladder.request;

import java.util.List;

import lombok.Data;

@Data
public class BoardRequest {
	private String boardId;
	private int length;
	private int breadth;
	private List<TransferPoint> transferPoints;
}