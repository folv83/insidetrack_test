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
@Schema(description = "Available criterias to filter data about Unit Types")
public class FilterUnitTypeDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "ID of the Unit Type")
	private Long idUnitType;
	
	@Schema(description = "Name of the Unit Type")
	private String nameUnitType;

}
