package com.insidetrackdata.test.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.insidetrackdata.test.domain.entity.DistributorRoot;

@Repository
public interface DistributorRootRepository extends JpaRepository<DistributorRoot, Long>, JpaSpecificationExecutor<DistributorRoot> {

	public Page<DistributorRoot> findAll(Specification<DistributorRoot> specifications, Pageable pager);
	
	public List<DistributorRoot> findAll(Specification<DistributorRoot> specifications, Sort sort);

	public List<DistributorRoot> findAll(Specification<DistributorRoot> specifications);

}
