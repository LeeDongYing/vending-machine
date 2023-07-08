package com.dongying.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dongying.mongoDB.dao.CartGoodsDao;
import com.dongying.mongoDB.model.CartGoodsList;
import com.dongying.service.MemberService;
import com.dongying.vo.CartGoodsVo;
import com.dongying.vo.GoodsVo;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private CartGoodsDao cartGoodsDao;

	@Override
	public CartGoodsList addCartGoods(CartGoodsVo cartGoodsVo) {
		// 先撈使用者有沒有購物車及商品
		Optional<CartGoodsList> optCartGoodsList = cartGoodsDao
				.findByIdentificationNo(cartGoodsVo.getIdentificationNo());
		if (!optCartGoodsList.isPresent()) {
			CartGoodsList newCartGoodsList = CartGoodsList.builder().identificationNo(cartGoodsVo.getIdentificationNo())
					.build();
			List<GoodsVo> newCart = new ArrayList<>();
			newCart.add(cartGoodsVo.getCartGoods());
			newCartGoodsList.setCartGoods(newCart);
			return cartGoodsDao.save(newCartGoodsList);
		}
		CartGoodsList dbCartGoodsList = optCartGoodsList.get();
		List<GoodsVo> dbcartGoods = dbCartGoodsList.getCartGoods();
		dbcartGoods.add(cartGoodsVo.getCartGoods());
		dbCartGoodsList.setCartGoods(dbcartGoods);

		return cartGoodsDao.save(dbCartGoodsList);
	}
	

	@Override
	public CartGoodsList updateCartGoods(CartGoodsVo cartGoodsVo) {
		Optional<CartGoodsList> optCartGoodsList = cartGoodsDao
				.findByIdentificationNo(cartGoodsVo.getIdentificationNo());
		CartGoodsList dbCartGoodsList = optCartGoodsList.get();
		List<GoodsVo> dbcartGoods = dbCartGoodsList.getCartGoods();
		dbcartGoods.set(cartGoodsVo.getIndex(), cartGoodsVo.getCartGoods());
		dbCartGoodsList.setCartGoods(dbcartGoods);

		return cartGoodsDao.save(dbCartGoodsList);
	}


	@Override
	public CartGoodsList queryCartGoods(CartGoodsVo cartGoodsVo) {
		Optional<CartGoodsList> optCartGoodsList = cartGoodsDao
				.findByIdentificationNo(cartGoodsVo.getIdentificationNo());
		if (!optCartGoodsList.isPresent()) {
			return cartGoodsDao.save(CartGoodsList.builder().identificationNo(cartGoodsVo.getIdentificationNo())
					.cartGoods(Collections.emptyList()).build());
		}
		return optCartGoodsList.get();
	}

	@Override
	public CartGoodsList cancelCartGoods(CartGoodsVo cartGoodsVo) {
		Optional<CartGoodsList> optCartGoodsList = cartGoodsDao
				.findByIdentificationNo(cartGoodsVo.getIdentificationNo());
		CartGoodsList dbCartGoodsList = optCartGoodsList.get();
		List<GoodsVo> cartGoods = dbCartGoodsList.getCartGoods();
		cartGoods.remove(cartGoodsVo.getIndex());
		dbCartGoodsList.setCartGoods(cartGoods);
		return cartGoodsDao.save(dbCartGoodsList);
	}

	@Override
	public CartGoodsList clearCartGoods(CartGoodsVo cartGoodsVo) {
		Optional<CartGoodsList> optCartGoodsList = cartGoodsDao
				.findByIdentificationNo(cartGoodsVo.getIdentificationNo());
		CartGoodsList dbCartGoodsList = optCartGoodsList.get();
		dbCartGoodsList.setCartGoods(Collections.emptyList());
		return cartGoodsDao.save(dbCartGoodsList);
	}

}
