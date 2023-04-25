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
@Schema(description = "Available criterias to filter data about Distributors Leaf")
public class FilterDistributorLeafDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "ID of the Distributor Leaf")
	private Long idDistributorLeaf;
	
	@Schema(description = "ID of the Distributor Root")
	private Long idDistributorRoot;
	
	@Schema(description = "ID of the City")
	private Long idCity;
	
	@Schema(description = "Name of the Distributor Leaf")
	private String nameDistributorLeaf;

}
