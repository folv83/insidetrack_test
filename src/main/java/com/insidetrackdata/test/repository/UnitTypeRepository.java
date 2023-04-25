package com.insidetrackdata.test.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.insidetrackdata.test.domain.entity.UnitType;

@Repository
public interface UnitTypeRepository extends JpaRepository<UnitType, Long>, JpaSpecificationExecutor<UnitType> {

	public Page<UnitType> findAll(Specification<UnitType> specifications, Pageable pager);
	
	public List<UnitType> findAll(Specification<UnitType> specifications, Sort sort);

	public List<UnitType> findAll(Specification<UnitType> specifications);

}
