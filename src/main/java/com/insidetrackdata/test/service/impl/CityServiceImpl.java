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

import com.insidetrackdata.test.domain.dto.CityDTO;
import com.insidetrackdata.test.domain.dto.GenericBeanDTO;
import com.insidetrackdata.test.domain.dto.PagerDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterCityDTO;
import com.insidetrackdata.test.domain.entity.City;
import com.insidetrackdata.test.domain.entity.City_;
import com.insidetrackdata.test.repository.CityRepository;
import com.insidetrackdata.test.service.CityService;
import com.insidetrackdata.test.util.MessagesUtil;
import com.insidetrackdata.test.util.PaginatorUtil;
import com.insidetrackdata.test.util.PropertiesUtil;

@Service
@Transactional(readOnly = true)
public class CityServiceImpl implements CityService {

	@Autowired
	private CityRepository cityRepository;
	@Autowired
    private PropertiesUtil propertiesUtil;

	@Override
	public Page<CityDTO> getPageDataCities(FilterCityDTO filterCityDTO, PagerDTO pagerDTO) throws Exception {
		Specification<City> specifications = City.createSpecifications(filterCityDTO);
		
		Page<CityDTO> pageCityDTO = cityRepository.findAll(specifications, this.getPager(pagerDTO))
				.map(e -> e.getCityDTO());
		
		return pageCityDTO;
	}
	
	private Pageable getPager(PagerDTO pagerDTO) throws Exception {
		String sortCriteria = null;
		if(pagerDTO.getSortCriteria() != null && !pagerDTO.getSortCriteria().isEmpty()){
			sortCriteria = City_.ListSortFieldEnum.fromKey(pagerDTO.getSortCriteria()).getSortField();
		}
		Pageable paginador = PaginatorUtil.generatePager(pagerDTO.getPage(), pagerDTO.getPageSize(), sortCriteria, pagerDTO.getSortDirection());
		return paginador;
	}
	
	@Override
	public List<CityDTO> listCities(FilterCityDTO filterCityDTO, Sort sort) throws Exception {
		Specification<City> specifications = City.createSpecifications(filterCityDTO);
		
		List<CityDTO> listCityDTO = null;
		List<City> listCity = null;
		if(sort != null){
			listCity = cityRepository.findAll(specifications, sort);
		}else{
			listCity = cityRepository.findAll(specifications);
		}
		listCityDTO = listCity
				.stream()
				.map(e -> e.getCityDTO())
				.collect(Collectors.toList());
		
		return listCityDTO;
	}
	
	@Override
	public List<CityDTO> listCities(FilterCityDTO filterCityDTO) throws Exception {
		return this.listCities(filterCityDTO, null);
	}
	
	@Override
	public List<GenericBeanDTO> listCitiesBean(FilterCityDTO filterCityDTO) throws Exception {
		Specification<City> specifications = City.createSpecifications(filterCityDTO);
		
		List<GenericBeanDTO> beans = cityRepository.findAll(specifications, Sort.by(Sort.Direction.ASC, City_.ListSortFieldEnum.CODE_CITY.getSortField()))
				.stream()
				.map(e -> e.getGenericBeanDTO())
				.collect(Collectors.toList());

		return beans;
	}
	
	@Override
	public CityDTO getCity(Long idCity) throws Exception {
		CityDTO cityDTO = null;
		City city = cityRepository.findById(idCity).orElse(null);
		if(city != null){
			cityDTO = city.getCityDTO();
		}
		return cityDTO;
	}

	@Override
	public byte[] generateExportFileCities(FilterCityDTO filterCityDTO, String sortCriteria, String sortDirection) throws Exception {
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
				MessagesUtil.getMessage("city.idCity"),
				MessagesUtil.getMessage("city.codeCity")
		};
		
		PagerDTO pagerDTO = new PagerDTO(new Integer(0), propertiesUtil.sizeSheetExportFile, sortCriteria, sortDirection);
		Page<CityDTO> pageCityDTO = this.getPageDataCities(filterCityDTO, pagerDTO);

		long totalCount = pageCityDTO.getTotalElements();
		long numberOfPages = (totalCount / propertiesUtil.sizeSheetExportFile.intValue()) + (totalCount % propertiesUtil.sizeSheetExportFile.intValue() > 0 ? 1 : 0);
		long currentPage = 1;
		List<CityDTO> listCityDTO = null;
		Row row = null;
		Cell cell = null;
		int currentRow;
		int startCol;
		int index;
		
		while(currentPage <= numberOfPages){
			if(pageCityDTO == null){
				pagerDTO = new PagerDTO(new Integer(new Long(currentPage - 1).intValue()), propertiesUtil.sizeSheetExportFile, sortCriteria, sortDirection);
				pageCityDTO = this.getPageDataCities(filterCityDTO, pagerDTO);
			}
			listCityDTO = pageCityDTO.getContent();
			pageCityDTO = null;
			
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
            
			for(CityDTO cityDTO : listCityDTO){
				currentRow++;
				row = sheet.createRow(currentRow);
				index = 0;
                
				cell = row.createCell(startCol + index);
				cell.setCellValue(cityDTO.getIdCity());
				cell.setCellStyle(integerCellStyle);
				index++;
				
				cell = row.createCell(startCol + index);
				cell.setCellValue(cityDTO.getCodeCity());
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
