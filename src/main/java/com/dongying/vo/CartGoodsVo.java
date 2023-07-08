package com.dongying.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Data
public class CartGoodsVo {

	private String identificationNo;

	private GoodsVo cartGoods;
	
	private int index;

}
