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

import com.insidetrackdata.test.domain.dto.CategoryDTO;
import com.insidetrackdata.test.domain.dto.GenericBeanDTO;
import com.insidetrackdata.test.domain.dto.PagerDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterCategoryDTO;
import com.insidetrackdata.test.domain.entity.Category;
import com.insidetrackdata.test.domain.entity.Category_;
import com.insidetrackdata.test.repository.CategoryRepository;
import com.insidetrackdata.test.service.CategoryService;
import com.insidetrackdata.test.util.MessagesUtil;
import com.insidetrackdata.test.util.PaginatorUtil;
import com.insidetrackdata.test.util.PropertiesUtil;

@Service
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
    private PropertiesUtil propertiesUtil;

	@Override
	public Page<CategoryDTO> getPageDataCategories(FilterCategoryDTO filterCategoryDTO, PagerDTO pagerDTO) throws Exception {
		Specification<Category> specifications = Category.createSpecifications(filterCategoryDTO);
		
		Page<CategoryDTO> pageCategoryDTO = categoryRepository.findAll(specifications, this.getPager(pagerDTO))
				.map(e -> e.getCategoryDTO());
		
		return pageCategoryDTO;
	}
	
	private Pageable getPager(PagerDTO pagerDTO) throws Exception {
		String sortCriteria = null;
		if(pagerDTO.getSortCriteria() != null && !pagerDTO.getSortCriteria().isEmpty()){
			sortCriteria = Category_.ListSortFieldEnum.fromKey(pagerDTO.getSortCriteria()).getSortField();
		}
		Pageable paginador = PaginatorUtil.generatePager(pagerDTO.getPage(), pagerDTO.getPageSize(), sortCriteria, pagerDTO.getSortDirection());
		return paginador;
	}
	
	@Override
	public List<CategoryDTO> listCategories(FilterCategoryDTO filterCategoryDTO, Sort sort) throws Exception {
		Specification<Category> specifications = Category.createSpecifications(filterCategoryDTO);
		
		List<CategoryDTO> listCategoryDTO = null;
		List<Category> listCategory = null;
		if(sort != null){
			listCategory = categoryRepository.findAll(specifications, sort);
		}else{
			listCategory = categoryRepository.findAll(specifications);
		}
		listCategoryDTO = listCategory
				.stream()
				.map(e -> e.getCategoryDTO())
				.collect(Collectors.toList());
		
		return listCategoryDTO;
	}
	
	@Override
	public List<CategoryDTO> listCategories(FilterCategoryDTO filterCategoryDTO) throws Exception {
		return this.listCategories(filterCategoryDTO, null);
	}
	
	@Override
	public List<GenericBeanDTO> listCategoriesBean(FilterCategoryDTO filterCategoryDTO) throws Exception {
		Specification<Category> specifications = Category.createSpecifications(filterCategoryDTO);
		
		List<GenericBeanDTO> beans = categoryRepository.findAll(specifications, Sort.by(Sort.Direction.ASC, Category_.ListSortFieldEnum.NAME_CATEGORY.getSortField()))
				.stream()
				.map(e -> e.getGenericBeanDTO())
				.collect(Collectors.toList());

		return beans;
	}
	
	@Override
	public CategoryDTO getCategory(Long idCategory) throws Exception {
		CategoryDTO categoryDTO = null;
		Category category = categoryRepository.findById(idCategory).orElse(null);
		if(category != null){
			categoryDTO = category.getCategoryDTO();
		}
		return categoryDTO;
	}

	@Override
	public byte[] generateExportFileCategories(FilterCategoryDTO filterCategoryDTO, String sortCriteria, String sortDirection) throws Exception {
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
				MessagesUtil.getMessage("category.idCategory"),
				MessagesUtil.getMessage("category.nameCategory")
		};
		
		PagerDTO pagerDTO = new PagerDTO(new Integer(0), propertiesUtil.sizeSheetExportFile, sortCriteria, sortDirection);
		Page<CategoryDTO> pageCategoryDTO = this.getPageDataCategories(filterCategoryDTO, pagerDTO);

		long totalCount = pageCategoryDTO.getTotalElements();
		long numberOfPages = (totalCount / propertiesUtil.sizeSheetExportFile.intValue()) + (totalCount % propertiesUtil.sizeSheetExportFile.intValue() > 0 ? 1 : 0);
		long currentPage = 1;
		List<CategoryDTO> listCategoryDTO = null;
		Row row = null;
		Cell cell = null;
		int currentRow;
		int startCol;
		int index;
		
		while(currentPage <= numberOfPages){
			if(pageCategoryDTO == null){
				pagerDTO = new PagerDTO(new Integer(new Long(currentPage - 1).intValue()), propertiesUtil.sizeSheetExportFile, sortCriteria, sortDirection);
				pageCategoryDTO = this.getPageDataCategories(filterCategoryDTO, pagerDTO);
			}
			listCategoryDTO = pageCategoryDTO.getContent();
			pageCategoryDTO = null;
			
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
            
			for(CategoryDTO categoryDTO : listCategoryDTO){
				currentRow++;
				row = sheet.createRow(currentRow);
				index = 0;
                
				cell = row.createCell(startCol + index);
				cell.setCellValue(categoryDTO.getIdCategory());
				cell.setCellStyle(integerCellStyle);
				index++;
				
				cell = row.createCell(startCol + index);
				cell.setCellValue(categoryDTO.getNameCategory());
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
