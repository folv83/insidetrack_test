package com.insidetrackdata.test.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.insidetrackdata.test.domain.dto.GenericBeanDTO;
import com.insidetrackdata.test.domain.dto.PagerDTO;
import com.insidetrackdata.test.domain.dto.UnitTypeDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterUnitTypeDTO;

public interface UnitTypeService {

	public Page<UnitTypeDTO> getPageDataUnitTypes(FilterUnitTypeDTO filterUnitTypeDTO, PagerDTO pagerDTO) throws Exception;
	
	public List<UnitTypeDTO> listUnitTypes(FilterUnitTypeDTO filterUnitTypeDTO, Sort sort) throws Exception;
	
	public List<UnitTypeDTO> listUnitTypes(FilterUnitTypeDTO filterUnitTypeDTO) throws Exception;
	
	public List<GenericBeanDTO> listUnitTypesBean(FilterUnitTypeDTO filterUnitTypeDTO) throws Exception;
	
	public UnitTypeDTO getUnitType(Long idUnitType) throws Exception;
	
	public byte[] generateExportFileUnitTypes(FilterUnitTypeDTO filterUnitTypeDTO, String sortCriteria, String sortDirection) throws Exception;

}
