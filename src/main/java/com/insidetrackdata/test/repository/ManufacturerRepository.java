package com.insidetrackdata.test.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.insidetrackdata.test.domain.entity.Manufacturer;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long>, JpaSpecificationExecutor<Manufacturer> {

	public Page<Manufacturer> findAll(Specification<Manufacturer> specifications, Pageable pager);
	
	public List<Manufacturer> findAll(Specification<Manufacturer> specifications, Sort sort);

	public List<Manufacturer> findAll(Specification<Manufacturer> specifications);

}
