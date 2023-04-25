package com.insidetrackdata.test.domain.dto.filter;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@ToString
@Schema(description = "Available criterias to filter aggregated data about Products per Purchase")
public class FilterAggregatedDataProductPurchaseDTO extends FilterProductPurchaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Indicates the column that determines the aggregation "
			+ "(available values: 'all', 'invoice', "
			+ "'customerRoot', 'customerLeaf', 'cityCustomerLeaf', "
			+ "'product', 'manufacturer', 'category', 'unitType', 'packSize', "
			+ "'distributorRoot', 'distributorLeaf', 'cityDistributorLeaf')")
	private String aggregatedBy;
	
	@Schema(description = "Indicates if data will be aggregated firstly by Date of the Purchase (additionally to what was indicated in parameter 'aggregatedBy')")
	private Boolean aggregatedByDatePurchaseFirst;
	
	@Schema(description = "Indicates how the Date of Purchase will be used to aggregate data (available values: 'year', 'month', 'date')")
	private String useDatePurchaseToAggregateAs;
	
	@Schema(description = "Indicates if the count will be gotten")
	private Boolean getCount;
	
	@Schema(description = "Indicates if the sum of the Quantity of Product will be gotten")
	private Boolean getSumQuantityProduct;
	
	@Schema(description = "Indicates if the average of the Price of Product will be gotten")
	private Boolean getAveragePriceProduct;
	
	@Schema(description = "Indicates if the minimum of the Price of Product will be gotten")
	private Boolean getMinimumPriceProduct;
	
	@Schema(description = "Indicates if the maximum of the Price of Product will be gotten")
	private Boolean getMaximumPriceProduct;

}
