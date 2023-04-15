package com.dongying.mysql.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.type.descriptor.sql.LongNVarcharTypeDescriptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.dongying.mysql.model.BeverageGoods;
import com.dongying.vo.GenericPageable;
import com.dongying.vo.GoodsDataCondition;

@Repository
public class GoodsCriteriaQueryDao {

	@PersistenceContext(name = "mySqlEntityManager")
	private EntityManager entityManager;

	public Page<BeverageGoods> findGoodsByFilter(GoodsDataCondition condition, GenericPageable genericPageable) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<BeverageGoods> cq = cb.createQuery(BeverageGoods.class);
		Root<BeverageGoods> beverageGoods = cq.from(BeverageGoods.class);

		List<Predicate> predicates = new ArrayList<>();
		Integer goodId = condition.getGoodsID();
		Integer startPrice = condition.getStartPrice();
		Integer endPrice = condition.getEndPrice();
		Integer quantity = condition.getQuantity();
		String goodsName = condition.getGoodsName();
		String priceSort = condition.getPriceSort();

		// 有給goodId 用eq
		if (goodId != null) {
			predicates.add(cb.equal(beverageGoods.get("goodsID"), goodId));
		}
		// 有要搜尋的名稱 用模糊查詢
		if (goodsName != null) {
			predicates.add(cb.like(beverageGoods.get("goodsName"), "%" + goodsName + "%"));
		}
		// 有數量條件 要大於等於
		if (quantity != null) {
			predicates.add(cb.greaterThanOrEqualTo(beverageGoods.get("quantity"), quantity));
		}
		// 有價錢條件
		if (startPrice != null && endPrice != null) {
			predicates.add(cb.between(beverageGoods.get("price"), startPrice, endPrice));
		} else if (startPrice != null) {
			predicates.add(cb.greaterThanOrEqualTo(beverageGoods.get("price"), startPrice));
		} else if (endPrice != null) {
			predicates.add(cb.lessThanOrEqualTo(beverageGoods.get("price"), endPrice));
		}

		// 根據需要設置排序
		if (priceSort != null && priceSort.equals("asc")) {
			cq.orderBy(cb.asc(beverageGoods.get("price")));
		} else if (priceSort != null && priceSort.equals("desc")) {
			cq.orderBy(cb.desc(beverageGoods.get("price")));
		}
		//狀態必填
		predicates.add(cb.equal(beverageGoods.get("status"), condition.getStatus()));


		cq.select(beverageGoods).where(predicates.toArray(new Predicate[0]));
		// 分頁
		int currentPageNo = genericPageable.getCurrentPageNo();
		int pageDataSize = genericPageable.getPageDataSize();

		TypedQuery<BeverageGoods> query = entityManager.createQuery(cq);
		query.setFirstResult((currentPageNo - 1) * pageDataSize);
		query.setMaxResults(pageDataSize);
		List<BeverageGoods> goodsList = query.getResultList();

		// 查询符合条件的记录总数(bug)
		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		countQuery.select(cb.count(countQuery.from(BeverageGoods.class))).where(predicates.toArray(new Predicate[0]));
		Long total = entityManager.createQuery(countQuery).getSingleResult();
		
		return PageableExecutionUtils.getPage(goodsList, PageRequest.of(currentPageNo - 1, pageDataSize), ()-> total);
	}
	
	

}
