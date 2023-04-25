package com.insidetrackdata.test.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.insidetrackdata.test.domain.dto.GenericBeanDTO;
import com.insidetrackdata.test.domain.dto.PagerDTO;
import com.insidetrackdata.test.domain.dto.UnitTypeDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterUnitTypeDTO;
import com.insidetrackdata.test.domain.entity.UnitType;
import com.insidetrackdata.test.domain.entity.UnitType_;
import com.insidetrackdata.test.repository.UnitTypeRepository;
import com.insidetrackdata.test.service.UnitTypeService;
import com.insidetrackdata.test.util.MessagesUtil;
import com.insidetrackdata.test.util.PaginatorUtil;
import com.insidetrackdata.test.util.PropertiesUtil;

@Service
@Transactional(readOnly = true)
public class UnitTypeServiceImpl implements UnitTypeService {

	@Autowired
	private UnitTypeRepository unitTypeRepository;
	@Autowired
    private PropertiesUtil propertiesUtil;

	@Override
	public Page<UnitTypeDTO> getPageDataUnitTypes(FilterUnitTypeDTO filterUnitTypeDTO, PagerDTO pagerDTO) throws Exception {
		Specification<UnitType> specifications = UnitType.createSpecifications(filterUnitTypeDTO);
		
		Page<UnitTypeDTO> pageUnitTypeDTO = unitTypeRepository.findAll(specifications, this.getPager(pagerDTO))
				.map(e -> e.getUnitTypeDTO());
		
		return pageUnitTypeDTO;
	}
	
	private Pageable getPager(PagerDTO pagerDTO) throws Exception {
		String sortCriteria = null;
		if(pagerDTO.getSortCriteria() != null && !pagerDTO.getSortCriteria().isEmpty()){
			sortCriteria = UnitType_.ListSortFieldEnum.fromKey(pagerDTO.getSortCriteria()).getSortField();
		}
		Pageable paginador = PaginatorUtil.generatePager(pagerDTO.getPage(), pagerDTO.getPageSize(), sortCriteria, pagerDTO.getSortDirection());
		return paginador;
	}
	
	@Override
	public List<UnitTypeDTO> listUnitTypes(FilterUnitTypeDTO filterUnitTypeDTO, Sort sort) throws Exception {
		Specification<UnitType> specifications = UnitType.createSpecifications(filterUnitTypeDTO);
		
		List<UnitTypeDTO> listUnitTypeDTO = null;
		List<UnitType> listUnitType = null;
		if(sort != null){
			listUnitType = unitTypeRepository.findAll(specifications, sort);
		}else{
			listUnitType = unitTypeRepository.findAll(specifications);
		}
		listUnitTypeDTO = listUnitType
				.stream()
				.map(e -> e.getUnitTypeDTO())
				.collect(Collectors.toList());
		
		return listUnitTypeDTO;
	}
	
	@Override
	public List<UnitTypeDTO> listUnitTypes(FilterUnitTypeDTO filterUnitTypeDTO) throws Exception {
		return this.listUnitTypes(filterUnitTypeDTO, null);
	}
	
	@Override
	public List<GenericBeanDTO> listUnitTypesBean(FilterUnitTypeDTO filterUnitTypeDTO) throws Exception {
		Specification<UnitType> specifications = UnitType.createSpecifications(filterUnitTypeDTO);
		
		List<GenericBeanDTO> beans = unitTypeRepository.findAll(specifications, Sort.by(Sort.Direction.ASC, UnitType_.ListSortFieldEnum.NAME_UNIT_TYPE.getSortField()))
				.stream()
				.map(e -> e.getGenericBeanDTO())
				.collect(Collectors.toList());

		return beans;
	}
	
	@Override
	public UnitTypeDTO getUnitType(Long idUnitType) throws Exception {
		UnitTypeDTO unitTypeDTO = null;
		UnitType unitType = unitTypeRepository.findById(idUnitType).orElse(null);
		if(unitType != null){
			unitTypeDTO = unitType.getUnitTypeDTO();
		}
		return unitTypeDTO;
	}

	@Override
	public byte[] generateExportFileUnitTypes(FilterUnitTypeDTO filterUnitTypeDTO, String sortCriteria, String sortDirection) throws Exception {
		SXSSFWorkbook wb = new SXSSFWorkbook();
		wb.setCompressTempFiles(true);

		Font fontHeader = wb.createFont();
		fontHeader.setFontHeightInPoints((short)10);
		fontHeader.setBold(true);
		
		Font fontBody = wb.createFont();
		fontBody.setFontHeightInPoints((short)10);

		DataFormat format = wb.createDataFormat();
		
		CellStyle headerStyle = wb.createCellStyle();
		headerStyle.setFont(fontHeader);

		CellStyle textCellStyle = wb.createCellStyle();
		textCellStyle.setFont(fontBody);
		
		CellStyle integerCellStyle = wb.createCellStyle();
		integerCellStyle.setFont(fontBody);
		integerCellStyle.setDataFormat(format.getFormat("#########0"));
		integerCellStyle.setAlignment(HorizontalAlignment.RIGHT);

		String sheetName = MessagesUtil.getMessage("common.export.sheet.name");
		Sheet sheet = null;

		String[] columnas = {
				MessagesUtil.getMessage("unitType.idUnitType"),
				MessagesUtil.getMessage("unitType.nameUnitType")
		};
		
		PagerDTO pagerDTO = new PagerDTO(new Integer(0), propertiesUtil.sizeSheetExportFile, sortCriteria, sortDirection);
		Page<UnitTypeDTO> pageUnitTypeDTO = this.getPageDataUnitTypes(filterUnitTypeDTO, pagerDTO);

		long totalCount = pageUnitTypeDTO.getTotalElements();
		long numberOfPages = (totalCount / propertiesUtil.sizeSheetExportFile.intValue()) + (totalCount % propertiesUtil.sizeSheetExportFile.intValue() > 0 ? 1 : 0);
		long currentPage = 1;
		List<UnitTypeDTO> listUnitTypeDTO = null;
		Row row = null;
		Cell cell = null;
		int currentRow;
		int startCol;
		int index;
		
		while(currentPage <= numberOfPages){
			if(pageUnitTypeDTO == null){
				pagerDTO = new PagerDTO(new Integer(new Long(currentPage - 1).intValue()), propertiesUtil.sizeSheetExportFile, sortCriteria, sortDirection);
				pageUnitTypeDTO = this.getPageDataUnitTypes(filterUnitTypeDTO, pagerDTO);
			}
			listUnitTypeDTO = pageUnitTypeDTO.getContent();
			pageUnitTypeDTO = null;
			
			sheet = wb.createSheet(sheetName + " " + currentPage);
			
			currentRow = 0;
			startCol = 0;
			row = sheet.createRow(currentRow);
			cell = null;
			for(int i=0; i<columnas.length; i++){
				cell = row.createCell(startCol + i);
				cell.setCellValue(columnas[i]);
				cell.setCellStyle(headerStyle);
			}
            
			for(UnitTypeDTO unitTypeDTO : listUnitTypeDTO){
				currentRow++;
				row = sheet.createRow(currentRow);
				index = 0;
                
				cell = row.createCell(startCol + index);
				cell.setCellValue(unitTypeDTO.getIdUnitType());
				cell.setCellStyle(integerCellStyle);
				index++;
				
				cell = row.createCell(startCol + index);
				cell.setCellValue(unitTypeDTO.getNameUnitType());
				cell.setCellStyle(textCellStyle);
				index++;
			}
			
			currentPage++;
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try{
			wb.write(out);
		}finally{
			out.close();
			wb.close();
		}
        
		return out.toByteArray();
	}

}
