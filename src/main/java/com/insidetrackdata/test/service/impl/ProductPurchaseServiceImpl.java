package com.insidetrackdata.test.service.impl;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

import com.insidetrackdata.test.domain.dto.ItemAggregatedDataDTO;
import com.insidetrackdata.test.domain.dto.PagerDTO;
import com.insidetrackdata.test.domain.dto.ProductPurchaseDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterAggregatedDataProductPurchaseDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterProductPurchaseDTO;
import com.insidetrackdata.test.domain.entity.ProductPurchase;
import com.insidetrackdata.test.domain.entity.ProductPurchase_;
import com.insidetrackdata.test.repository.ProductPurchaseRepository;
import com.insidetrackdata.test.service.ProductPurchaseService;
import com.insidetrackdata.test.util.Constants;
import com.insidetrackdata.test.util.MessagesUtil;
import com.insidetrackdata.test.util.PaginatorUtil;
import com.insidetrackdata.test.util.PropertiesUtil;

@Service
@Transactional(readOnly = true)
public class ProductPurchaseServiceImpl implements ProductPurchaseService {

	@Autowired
	private ProductPurchaseRepository productPurchaseRepository;
	@Autowired
    private PropertiesUtil propertiesUtil;

	@Override
	public Page<ProductPurchaseDTO> getPageDataProductsPurchase(FilterProductPurchaseDTO filterProductPurchaseDTO, PagerDTO pagerDTO) throws Exception {
		Specification<ProductPurchase> specifications = ProductPurchase.createSpecifications(filterProductPurchaseDTO);
		
		Page<ProductPurchaseDTO> pageProductPurchaseDTO = productPurchaseRepository.findAll(specifications, this.getPager(pagerDTO))
				.map(e -> e.getProductPurchaseDTO());
		
		return pageProductPurchaseDTO;
	}
	
	private Pageable getPager(PagerDTO pagerDTO) throws Exception {
		String sortCriteria = null;
		if(pagerDTO.getSortCriteria() != null && !pagerDTO.getSortCriteria().isEmpty()){
			sortCriteria = ProductPurchase_.ListSortFieldEnum.fromKey(pagerDTO.getSortCriteria()).getSortField();
		}
		Pageable paginador = PaginatorUtil.generatePager(pagerDTO.getPage(), pagerDTO.getPageSize(), sortCriteria, pagerDTO.getSortDirection());
		return paginador;
	}
	
	@Override
	public List<ProductPurchaseDTO> listProductsPurchase(FilterProductPurchaseDTO filterProductPurchaseDTO, Sort sort) throws Exception {
		Specification<ProductPurchase> specifications = ProductPurchase.createSpecifications(filterProductPurchaseDTO);
		
		List<ProductPurchaseDTO> listProductPurchaseDTO = null;
		List<ProductPurchase> listProductPurchase = null;
		if(sort != null){
			listProductPurchase = productPurchaseRepository.findAll(specifications, sort);
		}else{
			listProductPurchase = productPurchaseRepository.findAll(specifications);
		}
		listProductPurchaseDTO = listProductPurchase
				.stream()
				.map(e -> e.getProductPurchaseDTO())
				.collect(Collectors.toList());
		
		return listProductPurchaseDTO;
	}
	
	@Override
	public List<ProductPurchaseDTO> listProductsPurchase(FilterProductPurchaseDTO filterProductPurchaseDTO) throws Exception {
		return this.listProductsPurchase(filterProductPurchaseDTO, null);
	}
	
	@Override
	public ProductPurchaseDTO getProductPurchase(Long idProductPurchase) throws Exception {
		ProductPurchaseDTO productPurchaseDTO = null;
		ProductPurchase productPurchase = productPurchaseRepository.findById(idProductPurchase).orElse(null);
		if(productPurchase != null){
			productPurchaseDTO = productPurchase.getProductPurchaseDTO();
		}
		return productPurchaseDTO;
	}

	@Override
	public byte[] generateExportFileProductsPurchase(FilterProductPurchaseDTO filterProductPurchaseDTO, String sortCriteria, String sortDirection) throws Exception {
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
		
		CellStyle decimalCellStyle = wb.createCellStyle();
		decimalCellStyle.setFont(fontBody);
		decimalCellStyle.setDataFormat(format.getFormat("#,##0.00"));
		decimalCellStyle.setAlignment(HorizontalAlignment.RIGHT);
		
		CellStyle dateCellStyle = wb.createCellStyle();
		dateCellStyle.setFont(fontBody);
		dateCellStyle.setDataFormat(format.getFormat("d/m/yy"));
		dateCellStyle.setAlignment(HorizontalAlignment.CENTER);

		String sheetName = MessagesUtil.getMessage("common.export.sheet.name");
		Sheet sheet = null;

		String[] columnas = {
				MessagesUtil.getMessage("productPurchase.idProductPurchase"),
				MessagesUtil.getMessage("productPurchase.codeInvoice"),
				MessagesUtil.getMessage("productPurchase.datePurchase"),
				MessagesUtil.getMessage("productPurchase.nameCustomerRoot"),
				MessagesUtil.getMessage("productPurchase.nameCustomerLeaf"),
				MessagesUtil.getMessage("productPurchase.codeCityCustomerLeaf"),
				MessagesUtil.getMessage("productPurchase.descriptionProduct"),
				MessagesUtil.getMessage("productPurchase.nameManufacturer"),
				MessagesUtil.getMessage("productPurchase.nameCategory"),
				MessagesUtil.getMessage("productPurchase.nameUnitType"),
				MessagesUtil.getMessage("productPurchase.namePackSize"),
				MessagesUtil.getMessage("productPurchase.nameDistributorRoot"),
				MessagesUtil.getMessage("productPurchase.nameDistributorLeaf"),
				MessagesUtil.getMessage("productPurchase.codeCityDistributorLeaf"),
				MessagesUtil.getMessage("productPurchase.quantityProduct"),
				MessagesUtil.getMessage("productPurchase.priceProduct"),
				MessagesUtil.getMessage("productPurchase.totalProduct"),
		};
		
		PagerDTO pagerDTO = new PagerDTO(new Integer(0), propertiesUtil.sizeSheetExportFile, sortCriteria, sortDirection);
		Page<ProductPurchaseDTO> pageProductPurchaseDTO = this.getPageDataProductsPurchase(filterProductPurchaseDTO, pagerDTO);

		long totalCount = pageProductPurchaseDTO.getTotalElements();
		long numberOfPages = (totalCount / propertiesUtil.sizeSheetExportFile.intValue()) + (totalCount % propertiesUtil.sizeSheetExportFile.intValue() > 0 ? 1 : 0);
		long currentPage = 1;
		List<ProductPurchaseDTO> listProductPurchaseDTO = null;
		Row row = null;
		Cell cell = null;
		int currentRow;
		int startCol;
		int index;
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		
		while(currentPage <= numberOfPages){
			if(pageProductPurchaseDTO == null){
				pagerDTO = new PagerDTO(new Integer(new Long(currentPage - 1).intValue()), propertiesUtil.sizeSheetExportFile, sortCriteria, sortDirection);
				pageProductPurchaseDTO = this.getPageDataProductsPurchase(filterProductPurchaseDTO, pagerDTO);
			}
			listProductPurchaseDTO = pageProductPurchaseDTO.getContent();
			pageProductPurchaseDTO = null;
			
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
            
			for(ProductPurchaseDTO productPurchaseDTO : listProductPurchaseDTO){
				currentRow++;
				row = sheet.createRow(currentRow);
				index = 0;
                
				cell = row.createCell(startCol + index);
				cell.setCellValue(productPurchaseDTO.getIdProductPurchase());
				cell.setCellStyle(integerCellStyle);
				index++;
				
				cell = row.createCell(startCol + index);
				cell.setCellValue(productPurchaseDTO.getCodeInvoice());
				cell.setCellStyle(textCellStyle);
				index++;
				
				cell = row.createCell(startCol + index);
				cell.setCellValue(productPurchaseDTO.getDatePurchase().format(dtf));
				cell.setCellStyle(dateCellStyle);
				index++;
				
				cell = row.createCell(startCol + index);
				cell.setCellValue(productPurchaseDTO.getNameCustomerRoot());
				cell.setCellStyle(textCellStyle);
				index++;
				
				cell = row.createCell(startCol + index);
				cell.setCellValue(productPurchaseDTO.getNameCustomerLeaf());
				cell.setCellStyle(textCellStyle);
				index++;
				
				cell = row.createCell(startCol + index);
				cell.setCellValue(productPurchaseDTO.getCodeCityCustomerLeaf());
				cell.setCellStyle(textCellStyle);
				index++;
				
				cell = row.createCell(startCol + index);
				cell.setCellValue(productPurchaseDTO.getDescriptionProduct());
				cell.setCellStyle(textCellStyle);
				index++;
				
				cell = row.createCell(startCol + index);
				cell.setCellValue(productPurchaseDTO.getNameManufacturer());
				cell.setCellStyle(textCellStyle);
				index++;
				
				cell = row.createCell(startCol + index);
				cell.setCellValue(productPurchaseDTO.getNameCategory());
				cell.setCellStyle(textCellStyle);
				index++;
				
				cell = row.createCell(startCol + index);
				cell.setCellValue(productPurchaseDTO.getNameUnitType());
				cell.setCellStyle(textCellStyle);
				index++;
				
				cell = row.createCell(startCol + index);
				cell.setCellValue(productPurchaseDTO.getNamePackSize());
				cell.setCellStyle(textCellStyle);
				index++;
				
				cell = row.createCell(startCol + index);
				cell.setCellValue(productPurchaseDTO.getNameDistributorRoot());
				cell.setCellStyle(textCellStyle);
				index++;
				
				cell = row.createCell(startCol + index);
				cell.setCellValue(productPurchaseDTO.getNameDistributorLeaf());
				cell.setCellStyle(textCellStyle);
				index++;
				
				cell = row.createCell(startCol + index);
				cell.setCellValue(productPurchaseDTO.getCodeCityDistributorLeaf());
				cell.setCellStyle(textCellStyle);
				index++;
				
				cell = row.createCell(startCol + index);
				cell.setCellValue(productPurchaseDTO.getQuantityProduct().doubleValue());
				cell.setCellStyle(decimalCellStyle);
				index++;
				
				cell = row.createCell(startCol + index);
				cell.setCellValue(productPurchaseDTO.getPriceProduct().doubleValue());
				cell.setCellStyle(decimalCellStyle);
				index++;
				
				cell = row.createCell(startCol + index);
				cell.setCellValue(productPurchaseDTO.getTotalProduct().doubleValue());
				cell.setCellStyle(decimalCellStyle);
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
	
	@Override
	public Page<ItemAggregatedDataDTO> getPageAggregatedDataProductsPurchase(FilterAggregatedDataProductPurchaseDTO filterAggregatedDataProductPurchaseDTO, PagerDTO pagerDTO) throws Exception {
		Page<ItemAggregatedDataDTO> pageItemAggregatedDataDTO = productPurchaseRepository.findAll(filterAggregatedDataProductPurchaseDTO, this.getPager(pagerDTO));
		return pageItemAggregatedDataDTO;
	}
	
	@Override
	public byte[] generateExportFileAggregatedDataProductsPurchase(FilterAggregatedDataProductPurchaseDTO filterAggregatedDataProductPurchaseDTO) throws Exception {
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
		
		CellStyle decimalCellStyle = wb.createCellStyle();
		decimalCellStyle.setFont(fontBody);
		decimalCellStyle.setDataFormat(format.getFormat("#,##0.00"));
		decimalCellStyle.setAlignment(HorizontalAlignment.RIGHT);
		
		CellStyle dateCellStyle = wb.createCellStyle();
		dateCellStyle.setFont(fontBody);
		dateCellStyle.setDataFormat(format.getFormat("d/m/yy"));
		dateCellStyle.setAlignment(HorizontalAlignment.CENTER);

		String sheetName = MessagesUtil.getMessage("common.export.sheet.name");
		Sheet sheet = null;

		List<String> listColumna = new ArrayList<String>();
		if(filterAggregatedDataProductPurchaseDTO.getAggregatedByDatePurchaseFirst() != null && filterAggregatedDataProductPurchaseDTO.getAggregatedByDatePurchaseFirst().booleanValue()){
			String columnDate = null;
			if(filterAggregatedDataProductPurchaseDTO.getUseDatePurchaseToAggregateAs().equals(Constants.USE_DATE_TO_AGGREGATE_AS_YEAR)){
				columnDate = MessagesUtil.getMessage("aggregatedDataProductPurchase.valueYear");
			}else if(filterAggregatedDataProductPurchaseDTO.getUseDatePurchaseToAggregateAs().equals(Constants.USE_DATE_TO_AGGREGATE_AS_MONTH)){
				columnDate = MessagesUtil.getMessage("aggregatedDataProductPurchase.valueMonth");
			}else if(filterAggregatedDataProductPurchaseDTO.getUseDatePurchaseToAggregateAs().equals(Constants.USE_DATE_TO_AGGREGATE_AS_DATE)){
				columnDate = MessagesUtil.getMessage("aggregatedDataProductPurchase.valueDate");
			}
			if(columnDate != null){
				listColumna.add(columnDate);
			}
		}
		if(filterAggregatedDataProductPurchaseDTO.getAggregatedBy() != null && !filterAggregatedDataProductPurchaseDTO.getAggregatedBy().equals(ProductPurchase_.ListAggregationFieldEnum.ALL.getKey())){
			if(filterAggregatedDataProductPurchaseDTO.getAggregatedBy().equals(ProductPurchase_.ListAggregationFieldEnum.INVOICE.getKey())){
				listColumna.add(MessagesUtil.getMessage("aggregatedDataProductPurchase.invoice"));
        	}
        	if(filterAggregatedDataProductPurchaseDTO.getAggregatedBy().equals(ProductPurchase_.ListAggregationFieldEnum.CUSTOMER_ROOT.getKey())){
        		listColumna.add(MessagesUtil.getMessage("aggregatedDataProductPurchase.customerRoot"));
        	}
        	if(filterAggregatedDataProductPurchaseDTO.getAggregatedBy().equals(ProductPurchase_.ListAggregationFieldEnum.CUSTOMER_LEAF.getKey())){
        		listColumna.add(MessagesUtil.getMessage("aggregatedDataProductPurchase.customerLeaf"));
        	}
        	if(filterAggregatedDataProductPurchaseDTO.getAggregatedBy().equals(ProductPurchase_.ListAggregationFieldEnum.CITY_CUSTOMER_LEAF.getKey())){
        		listColumna.add(MessagesUtil.getMessage("aggregatedDataProductPurchase.cityCustomerLeaf"));
        	}
        	if(filterAggregatedDataProductPurchaseDTO.getAggregatedBy().equals(ProductPurchase_.ListAggregationFieldEnum.PRODUCT.getKey())){
        		listColumna.add(MessagesUtil.getMessage("aggregatedDataProductPurchase.product"));
        	}
        	if(filterAggregatedDataProductPurchaseDTO.getAggregatedBy().equals(ProductPurchase_.ListAggregationFieldEnum.MANUFACTURER.getKey())){
        		listColumna.add(MessagesUtil.getMessage("aggregatedDataProductPurchase.manufacturer"));
        	}
        	if(filterAggregatedDataProductPurchaseDTO.getAggregatedBy().equals(ProductPurchase_.ListAggregationFieldEnum.CATEGORY.getKey())){
        		listColumna.add(MessagesUtil.getMessage("aggregatedDataProductPurchase.category"));
        	}
        	if(filterAggregatedDataProductPurchaseDTO.getAggregatedBy().equals(ProductPurchase_.ListAggregationFieldEnum.UNIT_TYPE.getKey())){
        		listColumna.add(MessagesUtil.getMessage("aggregatedDataProductPurchase.unitType"));
        	}
        	if(filterAggregatedDataProductPurchaseDTO.getAggregatedBy().equals(ProductPurchase_.ListAggregationFieldEnum.PACK_SIZE.getKey())){
        		listColumna.add(MessagesUtil.getMessage("aggregatedDataProductPurchase.packSize"));
        	}
        	if(filterAggregatedDataProductPurchaseDTO.getAggregatedBy().equals(ProductPurchase_.ListAggregationFieldEnum.DISTRIBUTOR_ROOT.getKey())){
        		listColumna.add(MessagesUtil.getMessage("aggregatedDataProductPurchase.distributorRoot"));
        	}
        	if(filterAggregatedDataProductPurchaseDTO.getAggregatedBy().equals(ProductPurchase_.ListAggregationFieldEnum.DISTRIBUTOR_LEAF.getKey())){
        		listColumna.add(MessagesUtil.getMessage("aggregatedDataProductPurchase.distributorLeaf"));
        	}
        	if(filterAggregatedDataProductPurchaseDTO.getAggregatedBy().equals(ProductPurchase_.ListAggregationFieldEnum.CITY_DISTRIBUTOR_LEAF.getKey())){
        		listColumna.add(MessagesUtil.getMessage("aggregatedDataProductPurchase.cityDistributorLeaf"));
        	}
		}
		if(filterAggregatedDataProductPurchaseDTO.getGetCount() != null && filterAggregatedDataProductPurchaseDTO.getGetCount().booleanValue()){
			listColumna.add(MessagesUtil.getMessage("aggregatedDataProductPurchase.count"));
		}
		if(filterAggregatedDataProductPurchaseDTO.getGetSumQuantityProduct() != null && filterAggregatedDataProductPurchaseDTO.getGetSumQuantityProduct().booleanValue()){
			listColumna.add(MessagesUtil.getMessage("aggregatedDataProductPurchase.sumQuantityProduct"));
		}
		if(filterAggregatedDataProductPurchaseDTO.getGetAveragePriceProduct() != null && filterAggregatedDataProductPurchaseDTO.getGetAveragePriceProduct().booleanValue()){
			listColumna.add(MessagesUtil.getMessage("aggregatedDataProductPurchase.averagePriceProduct"));
		}
		if(filterAggregatedDataProductPurchaseDTO.getGetMinimumPriceProduct() != null && filterAggregatedDataProductPurchaseDTO.getGetMinimumPriceProduct().booleanValue()){
			listColumna.add(MessagesUtil.getMessage("aggregatedDataProductPurchase.minimumPriceProduct"));
		}
		if(filterAggregatedDataProductPurchaseDTO.getGetMaximumPriceProduct() != null && filterAggregatedDataProductPurchaseDTO.getGetMaximumPriceProduct().booleanValue()){
			listColumna.add(MessagesUtil.getMessage("aggregatedDataProductPurchase.maximumPriceProduct"));
		}
		listColumna.add(MessagesUtil.getMessage("aggregatedDataProductPurchase.sumTotalProduct"));
		String[] columnas = listColumna.toArray(new String[listColumna.size()]);
		
		PagerDTO pagerDTO = new PagerDTO(new Integer(0), propertiesUtil.sizeSheetExportFile, null, null);
		Page<ItemAggregatedDataDTO> pageItemAggregatedDataDTO = this.getPageAggregatedDataProductsPurchase(filterAggregatedDataProductPurchaseDTO, pagerDTO);

		long totalCount = pageItemAggregatedDataDTO.getTotalElements();
		long numberOfPages = (totalCount / propertiesUtil.sizeSheetExportFile.intValue()) + (totalCount % propertiesUtil.sizeSheetExportFile.intValue() > 0 ? 1 : 0);
		long currentPage = 1;
		List<ItemAggregatedDataDTO> listItemAggregatedDataDTO = null;
		Row row = null;
		Cell cell = null;
		int currentRow;
		int startCol;
		int index;
		
		while(currentPage <= numberOfPages){
			if(pageItemAggregatedDataDTO == null){
				pagerDTO = new PagerDTO(new Integer(new Long(currentPage - 1).intValue()), propertiesUtil.sizeSheetExportFile, null, null);
				pageItemAggregatedDataDTO = this.getPageAggregatedDataProductsPurchase(filterAggregatedDataProductPurchaseDTO, pagerDTO);
			}
			listItemAggregatedDataDTO = pageItemAggregatedDataDTO.getContent();
			pageItemAggregatedDataDTO = null;
			
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
            
			for(ItemAggregatedDataDTO itemAggregatedDataDTO : listItemAggregatedDataDTO){
				currentRow++;
				row = sheet.createRow(currentRow);
				index = 0;
				
				cell = row.createCell(startCol + index);
				if(filterAggregatedDataProductPurchaseDTO.getAggregatedByDatePurchaseFirst() != null && filterAggregatedDataProductPurchaseDTO.getAggregatedByDatePurchaseFirst().booleanValue()){
					if(filterAggregatedDataProductPurchaseDTO.getUseDatePurchaseToAggregateAs().equals(Constants.USE_DATE_TO_AGGREGATE_AS_DATE)){
						cell.setCellValue(itemAggregatedDataDTO.getValueDate());
						cell.setCellStyle(dateCellStyle);
					}else if(filterAggregatedDataProductPurchaseDTO.getUseDatePurchaseToAggregateAs().equals(Constants.USE_DATE_TO_AGGREGATE_AS_YEAR)){
						cell.setCellValue(new Integer(itemAggregatedDataDTO.getValueDate()));
						cell.setCellStyle(integerCellStyle);
					}else{
						cell.setCellValue(itemAggregatedDataDTO.getValueDate());
						cell.setCellStyle(textCellStyle);
					}
					index++;
				}
				
				if(filterAggregatedDataProductPurchaseDTO.getAggregatedBy() != null && !filterAggregatedDataProductPurchaseDTO.getAggregatedBy().equals(ProductPurchase_.ListAggregationFieldEnum.ALL.getKey())){
					cell = row.createCell(startCol + index);
					cell.setCellValue(itemAggregatedDataDTO.getValueItem());
					cell.setCellStyle(textCellStyle);
					index++;
				}
				
				if(filterAggregatedDataProductPurchaseDTO.getGetCount() != null && filterAggregatedDataProductPurchaseDTO.getGetCount().booleanValue()){
					cell = row.createCell(startCol + index);
					cell.setCellValue(itemAggregatedDataDTO.getCount());
					cell.setCellStyle(integerCellStyle);
					index++;
				}
				
				if(filterAggregatedDataProductPurchaseDTO.getGetSumQuantityProduct() != null && filterAggregatedDataProductPurchaseDTO.getGetSumQuantityProduct().booleanValue()){
					cell = row.createCell(startCol + index);
					cell.setCellValue(itemAggregatedDataDTO.getSumQuantityProduct().doubleValue());
					cell.setCellStyle(decimalCellStyle);
					index++;
				}
				
				if(filterAggregatedDataProductPurchaseDTO.getGetAveragePriceProduct() != null && filterAggregatedDataProductPurchaseDTO.getGetAveragePriceProduct().booleanValue()){
					cell = row.createCell(startCol + index);
					cell.setCellValue(itemAggregatedDataDTO.getAveragePriceProduct().doubleValue());
					cell.setCellStyle(decimalCellStyle);
					index++;
				}
				
				if(filterAggregatedDataProductPurchaseDTO.getGetMinimumPriceProduct() != null && filterAggregatedDataProductPurchaseDTO.getGetMinimumPriceProduct().booleanValue()){
					cell = row.createCell(startCol + index);
					cell.setCellValue(itemAggregatedDataDTO.getMinimumPriceProduct().doubleValue());
					cell.setCellStyle(decimalCellStyle);
					index++;
				}
				
				if(filterAggregatedDataProductPurchaseDTO.getGetMaximumPriceProduct() != null && filterAggregatedDataProductPurchaseDTO.getGetMaximumPriceProduct().booleanValue()){
					cell = row.createCell(startCol + index);
					cell.setCellValue(itemAggregatedDataDTO.getMaximumPriceProduct().doubleValue());
					cell.setCellStyle(decimalCellStyle);
					index++;
				}
				
				cell = row.createCell(startCol + index);
				cell.setCellValue(itemAggregatedDataDTO.getSumTotalProduct().doubleValue());
				cell.setCellStyle(decimalCellStyle);
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
