package com.dongying.service;

import com.dongying.mysql.model.BeverageGoods;
import com.dongying.vo.GoodsOrderVo;

public interface GoodsOrderService {

	BeverageGoods createGoodsOrder(GoodsOrderVo goodsOrderVo);
}
