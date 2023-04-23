package com.dongying.mysql.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "BEVERAGE_GOODS")
public class BeverageGoods {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "GOODS_ID")
	private Long goodsID;

	@Column(name = "GOODS_NAME")
	private String goodsName;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "PRICE")
	private Integer price;

	@Column(name = "QUANTITY")
	private Integer quantity;

	@Column(name = "IMAGE_NAME")
	private String imageName;

	@Column(name = "STATUS")
	private String status;

	@OneToMany(mappedBy = "beverageGoods", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OrderBy(value = "orderID")
	private List<BeverageOrder> beverageOrders;
}
