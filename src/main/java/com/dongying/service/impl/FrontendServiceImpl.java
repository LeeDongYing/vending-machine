package com.dongying.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.dongying.mysql.dao.CriteriaQueryDao;
import com.dongying.mysql.dao.GoodsDao;
import com.dongying.mysql.dao.OrderDao;
import com.dongying.mysql.model.BeverageGoods;
import com.dongying.mysql.model.BeverageOrder;
import com.dongying.service.FrontendService;
import com.dongying.vo.CheckoutCompleteInfo;
import com.dongying.vo.GenericPageable;
import com.dongying.vo.GoodsDataCondition;
import com.dongying.vo.GoodsDataInfo;
import com.dongying.vo.GoodsVo;
import com.dongying.vo.MemberInfo;
import com.dongying.vo.OrderCustomer;

@Service
public class FrontendServiceImpl implements FrontendService {

	@Autowired
	private CriteriaQueryDao dao;
	@Autowired
	private GoodsDao goodsDao;
	@Autowired
	private OrderDao orderDao;

	@Override
	public GoodsDataInfo queryGoodsData(GoodsDataCondition condition, GenericPageable genericPageable) {
		Page<BeverageGoods> goodsResult = dao.findGoodsByFilter(condition, genericPageable);
		Long dataTotalSize = goodsResult.getTotalElements();
		// 回填page data
		genericPageable = setGenericPageable(genericPageable, dataTotalSize);
		GoodsDataInfo goodsDataInfo = GoodsDataInfo.builder().goodsDatas(goodsResult.toList())
				.genericPageable(genericPageable).build();
		return goodsDataInfo;
	}

	@Transactional(rollbackOn = Exception.class)
	@Override
	public CheckoutCompleteInfo checkoutGoods(MemberInfo sessionMemberInfo, OrderCustomer customer,
			List<GoodsVo> cartGoods) {
		if (!sessionMemberInfo.isLogin()) {
			return null;
		}

		List<BeverageOrder> orderList = new ArrayList<>();
		for (GoodsVo goods : cartGoods) {
			// 查goodsID 把現在價格差出來 跟庫存夠不夠 數量不夠就跟庫存一樣多
			Optional<BeverageGoods> otpBeverageGoods = goodsDao.findById(goods.getGoodsID());
			if (!otpBeverageGoods.isPresent()) {
				continue;
			}
			BeverageGoods dbBeverageGoods = otpBeverageGoods.get();
			if ("0".equals(dbBeverageGoods.getStatus())) {
				continue;
			}

			int quantity = goods.getQuantity() > dbBeverageGoods.getQuantity() ? dbBeverageGoods.getQuantity()
					: dbBeverageGoods.getQuantity();
			dbBeverageGoods.setQuantity(dbBeverageGoods.getQuantity() - quantity);

			BeverageOrder dbBeverageOrder = BeverageOrder.builder().orderDate(LocalDateTime.now())
					.goodsBuyPrice(dbBeverageGoods.getPrice()).buyQuantity(quantity)
					.goodsID(dbBeverageGoods.getGoodsID()).customerID(sessionMemberInfo.getIdentificationNo()).build();
			orderList.add(dbBeverageOrder);
		}

		orderDao.saveAll(orderList);
		CheckoutCompleteInfo checkoutCompleteInfo = CheckoutCompleteInfo.builder().customer(customer)
				.orderGoodsList(cartGoods).build();
		return checkoutCompleteInfo;
	}

	private GenericPageable setGenericPageable(GenericPageable genericPageable, Long dataTotalSize) {
		int pageDataSize = genericPageable.getPageDataSize();
		int dataTotalSizeInt = dataTotalSize.intValue();
		int totalPage = 0;
		if (dataTotalSizeInt % pageDataSize == 0) {
			totalPage = dataTotalSizeInt / pageDataSize;
		} else {
			totalPage = (dataTotalSizeInt / pageDataSize) + 1;
		}
		genericPageable.setDataTotalSize(dataTotalSizeInt);
		genericPageable.setTotalPage(totalPage);
		return genericPageable;
	}
}
