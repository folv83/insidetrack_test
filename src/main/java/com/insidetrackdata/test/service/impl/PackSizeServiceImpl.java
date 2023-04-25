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
import com.insidetrackdata.test.domain.dto.PackSizeDTO;
import com.insidetrackdata.test.domain.dto.PagerDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterPackSizeDTO;
import com.insidetrackdata.test.domain.entity.PackSize;
import com.insidetrackdata.test.domain.entity.PackSize_;
import com.insidetrackdata.test.repository.PackSizeRepository;
import com.insidetrackdata.test.service.PackSizeService;
import com.insidetrackdata.test.util.MessagesUtil;
import com.insidetrackdata.test.util.PaginatorUtil;
import com.insidetrackdata.test.util.PropertiesUtil;

@Service
@Transactional(readOnly = true)
public class PackSizeServiceImpl implements PackSizeService {

	@Autowired
	private PackSizeRepository packSizeRepository;
	@Autowired
    private PropertiesUtil propertiesUtil;

	@Override
	public Page<PackSizeDTO> getPageDataPackSizes(FilterPackSizeDTO filterPackSizeDTO, PagerDTO pagerDTO) throws Exception {
		Specification<PackSize> specifications = PackSize.createSpecifications(filterPackSizeDTO);
		
		Page<PackSizeDTO> pagePackSizeDTO = packSizeRepository.findAll(specifications, this.getPager(pagerDTO))
				.map(e -> e.getPackSizeDTO());
		
		return pagePackSizeDTO;
	}
	
	private Pageable getPager(PagerDTO pagerDTO) throws Exception {
		String sortCriteria = null;
		if(pagerDTO.getSortCriteria() != null && !pagerDTO.getSortCriteria().isEmpty()){
			sortCriteria = PackSize_.ListSortFieldEnum.fromKey(pagerDTO.getSortCriteria()).getSortField();
		}
		Pageable paginador = PaginatorUtil.generatePager(pagerDTO.getPage(), pagerDTO.getPageSize(), sortCriteria, pagerDTO.getSortDirection());
		return paginador;
	}
	
	@Override
	public List<PackSizeDTO> listPackSizes(FilterPackSizeDTO filterPackSizeDTO, Sort sort) throws Exception {
		Specification<PackSize> specifications = PackSize.createSpecifications(filterPackSizeDTO);
		
		List<PackSizeDTO> listPackSizeDTO = null;
		List<PackSize> listPackSize = null;
		if(sort != null){
			listPackSize = packSizeRepository.findAll(specifications, sort);
		}else{
			listPackSize = packSizeRepository.findAll(specifications);
		}
		listPackSizeDTO = listPackSize
				.stream()
				.map(e -> e.getPackSizeDTO())
				.collect(Collectors.toList());
		
		return listPackSizeDTO;
	}
	
	@Override
	public List<PackSizeDTO> listPackSizes(FilterPackSizeDTO filterPackSizeDTO) throws Exception {
		return this.listPackSizes(filterPackSizeDTO, null);
	}
	
	@Override
	public List<GenericBeanDTO> listPackSizesBean(FilterPackSizeDTO filterPackSizeDTO) throws Exception {
		Specification<PackSize> specifications = PackSize.createSpecifications(filterPackSizeDTO);
		
		List<GenericBeanDTO> beans = packSizeRepository.findAll(specifications, Sort.by(Sort.Direction.ASC, PackSize_.ListSortFieldEnum.NAME_PACK_SIZE.getSortField()))
				.stream()
				.map(e -> e.getGenericBeanDTO())
				.collect(Collectors.toList());

		return beans;
	}
	
	@Override
	public PackSizeDTO getPackSize(Long idPackSize) throws Exception {
		PackSizeDTO packSizeDTO = null;
		PackSize packSize = packSizeRepository.findById(idPackSize).orElse(null);
		if(packSize != null){
			packSizeDTO = packSize.getPackSizeDTO();
		}
		return packSizeDTO;
	}

	@Override
	public byte[] generateExportFilePackSizes(FilterPackSizeDTO filterPackSizeDTO, String sortCriteria, String sortDirection) throws Exception {
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
				MessagesUtil.getMessage("packSize.idPackSize"),
				MessagesUtil.getMessage("packSize.namePackSize")
		};
		
		PagerDTO pagerDTO = new PagerDTO(new Integer(0), propertiesUtil.sizeSheetExportFile, sortCriteria, sortDirection);
		Page<PackSizeDTO> pagePackSizeDTO = this.getPageDataPackSizes(filterPackSizeDTO, pagerDTO);

		long totalCount = pagePackSizeDTO.getTotalElements();
		long numberOfPages = (totalCount / propertiesUtil.sizeSheetExportFile.intValue()) + (totalCount % propertiesUtil.sizeSheetExportFile.intValue() > 0 ? 1 : 0);
		long currentPage = 1;
		List<PackSizeDTO> listPackSizeDTO = null;
		Row row = null;
		Cell cell = null;
		int currentRow;
		int startCol;
		int index;
		
		while(currentPage <= numberOfPages){
			if(pagePackSizeDTO == null){
				pagerDTO = new PagerDTO(new Integer(new Long(currentPage - 1).intValue()), propertiesUtil.sizeSheetExportFile, sortCriteria, sortDirection);
				pagePackSizeDTO = this.getPageDataPackSizes(filterPackSizeDTO, pagerDTO);
			}
			listPackSizeDTO = pagePackSizeDTO.getContent();
			pagePackSizeDTO = null;
			
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
            
			for(PackSizeDTO packSizeDTO : listPackSizeDTO){
				currentRow++;
				row = sheet.createRow(currentRow);
				index = 0;
                
				cell = row.createCell(startCol + index);
				cell.setCellValue(packSizeDTO.getIdPackSize());
				cell.setCellStyle(integerCellStyle);
				index++;
				
				cell = row.createCell(startCol + index);
				cell.setCellValue(packSizeDTO.getNamePackSize());
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
