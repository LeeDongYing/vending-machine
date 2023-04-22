package com.dongying.vo;

import java.util.List;

import com.dongying.mysql.model.BeverageOrder;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
@Data
public class GoodsReportSalesInfo {
	
	private List<BeverageOrder> goodsReportSalesList;

	private GenericPageable genericPageable;

}
