package com.dongying.mysql.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "BEVERAGE_MEMBER")
public class Member {
	@Id
	@Column(name = "IDENTIFICATION_NO")
	private String identificationNo;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "CUSTOMER_NAME")
	private String customerName;

	@JsonIgnore
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OrderBy(value = "orderID")
	private List<BeverageOrder> beverageOrders;
}
