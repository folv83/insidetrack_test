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

import com.insidetrackdata.test.domain.entity.DistributorLeaf;

@Repository
public interface DistributorLeafRepository extends JpaRepository<DistributorLeaf, Long>, JpaSpecificationExecutor<DistributorLeaf> {

	@EntityGraph(
			type = EntityGraphType.FETCH,
			value = DistributorLeaf.COMMON_GRAPH
			)
	public Page<DistributorLeaf> findAll(Specification<DistributorLeaf> specifications, Pageable pager);
	
	@EntityGraph(
			type = EntityGraphType.FETCH,
			value = DistributorLeaf.COMMON_GRAPH
			)
	public List<DistributorLeaf> findAll(Specification<DistributorLeaf> specifications, Sort sort);

	@EntityGraph(
			type = EntityGraphType.FETCH,
			value = DistributorLeaf.COMMON_GRAPH
			)
	public List<DistributorLeaf> findAll(Specification<DistributorLeaf> specifications);

}
