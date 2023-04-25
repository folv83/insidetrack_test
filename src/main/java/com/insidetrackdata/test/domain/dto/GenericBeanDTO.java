package com.insidetrackdata.test.domain.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GenericBeanDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include private String value;
	private String label;
	
	public GenericBeanDTO(String value, String label) {
		this.value = value;
		this.label = label;
	}
	
	public GenericBeanDTO(Long value, String label) {
		this.value = value.toString();
		this.label = label;
	}

}
