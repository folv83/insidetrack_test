package com.insidetrackdata.test.domain.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class DistributorLeafDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EqualsAndHashCode.Include @ToString.Include private Long idDistributorLeaf;
	private Long idDistributorRoot;
	private String nameDistributorRoot;
	private Long idCity;
	private String codeCity;
	private String nameDistributorLeaf;

}
