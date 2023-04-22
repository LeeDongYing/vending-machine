package com.dongying.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dongying.mysql.dao.CriteriaQueryDao;
import com.dongying.mysql.dao.GoodsDao;
import com.dongying.mysql.model.BeverageGoods;
import com.dongying.mysql.model.BeverageOrder;
import com.dongying.service.BackendService;
import com.dongying.vo.GenericPageable;
import com.dongying.vo.GoodsDataCondition;
import com.dongying.vo.GoodsDataInfo;
import com.dongying.vo.GoodsReportSalesInfo;
import com.dongying.vo.GoodsSalesReportCondition;
import com.dongying.vo.GoodsVo;

@Service
public class BackendServiceImpl implements BackendService {
	private static Logger logger = LoggerFactory.getLogger(BackendServiceImpl.class);

	@Autowired
	private GoodsDao goodsDao;
	@Autowired
	private CriteriaQueryDao criQuery;

	@Override
	public BeverageGoods createGoods(GoodsVo goodsVo) {
		copyPicture(goodsVo);
		BeverageGoods beverageGoods = BeverageGoods.builder().goodsName(goodsVo.getGoodsName())
				.description(goodsVo.getDescription()).price(goodsVo.getPrice()).quantity(goodsVo.getQuantity())
				.imageName(goodsVo.getImageName()).status(goodsVo.getStatus()).build();

		return goodsDao.save(beverageGoods);
	}

	@Override
	@Transactional
	public BeverageGoods updateGoods(GoodsVo goodsVo) {
		Optional<BeverageGoods> optBeverageGoods = goodsDao.findById(goodsVo.getGoodsID());

		BeverageGoods beverageGoods = null;
		if (optBeverageGoods.isPresent()) {
			copyPicture(goodsVo);
			beverageGoods = optBeverageGoods.get();
			beverageGoods.setStatus(goodsVo.getStatus());
			beverageGoods.setGoodsName(goodsVo.getGoodsName());
			beverageGoods.setImageName(goodsVo.getImageName());
			beverageGoods.setQuantity(goodsVo.getQuantity());
			beverageGoods.setDescription(goodsVo.getDescription());
			beverageGoods.setPrice(goodsVo.getPrice());
		}
		return beverageGoods;
	}

	@Override
	public List<BeverageGoods> queryAllGoods() {
		return goodsDao.findAll();
	}

	@Override
	public BeverageGoods queryGoodsByID(long goodsID) {
		Optional<BeverageGoods> optBeverageGoods = goodsDao.findById(goodsID);
		BeverageGoods beverageGoods = null;
		if (optBeverageGoods.isPresent()) {
			beverageGoods = optBeverageGoods.get();
		}
		return beverageGoods;
	}

	@Override
	public GoodsDataInfo queryGoodsData(GoodsDataCondition condition, GenericPageable genericPageable) {
		Page<BeverageGoods> goodsResult = criQuery.findGoodsByFilter(condition, genericPageable);
		Long dataTotalSize = goodsResult.getTotalElements();
		// 回填page data
		genericPageable = setGenericPageable(genericPageable, dataTotalSize);
		GoodsDataInfo goodsDataInfo = GoodsDataInfo.builder().goodsDatas(goodsResult.toList())
				.genericPageable(genericPageable).build();

		return goodsDataInfo;
	}

	@Override
	public GoodsReportSalesInfo queryGoodsSales(GoodsSalesReportCondition condition, GenericPageable genericPageable) {
		/*
		 * startDate:2022/09/19 endDate:2022/09/19 currentPageNo:1 pageDataSize: 3
		 */
		Page<BeverageOrder> orderList = criQuery.findOrdersByFilter(condition, genericPageable);
		Long dataTotalSize = orderList.getTotalElements();
		// 回填page data
		genericPageable = setGenericPageable(genericPageable, dataTotalSize);
		List<BeverageOrder> beverageOrders = new ArrayList<>();
		if(!orderList.isEmpty()) {
			beverageOrders = orderList.toList();
		}
		GoodsReportSalesInfo goodsReportSalesInfo = GoodsReportSalesInfo.builder()
				.goodsReportSalesList(beverageOrders).genericPageable(genericPageable).build();		
		return goodsReportSalesInfo;
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

	private void copyPicture(GoodsVo goodsVo) {
		// 複制檔案
		MultipartFile file = goodsVo.getFile();
		// 前端傳入檔案名稱
		String fileName = goodsVo.getImageName();

		try {
			Files.copy(file.getInputStream(), Paths.get("/Users/hsiehyajen/Documents/DrinksImage").resolve(fileName));
		} catch (IOException e) {
			logger.info("圖檔發生錯誤：");
			logger.info(e.getMessage());
		}
	}
}
