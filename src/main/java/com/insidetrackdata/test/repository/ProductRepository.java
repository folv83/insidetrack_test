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

import com.insidetrackdata.test.domain.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

	@EntityGraph(
			type = EntityGraphType.FETCH,
			value = Product.COMMON_GRAPH
			)
	public Page<Product> findAll(Specification<Product> specifications, Pageable pager);
	
	@EntityGraph(
			type = EntityGraphType.FETCH,
			value = Product.COMMON_GRAPH
			)
	public List<Product> findAll(Specification<Product> specifications, Sort sort);

	@EntityGraph(
			type = EntityGraphType.FETCH,
			value = Product.COMMON_GRAPH
			)
	public List<Product> findAll(Specification<Product> specifications);

}
