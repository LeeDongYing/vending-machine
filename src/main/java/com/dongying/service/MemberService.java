package com.dongying.service;

import com.dongying.mongoDB.model.CartGoodsList;
import com.dongying.vo.CartGoodsVo;

public interface MemberService {

	CartGoodsList addCartGoods(CartGoodsVo cartGoodsVo);
	
	CartGoodsList updateCartGoods(CartGoodsVo cartGoodsVo);
	
	CartGoodsList queryCartGoods(CartGoodsVo cartGoodsVo);
	
	CartGoodsList cancelCartGoods(CartGoodsVo cartGoodsVo);
	
	CartGoodsList clearCartGoods(CartGoodsVo cartGoodsVo);
}
