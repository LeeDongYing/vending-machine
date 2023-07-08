package com.dongying.mongoDB.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
@SuperBuilder
@NoArgsConstructor
@Data
@Document(collection = "cartGoods")
public class CartGoods {
	@Id
	private String id;
	
	@Field("goods_id")
	private long goodsID;
	
	@Field("goods_name")
	private String goodsName;
	
	@Field("description")
	private String description;
	
	@Field("price")
	private int price;
	
	@Field("buyQuantity")
	private int buyQuantity;
	
	@Field("imageName")
	private String imageName;
	
	@Field("status")
	private String status;
	
}
