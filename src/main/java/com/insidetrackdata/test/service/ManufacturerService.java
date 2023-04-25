package com.insidetrackdata.test.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.insidetrackdata.test.domain.dto.GenericBeanDTO;
import com.insidetrackdata.test.domain.dto.ManufacturerDTO;
import com.insidetrackdata.test.domain.dto.PagerDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterManufacturerDTO;

public interface ManufacturerService {

	public Page<ManufacturerDTO> getPageDataManufacturers(FilterManufacturerDTO filterManufacturerDTO, PagerDTO pagerDTO) throws Exception;
	
	public List<ManufacturerDTO> listManufacturers(FilterManufacturerDTO filterManufacturerDTO, Sort sort) throws Exception;
	
	public List<ManufacturerDTO> listManufacturers(FilterManufacturerDTO filterManufacturerDTO) throws Exception;
	
	public List<GenericBeanDTO> listManufacturersBean(FilterManufacturerDTO filterManufacturerDTO) throws Exception;
	
	public ManufacturerDTO getManufacturer(Long idManufacturer) throws Exception;
	
	public byte[] generateExportFileManufacturers(FilterManufacturerDTO filterManufacturerDTO, String sortCriteria, String sortDirection) throws Exception;

}
