package com.insidetrackdata.test.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.insidetrackdata.test.domain.dto.CustomerLeafDTO;
import com.insidetrackdata.test.domain.dto.GenericBeanDTO;
import com.insidetrackdata.test.domain.dto.PagerDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterCustomerLeafDTO;

public interface CustomerLeafService {

	public Page<CustomerLeafDTO> getPageDataCustomersLeaf(FilterCustomerLeafDTO filterCustomerLeafDTO, PagerDTO pagerDTO) throws Exception;
	
	public List<CustomerLeafDTO> listCustomersLeaf(FilterCustomerLeafDTO filterCustomerLeafDTO, Sort sort) throws Exception;
	
	public List<CustomerLeafDTO> listCustomersLeaf(FilterCustomerLeafDTO filterCustomerLeafDTO) throws Exception;
	
	public List<GenericBeanDTO> listCustomersLeafBean(FilterCustomerLeafDTO filterCustomerLeafDTO) throws Exception;
	
	public List<GenericBeanDTO> listCustomersLeafBean(Long idCustomerRoot, Long idCity) throws Exception;
	
	public CustomerLeafDTO getCustomerLeaf(Long idCustomerLeaf) throws Exception;
	
	public byte[] generateExportFileCustomersLeaf(FilterCustomerLeafDTO filterCustomerLeafDTO, String sortCriteria, String sortDirection) throws Exception;

}
