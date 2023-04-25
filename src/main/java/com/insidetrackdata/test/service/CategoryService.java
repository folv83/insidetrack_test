package com.insidetrackdata.test.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.insidetrackdata.test.domain.dto.CategoryDTO;
import com.insidetrackdata.test.domain.dto.GenericBeanDTO;
import com.insidetrackdata.test.domain.dto.PagerDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterCategoryDTO;

public interface CategoryService {

	public Page<CategoryDTO> getPageDataCategories(FilterCategoryDTO filterCategoryDTO, PagerDTO pagerDTO) throws Exception;
	
	public List<CategoryDTO> listCategories(FilterCategoryDTO filterCategoryDTO, Sort sort) throws Exception;
	
	public List<CategoryDTO> listCategories(FilterCategoryDTO filterCategoryDTO) throws Exception;
	
	public List<GenericBeanDTO> listCategoriesBean(FilterCategoryDTO filterCategoryDTO) throws Exception;
	
	public CategoryDTO getCategory(Long idCategory) throws Exception;
	
	public byte[] generateExportFileCategories(FilterCategoryDTO filterCategoryDTO, String sortCriteria, String sortDirection) throws Exception;

}
