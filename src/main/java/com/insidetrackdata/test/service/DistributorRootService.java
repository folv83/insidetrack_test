package com.insidetrackdata.test.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.insidetrackdata.test.domain.dto.DistributorRootDTO;
import com.insidetrackdata.test.domain.dto.GenericBeanDTO;
import com.insidetrackdata.test.domain.dto.PagerDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterDistributorRootDTO;

public interface DistributorRootService {

	public Page<DistributorRootDTO> getPageDataDistributorsRoot(FilterDistributorRootDTO filterDistributorRootDTO, PagerDTO pagerDTO) throws Exception;
	
	public List<DistributorRootDTO> listDistributorsRoot(FilterDistributorRootDTO filterDistributorRootDTO, Sort sort) throws Exception;
	
	public List<DistributorRootDTO> listDistributorsRoot(FilterDistributorRootDTO filterDistributorRootDTO) throws Exception;
	
	public List<GenericBeanDTO> listDistributorsRootBean(FilterDistributorRootDTO filterDistributorRootDTO) throws Exception;
	
	public DistributorRootDTO getDistributorRoot(Long idDistributorRoot) throws Exception;
	
	public byte[] generateExportFileDistributorsRoot(FilterDistributorRootDTO filterDistributorRootDTO, String sortCriteria, String sortDirection) throws Exception;

}
