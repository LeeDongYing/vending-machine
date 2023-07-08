package com.dongying.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class GoodsDataCondition {

	private Integer goodsID;

	private String goodsName;

	private Integer startPrice;

	private Integer endPrice;

	private String priceSort;

	private Integer quantity;
	
	private boolean relationOper;

	private String status;

}
