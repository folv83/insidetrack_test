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

import com.insidetrackdata.test.domain.dto.CustomerLeafDTO;
import com.insidetrackdata.test.domain.dto.GenericBeanDTO;
import com.insidetrackdata.test.domain.dto.PagerDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterCustomerLeafDTO;
import com.insidetrackdata.test.domain.entity.CustomerLeaf;
import com.insidetrackdata.test.domain.entity.CustomerLeaf_;
import com.insidetrackdata.test.repository.CustomerLeafRepository;
import com.insidetrackdata.test.service.CustomerLeafService;
import com.insidetrackdata.test.util.MessagesUtil;
import com.insidetrackdata.test.util.PaginatorUtil;
import com.insidetrackdata.test.util.PropertiesUtil;

@Service
@Transactional(readOnly = true)
public class CustomerLeafServiceImpl implements CustomerLeafService {

	@Autowired
	private CustomerLeafRepository customerLeafRepository;
	@Autowired
    private PropertiesUtil propertiesUtil;

	@Override
	public Page<CustomerLeafDTO> getPageDataCustomersLeaf(FilterCustomerLeafDTO filterCustomerLeafDTO, PagerDTO pagerDTO) throws Exception {
		Specification<CustomerLeaf> specifications = CustomerLeaf.createSpecifications(filterCustomerLeafDTO);
		
		Page<CustomerLeafDTO> pageCustomerLeafDTO = customerLeafRepository.findAll(specifications, this.getPager(pagerDTO))
				.map(e -> e.getCustomerLeafDTO());
		
		return pageCustomerLeafDTO;
	}
	
	private Pageable getPager(PagerDTO pagerDTO) throws Exception {
		String sortCriteria = null;
		if(pagerDTO.getSortCriteria() != null && !pagerDTO.getSortCriteria().isEmpty()){
			sortCriteria = CustomerLeaf_.ListSortFieldEnum.fromKey(pagerDTO.getSortCriteria()).getSortField();
		}
		Pageable paginador = PaginatorUtil.generatePager(pagerDTO.getPage(), pagerDTO.getPageSize(), sortCriteria, pagerDTO.getSortDirection());
		return paginador;
	}
	
	@Override
	public List<CustomerLeafDTO> listCustomersLeaf(FilterCustomerLeafDTO filterCustomerLeafDTO, Sort sort) throws Exception {
		Specification<CustomerLeaf> specifications = CustomerLeaf.createSpecifications(filterCustomerLeafDTO);
		
		List<CustomerLeafDTO> listCustomerLeafDTO = null;
		List<CustomerLeaf> listCustomerLeaf = null;
		if(sort != null){
			listCustomerLeaf = customerLeafRepository.findAll(specifications, sort);
		}else{
			listCustomerLeaf = customerLeafRepository.findAll(specifications);
		}
		listCustomerLeafDTO = listCustomerLeaf
				.stream()
				.map(e -> e.getCustomerLeafDTO())
				.collect(Collectors.toList());
		
		return listCustomerLeafDTO;
	}
	
	@Override
	public List<CustomerLeafDTO> listCustomersLeaf(FilterCustomerLeafDTO filterCustomerLeafDTO) throws Exception {
		return this.listCustomersLeaf(filterCustomerLeafDTO, null);
	}
	
	@Override
	public List<GenericBeanDTO> listCustomersLeafBean(FilterCustomerLeafDTO filterCustomerLeafDTO) throws Exception {
		Specification<CustomerLeaf> specifications = CustomerLeaf.createSpecifications(filterCustomerLeafDTO);
		
		List<GenericBeanDTO> beans = customerLeafRepository.findAll(specifications, Sort.by(Sort.Direction.ASC, CustomerLeaf_.ListSortFieldEnum.NAME_CUSTOMER_LEAF.getSortField()))
				.stream()
				.map(e -> e.getGenericBeanDTO())
				.collect(Collectors.toList());

		return beans;
	}
	
	@Override
	public List<GenericBeanDTO> listCustomersLeafBean(Long idCustomerRoot, Long idCity) throws Exception {
		FilterCustomerLeafDTO filterCustomerLeafDTO = FilterCustomerLeafDTO.builder()
				.idCustomerRoot(idCustomerRoot)
				.idCity(idCity)
				.build();
		return this.listCustomersLeafBean(filterCustomerLeafDTO);
	}
	
	@Override
	public CustomerLeafDTO getCustomerLeaf(Long idCustomerLeaf) throws Exception {
		CustomerLeafDTO customerLeafDTO = null;
		CustomerLeaf customerLeaf = customerLeafRepository.findById(idCustomerLeaf).orElse(null);
		if(customerLeaf != null){
			customerLeafDTO = customerLeaf.getCustomerLeafDTO();
		}
		return customerLeafDTO;
	}

	@Override
	public byte[] generateExportFileCustomersLeaf(FilterCustomerLeafDTO filterCustomerLeafDTO, String sortCriteria, String sortDirection) throws Exception {
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
				MessagesUtil.getMessage("customerLeaf.idCustomerLeaf"),
				MessagesUtil.getMessage("customerLeaf.nameCustomerRoot"),
				MessagesUtil.getMessage("customerLeaf.codeCity"),
				MessagesUtil.getMessage("customerLeaf.nameCustomerLeaf")
		};
		
		PagerDTO pagerDTO = new PagerDTO(new Integer(0), propertiesUtil.sizeSheetExportFile, sortCriteria, sortDirection);
		Page<CustomerLeafDTO> pageCustomerLeafDTO = this.getPageDataCustomersLeaf(filterCustomerLeafDTO, pagerDTO);

		long totalCount = pageCustomerLeafDTO.getTotalElements();
		long numberOfPages = (totalCount / propertiesUtil.sizeSheetExportFile.intValue()) + (totalCount % propertiesUtil.sizeSheetExportFile.intValue() > 0 ? 1 : 0);
		long currentPage = 1;
		List<CustomerLeafDTO> listCustomerLeafDTO = null;
		Row row = null;
		Cell cell = null;
		int currentRow;
		int startCol;
		int index;
		
		while(currentPage <= numberOfPages){
			if(pageCustomerLeafDTO == null){
				pagerDTO = new PagerDTO(new Integer(new Long(currentPage - 1).intValue()), propertiesUtil.sizeSheetExportFile, sortCriteria, sortDirection);
				pageCustomerLeafDTO = this.getPageDataCustomersLeaf(filterCustomerLeafDTO, pagerDTO);
			}
			listCustomerLeafDTO = pageCustomerLeafDTO.getContent();
			pageCustomerLeafDTO = null;
			
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
            
			for(CustomerLeafDTO customerLeafDTO : listCustomerLeafDTO){
				currentRow++;
				row = sheet.createRow(currentRow);
				index = 0;
                
				cell = row.createCell(startCol + index);
				cell.setCellValue(customerLeafDTO.getIdCustomerLeaf());
				cell.setCellStyle(integerCellStyle);
				index++;
				
				cell = row.createCell(startCol + index);
				cell.setCellValue(customerLeafDTO.getNameCustomerRoot());
				cell.setCellStyle(textCellStyle);
				index++;
				
				cell = row.createCell(startCol + index);
				cell.setCellValue(customerLeafDTO.getCodeCity());
				cell.setCellStyle(textCellStyle);
				index++;
				
				cell = row.createCell(startCol + index);
				cell.setCellValue(customerLeafDTO.getNameCustomerLeaf());
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
