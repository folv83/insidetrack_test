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

import com.insidetrackdata.test.domain.dto.DistributorLeafDTO;
import com.insidetrackdata.test.domain.dto.GenericBeanDTO;
import com.insidetrackdata.test.domain.dto.PagerDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterDistributorLeafDTO;
import com.insidetrackdata.test.domain.entity.DistributorLeaf;
import com.insidetrackdata.test.domain.entity.DistributorLeaf_;
import com.insidetrackdata.test.repository.DistributorLeafRepository;
import com.insidetrackdata.test.service.DistributorLeafService;
import com.insidetrackdata.test.util.MessagesUtil;
import com.insidetrackdata.test.util.PaginatorUtil;
import com.insidetrackdata.test.util.PropertiesUtil;

@Service
@Transactional(readOnly = true)
public class DistributorLeafServiceImpl implements DistributorLeafService {

	@Autowired
	private DistributorLeafRepository distributorLeafRepository;
	@Autowired
    private PropertiesUtil propertiesUtil;

	@Override
	public Page<DistributorLeafDTO> getPageDataDistributorsLeaf(FilterDistributorLeafDTO filterDistributorLeafDTO, PagerDTO pagerDTO) throws Exception {
		Specification<DistributorLeaf> specifications = DistributorLeaf.createSpecifications(filterDistributorLeafDTO);
		
		Page<DistributorLeafDTO> pageDistributorLeafDTO = distributorLeafRepository.findAll(specifications, this.getPager(pagerDTO))
				.map(e -> e.getDistributorLeafDTO());
		
		return pageDistributorLeafDTO;
	}
	
	private Pageable getPager(PagerDTO pagerDTO) throws Exception {
		String sortCriteria = null;
		if(pagerDTO.getSortCriteria() != null && !pagerDTO.getSortCriteria().isEmpty()){
			sortCriteria = DistributorLeaf_.ListSortFieldEnum.fromKey(pagerDTO.getSortCriteria()).getSortField();
		}
		Pageable paginador = PaginatorUtil.generatePager(pagerDTO.getPage(), pagerDTO.getPageSize(), sortCriteria, pagerDTO.getSortDirection());
		return paginador;
	}
	
	@Override
	public List<DistributorLeafDTO> listDistributorsLeaf(FilterDistributorLeafDTO filterDistributorLeafDTO, Sort sort) throws Exception {
		Specification<DistributorLeaf> specifications = DistributorLeaf.createSpecifications(filterDistributorLeafDTO);
		
		List<DistributorLeafDTO> listDistributorLeafDTO = null;
		List<DistributorLeaf> listDistributorLeaf = null;
		if(sort != null){
			listDistributorLeaf = distributorLeafRepository.findAll(specifications, sort);
		}else{
			listDistributorLeaf = distributorLeafRepository.findAll(specifications);
		}
		listDistributorLeafDTO = listDistributorLeaf
				.stream()
				.map(e -> e.getDistributorLeafDTO())
				.collect(Collectors.toList());
		
		return listDistributorLeafDTO;
	}
	
	@Override
	public List<DistributorLeafDTO> listDistributorsLeaf(FilterDistributorLeafDTO filterDistributorLeafDTO) throws Exception {
		return this.listDistributorsLeaf(filterDistributorLeafDTO, null);
	}
	
	@Override
	public List<GenericBeanDTO> listDistributorsLeafBean(FilterDistributorLeafDTO filterDistributorLeafDTO) throws Exception {
		Specification<DistributorLeaf> specifications = DistributorLeaf.createSpecifications(filterDistributorLeafDTO);
		
		List<GenericBeanDTO> beans = distributorLeafRepository.findAll(specifications, Sort.by(Sort.Direction.ASC, DistributorLeaf_.ListSortFieldEnum.NAME_DISTRIBUTOR_LEAF.getSortField()))
				.stream()
				.map(e -> e.getGenericBeanDTO())
				.collect(Collectors.toList());

		return beans;
	}
	
	@Override
	public List<GenericBeanDTO> listDistributorsLeafBean(Long idDistributorRoot, Long idCity) throws Exception {
		FilterDistributorLeafDTO filterDistributorLeafDTO = FilterDistributorLeafDTO.builder()
				.idDistributorRoot(idDistributorRoot)
				.idCity(idCity)
				.build();
		return this.listDistributorsLeafBean(filterDistributorLeafDTO);
	}
	
	@Override
	public DistributorLeafDTO getDistributorLeaf(Long idDistributorLeaf) throws Exception {
		DistributorLeafDTO distributorLeafDTO = null;
		DistributorLeaf distributorLeaf = distributorLeafRepository.findById(idDistributorLeaf).orElse(null);
		if(distributorLeaf != null){
			distributorLeafDTO = distributorLeaf.getDistributorLeafDTO();
		}
		return distributorLeafDTO;
	}

	@Override
	public byte[] generateExportFileDistributorsLeaf(FilterDistributorLeafDTO filterDistributorLeafDTO, String sortCriteria, String sortDirection) throws Exception {
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
				MessagesUtil.getMessage("distributorLeaf.idDistributorLeaf"),
				MessagesUtil.getMessage("distributorLeaf.nameDistributorRoot"),
				MessagesUtil.getMessage("distributorLeaf.codeCity"),
				MessagesUtil.getMessage("distributorLeaf.nameDistributorLeaf")
		};
		
		PagerDTO pagerDTO = new PagerDTO(new Integer(0), propertiesUtil.sizeSheetExportFile, sortCriteria, sortDirection);
		Page<DistributorLeafDTO> pageDistributorLeafDTO = this.getPageDataDistributorsLeaf(filterDistributorLeafDTO, pagerDTO);

		long totalCount = pageDistributorLeafDTO.getTotalElements();
		long numberOfPages = (totalCount / propertiesUtil.sizeSheetExportFile.intValue()) + (totalCount % propertiesUtil.sizeSheetExportFile.intValue() > 0 ? 1 : 0);
		long currentPage = 1;
		List<DistributorLeafDTO> listDistributorLeafDTO = null;
		Row row = null;
		Cell cell = null;
		int currentRow;
		int startCol;
		int index;
		
		while(currentPage <= numberOfPages){
			if(pageDistributorLeafDTO == null){
				pagerDTO = new PagerDTO(new Integer(new Long(currentPage - 1).intValue()), propertiesUtil.sizeSheetExportFile, sortCriteria, sortDirection);
				pageDistributorLeafDTO = this.getPageDataDistributorsLeaf(filterDistributorLeafDTO, pagerDTO);
			}
			listDistributorLeafDTO = pageDistributorLeafDTO.getContent();
			pageDistributorLeafDTO = null;
			
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
            
			for(DistributorLeafDTO distributorLeafDTO : listDistributorLeafDTO){
				currentRow++;
				row = sheet.createRow(currentRow);
				index = 0;
                
				cell = row.createCell(startCol + index);
				cell.setCellValue(distributorLeafDTO.getIdDistributorLeaf());
				cell.setCellStyle(integerCellStyle);
				index++;
				
				cell = row.createCell(startCol + index);
				cell.setCellValue(distributorLeafDTO.getNameDistributorRoot());
				cell.setCellStyle(textCellStyle);
				index++;
				
				cell = row.createCell(startCol + index);
				cell.setCellValue(distributorLeafDTO.getCodeCity());
				cell.setCellStyle(textCellStyle);
				index++;
				
				cell = row.createCell(startCol + index);
				cell.setCellValue(distributorLeafDTO.getNameDistributorLeaf());
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
