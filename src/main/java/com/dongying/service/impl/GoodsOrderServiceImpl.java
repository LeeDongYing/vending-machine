package com.dongying.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dongying.mysql.dao.GoodsDao;
import com.dongying.mysql.dao.OrderDao;
import com.dongying.mysql.model.BeverageGoods;
import com.dongying.mysql.model.BeverageOrder;
import com.dongying.service.GoodsOrderService;
import com.dongying.vo.GoodsOrderVo;
import com.dongying.vo.OrderVo;

@Service
public class GoodsOrderServiceImpl implements GoodsOrderService {

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private GoodsDao goodsDao;

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@Transactional
	@Override
	public BeverageOrder createGoodsOrder(OrderVo orderVo) {
		BeverageOrder beverageOrder = null;
		Long goodsID = orderVo.getGoodsID();
		Optional<BeverageGoods> optBeverageGoods = goodsDao.findById(goodsID);
		if (!optBeverageGoods.isPresent()) {
			return null;
		}
		String localDateTimeNow = LocalDateTime.now().format(formatter);
		LocalDateTime orderdate = LocalDateTime.parse(localDateTimeNow, formatter);
		String customerID = orderVo.getCustomerID();

		// goodsBuyPrice 要查詢當下商品價格
		BeverageGoods beverageGoods = goodsDao.findById(goodsID).get();
		Integer goodsBuyPrice = beverageGoods.getPrice();

		// 庫存不夠 直接等於庫存
		Integer goodsQuantity = beverageGoods.getQuantity();
		Integer buyQuantity = orderVo.getBuyQuantity();
		buyQuantity = buyQuantity > goodsQuantity ? goodsQuantity : buyQuantity;

		beverageOrder = BeverageOrder.builder().orderDate(orderdate).goodsBuyPrice(goodsBuyPrice)
				.buyQuantity(buyQuantity).goodsID(goodsID).customerID(customerID).beverageGoods(beverageGoods).build();

		return orderDao.save(beverageOrder);
	}

	@Override
	public BeverageOrder queryOrderById(Long orderID) {
		BeverageOrder beverageOrder = null;
		Optional<BeverageOrder> optBeverageOrder = orderDao.findById(orderID);
		if (optBeverageOrder.isPresent()) {
			beverageOrder = optBeverageOrder.get();
		}
		return beverageOrder;
	}

	@Override
	public List<BeverageOrder> queryOrders() {
		return orderDao.findAll();
	}

	@Transactional
	@Override
	public BeverageGoods updateGoodsOrder(GoodsOrderVo goodsOrderVo) {
		Optional<BeverageGoods> optBeverageGoods = goodsDao.findById(goodsOrderVo.getGoodsID());
		BeverageGoods beverageGoods = null;
		String localDateTimeNow = LocalDateTime.now().format(formatter);
		LocalDateTime orderdate = LocalDateTime.parse(localDateTimeNow, formatter);
		if (!optBeverageGoods.isPresent()) {
			return null;
		}
		String description = goodsOrderVo.getDescription();
		String goodsName = goodsOrderVo.getGoodsName();
		Integer price = goodsOrderVo.getPrice();
		Integer quantity = goodsOrderVo.getQuantity();
		String imageName = goodsOrderVo.getImageName();
		String status = goodsOrderVo.getStatus();

		beverageGoods = optBeverageGoods.get();
		if (description != null) {
			beverageGoods.setDescription(description);
		}
		if (goodsName != null) {
			beverageGoods.setGoodsName(goodsName);
		}
		if (price != null) {
			beverageGoods.setPrice(price);
		}
		if (quantity != null) {
			beverageGoods.setQuantity(quantity);
		}
		if (imageName != null)
			beverageGoods.setQuantity(quantity);
		{
		}
		if (status != null) {
			beverageGoods.setStatus(status);
		}
		List<BeverageOrder> newBeverageOrders = new ArrayList<>();
		List<BeverageOrder> dbBeverageOrders = beverageGoods.getBeverageOrders();
		for (OrderVo orderVo : goodsOrderVo.getOrderVos()) {
			// 把orderVo的orderID 和 goodID填入beverageOrder
			BeverageOrder beverageOrder = BeverageOrder.builder().orderID(orderVo.getOrderID()).build();
			// dbBeverageOrders有沒有這筆訂單
			if (dbBeverageOrders.contains(beverageOrder)) {
				// 有就取出來
				beverageOrder = dbBeverageOrders.get(dbBeverageOrders.indexOf(beverageOrder));
			}
			// 填入oderVo的值
			Integer buyQuantity = orderVo.getBuyQuantity();
			Integer goodsBuyPrice = orderVo.getGoodsBuyPrice();
			String customerID = orderVo.getCustomerID();

			if (buyQuantity != null) {
				beverageOrder.setBuyQuantity(buyQuantity);
			}
			if (goodsBuyPrice != null) {
				beverageOrder.setGoodsBuyPrice(goodsBuyPrice);
			}
			if (customerID != null) {
				beverageOrder.setCustomerID(customerID);
			}
			beverageOrder.setOrderDate(orderdate);
			newBeverageOrders.add(beverageOrder);
		}
		dbBeverageOrders.clear();
		dbBeverageOrders.addAll(newBeverageOrders);
		return beverageGoods;
	}

}
