package com.dongying.service;

import java.util.List;

import com.dongying.mysql.model.BeverageGoods;
import com.dongying.mysql.model.BeverageOrder;
import com.dongying.vo.GoodsOrderVo;
import com.dongying.vo.GoodsVo;
import com.dongying.vo.OrderVo;

public interface GoodsOrderService {

	List<BeverageOrder> createGoodsOrder(List<OrderVo> OrderVoList);

	BeverageOrder queryOrderById(Long orderID);

	List<BeverageOrder> queryOrders();	

	BeverageGoods updateGoodsOrder(GoodsOrderVo goodsOrderVo);

}
