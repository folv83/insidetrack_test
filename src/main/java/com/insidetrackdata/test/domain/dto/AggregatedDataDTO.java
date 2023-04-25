package com.insidetrackdata.test.domain.dto;

import java.io.Serializable;

import org.springframework.data.domain.Page;

import lombok.Data;

@Data
public class AggregatedDataDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String aggregatedBy;
	private Boolean aggregatedByDatePurchaseFirst;
	private String useDatePurchaseToAggregateAs;
	private Page<ItemAggregatedDataDTO> pageItems;

}
