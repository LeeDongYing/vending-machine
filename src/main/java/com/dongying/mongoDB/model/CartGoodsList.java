package com.dongying.mongoDB.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.dongying.vo.GoodsVo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Data
@Document(collection = "cartGoodsList")
public class CartGoodsList {

	@Id
	private String id;

	@Field("identificationNo")
	private String identificationNo;
	@Field("cart_goods")
	private List<GoodsVo> cartGoods;

}
