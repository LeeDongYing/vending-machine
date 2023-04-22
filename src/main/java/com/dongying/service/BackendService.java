package com.dongying.service;

import java.util.List;

import com.dongying.mysql.model.BeverageGoods;
import com.dongying.vo.GenericPageable;
import com.dongying.vo.GoodsDataCondition;
import com.dongying.vo.GoodsDataInfo;
import com.dongying.vo.GoodsReportSalesInfo;
import com.dongying.vo.GoodsSalesReportCondition;
import com.dongying.vo.GoodsVo;

public interface BackendService {

	BeverageGoods createGoods(GoodsVo goodsVo);

	BeverageGoods updateGoods(GoodsVo goodsVo);

	BeverageGoods queryGoodsByID(long goodsID);

	GoodsDataInfo queryGoodsData(GoodsDataCondition condition, GenericPageable genericPageable);

	List<BeverageGoods> queryAllGoods();

	GoodsReportSalesInfo queryGoodsSales(GoodsSalesReportCondition condition, GenericPageable genericPageable);
}
