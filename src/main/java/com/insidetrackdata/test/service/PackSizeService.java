package com.insidetrackdata.test.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.insidetrackdata.test.domain.dto.GenericBeanDTO;
import com.insidetrackdata.test.domain.dto.PackSizeDTO;
import com.insidetrackdata.test.domain.dto.PagerDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterPackSizeDTO;

public interface PackSizeService {

	public Page<PackSizeDTO> getPageDataPackSizes(FilterPackSizeDTO filterPackSizeDTO, PagerDTO pagerDTO) throws Exception;
	
	public List<PackSizeDTO> listPackSizes(FilterPackSizeDTO filterPackSizeDTO, Sort sort) throws Exception;
	
	public List<PackSizeDTO> listPackSizes(FilterPackSizeDTO filterPackSizeDTO) throws Exception;
	
	public List<GenericBeanDTO> listPackSizesBean(FilterPackSizeDTO filterPackSizeDTO) throws Exception;
	
	public PackSizeDTO getPackSize(Long idPackSize) throws Exception;
	
	public byte[] generateExportFilePackSizes(FilterPackSizeDTO filterPackSizeDTO, String sortCriteria, String sortDirection) throws Exception;

}
