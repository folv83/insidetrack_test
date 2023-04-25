package com.insidetrackdata.test.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.insidetrackdata.test.domain.dto.CityDTO;
import com.insidetrackdata.test.domain.dto.GenericBeanDTO;
import com.insidetrackdata.test.domain.dto.PagerDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterCityDTO;

public interface CityService {

	public Page<CityDTO> getPageDataCities(FilterCityDTO filterCityDTO, PagerDTO pagerDTO) throws Exception;
	
	public List<CityDTO> listCities(FilterCityDTO filterCityDTO, Sort sort) throws Exception;
	
	public List<CityDTO> listCities(FilterCityDTO filterCityDTO) throws Exception;
	
	public List<GenericBeanDTO> listCitiesBean(FilterCityDTO filterCityDTO) throws Exception;
	
	public CityDTO getCity(Long idCity) throws Exception;
	
	public byte[] generateExportFileCities(FilterCityDTO filterCityDTO, String sortCriteria, String sortDirection) throws Exception;

}
