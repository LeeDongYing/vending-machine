package com.dongying.vo;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Data
public class CheckoutCompleteInfo {
	private OrderCustomer customer;
	private List<GoodsVo> orderGoodsList;
}
