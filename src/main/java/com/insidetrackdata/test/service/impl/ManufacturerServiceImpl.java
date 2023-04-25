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
import com.insidetrackdata.test.domain.dto.ManufacturerDTO;
import com.insidetrackdata.test.domain.dto.PagerDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterManufacturerDTO;
import com.insidetrackdata.test.domain.entity.Manufacturer;
import com.insidetrackdata.test.domain.entity.Manufacturer_;
import com.insidetrackdata.test.repository.ManufacturerRepository;
import com.insidetrackdata.test.service.ManufacturerService;
import com.insidetrackdata.test.util.MessagesUtil;
import com.insidetrackdata.test.util.PaginatorUtil;
import com.insidetrackdata.test.util.PropertiesUtil;

@Service
@Transactional(readOnly = true)
public class ManufacturerServiceImpl implements ManufacturerService {

	@Autowired
	private ManufacturerRepository manufacturerRepository;
	@Autowired
    private PropertiesUtil propertiesUtil;

	@Override
	public Page<ManufacturerDTO> getPageDataManufacturers(FilterManufacturerDTO filterManufacturerDTO, PagerDTO pagerDTO) throws Exception {
		Specification<Manufacturer> specifications = Manufacturer.createSpecifications(filterManufacturerDTO);
		
		Page<ManufacturerDTO> pageManufacturerDTO = manufacturerRepository.findAll(specifications, this.getPager(pagerDTO))
				.map(e -> e.getManufacturerDTO());
		
		return pageManufacturerDTO;
	}
	
	private Pageable getPager(PagerDTO pagerDTO) throws Exception {
		String sortCriteria = null;
		if(pagerDTO.getSortCriteria() != null && !pagerDTO.getSortCriteria().isEmpty()){
			sortCriteria = Manufacturer_.ListSortFieldEnum.fromKey(pagerDTO.getSortCriteria()).getSortField();
		}
		Pageable paginador = PaginatorUtil.generatePager(pagerDTO.getPage(), pagerDTO.getPageSize(), sortCriteria, pagerDTO.getSortDirection());
		return paginador;
	}
	
	@Override
	public List<ManufacturerDTO> listManufacturers(FilterManufacturerDTO filterManufacturerDTO, Sort sort) throws Exception {
		Specification<Manufacturer> specifications = Manufacturer.createSpecifications(filterManufacturerDTO);
		
		List<ManufacturerDTO> listManufacturerDTO = null;
		List<Manufacturer> listManufacturer = null;
		if(sort != null){
			listManufacturer = manufacturerRepository.findAll(specifications, sort);
		}else{
			listManufacturer = manufacturerRepository.findAll(specifications);
		}
		listManufacturerDTO = listManufacturer
				.stream()
				.map(e -> e.getManufacturerDTO())
				.collect(Collectors.toList());
		
		return listManufacturerDTO;
	}
	
	@Override
	public List<ManufacturerDTO> listManufacturers(FilterManufacturerDTO filterManufacturerDTO) throws Exception {
		return this.listManufacturers(filterManufacturerDTO, null);
	}
	
	@Override
	public List<GenericBeanDTO> listManufacturersBean(FilterManufacturerDTO filterManufacturerDTO) throws Exception {
		Specification<Manufacturer> specifications = Manufacturer.createSpecifications(filterManufacturerDTO);
		
		List<GenericBeanDTO> beans = manufacturerRepository.findAll(specifications, Sort.by(Sort.Direction.ASC, Manufacturer_.ListSortFieldEnum.NAME_MANUFACTURER.getSortField()))
				.stream()
				.map(e -> e.getGenericBeanDTO())
				.collect(Collectors.toList());

		return beans;
	}
	
	@Override
	public ManufacturerDTO getManufacturer(Long idManufacturer) throws Exception {
		ManufacturerDTO manufacturerDTO = null;
		Manufacturer manufacturer = manufacturerRepository.findById(idManufacturer).orElse(null);
		if(manufacturer != null){
			manufacturerDTO = manufacturer.getManufacturerDTO();
		}
		return manufacturerDTO;
	}

	@Override
	public byte[] generateExportFileManufacturers(FilterManufacturerDTO filterManufacturerDTO, String sortCriteria, String sortDirection) throws Exception {
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
				MessagesUtil.getMessage("manufacturer.idManufacturer"),
				MessagesUtil.getMessage("manufacturer.nameManufacturer")
		};
		
		PagerDTO pagerDTO = new PagerDTO(new Integer(0), propertiesUtil.sizeSheetExportFile, sortCriteria, sortDirection);
		Page<ManufacturerDTO> pageManufacturerDTO = this.getPageDataManufacturers(filterManufacturerDTO, pagerDTO);

		long totalCount = pageManufacturerDTO.getTotalElements();
		long numberOfPages = (totalCount / propertiesUtil.sizeSheetExportFile.intValue()) + (totalCount % propertiesUtil.sizeSheetExportFile.intValue() > 0 ? 1 : 0);
		long currentPage = 1;
		List<ManufacturerDTO> listManufacturerDTO = null;
		Row row = null;
		Cell cell = null;
		int currentRow;
		int startCol;
		int index;
		
		while(currentPage <= numberOfPages){
			if(pageManufacturerDTO == null){
				pagerDTO = new PagerDTO(new Integer(new Long(currentPage - 1).intValue()), propertiesUtil.sizeSheetExportFile, sortCriteria, sortDirection);
				pageManufacturerDTO = this.getPageDataManufacturers(filterManufacturerDTO, pagerDTO);
			}
			listManufacturerDTO = pageManufacturerDTO.getContent();
			pageManufacturerDTO = null;
			
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
            
			for(ManufacturerDTO manufacturerDTO : listManufacturerDTO){
				currentRow++;
				row = sheet.createRow(currentRow);
				index = 0;
                
				cell = row.createCell(startCol + index);
				cell.setCellValue(manufacturerDTO.getIdManufacturer());
				cell.setCellStyle(integerCellStyle);
				index++;
				
				cell = row.createCell(startCol + index);
				cell.setCellValue(manufacturerDTO.getNameManufacturer());
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
