package com.dongying.mysql.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dongying.mysql.model.BeverageGoods;

@Repository
public interface GoodsDao extends JpaRepository<BeverageGoods, Long>{

	Page<BeverageGoods> findByGoodsIDIsNotNull(Pageable pageable);
}
