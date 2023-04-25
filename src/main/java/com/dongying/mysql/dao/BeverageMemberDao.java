package com.dongying.mysql.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dongying.mysql.model.Member;

public interface BeverageMemberDao extends JpaRepository<Member, String> {
	Optional<Member> findByIdentificationNoAndPassword(String identificationNo, String password);
}
