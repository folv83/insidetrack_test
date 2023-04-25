package com.insidetrackdata.test.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;
import javax.persistence.criteria.Expression;

import org.springframework.data.jpa.domain.Specification;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.insidetrackdata.test.domain.dto.ProductPurchaseDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterAggregatedDataProductPurchaseDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterProductPurchaseDTO;

@Entity
@Table(name = "ITD_FT_PRODUCT_PURCHASE")
@NamedEntityGraph(
		name = ProductPurchase.COMMON_GRAPH,
		attributeNodes = {
				@NamedAttributeNode(value = "customerRoot"),
				@NamedAttributeNode(value = "customerLeaf"),
				@NamedAttributeNode(value = "cityCustomerLeaf"),
				@NamedAttributeNode(value = "product"),
				@NamedAttributeNode(value = "manufacturer"),
				@NamedAttributeNode(value = "category"),
				@NamedAttributeNode(value = "unitType"),
				@NamedAttributeNode(value = "packSize"),
				@NamedAttributeNode(value = "distributorRoot"),
				@NamedAttributeNode(value = "distributorLeaf"),
				@NamedAttributeNode(value = "cityDistributorLeaf")
				}
		)
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class ProductPurchase implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final String COMMON_GRAPH = "productPurchaseCommonGraph";

	@Id
    @Column(name = "ID_PRODUCT_PURCHASE")
	@EqualsAndHashCode.Include @ToString.Include
	private Long idProductPurchase;
	
	@Column(name = "CO_INVOICE")
	private String codeInvoice;
	
	@Column(name = "DA_PURCHASE")
	private LocalDate datePurchase;
	
	@Column(name = "YE_PURCHASE")
	private Integer yearPurchase;
	
	@Column(name = "MO_PURCHASE")
	private Integer monthPurchase;
	
	@Column(name = "DM_PURCHASE")
	private Integer dayOfMonthPurchase;
	
	@Column(name = "FM_PURCHASE")
	private String fullMonthPurchase;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CUSTOMER_ROOT", insertable = false, updatable = false)
	private CustomerRoot customerRoot;
	
	@Column(name = "ID_CUSTOMER_ROOT")
	private Long idCustomerRoot;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CUSTOMER_LEAF", insertable = false, updatable = false)
	private CustomerLeaf customerLeaf;
	
	@Column(name = "ID_CUSTOMER_LEAF")
	private Long idCustomerLeaf;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CITY_CUSTOMER_LEAF", insertable = false, updatable = false)
	private City cityCustomerLeaf;
	
	@Column(name = "ID_CITY_CUSTOMER_LEAF")
	private Long idCityCustomerLeaf;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PRODUCT", insertable = false, updatable = false)
	private Product product;
	
	@Column(name = "ID_PRODUCT")
	private Long idProduct;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_MANUFACTURER", insertable = false, updatable = false)
	private Manufacturer manufacturer;
	
	@Column(name = "ID_MANUFACTURER")
	private Long idManufacturer;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CATEGORY", insertable = false, updatable = false)
	private Category category;
	
	@Column(name = "ID_CATEGORY")
	private Long idCategory;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_UNIT_TYPE", insertable = false, updatable = false)
	private UnitType unitType;
	
	@Column(name = "ID_UNIT_TYPE")
	private Long idUnitType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PACK_SIZE", insertable = false, updatable = false)
	private PackSize packSize;
	
	@Column(name = "ID_PACK_SIZE")
	private Long idPackSize;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DISTRIBUTOR_ROOT", insertable = false, updatable = false)
	private DistributorRoot distributorRoot;
	
	@Column(name = "ID_DISTRIBUTOR_ROOT")
	private Long idDistributorRoot;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DISTRIBUTOR_LEAF", insertable = false, updatable = false)
	private DistributorLeaf distributorLeaf;
	
	@Column(name = "ID_DISTRIBUTOR_LEAF")
	private Long idDistributorLeaf;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CITY_DISTRIBUTOR_LEAF", insertable = false, updatable = false)
	private City cityDistributorLeaf;
	
	@Column(name = "ID_CITY_DISTRIBUTOR_LEAF")
	private Long idCityDistributorLeaf;
	
	@Column(name = "AM_QUANTITY")
	private BigDecimal quantityProduct;
	
	@Column(name = "AM_PRICE")
	private BigDecimal priceProduct;
	
	@Column(name = "AM_TOTAL")
	private BigDecimal totalProduct;
	
	public ProductPurchase(Long idProductPurchase) {
        this.idProductPurchase = idProductPurchase;
    }
	
	public ProductPurchaseDTO getProductPurchaseDTO() {
		ProductPurchaseDTO productPurchaseDTO = new ProductPurchaseDTO();
		productPurchaseDTO.setIdProductPurchase(idProductPurchase);
		productPurchaseDTO.setCodeInvoice(codeInvoice);
		productPurchaseDTO.setDatePurchase(datePurchase);
		if(customerRoot != null){
			productPurchaseDTO.setIdCustomerRoot(customerRoot.getIdCustomerRoot());
			productPurchaseDTO.setNameCustomerRoot(customerRoot.getNameCustomerRoot());
		}
		if(customerLeaf != null){
			productPurchaseDTO.setIdCustomerLeaf(customerLeaf.getIdCustomerLeaf());
			productPurchaseDTO.setNameCustomerLeaf(customerLeaf.getNameCustomerLeaf());
		}
		if(cityCustomerLeaf != null){
			productPurchaseDTO.setIdCityCustomerLeaf(cityCustomerLeaf.getIdCity());
			productPurchaseDTO.setCodeCityCustomerLeaf(cityCustomerLeaf.getCodeCity());
		}
		if(product != null){
			productPurchaseDTO.setIdProduct(product.getIdProduct());
			productPurchaseDTO.setDescriptionProduct(product.getDescriptionProduct());
		}
		if(manufacturer != null){
			productPurchaseDTO.setIdManufacturer(manufacturer.getIdManufacturer());
			productPurchaseDTO.setNameManufacturer(manufacturer.getNameManufacturer());
		}
		if(category != null){
			productPurchaseDTO.setIdCategory(category.getIdCategory());
			productPurchaseDTO.setNameCategory(category.getNameCategory());
		}
		if(unitType != null){
			productPurchaseDTO.setIdUnitType(unitType.getIdUnitType());
			productPurchaseDTO.setNameUnitType(unitType.getNameUnitType());
		}
		if(packSize != null){
			productPurchaseDTO.setIdPackSize(packSize.getIdPackSize());
			productPurchaseDTO.setNamePackSize(packSize.getNamePackSize());
		}
		if(distributorRoot != null){
			productPurchaseDTO.setIdDistributorRoot(distributorRoot.getIdDistributorRoot());
			productPurchaseDTO.setNameDistributorRoot(distributorRoot.getNameDistributorRoot());
		}
		if(distributorLeaf != null){
			productPurchaseDTO.setIdDistributorLeaf(distributorLeaf.getIdDistributorLeaf());
			productPurchaseDTO.setNameDistributorLeaf(distributorLeaf.getNameDistributorLeaf());
		}
		if(cityDistributorLeaf != null){
			productPurchaseDTO.setIdCityDistributorLeaf(cityDistributorLeaf.getIdCity());
			productPurchaseDTO.setCodeCityDistributorLeaf(cityDistributorLeaf.getCodeCity());
		}
		productPurchaseDTO.setQuantityProduct(quantityProduct);
		productPurchaseDTO.setPriceProduct(priceProduct);
		productPurchaseDTO.setTotalProduct(totalProduct);
		return productPurchaseDTO;
	}
	
	public static Specification<ProductPurchase> createSpecifications(FilterProductPurchaseDTO filterProductPurchaseDTO) {
		Specification<ProductPurchase> specifications = Specification.where(null);
		if(filterProductPurchaseDTO != null){
			if(filterProductPurchaseDTO.getIdProductPurchase() != null){
	    		specifications = specifications.and(idProductPurchaseEqualsTo(filterProductPurchaseDTO.getIdProductPurchase()));
    		}
			if(filterProductPurchaseDTO.getCodeInvoice() != null && !filterProductPurchaseDTO.getCodeInvoice().isEmpty()){
	    		specifications = specifications.and(codeInvoiceEqualsTo(filterProductPurchaseDTO.getCodeInvoice()));
    		}
			if(filterProductPurchaseDTO.getDatePurchaseFrom() != null || filterProductPurchaseDTO.getDatePurchaseTo() != null){
				specifications = specifications.and(datePurchaseBetween(filterProductPurchaseDTO.getDatePurchaseFrom(), filterProductPurchaseDTO.getDatePurchaseTo()));
			}
			if(filterProductPurchaseDTO.getYearPurchase() != null){
	    		specifications = specifications.and(yearPurchaseEqualsTo(filterProductPurchaseDTO.getYearPurchase()));
    		}
			if(filterProductPurchaseDTO.getYearPurchaseFrom() != null || filterProductPurchaseDTO.getYearPurchaseTo() != null){
				specifications = specifications.and(yearPurchaseBetween(filterProductPurchaseDTO.getYearPurchaseFrom(), filterProductPurchaseDTO.getYearPurchaseTo()));
			}
			if(filterProductPurchaseDTO.getMonthPurchase() != null){
	    		specifications = specifications.and(monthPurchaseEqualsTo(filterProductPurchaseDTO.getMonthPurchase()));
    		}
			if(filterProductPurchaseDTO.getMonthPurchaseFrom() != null || filterProductPurchaseDTO.getMonthPurchaseTo() != null){
				specifications = specifications.and(monthPurchaseBetween(filterProductPurchaseDTO.getMonthPurchaseFrom(), filterProductPurchaseDTO.getMonthPurchaseTo()));
			}
			if(filterProductPurchaseDTO.getDayOfMonthPurchase() != null){
	    		specifications = specifications.and(dayOfMonthPurchaseEqualsTo(filterProductPurchaseDTO.getDayOfMonthPurchase()));
    		}
			if(filterProductPurchaseDTO.getDayOfMonthPurchaseFrom() != null || filterProductPurchaseDTO.getDayOfMonthPurchaseTo() != null){
				specifications = specifications.and(dayOfMonthPurchaseBetween(filterProductPurchaseDTO.getDayOfMonthPurchaseFrom(), filterProductPurchaseDTO.getDayOfMonthPurchaseTo()));
			}
			if(filterProductPurchaseDTO.getIdCustomerRoot() != null){
	    		specifications = specifications.and(idCustomerRootEqualsTo(filterProductPurchaseDTO.getIdCustomerRoot()));
    		}
			if(filterProductPurchaseDTO.getIdCustomerLeaf() != null){
	    		specifications = specifications.and(idCustomerLeafEqualsTo(filterProductPurchaseDTO.getIdCustomerLeaf()));
    		}
			if(filterProductPurchaseDTO.getIdCityCustomerLeaf() != null){
	    		specifications = specifications.and(idCityCustomerLeafEqualsTo(filterProductPurchaseDTO.getIdCityCustomerLeaf()));
    		}
			if(filterProductPurchaseDTO.getIdProduct() != null){
	    		specifications = specifications.and(idProductEqualsTo(filterProductPurchaseDTO.getIdProduct()));
    		}
			if(filterProductPurchaseDTO.getIdManufacturer() != null){
	    		specifications = specifications.and(idManufacturerEqualsTo(filterProductPurchaseDTO.getIdManufacturer()));
    		}
			if(filterProductPurchaseDTO.getIdCategory() != null){
	    		specifications = specifications.and(idCategoryEqualsTo(filterProductPurchaseDTO.getIdCategory()));
    		}
			if(filterProductPurchaseDTO.getIdUnitType() != null){
	    		specifications = specifications.and(idUnitTypeEqualsTo(filterProductPurchaseDTO.getIdUnitType()));
    		}
			if(filterProductPurchaseDTO.getIdPackSize() != null){
	    		specifications = specifications.and(idPackSizeEqualsTo(filterProductPurchaseDTO.getIdPackSize()));
    		}
			if(filterProductPurchaseDTO.getIdDistributorRoot() != null){
	    		specifications = specifications.and(idDistributorRootEqualsTo(filterProductPurchaseDTO.getIdDistributorRoot()));
    		}
			if(filterProductPurchaseDTO.getIdDistributorLeaf() != null){
	    		specifications = specifications.and(idDistributorLeafEqualsTo(filterProductPurchaseDTO.getIdDistributorLeaf()));
    		}
			if(filterProductPurchaseDTO.getIdCityDistributorLeaf() != null){
	    		specifications = specifications.and(idCityDistributorLeafEqualsTo(filterProductPurchaseDTO.getIdCityDistributorLeaf()));
    		}
		}
		return specifications;
	}
	
	public static Specification<ProductPurchase> createSpecificationsForAggregatedData(FilterAggregatedDataProductPurchaseDTO filterAggregatedDataProductPurchaseDTO) {
		Specification<ProductPurchase> specificationsWhere = createSpecifications(filterAggregatedDataProductPurchaseDTO);
		Specification<ProductPurchase> specifications = Specification.where(specificationsWhere);
		return specifications;
	}
	
	private static Specification<ProductPurchase> idProductPurchaseEqualsTo(Long idProductPurchase) {
		return (root, query, builder) -> 
			builder.equal(
					root.get(ProductPurchase_.idProductPurchase), 
					idProductPurchase
					);
	}
	
	private static Specification<ProductPurchase> codeInvoiceEqualsTo(String codeInvoice) {
		return (root, query, builder) -> 
			builder.equal(
					root.get(ProductPurchase_.codeInvoice), 
					codeInvoice
					);
	}
	
	private static Specification<ProductPurchase> datePurchaseBetween(LocalDate datePurchaseFrom, LocalDate datePurchaseTo) {
		return (root, query, builder) -> {
			Expression<LocalDate> campo = builder.function("TRUNC", LocalDate.class, root.get(ProductPurchase_.datePurchase));
			if(datePurchaseFrom != null && datePurchaseTo == null){
				Expression<LocalDate> dateFrom = builder.function("TRUNC", LocalDate.class, builder.literal(datePurchaseFrom));
				return builder.greaterThanOrEqualTo(campo, dateFrom);
			}else if(datePurchaseFrom == null && datePurchaseTo != null){
				Expression<LocalDate> dateTo = builder.function("TRUNC", LocalDate.class, builder.literal(datePurchaseTo));
				return builder.lessThanOrEqualTo(campo, dateTo);
			}else{
				Expression<LocalDate> dateFrom = builder.function("TRUNC", LocalDate.class, builder.literal(datePurchaseFrom));
				Expression<LocalDate> dateTo = builder.function("TRUNC", LocalDate.class, builder.literal(datePurchaseTo));
				return builder.between(campo, dateFrom, dateTo);
			}
		};
	}
	
	private static Specification<ProductPurchase> yearPurchaseEqualsTo(Integer yearPurchase) {
		return (root, query, builder) -> 
			builder.equal(
					root.get(ProductPurchase_.yearPurchase), 
					yearPurchase
					);
	}
	
	private static Specification<ProductPurchase> yearPurchaseBetween(Integer yearPurchaseFrom, Integer yearPurchaseTo) {
		return (root, query, builder) -> {
			Expression<Integer> campo = root.get(ProductPurchase_.yearPurchase);
			if(yearPurchaseFrom != null && yearPurchaseTo == null){
				return builder.greaterThanOrEqualTo(campo, yearPurchaseFrom);
			}else if(yearPurchaseFrom == null && yearPurchaseTo != null){
				return builder.lessThanOrEqualTo(campo, yearPurchaseTo);
			}else{
				return builder.between(campo, yearPurchaseFrom, yearPurchaseTo);
			}
		};
	}
	
	private static Specification<ProductPurchase> monthPurchaseEqualsTo(Integer monthPurchase) {
		return (root, query, builder) -> 
			builder.equal(
					root.get(ProductPurchase_.monthPurchase), 
					monthPurchase
					);
	}
	
	private static Specification<ProductPurchase> monthPurchaseBetween(Integer monthPurchaseFrom, Integer monthPurchaseTo) {
		return (root, query, builder) -> {
			Expression<Integer> campo = root.get(ProductPurchase_.monthPurchase);
			if(monthPurchaseFrom != null && monthPurchaseTo == null){
				return builder.greaterThanOrEqualTo(campo, monthPurchaseFrom);
			}else if(monthPurchaseFrom == null && monthPurchaseTo != null){
				return builder.lessThanOrEqualTo(campo, monthPurchaseTo);
			}else{
				return builder.between(campo, monthPurchaseFrom, monthPurchaseTo);
			}
		};
	}
	
	private static Specification<ProductPurchase> dayOfMonthPurchaseEqualsTo(Integer dayOfMonthPurchase) {
		return (root, query, builder) -> 
			builder.equal(
					root.get(ProductPurchase_.dayOfMonthPurchase), 
					dayOfMonthPurchase
					);
	}
	
	private static Specification<ProductPurchase> dayOfMonthPurchaseBetween(Integer dayOfMonthPurchaseFrom, Integer dayOfMonthPurchaseTo) {
		return (root, query, builder) -> {
			Expression<Integer> campo = root.get(ProductPurchase_.dayOfMonthPurchase);
			if(dayOfMonthPurchaseFrom != null && dayOfMonthPurchaseTo == null){
				return builder.greaterThanOrEqualTo(campo, dayOfMonthPurchaseFrom);
			}else if(dayOfMonthPurchaseFrom == null && dayOfMonthPurchaseTo != null){
				return builder.lessThanOrEqualTo(campo, dayOfMonthPurchaseTo);
			}else{
				return builder.between(campo, dayOfMonthPurchaseFrom, dayOfMonthPurchaseTo);
			}
		};
	}
	
	private static Specification<ProductPurchase> idCustomerRootEqualsTo(Long idCustomerRoot) {
		return (root, query, builder) -> 
			builder.equal(
					root.get(ProductPurchase_.idCustomerRoot), 
					idCustomerRoot
					);
	}
	
	private static Specification<ProductPurchase> idCustomerLeafEqualsTo(Long idCustomerLeaf) {
		return (root, query, builder) -> 
			builder.equal(
					root.get(ProductPurchase_.idCustomerLeaf), 
					idCustomerLeaf
					);
	}
	
	private static Specification<ProductPurchase> idCityCustomerLeafEqualsTo(Long idCityCustomerLeaf) {
		return (root, query, builder) -> 
			builder.equal(
					root.get(ProductPurchase_.idCityCustomerLeaf), 
					idCityCustomerLeaf
					);
	}
	
	private static Specification<ProductPurchase> idProductEqualsTo(Long idProduct) {
		return (root, query, builder) -> 
			builder.equal(
					root.get(ProductPurchase_.idProduct), 
					idProduct
					);
	}
	
	private static Specification<ProductPurchase> idManufacturerEqualsTo(Long idManufacturer) {
		return (root, query, builder) -> 
			builder.equal(
					root.get(ProductPurchase_.idManufacturer), 
					idManufacturer
					);
	}
	
	private static Specification<ProductPurchase> idCategoryEqualsTo(Long idCategory) {
		return (root, query, builder) -> 
			builder.equal(
					root.get(ProductPurchase_.idCategory), 
					idCategory
					);
	}
	
	private static Specification<ProductPurchase> idUnitTypeEqualsTo(Long idUnitType) {
		return (root, query, builder) -> 
			builder.equal(
					root.get(ProductPurchase_.idUnitType), 
					idUnitType
					);
	}
	
	private static Specification<ProductPurchase> idPackSizeEqualsTo(Long idPackSize) {
		return (root, query, builder) -> 
			builder.equal(
					root.get(ProductPurchase_.idPackSize), 
					idPackSize
					);
	}
	
	private static Specification<ProductPurchase> idDistributorRootEqualsTo(Long idDistributorRoot) {
		return (root, query, builder) -> 
			builder.equal(
					root.get(ProductPurchase_.idDistributorRoot), 
					idDistributorRoot
					);
	}
	
	private static Specification<ProductPurchase> idDistributorLeafEqualsTo(Long idDistributorLeaf) {
		return (root, query, builder) -> 
			builder.equal(
					root.get(ProductPurchase_.idDistributorLeaf), 
					idDistributorLeaf
					);
	}
	
	private static Specification<ProductPurchase> idCityDistributorLeafEqualsTo(Long idCityDistributorLeaf) {
		return (root, query, builder) -> 
			builder.equal(
					root.get(ProductPurchase_.idCityDistributorLeaf), 
					idCityDistributorLeaf
					);
	}

}
