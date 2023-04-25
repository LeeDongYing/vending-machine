package com.dongying.mysql.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dongying.mysql.model.BeverageOrder;

@Repository
public interface OrderDao extends JpaRepository<BeverageOrder, Long> {
	List<BeverageOrder> findByGoodsID(Long goodsID);
}
