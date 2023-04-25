package com.insidetrackdata.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import com.insidetrackdata.test.common.RestUrlDirectory;
import com.insidetrackdata.test.domain.dto.AggregatedDataDTO;
import com.insidetrackdata.test.domain.dto.ItemAggregatedDataDTO;
import com.insidetrackdata.test.domain.dto.PagerDTO;
import com.insidetrackdata.test.domain.dto.ProductPurchaseDTO;
import com.insidetrackdata.test.domain.dto.ResponseDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterAggregatedDataProductPurchaseDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterProductPurchaseDTO;
import com.insidetrackdata.test.exception.BusinessException;
import com.insidetrackdata.test.service.ProductPurchaseService;
import com.insidetrackdata.test.util.Constants;
import com.insidetrackdata.test.util.MessagesUtil;
import com.insidetrackdata.test.util.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(RestUrlDirectory.RESOURCE_PRODUCT_PURCHASE)
@Tag(name = "11. Web services about Products per Purchase", description = "This API has web services to get data about Products per Purchase")
@Slf4j
public class ProductPurchaseController {

	@Autowired
	private ProductPurchaseService productPurchaseService;
	
	@PostMapping(RestUrlDirectory.ENDPOINT_PRODUCT_PURCHASE_LIST)
	@Operation(summary = "List Products per Purchase", description = "This WS lets you list data about Products per Purchase using filtering and pagination")
    public ResponseEntity<ResponseDTO> listProductsPurchase(
    		@RequestBody FilterProductPurchaseDTO filterProductPurchaseDTO,
    		@Parameter(description = "Required page number (zero-based indexing)") @RequestParam(name = "page", required = true) Integer page,
    		@Parameter(description = "Size of page") @RequestParam(name = "pageSize", required = true) Integer pageSize,
    		@Parameter(description = "Sort criteria "
    				+ "(available values: 'idProductPurchase', 'codeInvoice', 'datePurchase', "
    				+ "'nameCustomerRoot', 'nameCustomerLeaf', 'codeCityCustomerLeaf', "
    				+ "'descriptionProduct', 'nameManufacturer', 'nameCategory', 'nameUnitType', 'namePackSize', "
    				+ "'nameDistributorRoot', 'nameDistributorLeaf', 'codeCityDistributorLeaf', "
    				+ "'quantityProduct', 'priceProduct', 'totalProduct')"
    				) @RequestParam(name = "sortCriteria", required = false) String sortCriteria,
    		@Parameter(description = "Sort direction (values: 'ASC', 'DESC')") @RequestParam(name = "sortDirection", required = false) String sortDirection
            ) {
    	log.debug("Beginning requestMapping " + RestUrlDirectory.RESOURCE_PRODUCT_PURCHASE + RestUrlDirectory.ENDPOINT_PRODUCT_PURCHASE_LIST);
    	ResponseDTO response = new ResponseDTO();
    	try{
	        //listing the object with pagination
        	PagerDTO pagerDTO = new PagerDTO(page, pageSize, sortCriteria, sortDirection);
        	Page<ProductPurchaseDTO> pageProductPurchaseDTO = productPurchaseService.getPageDataProductsPurchase(filterProductPurchaseDTO, pagerDTO);
	        
	        //generating response object
	        response.setResult(Constants.WEB_SERVICE_RESULT_SUCCESS);
	        response.setData(pageProductPurchaseDTO);
    	}catch(BusinessException bx){
    		log.error(bx.getMessage());
    		response.setResult(Constants.WEB_SERVICE_RESULT_ERROR);
    		response.setMessage(bx.getMessage());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    	}catch(Exception ex){
    		log.error(ex.getMessage(), ex);
    		response.setResult(Constants.WEB_SERVICE_RESULT_ERROR);
    		response.setMessage(MessagesUtil.getMessage("common.error.unexpected"));
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    	log.debug("Ending requestMapping " + RestUrlDirectory.RESOURCE_PRODUCT_PURCHASE + RestUrlDirectory.ENDPOINT_PRODUCT_PURCHASE_LIST);
    	return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }
	
	@PostMapping(RestUrlDirectory.ENDPOINT_PRODUCT_PURCHASE_EXPORT_FILE_DOWNLOAD)
	@Operation(summary = "Download export file about Products per Purchase", description = "This WS lets you download an Excel file with data about Products per Purchase using filtering and sorting")
	public ResponseEntity<byte[]> downloadExportFileProductsPurchase(
    		@RequestBody FilterProductPurchaseDTO filterProductPurchaseDTO,
    		@Parameter(description = "Sort criteria ("
    				+ "available values: 'idProductPurchase', 'codeInvoice', 'datePurchase', "
    				+ "'nameCustomerRoot', 'nameCustomerLeaf', 'codeCityCustomerLeaf', "
    				+ "'descriptionProduct', 'nameManufacturer', 'nameCategory', 'nameUnitType', 'namePackSize', "
    				+ "'nameDistributorRoot', 'nameDistributorLeaf', 'codeCityDistributorLeaf', "
    				+ "'quantityProduct', 'priceProduct', 'totalProduct')"
    				) @RequestParam(name = "sortCriteria", required = false) String sortCriteria,
    		@Parameter(description = "Sort direction (values: 'ASC', 'DESC')") @RequestParam(name = "sortDirection", required = false) String sortDirection
            ) {
		log.debug("Beginning requestMapping " + RestUrlDirectory.RESOURCE_PRODUCT_PURCHASE + RestUrlDirectory.ENDPOINT_PRODUCT_PURCHASE_EXPORT_FILE_DOWNLOAD);
		byte[] fileContent = null;
		HttpHeaders headers = new HttpHeaders();
		try{
			fileContent = productPurchaseService.generateExportFileProductsPurchase(filterProductPurchaseDTO, sortCriteria, sortDirection);
			String filename = MessagesUtil.getMessage("productPurchase.exportFile.title") + "." + Constants.FILE_EXTENSION_XLSX;
			headers = ResponseUtil.generateHeadersDownloadFile(filename, fileContent.length);
			log.debug("Ending requestMapping " + RestUrlDirectory.RESOURCE_PRODUCT_PURCHASE + RestUrlDirectory.ENDPOINT_PRODUCT_PURCHASE_EXPORT_FILE_DOWNLOAD);
		}catch(Exception ex){
			log.error(ex.getMessage(), ex);
		}
		return new ResponseEntity<byte[]>(fileContent, headers, HttpStatus.OK);
	}
	
	@GetMapping(RestUrlDirectory.ENDPOINT_PRODUCT_PURCHASE_GET + "/{idProductPurchase}")
	@Operation(summary = "Get Product per Purchase by ID", description = "This WS lets you get data of a Product per Purchase by its ID")
    public ResponseEntity<ResponseDTO> getProductPurchase(
    		@Parameter(description = "ID of the Product per Purchase") @PathVariable Long idProductPurchase
    		) {
    	log.debug("Beginning requestMapping " + RestUrlDirectory.RESOURCE_PRODUCT_PURCHASE + RestUrlDirectory.ENDPOINT_PRODUCT_PURCHASE_GET);
    	ResponseDTO response = new ResponseDTO();
    	try{
	        //getting the object
    		ProductPurchaseDTO productPurchaseDTO = productPurchaseService.getProductPurchase(idProductPurchase);
	        
	        //generating response object
	        response.setResult(Constants.WEB_SERVICE_RESULT_SUCCESS);
	        response.setData(productPurchaseDTO);
    	}catch(BusinessException bx){
    		log.error(bx.getMessage());
    		response.setResult(Constants.WEB_SERVICE_RESULT_ERROR);
    		response.setMessage(bx.getMessage());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    	}catch(Exception ex){
    		log.error(ex.getMessage(), ex);
    		response.setResult(Constants.WEB_SERVICE_RESULT_ERROR);
    		response.setMessage(MessagesUtil.getMessage("common.error.unexpected"));
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    	log.debug("Ending requestMapping " + RestUrlDirectory.RESOURCE_PRODUCT_PURCHASE + RestUrlDirectory.ENDPOINT_PRODUCT_PURCHASE_GET);
    	return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }
	
	@PostMapping(RestUrlDirectory.ENDPOINT_AGGREGATED_DATA_PRODUCT_PURCHASE_LIST)
	@Operation(summary = "List Aggregated Data for Products per Purchase", description = "This WS lets you list aggregated data about Products per Purchase using filtering and pagination")
    public ResponseEntity<ResponseDTO> listAggregatedDataProductsPurchase(
    		@RequestBody FilterAggregatedDataProductPurchaseDTO filterAggregatedDataProductPurchaseDTO,
    		@Parameter(description = "Required page number (zero-based indexing)") @RequestParam(name = "page", required = true) Integer page,
    		@Parameter(description = "Size of page") @RequestParam(name = "pageSize", required = true) Integer pageSize
            ) {
    	log.debug("Beginning requestMapping " + RestUrlDirectory.RESOURCE_PRODUCT_PURCHASE + RestUrlDirectory.ENDPOINT_AGGREGATED_DATA_PRODUCT_PURCHASE_LIST);
    	ResponseDTO response = new ResponseDTO();
    	try{
	        //listing the object with pagination
        	PagerDTO pagerDTO = new PagerDTO(page, pageSize, null, null);
        	Page<ItemAggregatedDataDTO> pageItemAggregatedDataDTO = productPurchaseService.getPageAggregatedDataProductsPurchase(filterAggregatedDataProductPurchaseDTO, pagerDTO);
        	
        	//generating the object with aggregated data
        	AggregatedDataDTO aggregatedDataDTO = new AggregatedDataDTO();
        	aggregatedDataDTO.setAggregatedBy(filterAggregatedDataProductPurchaseDTO.getAggregatedBy());
        	aggregatedDataDTO.setAggregatedByDatePurchaseFirst(filterAggregatedDataProductPurchaseDTO.getAggregatedByDatePurchaseFirst());
        	if(filterAggregatedDataProductPurchaseDTO.getAggregatedByDatePurchaseFirst() != null && filterAggregatedDataProductPurchaseDTO.getAggregatedByDatePurchaseFirst().booleanValue()){
        		aggregatedDataDTO.setUseDatePurchaseToAggregateAs(filterAggregatedDataProductPurchaseDTO.getUseDatePurchaseToAggregateAs());
        	}
        	aggregatedDataDTO.setPageItems(pageItemAggregatedDataDTO);
	        
	        //generating response object
	        response.setResult(Constants.WEB_SERVICE_RESULT_SUCCESS);
	        response.setData(aggregatedDataDTO);
    	}catch(BusinessException bx){
    		log.error(bx.getMessage());
    		response.setResult(Constants.WEB_SERVICE_RESULT_ERROR);
    		response.setMessage(bx.getMessage());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    	}catch(Exception ex){
    		log.error(ex.getMessage(), ex);
    		response.setResult(Constants.WEB_SERVICE_RESULT_ERROR);
    		response.setMessage(MessagesUtil.getMessage("common.error.unexpected"));
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    	log.debug("Ending requestMapping " + RestUrlDirectory.RESOURCE_PRODUCT_PURCHASE + RestUrlDirectory.ENDPOINT_AGGREGATED_DATA_PRODUCT_PURCHASE_LIST);
    	return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }
	
	@PostMapping(RestUrlDirectory.ENDPOINT_AGGREGATED_DATA_PRODUCT_PURCHASE_EXPORT_FILE_DOWNLOAD)
	@Operation(summary = "Download export file about Aggregated Data of Products per Purchase", description = "This WS lets you download an Excel file with data about Aggregated Data of Products per Purchase using filtering and sorting")
	public ResponseEntity<byte[]> downloadExportFileAggregatedDataProductsPurchase(@RequestBody FilterAggregatedDataProductPurchaseDTO filterAggregatedDataProductPurchaseDTO) {
		log.debug("Beginning requestMapping " + RestUrlDirectory.RESOURCE_PRODUCT_PURCHASE + RestUrlDirectory.ENDPOINT_AGGREGATED_DATA_PRODUCT_PURCHASE_EXPORT_FILE_DOWNLOAD);
		byte[] fileContent = null;
		HttpHeaders headers = new HttpHeaders();
		try{
			fileContent = productPurchaseService.generateExportFileAggregatedDataProductsPurchase(filterAggregatedDataProductPurchaseDTO);
			String filename = MessagesUtil.getMessage("aggregatedDataProductPurchase.exportFile.title") + "." + Constants.FILE_EXTENSION_XLSX;
			headers = ResponseUtil.generateHeadersDownloadFile(filename, fileContent.length);
			log.debug("Ending requestMapping " + RestUrlDirectory.RESOURCE_PRODUCT_PURCHASE + RestUrlDirectory.ENDPOINT_AGGREGATED_DATA_PRODUCT_PURCHASE_EXPORT_FILE_DOWNLOAD);
		}catch(Exception ex){
			log.error(ex.getMessage(), ex);
		}
		return new ResponseEntity<byte[]>(fileContent, headers, HttpStatus.OK);
	}

}
