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
import com.insidetrackdata.test.domain.dto.ProductDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterProductDTO;
import com.insidetrackdata.test.domain.entity.Product;
import com.insidetrackdata.test.domain.entity.Product_;
import com.insidetrackdata.test.repository.ProductRepository;
import com.insidetrackdata.test.service.ProductService;
import com.insidetrackdata.test.util.MessagesUtil;
import com.insidetrackdata.test.util.PaginatorUtil;
import com.insidetrackdata.test.util.PropertiesUtil;

@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
    private PropertiesUtil propertiesUtil;

	@Override
	public Page<ProductDTO> getPageDataProducts(FilterProductDTO filterProductDTO, PagerDTO pagerDTO) throws Exception {
		Specification<Product> specifications = Product.createSpecifications(filterProductDTO);
		
		Page<ProductDTO> pageProductDTO = productRepository.findAll(specifications, this.getPager(pagerDTO))
				.map(e -> e.getProductDTO());
		
		return pageProductDTO;
	}
	
	private Pageable getPager(PagerDTO pagerDTO) throws Exception {
		String sortCriteria = null;
		if(pagerDTO.getSortCriteria() != null && !pagerDTO.getSortCriteria().isEmpty()){
			sortCriteria = Product_.ListSortFieldEnum.fromKey(pagerDTO.getSortCriteria()).getSortField();
		}
		Pageable paginador = PaginatorUtil.generatePager(pagerDTO.getPage(), pagerDTO.getPageSize(), sortCriteria, pagerDTO.getSortDirection());
		return paginador;
	}
	
	@Override
	public List<ProductDTO> listProducts(FilterProductDTO filterProductDTO, Sort sort) throws Exception {
		Specification<Product> specifications = Product.createSpecifications(filterProductDTO);
		
		List<ProductDTO> listProductDTO = null;
		List<Product> listProduct = null;
		if(sort != null){
			listProduct = productRepository.findAll(specifications, sort);
		}else{
			listProduct = productRepository.findAll(specifications);
		}
		listProductDTO = listProduct
				.stream()
				.map(e -> e.getProductDTO())
				.collect(Collectors.toList());
		
		return listProductDTO;
	}
	
	@Override
	public List<ProductDTO> listProducts(FilterProductDTO filterProductDTO) throws Exception {
		return this.listProducts(filterProductDTO, null);
	}
	
	@Override
	public List<GenericBeanDTO> listProductsBean(FilterProductDTO filterProductDTO) throws Exception {
		Specification<Product> specifications = Product.createSpecifications(filterProductDTO);
		
		List<GenericBeanDTO> beans = productRepository.findAll(specifications, Sort.by(Sort.Direction.ASC, Product_.ListSortFieldEnum.DESCRIPTION_PRODUCT.getSortField()))
				.stream()
				.map(e -> e.getGenericBeanDTO())
				.collect(Collectors.toList());

		return beans;
	}
	
	@Override
	public List<GenericBeanDTO> listProductsBean(Long idManufacturer, Long idCategory) throws Exception {
		FilterProductDTO filterProductDTO = FilterProductDTO.builder()
				.idManufacturer(idManufacturer)
				.idCategory(idCategory)
				.build();
		return this.listProductsBean(filterProductDTO);
	}
	
	@Override
	public ProductDTO getProduct(Long idProduct) throws Exception {
		ProductDTO productDTO = null;
		Product product = productRepository.findById(idProduct).orElse(null);
		if(product != null){
			productDTO = product.getProductDTO();
		}
		return productDTO;
	}

	@Override
	public byte[] generateExportFileProducts(FilterProductDTO filterProductDTO, String sortCriteria, String sortDirection) throws Exception {
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
				MessagesUtil.getMessage("product.idProduct"),
				MessagesUtil.getMessage("product.descriptionProduct"),
				MessagesUtil.getMessage("product.nameManufacturer"),
				MessagesUtil.getMessage("product.nameCategory")
		};
		
		PagerDTO pagerDTO = new PagerDTO(new Integer(0), propertiesUtil.sizeSheetExportFile, sortCriteria, sortDirection);
		Page<ProductDTO> pageProductDTO = this.getPageDataProducts(filterProductDTO, pagerDTO);

		long totalCount = pageProductDTO.getTotalElements();
		long numberOfPages = (totalCount / propertiesUtil.sizeSheetExportFile.intValue()) + (totalCount % propertiesUtil.sizeSheetExportFile.intValue() > 0 ? 1 : 0);
		long currentPage = 1;
		List<ProductDTO> listProductDTO = null;
		Row row = null;
		Cell cell = null;
		int currentRow;
		int startCol;
		int index;
		
		while(currentPage <= numberOfPages){
			if(pageProductDTO == null){
				pagerDTO = new PagerDTO(new Integer(new Long(currentPage - 1).intValue()), propertiesUtil.sizeSheetExportFile, sortCriteria, sortDirection);
				pageProductDTO = this.getPageDataProducts(filterProductDTO, pagerDTO);
			}
			listProductDTO = pageProductDTO.getContent();
			pageProductDTO = null;
			
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
            
			for(ProductDTO productDTO : listProductDTO){
				currentRow++;
				row = sheet.createRow(currentRow);
				index = 0;
                
				cell = row.createCell(startCol + index);
				cell.setCellValue(productDTO.getIdProduct());
				cell.setCellStyle(integerCellStyle);
				index++;
				
				cell = row.createCell(startCol + index);
				cell.setCellValue(productDTO.getDescriptionProduct());
				cell.setCellStyle(textCellStyle);
				index++;
				
				cell = row.createCell(startCol + index);
				cell.setCellValue(productDTO.getNameManufacturer());
				cell.setCellStyle(textCellStyle);
				index++;
				
				cell = row.createCell(startCol + index);
				cell.setCellValue(productDTO.getNameCategory());
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
