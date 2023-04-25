package com.dongying.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dongying.mysql.dao.BeverageMemberDao;
import com.dongying.mysql.model.Member;
import com.dongying.service.BeverageMemberService;
import com.dongying.vo.MemberInfo;

@Service
public class BeverageMemberServiceImpl implements BeverageMemberService {

	@Autowired
	private BeverageMemberDao beverageMemberDao;

	@Override
	public MemberInfo login(MemberInfo memberInfo) {
		String identificationNo = memberInfo.getIdentificationNo();
		String password = memberInfo.getPassword();

		MemberInfo memberVo = MemberInfo.builder().isLogin(false).build();
		Optional<Member> optMember = beverageMemberDao.findByIdentificationNoAndPassword(identificationNo, password);
		if (optMember.isPresent()) {
			Member member = optMember.get();
			memberVo.setLogin(true);
			memberVo.setCustomerName(member.getCustomerName());
			memberVo.setIdentificationNo(member.getIdentificationNo());
		}

		return memberVo;
	}

}
