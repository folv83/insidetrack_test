package com.insidetrackdata.test.domain.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class ItemAggregatedDataDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String valueDate;
	private String valueItem;
	private Long count;
	private BigDecimal sumQuantityProduct;
	private BigDecimal averagePriceProduct;
	private BigDecimal minimumPriceProduct;
	private BigDecimal maximumPriceProduct;
	private BigDecimal sumTotalProduct;
	
	public ItemAggregatedDataDTO(String valueDate, String valueItem, Long count, BigDecimal sumQuantityProduct, BigDecimal averagePriceProduct, BigDecimal minimumPriceProduct, BigDecimal maximumPriceProduct, BigDecimal sumTotalProduct){
		this.valueDate = valueDate;
		this.valueItem = valueItem;
		this.count = count;
		this.sumQuantityProduct = sumQuantityProduct;
		this.averagePriceProduct = averagePriceProduct;
		this.minimumPriceProduct = minimumPriceProduct;
		this.maximumPriceProduct = maximumPriceProduct;
		this.sumTotalProduct = sumTotalProduct;
	}

}
