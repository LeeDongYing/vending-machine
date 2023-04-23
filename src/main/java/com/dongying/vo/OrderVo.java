package com.dongying.vo;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class OrderVo {

	private long orderID;
	
	private LocalDateTime orderDate;

	private String customerID;

	private long goodsID;

	private Integer goodsBuyPrice;

	private Integer buyQuantity;
	
}
