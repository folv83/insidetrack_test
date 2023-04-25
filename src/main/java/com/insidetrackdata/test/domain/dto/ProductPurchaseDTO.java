package com.insidetrackdata.test.domain.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class ProductPurchaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EqualsAndHashCode.Include @ToString.Include private Long idProductPurchase;
	private String codeInvoice;
	private LocalDate datePurchase;
	private Long idCustomerRoot;
	private String nameCustomerRoot;
	private Long idCustomerLeaf;
	private String nameCustomerLeaf;
	private Long idCityCustomerLeaf;
	private String codeCityCustomerLeaf;
	private Long idProduct;
	private String descriptionProduct;
	private Long idManufacturer;
	private String nameManufacturer;
	private Long idCategory;
	private String nameCategory;
	private Long idUnitType;
	private String nameUnitType;
	private Long idPackSize;
	private String namePackSize;
	private Long idDistributorRoot;
	private String nameDistributorRoot;
	private Long idDistributorLeaf;
	private String nameDistributorLeaf;
	private Long idCityDistributorLeaf;
	private String codeCityDistributorLeaf;
	private BigDecimal quantityProduct;
	private BigDecimal priceProduct;
	private BigDecimal totalProduct;

}
