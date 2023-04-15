package com.dongying.vo;

import java.util.List;

import com.dongying.mysql.model.BeverageGoods;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Data
public class GoodsDataInfo {
	
	private List<BeverageGoods> goodsDatas;
	
	private GenericPageable genericPageable;
}
