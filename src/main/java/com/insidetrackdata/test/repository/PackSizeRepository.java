package com.insidetrackdata.test.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.insidetrackdata.test.domain.entity.PackSize;

@Repository
public interface PackSizeRepository extends JpaRepository<PackSize, Long>, JpaSpecificationExecutor<PackSize> {

	public Page<PackSize> findAll(Specification<PackSize> specifications, Pageable pager);
	
	public List<PackSize> findAll(Specification<PackSize> specifications, Sort sort);

	public List<PackSize> findAll(Specification<PackSize> specifications);

}
