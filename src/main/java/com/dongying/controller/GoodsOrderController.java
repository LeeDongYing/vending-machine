package com.dongying.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dongying.mysql.model.BeverageGoods;
import com.dongying.mysql.model.BeverageOrder;
import com.dongying.service.GoodsOrderService;
import com.dongying.vo.GoodsOrderVo;
import com.dongying.vo.GoodsVo;
import com.dongying.vo.OrderVo;

//import com.ecommerce.entity.BeverageGoods;
//import com.ecommerce.service.GoodsOrderService;
//import com.ecommerce.vo.GoodsOrderVo;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/ecommerce/GoodsOrderController")
public class GoodsOrderController {

	@Autowired
	private GoodsOrderService goodsOrderService;

	@ApiOperation("新增商品訂單")
	@PostMapping(value = "/createGoodsOrder")
	public ResponseEntity<List<BeverageOrder>> createGoodsOrder(@RequestBody List<OrderVo> OrderVoList) {

		List<BeverageOrder> beverageOrders = goodsOrderService.createGoodsOrder(OrderVoList);

		return ResponseEntity.ok(beverageOrders);
	}

	@ApiOperation("查詢商品訂單")
	@GetMapping(value = "/queryOrderById")
	public ResponseEntity<BeverageOrder> queryOrderById(Long orderID) {

		BeverageOrder beverageOrder = goodsOrderService.queryOrderById(orderID);
		return ResponseEntity.ok(beverageOrder);
	}

	@ApiOperation("查詢所有商品訂單")
	@GetMapping(value = "/queryOrders")
	public ResponseEntity<List<BeverageOrder>> queryOrders() {
		List<BeverageOrder> beverageOrders = goodsOrderService.queryOrders();
		return ResponseEntity.ok(beverageOrders);
	}

	@ApiOperation("更新商品訂單(一對多)")
	@PatchMapping(value = "/updateGoodsOrder")
	public ResponseEntity<BeverageGoods> updateGoodsOrder(@RequestBody GoodsOrderVo goodsOrderVo) {
		// 更新「一筆商品」同時更新「多筆訂單」
		/*
		 * ` 前端只傳入須要更新的部份欄位,未傳入的欄位不更新! UPDATE * 1 筆商品(39) UPDATE * 2 筆訂單(48、49) INSERT *
		 * 2 筆訂單(51、52) DELETE * 1 筆訂單(50) { "goodsID": 39, "goodsName":
		 * "iPhone 12 Pro(256GB)", "price": 36666, "orderVos": [ { "orderID": 48,
		 * "orderDate": "2023-04-01T00:00:00" }, { "orderID": 49, "buyQuantity": 3 }, {
		 * "orderDate": "2023-05-01T00:00:00", "customerID": "F126873254",
		 * "buyQuantity": 1, "goodsBuyPrice": 32000 }, { "orderDate":
		 * "2023-05-02T00:00:00", "customerID": "G436565447", "buyQuantity": 1,
		 * "goodsBuyPrice": 32000 } ] }
		 * 
		 */
		BeverageGoods beverageGoods = goodsOrderService.updateGoodsOrder(goodsOrderVo);

		return ResponseEntity.ok(beverageGoods);
	}

}
