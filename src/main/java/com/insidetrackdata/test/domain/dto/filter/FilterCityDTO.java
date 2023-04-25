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
@Schema(description = "Available criterias to filter data about Cities")
public class FilterCityDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "ID of the City")
	private Long idCity;
	
	@Schema(description = "Code of the City")
	private String codeCity;

}
