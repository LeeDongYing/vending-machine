package com.dongying.mysql.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.dongying.mysql.model.BeverageGoods;
import com.dongying.mysql.model.BeverageOrder;
import com.dongying.vo.GenericPageable;
import com.dongying.vo.GoodsDataCondition;
import com.dongying.vo.GoodsSalesReportCondition;

@Repository
public class CriteriaQueryDao {

	@PersistenceContext(name = "mySqlEntityManager")
	private EntityManager entityManager;

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public Page<BeverageGoods> findGoodsByFilter(GoodsDataCondition condition, GenericPageable genericPageable) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<BeverageGoods> cq = cb.createQuery(BeverageGoods.class);
		Root<BeverageGoods> beverageGoods = cq.from(BeverageGoods.class);

		List<Predicate> predicates = new ArrayList<>();
		Integer goodId = condition.getGoodsID();
		Integer startPrice = condition.getStartPrice();
		Integer endPrice = condition.getEndPrice();
		Integer quantity = condition.getQuantity();
		boolean relationOper = condition.isRelationOper();
		String goodsName = condition.getGoodsName();
		String priceSort = condition.getPriceSort();
		String status = condition.getStatus();

		// 有給goodId 用eq
		if (goodId != null) {
			predicates.add(cb.equal(beverageGoods.get("goodsID"), goodId));
		} else {
			// 有要搜尋的名稱 用模糊查詢
			if (goodsName != null) {
				predicates.add(cb.like(beverageGoods.get("goodsName"), "%" + goodsName + "%"));
			}
			// 有數量條件 要大於等於 true >= ,false <=
			if (quantity != null) {
				if (relationOper) {
					predicates.add(cb.greaterThanOrEqualTo(beverageGoods.get("quantity"), quantity));
				} else {
					predicates.add(cb.lessThanOrEqualTo(beverageGoods.get("quantity"), quantity));
				}
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
			if (priceSort != null) {
				if (priceSort.equals("asc")) {
					cq.orderBy(cb.asc(beverageGoods.get("price")));
				} else if (priceSort.equals("desc")) {
					cq.orderBy(cb.desc(beverageGoods.get("price")));
				}
			}
			// 狀態
			if (status != null && !"2".equals(status)) {
				predicates.add(cb.equal(beverageGoods.get("status"), condition.getStatus()));
			}
		}
		// 組合條件查詢
		Predicate[] predicateArr = new Predicate[predicates.size()];
		cq.select(beverageGoods).where(predicates.toArray(predicateArr));
		// 分頁
		int currentPageNo = genericPageable.getCurrentPageNo();
		int pageDataSize = genericPageable.getPageDataSize();

		TypedQuery<BeverageGoods> query = entityManager.createQuery(cq);
		query.setFirstResult((currentPageNo - 1) * pageDataSize);
		query.setMaxResults(pageDataSize);
		List<BeverageGoods> goodsList = query.getResultList();

		// data總數
		Long total = countData(predicates, BeverageGoods.class);

		return PageableExecutionUtils.getPage(goodsList, PageRequest.of(currentPageNo - 1, pageDataSize), () -> total);
	}

	public Page<BeverageOrder> findOrdersByFilter(GoodsSalesReportCondition condition,
			GenericPageable genericPageable) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<BeverageOrder> cq = cb.createQuery(BeverageOrder.class);
		Root<BeverageOrder> beverageOrder = cq.from(BeverageOrder.class);
		List<Predicate> predicates = new ArrayList<>();
		String startDateString = condition.getStartDate();
		String endDateString = condition.getEndDate();

		// 有起日
		if (startDateString != null) {
			startDateString = startDateString.replace("/", "-") + " 00:00:00";
			LocalDateTime startDate = LocalDateTime.parse(startDateString, formatter);
			predicates.add(cb.greaterThanOrEqualTo(beverageOrder.get("orderDate"), startDate));
		}
		// 有迄日
		if (endDateString != null) {
			endDateString = endDateString.replace("/", "-") + " 23:59:59";
			LocalDateTime endDate = LocalDateTime.parse(endDateString, formatter);
			predicates.add(cb.lessThanOrEqualTo(beverageOrder.get("orderDate"), endDate));
		}

		// 排序
		Order order = cb.desc(beverageOrder.get("orderID"));

		// 組合條件查詢
		Predicate[] predicateArr = new Predicate[predicates.size()];
		cq.select(beverageOrder).where(predicates.toArray(predicateArr)).orderBy(order);

		// 分頁
		int currentPageNo = genericPageable.getCurrentPageNo();
		int pageDataSize = genericPageable.getPageDataSize();

		TypedQuery<BeverageOrder> query = entityManager.createQuery(cq);
		query.setFirstResult((currentPageNo - 1) * pageDataSize);
		query.setMaxResults(pageDataSize);
		List<BeverageOrder> orderList = query.getResultList();

		// data總數
		Long total = countData(predicates, BeverageOrder.class);
		return PageableExecutionUtils.getPage(orderList, PageRequest.of(currentPageNo - 1, pageDataSize), () -> total);
	}

	public Long countData(List<Predicate> predicates, Class<?> entityClass) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		countQuery.select(cb.count(countQuery.from(entityClass))).where(predicates.toArray(new Predicate[0]));
		Long total = entityManager.createQuery(countQuery).getSingleResult();
		return total;
	}

}
