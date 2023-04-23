package com.dongying.vo;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
@SuperBuilder
@NoArgsConstructor
@Data
public class GoodsOrderVo {

	private long goodsID;
	
	private String goodsName;

	private String description;	

	private int price;

	private int quantity;

	private String imageName;

	private String status;
	
	private List<OrderVo> orderVos;
	
}
