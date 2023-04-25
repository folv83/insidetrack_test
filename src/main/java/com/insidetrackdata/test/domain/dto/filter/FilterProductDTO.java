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
@Schema(description = "Available criterias to filter data about Products")
public class FilterProductDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "ID of the Product")
	private Long idProduct;
	
	@Schema(description = "Description of the Product")
	private String descriptionProduct;
	
	@Schema(description = "ID of the Manufacturer")
	private Long idManufacturer;
	
	@Schema(description = "ID of the Category")
	private Long idCategory;

}
