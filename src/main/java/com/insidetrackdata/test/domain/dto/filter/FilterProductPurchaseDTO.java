package com.insidetrackdata.test.domain.dto.filter;

import java.io.Serializable;
import java.time.LocalDate;

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
@Schema(description = "Available criterias to filter data about Products per Purchase")
public class FilterProductPurchaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "ID of the Product per Purchase")
	private Long idProductPurchase;
	
	@Schema(description = "Code of the Invoice")
	private String codeInvoice;
	
	@Schema(description = "Date of the Purchase (From)")
	private LocalDate datePurchaseFrom;
	
	@Schema(description = "Date of the Purchase (To)")
	private LocalDate datePurchaseTo;
	
	@Schema(description = "Year of the Purchase")
	private Integer yearPurchase;
	
	@Schema(description = "Year of the Purchase (From)")
	private Integer yearPurchaseFrom;
	
	@Schema(description = "Year of the Purchase (To)")
	private Integer yearPurchaseTo;
	
	@Schema(description = "Month of the Purchase")
	private Integer monthPurchase;
	
	@Schema(description = "Month of the Purchase (From)")
	private Integer monthPurchaseFrom;
	
	@Schema(description = "Month of the Purchase (To)")
	private Integer monthPurchaseTo;
	
	@Schema(description = "Day of Month of the Purchase")
	private Integer dayOfMonthPurchase;
	
	@Schema(description = "Day of Month of the Purchase (From)")
	private Integer dayOfMonthPurchaseFrom;
	
	@Schema(description = "Day of Month of the Purchase (To)")
	private Integer dayOfMonthPurchaseTo;
	
	@Schema(description = "ID of the Customer Root")
	private Long idCustomerRoot;
	
	@Schema(description = "ID of the Customer Leaf")
	private Long idCustomerLeaf;
	
	@Schema(description = "ID of the City of the Customer Leaf")
	private Long idCityCustomerLeaf;
	
	@Schema(description = "ID of the Product")
	private Long idProduct;
	
	@Schema(description = "ID of the Manufacturer")
	private Long idManufacturer;
	
	@Schema(description = "ID of the Category")
	private Long idCategory;
	
	@Schema(description = "ID of the Unit Type")
	private Long idUnitType;
	
	@Schema(description = "ID of the Pack Size")
	private Long idPackSize;
	
	@Schema(description = "ID of the Distributor Root")
	private Long idDistributorRoot;
	
	@Schema(description = "ID of the Distributor Leaf")
	private Long idDistributorLeaf;
	
	@Schema(description = "ID of the City of the Distributor Leaf")
	private Long idCityDistributorLeaf;

}
