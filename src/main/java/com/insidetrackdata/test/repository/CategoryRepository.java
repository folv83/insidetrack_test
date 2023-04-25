package com.insidetrackdata.test.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.insidetrackdata.test.domain.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {

	public Page<Category> findAll(Specification<Category> specifications, Pageable pager);
	
	public List<Category> findAll(Specification<Category> specifications, Sort sort);

	public List<Category> findAll(Specification<Category> specifications);

}
