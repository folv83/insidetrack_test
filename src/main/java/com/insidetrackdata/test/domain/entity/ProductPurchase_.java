package com.insidetrackdata.test.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ProductPurchase.class)
public class ProductPurchase_ {

	public static volatile SingularAttribute<ProductPurchase, Long> idProductPurchase;
	public static volatile SingularAttribute<ProductPurchase, String> codeInvoice;
	public static volatile SingularAttribute<ProductPurchase, LocalDate> datePurchase;
	public static volatile SingularAttribute<ProductPurchase, Integer> yearPurchase;
	public static volatile SingularAttribute<ProductPurchase, Integer> monthPurchase;
	public static volatile SingularAttribute<ProductPurchase, Integer> dayOfMonthPurchase;
	public static volatile SingularAttribute<ProductPurchase, String> fullMonthPurchase;
	public static volatile SingularAttribute<ProductPurchase, CustomerRoot> customerRoot;
	public static volatile SingularAttribute<ProductPurchase, Long> idCustomerRoot;
	public static volatile SingularAttribute<ProductPurchase, CustomerLeaf> customerLeaf;
	public static volatile SingularAttribute<ProductPurchase, Long> idCustomerLeaf;
	public static volatile SingularAttribute<ProductPurchase, City> cityCustomerLeaf;
	public static volatile SingularAttribute<ProductPurchase, Long> idCityCustomerLeaf;
	public static volatile SingularAttribute<ProductPurchase, Product> product;
	public static volatile SingularAttribute<ProductPurchase, Long> idProduct;
	public static volatile SingularAttribute<ProductPurchase, Manufacturer> manufacturer;
	public static volatile SingularAttribute<ProductPurchase, Long> idManufacturer;
	public static volatile SingularAttribute<ProductPurchase, Category> category;
	public static volatile SingularAttribute<ProductPurchase, Long> idCategory;
	public static volatile SingularAttribute<ProductPurchase, UnitType> unitType;
	public static volatile SingularAttribute<ProductPurchase, Long> idUnitType;
	public static volatile SingularAttribute<ProductPurchase, PackSize> packSize;
	public static volatile SingularAttribute<ProductPurchase, Long> idPackSize;
	public static volatile SingularAttribute<ProductPurchase, DistributorRoot> distributorRoot;
	public static volatile SingularAttribute<ProductPurchase, Long> idDistributorRoot;
	public static volatile SingularAttribute<ProductPurchase, DistributorLeaf> distributorLeaf;
	public static volatile SingularAttribute<ProductPurchase, Long> idDistributorLeaf;
	public static volatile SingularAttribute<ProductPurchase, City> cityDistributorLeaf;
	public static volatile SingularAttribute<ProductPurchase, Long> idCityDistributorLeaf;
	public static volatile SingularAttribute<ProductPurchase, BigDecimal> quantityProduct;
	public static volatile SingularAttribute<ProductPurchase, BigDecimal> priceProduct;
	public static volatile SingularAttribute<ProductPurchase, BigDecimal> totalProduct;
	
	public enum ListSortFieldEnum {

		ID_PRODUCT_PURCHASE("idProductPurchase", "idProductPurchase"),
		CODE_INVOICE("codeInvoice", "codeInvoice"),
		DATE_PURCHASE("datePurchase", "datePurchase"),
		YEAR_PURCHASE("yearPurchase", "yearPurchase"),
		FULL_MONTH_PURCHASE("fullMonthPurchase", "fullMonthPurchase"),
		NAME_CUSTOMER_ROOT("customerRoot.nameCustomerRoot", "nameCustomerRoot"),
		NAME_CUSTOMER_LEAF("customerLeaf.nameCustomerLeaf", "nameCustomerLeaf"),
		CODE_CITY_CUSTOMER_LEAF("cityCustomerLeaf.codeCity", "codeCityCustomerLeaf"),
		DESCRIPTION_PRODUCT("product.descriptionProduct", "descriptionProduct"),
		NAME_MANUFACTURER("manufacturer.nameManufacturer", "nameManufacturer"),
		NAME_CATEGORY("category.nameCategory", "nameCategory"),
		NAME_UNIT_TYPE("unitType.nameUnitType", "nameUnitType"),
		NAME_PACK_SIZE("packSize.namePackSize", "namePackSize"),
		NAME_DISTRIBUTOR_ROOT("customerRoot.nameDistributorRoot", "nameDistributorRoot"),
		NAME_DISTRIBUTOR_LEAF("customerLeaf.nameDistributorLeaf", "nameDistributorLeaf"),
		CODE_CITY_DISTRIBUTOR_LEAF("cityDistributorLeaf.codeCity", "codeCityDistributorLeaf"),
		QUANTITY_PRODUCT("quantityProduct", "quantityProduct"),
		PRICE_PRODUCT("priceProduct", "priceProduct"),
		TOTAL_PRODUCT("totalProduct", "totalProduct");
	    
	    String sortField;
	    String key;

	    ListSortFieldEnum(String sortField, String key) {
	        this.sortField = sortField;
	        this.key = key;
	    }

	    public String getSortField() {
	        return sortField;
	    }

	    public String getKey() {
	        return key;
	    }

	    public static ListSortFieldEnum fromValue(String sortField) {
	        if(sortField != null && !sortField.isEmpty()){
	            for(ListSortFieldEnum listSortFieldEnum : ListSortFieldEnum.values()){
	                if(listSortFieldEnum.getSortField() == sortField){
	                    return listSortFieldEnum;
	                }
	            }
	        }
	        return null;
	    }

	    public static ListSortFieldEnum fromKey(String key) {
	        if(key != null && !key.isEmpty()){
	            for(ListSortFieldEnum listSortFieldEnum : ListSortFieldEnum.values()){
	                if(listSortFieldEnum.getKey().equalsIgnoreCase(key)){
	                    return listSortFieldEnum;
	                }
	            }
	        }
	        return null;
	    }
	    
	}
	
	public enum ListAggregationFieldEnum {

		ALL("", "all"),
		INVOICE("codeInvoice", "invoice"),
		CUSTOMER_ROOT("customerRoot.nameCustomerRoot", "customerRoot"),
		CUSTOMER_LEAF("customerLeaf.nameCustomerLeaf", "customerLeaf"),
		CITY_CUSTOMER_LEAF("cityCustomerLeaf.codeCity", "cityCustomerLeaf"),
		PRODUCT("product.descriptionProduct", "product"),
		MANUFACTURER("manufacturer.nameManufacturer", "manufacturer"),
		CATEGORY("category.nameCategory", "category"),
		UNIT_TYPE("unitType.nameUnitType", "unitType"),
		PACK_SIZE("packSize.namePackSize", "packSize"),
		DISTRIBUTOR_ROOT("customerRoot.nameDistributorRoot", "distributorRoot"),
		DISTRIBUTOR_LEAF("customerLeaf.nameDistributorLeaf", "distributorLeaf"),
		CITY_DISTRIBUTOR_LEAF("cityDistributorLeaf.codeCity", "cityDistributorLeaf");
		
	    String aggregationField;
	    String key;

	    ListAggregationFieldEnum(String aggregationField, String key) {
	        this.aggregationField = aggregationField;
	        this.key = key;
	    }

	    public String getAggregationField() {
	        return aggregationField;
	    }

	    public String getKey() {
	        return key;
	    }

	    public static ListAggregationFieldEnum fromValue(String aggregationField) {
	        if(aggregationField != null && !aggregationField.isEmpty()){
	            for(ListAggregationFieldEnum listAggregationFieldEnum : ListAggregationFieldEnum.values()){
	                if(listAggregationFieldEnum.getAggregationField() == aggregationField){
	                    return listAggregationFieldEnum;
	                }
	            }
	        }
	        return null;
	    }

	    public static ListAggregationFieldEnum fromKey(String key) {
	        if(key != null && !key.isEmpty()){
	            for(ListAggregationFieldEnum listAggregationFieldEnum : ListAggregationFieldEnum.values()){
	                if(listAggregationFieldEnum.getKey().equalsIgnoreCase(key)){
	                    return listAggregationFieldEnum;
	                }
	            }
	        }
	        return null;
	    }
	    
	}
    
}
