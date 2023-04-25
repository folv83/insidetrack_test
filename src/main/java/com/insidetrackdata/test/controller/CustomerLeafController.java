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
import com.insidetrackdata.test.domain.dto.CustomerLeafDTO;
import com.insidetrackdata.test.domain.dto.PagerDTO;
import com.insidetrackdata.test.domain.dto.ResponseDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterCustomerLeafDTO;
import com.insidetrackdata.test.exception.BusinessException;
import com.insidetrackdata.test.service.CustomerLeafService;
import com.insidetrackdata.test.util.Constants;
import com.insidetrackdata.test.util.MessagesUtil;
import com.insidetrackdata.test.util.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(RestUrlDirectory.RESOURCE_CUSTOMER_LEAF)
@Tag(name = "03. Web services about Customers Leaf", description = "This API has web services to get data about Customers Leaf")
@Slf4j
public class CustomerLeafController {

	@Autowired
	private CustomerLeafService customerLeafService;
	
	@PostMapping(RestUrlDirectory.ENDPOINT_CUSTOMER_LEAF_LIST)
	@Operation(summary = "List Customers Leaf", description = "This WS lets you list data about Customers Leaf using filtering and pagination")
    public ResponseEntity<ResponseDTO> listCustomersLeaf(
    		@RequestBody FilterCustomerLeafDTO filterCustomerLeafDTO,
    		@Parameter(description = "Required page number (zero-based indexing)") @RequestParam(name = "page", required = true) Integer page,
    		@Parameter(description = "Size of page") @RequestParam(name = "pageSize", required = true) Integer pageSize,
    		@Parameter(description = "Sort criteria (available values: 'idCustomerLeaf', 'nameCustomerRoot', 'codeCity', 'nameCustomerLeaf')") @RequestParam(name = "sortCriteria", required = false) String sortCriteria,
    		@Parameter(description = "Sort direction (values: 'ASC', 'DESC')") @RequestParam(name = "sortDirection", required = false) String sortDirection
            ) {
    	log.debug("Beginning requestMapping " + RestUrlDirectory.RESOURCE_CUSTOMER_LEAF + RestUrlDirectory.ENDPOINT_CUSTOMER_LEAF_LIST);
    	ResponseDTO response = new ResponseDTO();
    	try{
	        //listing the object with pagination
        	PagerDTO pagerDTO = new PagerDTO(page, pageSize, sortCriteria, sortDirection);
        	Page<CustomerLeafDTO> pageCustomerLeafDTO = customerLeafService.getPageDataCustomersLeaf(filterCustomerLeafDTO, pagerDTO);
	        
	        //generating response object
	        response.setResult(Constants.WEB_SERVICE_RESULT_SUCCESS);
	        response.setData(pageCustomerLeafDTO);
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
    	log.debug("Ending requestMapping " + RestUrlDirectory.RESOURCE_CUSTOMER_LEAF + RestUrlDirectory.ENDPOINT_CUSTOMER_LEAF_LIST);
    	return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }
	
	@PostMapping(RestUrlDirectory.ENDPOINT_CUSTOMER_LEAF_EXPORT_FILE_DOWNLOAD)
	@Operation(summary = "Download export file about Customers Leaf", description = "This WS lets you download an Excel file with data about Customers Leaf using filtering and sorting")
	public ResponseEntity<byte[]> downloadExportFileCustomersLeaf(
    		@RequestBody FilterCustomerLeafDTO filterCustomerLeafDTO,
    		@Parameter(description = "Sort criteria (available values: 'idCustomerLeaf', 'nameCustomerRoot', 'codeCity', 'nameCustomerLeaf')") @RequestParam(name = "sortCriteria", required = false) String sortCriteria,
    		@Parameter(description = "Sort direction (values: 'ASC', 'DESC')") @RequestParam(name = "sortDirection", required = false) String sortDirection
            ) {
		log.debug("Beginning requestMapping " + RestUrlDirectory.RESOURCE_CUSTOMER_LEAF + RestUrlDirectory.ENDPOINT_CUSTOMER_LEAF_EXPORT_FILE_DOWNLOAD);
		byte[] fileContent = null;
		HttpHeaders headers = new HttpHeaders();
		try{
			fileContent = customerLeafService.generateExportFileCustomersLeaf(filterCustomerLeafDTO, sortCriteria, sortDirection);
			String filename = MessagesUtil.getMessage("customerLeaf.exportFile.title") + "." + Constants.FILE_EXTENSION_XLSX;
			headers = ResponseUtil.generateHeadersDownloadFile(filename, fileContent.length);
			log.debug("Ending requestMapping " + RestUrlDirectory.RESOURCE_CUSTOMER_LEAF + RestUrlDirectory.ENDPOINT_CUSTOMER_LEAF_EXPORT_FILE_DOWNLOAD);
		}catch(Exception ex){
			log.error(ex.getMessage(), ex);
		}
		return new ResponseEntity<byte[]>(fileContent, headers, HttpStatus.OK);
	}
	
	@GetMapping(RestUrlDirectory.ENDPOINT_CUSTOMER_LEAF_GET + "/{idCustomerLeaf}")
	@Operation(summary = "Get Customer Leaf by ID", description = "This WS lets you get data of a Customer Leaf by its ID")
    public ResponseEntity<ResponseDTO> getCustomerLeaf(
    		@Parameter(description = "ID of the Customer Leaf") @PathVariable Long idCustomerLeaf
    		) {
    	log.debug("Beginning requestMapping " + RestUrlDirectory.RESOURCE_CUSTOMER_LEAF + RestUrlDirectory.ENDPOINT_CUSTOMER_LEAF_GET);
    	ResponseDTO response = new ResponseDTO();
    	try{
	        //getting the object
    		CustomerLeafDTO customerLeafDTO = customerLeafService.getCustomerLeaf(idCustomerLeaf);
	        
	        //generating response object
	        response.setResult(Constants.WEB_SERVICE_RESULT_SUCCESS);
	        response.setData(customerLeafDTO);
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
    	log.debug("Ending requestMapping " + RestUrlDirectory.RESOURCE_CUSTOMER_LEAF + RestUrlDirectory.ENDPOINT_CUSTOMER_LEAF_GET);
    	return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }

}
