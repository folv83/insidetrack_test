package com.insidetrackdata.test.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.insidetrackdata.test.domain.dto.DistributorLeafDTO;
import com.insidetrackdata.test.domain.dto.GenericBeanDTO;
import com.insidetrackdata.test.domain.dto.PagerDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterDistributorLeafDTO;

public interface DistributorLeafService {

	public Page<DistributorLeafDTO> getPageDataDistributorsLeaf(FilterDistributorLeafDTO filterDistributorLeafDTO, PagerDTO pagerDTO) throws Exception;
	
	public List<DistributorLeafDTO> listDistributorsLeaf(FilterDistributorLeafDTO filterDistributorLeafDTO, Sort sort) throws Exception;
	
	public List<DistributorLeafDTO> listDistributorsLeaf(FilterDistributorLeafDTO filterDistributorLeafDTO) throws Exception;
	
	public List<GenericBeanDTO> listDistributorsLeafBean(FilterDistributorLeafDTO filterDistributorLeafDTO) throws Exception;
	
	public List<GenericBeanDTO> listDistributorsLeafBean(Long idDistributorRoot, Long idCity) throws Exception;
	
	public DistributorLeafDTO getDistributorLeaf(Long idDistributorLeaf) throws Exception;
	
	public byte[] generateExportFileDistributorsLeaf(FilterDistributorLeafDTO filterDistributorLeafDTO, String sortCriteria, String sortDirection) throws Exception;

}
