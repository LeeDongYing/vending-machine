package com.dongying.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dongying.mysql.dao.GoodsDao;
import com.dongying.mysql.model.BeverageGoods;
import com.dongying.service.BackendService;
import com.dongying.vo.GenericPageable;
import com.dongying.vo.GoodsDataCondition;
import com.dongying.vo.GoodsDataInfo;
import com.dongying.vo.GoodsVo;

@Service
public class BackendServiceImpl implements BackendService {
	private static Logger logger = LoggerFactory.getLogger(BackendServiceImpl.class);

	@Autowired
	private GoodsDao goodsDao;

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
		Pageable pageable = PageRequest.of(genericPageable.getCurrentPageNo(), genericPageable.getPageDataSize());
		Page<BeverageGoods> goodsResult = goodsDao.findByGoodsIDIsNotNull(pageable);
		Long dataTotalSize = goodsResult.getTotalElements();
		
		genericPageable.setDataTotalSize(dataTotalSize.intValue());

		int endPageNo = (dataTotalSize.intValue()/ genericPageable.getPageDataSize()) + 1;
		genericPageable.setEndPageNo(endPageNo);

//		int currentPageNo = genericPageable.getCurrentPageNo();
//		if (currentPageNo == 0) {
//			
//		}
//		if (currentPageNo > endPageNo) {
//			currentPageNo = endPageNo;
//		}
//
//		genericPageable.setPagination(pagination);

		GoodsDataInfo goodsDataInfo = GoodsDataInfo.builder().goodsDatas(goodsResult.toList()).genericPageable(genericPageable)
				.build();
		return goodsDataInfo;
	}

	public void copyPicture(GoodsVo goodsVo) {
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
