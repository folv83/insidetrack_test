package com.insidetrackdata.test.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.insidetrackdata.test.domain.dto.ItemAggregatedDataDTO;
import com.insidetrackdata.test.domain.dto.PagerDTO;
import com.insidetrackdata.test.domain.dto.ProductPurchaseDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterAggregatedDataProductPurchaseDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterProductPurchaseDTO;

public interface ProductPurchaseService {

	public Page<ProductPurchaseDTO> getPageDataProductsPurchase(FilterProductPurchaseDTO filterProductPurchaseDTO, PagerDTO pagerDTO) throws Exception;
	
	public List<ProductPurchaseDTO> listProductsPurchase(FilterProductPurchaseDTO filterProductPurchaseDTO, Sort sort) throws Exception;
	
	public List<ProductPurchaseDTO> listProductsPurchase(FilterProductPurchaseDTO filterProductPurchaseDTO) throws Exception;
	
	public ProductPurchaseDTO getProductPurchase(Long idProductPurchase) throws Exception;
	
	public byte[] generateExportFileProductsPurchase(FilterProductPurchaseDTO filterProductPurchaseDTO, String sortCriteria, String sortDirection) throws Exception;
	
	public Page<ItemAggregatedDataDTO> getPageAggregatedDataProductsPurchase(FilterAggregatedDataProductPurchaseDTO filterAggregatedDataProductPurchaseDTO, PagerDTO pagerDTO) throws Exception;
	
	public byte[] generateExportFileAggregatedDataProductsPurchase(FilterAggregatedDataProductPurchaseDTO filterAggregatedDataProductPurchaseDTO) throws Exception;

}
