package com.dongying.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Data
public class MemberInfo {

	private boolean isLogin;

	private String identificationNo;

	private String customerName;

	private String password;
}
