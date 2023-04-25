package com.insidetrackdata.test.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.insidetrackdata.test.domain.dto.CustomerRootDTO;
import com.insidetrackdata.test.domain.dto.GenericBeanDTO;
import com.insidetrackdata.test.domain.dto.PagerDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterCustomerRootDTO;

public interface CustomerRootService {

	public Page<CustomerRootDTO> getPageDataCustomersRoot(FilterCustomerRootDTO filterCustomerRootDTO, PagerDTO pagerDTO) throws Exception;
	
	public List<CustomerRootDTO> listCustomersRoot(FilterCustomerRootDTO filterCustomerRootDTO, Sort sort) throws Exception;
	
	public List<CustomerRootDTO> listCustomersRoot(FilterCustomerRootDTO filterCustomerRootDTO) throws Exception;
	
	public List<GenericBeanDTO> listCustomersRootBean(FilterCustomerRootDTO filterCustomerRootDTO) throws Exception;
	
	public CustomerRootDTO getCustomerRoot(Long idCustomerRoot) throws Exception;
	
	public byte[] generateExportFileCustomersRoot(FilterCustomerRootDTO filterCustomerRootDTO, String sortCriteria, String sortDirection) throws Exception;

}
