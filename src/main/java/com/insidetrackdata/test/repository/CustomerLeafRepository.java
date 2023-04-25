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

import com.insidetrackdata.test.domain.entity.CustomerLeaf;

@Repository
public interface CustomerLeafRepository extends JpaRepository<CustomerLeaf, Long>, JpaSpecificationExecutor<CustomerLeaf> {

	@EntityGraph(
			type = EntityGraphType.FETCH,
			value = CustomerLeaf.COMMON_GRAPH
			)
	public Page<CustomerLeaf> findAll(Specification<CustomerLeaf> specifications, Pageable pager);
	
	@EntityGraph(
			type = EntityGraphType.FETCH,
			value = CustomerLeaf.COMMON_GRAPH
			)
	public List<CustomerLeaf> findAll(Specification<CustomerLeaf> specifications, Sort sort);

	@EntityGraph(
			type = EntityGraphType.FETCH,
			value = CustomerLeaf.COMMON_GRAPH
			)
	public List<CustomerLeaf> findAll(Specification<CustomerLeaf> specifications);

}
