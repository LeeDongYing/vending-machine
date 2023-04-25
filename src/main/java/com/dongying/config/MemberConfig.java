package com.dongying.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

import com.dongying.vo.GoodsVo;
import com.dongying.vo.MemberInfo;

@Configuration
public class MemberConfig {

	@Bean
	@SessionScope
	public MemberInfo sessionMemberInfo() {
		return MemberInfo.builder().isLogin(false).build();
	}

	@Bean
	@SessionScope
	public List<GoodsVo> sessionCartGoods() {
		return new ArrayList<GoodsVo>();
	}

}
