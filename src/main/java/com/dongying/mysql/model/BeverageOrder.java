package com.dongying.mysql.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "BEVERAGE_ORDER")
public class BeverageOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_ID")
	private Long orderID;

	@Column(name = "ORDER_DATE")
	private LocalDateTime orderDate;
	
	@Column(name = "GOODS_BUY_PRICE")
	private Integer goodsBuyPrice;

	@Column(name = "BUY_QUANTITY")
	private Integer buyQuantity;

	@Column(name = "GOODS_ID")
	private Long goodsID;

	@Column(name = "CUSTOMER_ID")
	private String customerID;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "GOODS_ID",insertable = false,updatable = false)
	private BeverageGoods beverageGoods;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CUSTOMER_ID",insertable = false,updatable = false)
	private Member member;

}
