package com.insidetrackdata.test.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.insidetrackdata.test.domain.entity.ProductPurchase;

@Repository
public interface ProductPurchaseRepository extends JpaRepository<ProductPurchase, Long>, JpaSpecificationExecutor<ProductPurchase>, AggregatedDataProductPurchaseRepository {

	@EntityGraph(
			type = EntityGraphType.FETCH,
			value = ProductPurchase.COMMON_GRAPH
			)
	public Page<ProductPurchase> findAll(Specification<ProductPurchase> specifications, Pageable pager);
	
	@EntityGraph(
			type = EntityGraphType.FETCH,
			value = ProductPurchase.COMMON_GRAPH
			)
	public List<ProductPurchase> findAll(Specification<ProductPurchase> specifications, Sort sort);

	@EntityGraph(
			type = EntityGraphType.FETCH,
			value = ProductPurchase.COMMON_GRAPH
			)
	public List<ProductPurchase> findAll(Specification<ProductPurchase> specifications);

}
