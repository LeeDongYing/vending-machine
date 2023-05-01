package com.dongying.service;

import java.util.List;

import com.dongying.vo.CheckoutCompleteInfo;
import com.dongying.vo.GenericPageable;
import com.dongying.vo.GoodsDataCondition;
import com.dongying.vo.GoodsDataInfo;
import com.dongying.vo.GoodsVo;
import com.dongying.vo.MemberInfo;
import com.dongying.vo.OrderCustomer;

public interface FrontendService {

	GoodsDataInfo queryGoodsData(GoodsDataCondition condition, GenericPageable genericPageable);

	CheckoutCompleteInfo checkoutGoods(MemberInfo sessionMemberInfo, OrderCustomer customer, List<GoodsVo> cartGoods);
}