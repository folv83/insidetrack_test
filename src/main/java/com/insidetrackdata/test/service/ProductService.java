package com.insidetrackdata.test.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.insidetrackdata.test.domain.dto.GenericBeanDTO;
import com.insidetrackdata.test.domain.dto.PagerDTO;
import com.insidetrackdata.test.domain.dto.ProductDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterProductDTO;

public interface ProductService {

	public Page<ProductDTO> getPageDataProducts(FilterProductDTO filterProductDTO, PagerDTO pagerDTO) throws Exception;
	
	public List<ProductDTO> listProducts(FilterProductDTO filterProductDTO, Sort sort) throws Exception;
	
	public List<ProductDTO> listProducts(FilterProductDTO filterProductDTO) throws Exception;
	
	public List<GenericBeanDTO> listProductsBean(FilterProductDTO filterProductDTO) throws Exception;
	
	public List<GenericBeanDTO> listProductsBean(Long idManufacturer, Long idCategory) throws Exception;
	
	public ProductDTO getProduct(Long idProduct) throws Exception;
	
	public byte[] generateExportFileProducts(FilterProductDTO filterProductDTO, String sortCriteria, String sortDirection) throws Exception;

}
