package com.dongying.mongoDB.dao;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dongying.mongoDB.model.CartGoodsList;
@Repository
public interface CartGoodsDao extends MongoRepository<CartGoodsList, String>{

	Optional<CartGoodsList> findByIdentificationNo(String identificationNo);
}
