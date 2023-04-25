package com.insidetrackdata.test.domain.dto.filter;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@Schema(description = "Available criterias to filter data about Customers Leaf")
public class FilterCustomerLeafDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "ID of the Customer Leaf")
	private Long idCustomerLeaf;
	
	@Schema(description = "ID of the Customer Root")
	private Long idCustomerRoot;
	
	@Schema(description = "ID of the City")
	private Long idCity;
	
	@Schema(description = "Name of the Customer Leaf")
	private String nameCustomerLeaf;

}
