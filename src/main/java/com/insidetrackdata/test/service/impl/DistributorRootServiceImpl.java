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

import com.insidetrackdata.test.domain.dto.DistributorRootDTO;
import com.insidetrackdata.test.domain.dto.GenericBeanDTO;
import com.insidetrackdata.test.domain.dto.PagerDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterDistributorRootDTO;
import com.insidetrackdata.test.domain.entity.DistributorRoot;
import com.insidetrackdata.test.domain.entity.DistributorRoot_;
import com.insidetrackdata.test.repository.DistributorRootRepository;
import com.insidetrackdata.test.service.DistributorRootService;
import com.insidetrackdata.test.util.MessagesUtil;
import com.insidetrackdata.test.util.PaginatorUtil;
import com.insidetrackdata.test.util.PropertiesUtil;

@Service
@Transactional(readOnly = true)
public class DistributorRootServiceImpl implements DistributorRootService {

	@Autowired
	private DistributorRootRepository distributorRootRepository;
	@Autowired
    private PropertiesUtil propertiesUtil;

	@Override
	public Page<DistributorRootDTO> getPageDataDistributorsRoot(FilterDistributorRootDTO filterDistributorRootDTO, PagerDTO pagerDTO) throws Exception {
		Specification<DistributorRoot> specifications = DistributorRoot.createSpecifications(filterDistributorRootDTO);
		
		Page<DistributorRootDTO> pageDistributorRootDTO = distributorRootRepository.findAll(specifications, this.getPager(pagerDTO))
				.map(e -> e.getDistributorRootDTO());
		
		return pageDistributorRootDTO;
	}
	
	private Pageable getPager(PagerDTO pagerDTO) throws Exception {
		String sortCriteria = null;
		if(pagerDTO.getSortCriteria() != null && !pagerDTO.getSortCriteria().isEmpty()){
			sortCriteria = DistributorRoot_.ListSortFieldEnum.fromKey(pagerDTO.getSortCriteria()).getSortField();
		}
		Pageable paginador = PaginatorUtil.generatePager(pagerDTO.getPage(), pagerDTO.getPageSize(), sortCriteria, pagerDTO.getSortDirection());
		return paginador;
	}
	
	@Override
	public List<DistributorRootDTO> listDistributorsRoot(FilterDistributorRootDTO filterDistributorRootDTO, Sort sort) throws Exception {
		Specification<DistributorRoot> specifications = DistributorRoot.createSpecifications(filterDistributorRootDTO);
		
		List<DistributorRootDTO> listDistributorRootDTO = null;
		List<DistributorRoot> listDistributorRoot = null;
		if(sort != null){
			listDistributorRoot = distributorRootRepository.findAll(specifications, sort);
		}else{
			listDistributorRoot = distributorRootRepository.findAll(specifications);
		}
		listDistributorRootDTO = listDistributorRoot
				.stream()
				.map(e -> e.getDistributorRootDTO())
				.collect(Collectors.toList());
		
		return listDistributorRootDTO;
	}
	
	@Override
	public List<DistributorRootDTO> listDistributorsRoot(FilterDistributorRootDTO filterDistributorRootDTO) throws Exception {
		return this.listDistributorsRoot(filterDistributorRootDTO, null);
	}
	
	@Override
	public List<GenericBeanDTO> listDistributorsRootBean(FilterDistributorRootDTO filterDistributorRootDTO) throws Exception {
		Specification<DistributorRoot> specifications = DistributorRoot.createSpecifications(filterDistributorRootDTO);
		
		List<GenericBeanDTO> beans = distributorRootRepository.findAll(specifications, Sort.by(Sort.Direction.ASC, DistributorRoot_.ListSortFieldEnum.NAME_DISTRIBUTOR_ROOT.getSortField()))
				.stream()
				.map(e -> e.getGenericBeanDTO())
				.collect(Collectors.toList());

		return beans;
	}
	
	@Override
	public DistributorRootDTO getDistributorRoot(Long idDistributorRoot) throws Exception {
		DistributorRootDTO distributorRootDTO = null;
		DistributorRoot distributorRoot = distributorRootRepository.findById(idDistributorRoot).orElse(null);
		if(distributorRoot != null){
			distributorRootDTO = distributorRoot.getDistributorRootDTO();
		}
		return distributorRootDTO;
	}

	@Override
	public byte[] generateExportFileDistributorsRoot(FilterDistributorRootDTO filterDistributorRootDTO, String sortCriteria, String sortDirection) throws Exception {
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
				MessagesUtil.getMessage("distributorRoot.idDistributorRoot"),
				MessagesUtil.getMessage("distributorRoot.nameDistributorRoot")
		};
		
		PagerDTO pagerDTO = new PagerDTO(new Integer(0), propertiesUtil.sizeSheetExportFile, sortCriteria, sortDirection);
		Page<DistributorRootDTO> pageDistributorRootDTO = this.getPageDataDistributorsRoot(filterDistributorRootDTO, pagerDTO);

		long totalCount = pageDistributorRootDTO.getTotalElements();
		long numberOfPages = (totalCount / propertiesUtil.sizeSheetExportFile.intValue()) + (totalCount % propertiesUtil.sizeSheetExportFile.intValue() > 0 ? 1 : 0);
		long currentPage = 1;
		List<DistributorRootDTO> listDistributorRootDTO = null;
		Row row = null;
		Cell cell = null;
		int currentRow;
		int startCol;
		int index;
		
		while(currentPage <= numberOfPages){
			if(pageDistributorRootDTO == null){
				pagerDTO = new PagerDTO(new Integer(new Long(currentPage - 1).intValue()), propertiesUtil.sizeSheetExportFile, sortCriteria, sortDirection);
				pageDistributorRootDTO = this.getPageDataDistributorsRoot(filterDistributorRootDTO, pagerDTO);
			}
			listDistributorRootDTO = pageDistributorRootDTO.getContent();
			pageDistributorRootDTO = null;
			
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
            
			for(DistributorRootDTO distributorRootDTO : listDistributorRootDTO){
				currentRow++;
				row = sheet.createRow(currentRow);
				index = 0;
                
				cell = row.createCell(startCol + index);
				cell.setCellValue(distributorRootDTO.getIdDistributorRoot());
				cell.setCellStyle(integerCellStyle);
				index++;
				
				cell = row.createCell(startCol + index);
				cell.setCellValue(distributorRootDTO.getNameDistributorRoot());
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
