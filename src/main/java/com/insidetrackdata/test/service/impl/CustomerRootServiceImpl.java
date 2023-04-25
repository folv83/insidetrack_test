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

import com.insidetrackdata.test.domain.dto.CustomerRootDTO;
import com.insidetrackdata.test.domain.dto.GenericBeanDTO;
import com.insidetrackdata.test.domain.dto.PagerDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterCustomerRootDTO;
import com.insidetrackdata.test.domain.entity.CustomerRoot;
import com.insidetrackdata.test.domain.entity.CustomerRoot_;
import com.insidetrackdata.test.repository.CustomerRootRepository;
import com.insidetrackdata.test.service.CustomerRootService;
import com.insidetrackdata.test.util.MessagesUtil;
import com.insidetrackdata.test.util.PaginatorUtil;
import com.insidetrackdata.test.util.PropertiesUtil;

@Service
@Transactional(readOnly = true)
public class CustomerRootServiceImpl implements CustomerRootService {

	@Autowired
	private CustomerRootRepository customerRootRepository;
	@Autowired
    private PropertiesUtil propertiesUtil;

	@Override
	public Page<CustomerRootDTO> getPageDataCustomersRoot(FilterCustomerRootDTO filterCustomerRootDTO, PagerDTO pagerDTO) throws Exception {
		Specification<CustomerRoot> specifications = CustomerRoot.createSpecifications(filterCustomerRootDTO);
		
		Page<CustomerRootDTO> pageCustomerRootDTO = customerRootRepository.findAll(specifications, this.getPager(pagerDTO))
				.map(e -> e.getCustomerRootDTO());
		
		return pageCustomerRootDTO;
	}
	
	private Pageable getPager(PagerDTO pagerDTO) throws Exception {
		String sortCriteria = null;
		if(pagerDTO.getSortCriteria() != null && !pagerDTO.getSortCriteria().isEmpty()){
			sortCriteria = CustomerRoot_.ListSortFieldEnum.fromKey(pagerDTO.getSortCriteria()).getSortField();
		}
		Pageable paginador = PaginatorUtil.generatePager(pagerDTO.getPage(), pagerDTO.getPageSize(), sortCriteria, pagerDTO.getSortDirection());
		return paginador;
	}
	
	@Override
	public List<CustomerRootDTO> listCustomersRoot(FilterCustomerRootDTO filterCustomerRootDTO, Sort sort) throws Exception {
		Specification<CustomerRoot> specifications = CustomerRoot.createSpecifications(filterCustomerRootDTO);
		
		List<CustomerRootDTO> listCustomerRootDTO = null;
		List<CustomerRoot> listCustomerRoot = null;
		if(sort != null){
			listCustomerRoot = customerRootRepository.findAll(specifications, sort);
		}else{
			listCustomerRoot = customerRootRepository.findAll(specifications);
		}
		listCustomerRootDTO = listCustomerRoot
				.stream()
				.map(e -> e.getCustomerRootDTO())
				.collect(Collectors.toList());
		
		return listCustomerRootDTO;
	}
	
	@Override
	public List<CustomerRootDTO> listCustomersRoot(FilterCustomerRootDTO filterCustomerRootDTO) throws Exception {
		return this.listCustomersRoot(filterCustomerRootDTO, null);
	}
	
	@Override
	public List<GenericBeanDTO> listCustomersRootBean(FilterCustomerRootDTO filterCustomerRootDTO) throws Exception {
		Specification<CustomerRoot> specifications = CustomerRoot.createSpecifications(filterCustomerRootDTO);
		
		List<GenericBeanDTO> beans = customerRootRepository.findAll(specifications, Sort.by(Sort.Direction.ASC, CustomerRoot_.ListSortFieldEnum.NAME_CUSTOMER_ROOT.getSortField()))
				.stream()
				.map(e -> e.getGenericBeanDTO())
				.collect(Collectors.toList());

		return beans;
	}
	
	@Override
	public CustomerRootDTO getCustomerRoot(Long idCustomerRoot) throws Exception {
		CustomerRootDTO customerRootDTO = null;
		CustomerRoot customerRoot = customerRootRepository.findById(idCustomerRoot).orElse(null);
		if(customerRoot != null){
			customerRootDTO = customerRoot.getCustomerRootDTO();
		}
		return customerRootDTO;
	}

	@Override
	public byte[] generateExportFileCustomersRoot(FilterCustomerRootDTO filterCustomerRootDTO, String sortCriteria, String sortDirection) throws Exception {
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
				MessagesUtil.getMessage("customerRoot.idCustomerRoot"),
				MessagesUtil.getMessage("customerRoot.nameCustomerRoot")
		};
		
		PagerDTO pagerDTO = new PagerDTO(new Integer(0), propertiesUtil.sizeSheetExportFile, sortCriteria, sortDirection);
		Page<CustomerRootDTO> pageCustomerRootDTO = this.getPageDataCustomersRoot(filterCustomerRootDTO, pagerDTO);

		long totalCount = pageCustomerRootDTO.getTotalElements();
		long numberOfPages = (totalCount / propertiesUtil.sizeSheetExportFile.intValue()) + (totalCount % propertiesUtil.sizeSheetExportFile.intValue() > 0 ? 1 : 0);
		long currentPage = 1;
		List<CustomerRootDTO> listCustomerRootDTO = null;
		Row row = null;
		Cell cell = null;
		int currentRow;
		int startCol;
		int index;
		
		while(currentPage <= numberOfPages){
			if(pageCustomerRootDTO == null){
				pagerDTO = new PagerDTO(new Integer(new Long(currentPage - 1).intValue()), propertiesUtil.sizeSheetExportFile, sortCriteria, sortDirection);
				pageCustomerRootDTO = this.getPageDataCustomersRoot(filterCustomerRootDTO, pagerDTO);
			}
			listCustomerRootDTO = pageCustomerRootDTO.getContent();
			pageCustomerRootDTO = null;
			
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
            
			for(CustomerRootDTO customerRootDTO : listCustomerRootDTO){
				currentRow++;
				row = sheet.createRow(currentRow);
				index = 0;
                
				cell = row.createCell(startCol + index);
				cell.setCellValue(customerRootDTO.getIdCustomerRoot());
				cell.setCellStyle(integerCellStyle);
				index++;
				
				cell = row.createCell(startCol + index);
				cell.setCellValue(customerRootDTO.getNameCustomerRoot());
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
