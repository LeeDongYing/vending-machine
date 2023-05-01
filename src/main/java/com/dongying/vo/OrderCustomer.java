package com.dongying.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
@Data
public class OrderCustomer {
	private String cusName;
	private String mobileNumber;
	private String homeNumber;
	private String orderAddr;

}
