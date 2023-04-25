package com.insidetrackdata.test.domain.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Product.class)
public class Product_ {

	public static volatile SingularAttribute<Product, Long> idProduct;
	public static volatile SingularAttribute<Product, String> descriptionProduct;
	public static volatile SingularAttribute<Product, Manufacturer> manufacturer;
	public static volatile SingularAttribute<Product, Category> category;
	
	public enum ListSortFieldEnum {

		ID_PRODUCT("idProduct", "idProduct"),
		DESCRIPTION_PRODUCT("descriptionProduct", "descriptionProduct"),
		NAME_MANUFACTURER("manufacturer.nameManufacturer", "nameManufacturer"),
		NAME_CATEGORY("category.nameCategory", "nameCategory");
	    
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
    
}
