package com.snl.application.snakenladder.request;

import lombok.Data;

@Data
public class TransferPoint {
	private int position1;
	private int position2;
	private TransferType transferType;

	public TransferPoint(int position1, int position2, TransferType transferType) {
		this.position1 = position1;
		this.position2 = position2;
		this.transferType = transferType;
	}

}