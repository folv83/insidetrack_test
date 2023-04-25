package com.insidetrackdata.test.domain.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PagerDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer page;
	private Integer pageSize;
	private String sortCriteria;
	private String sortDirection;

}
