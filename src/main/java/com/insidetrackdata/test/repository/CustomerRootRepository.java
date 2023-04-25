package com.insidetrackdata.test.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.insidetrackdata.test.domain.entity.CustomerRoot;

@Repository
public interface CustomerRootRepository extends JpaRepository<CustomerRoot, Long>, JpaSpecificationExecutor<CustomerRoot> {

	public Page<CustomerRoot> findAll(Specification<CustomerRoot> specifications, Pageable pager);
	
	public List<CustomerRoot> findAll(Specification<CustomerRoot> specifications, Sort sort);

	public List<CustomerRoot> findAll(Specification<CustomerRoot> specifications);

}
