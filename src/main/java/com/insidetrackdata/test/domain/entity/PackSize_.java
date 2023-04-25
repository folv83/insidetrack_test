package com.insidetrackdata.test.domain.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(PackSize.class)
public class PackSize_ {

	public static volatile SingularAttribute<PackSize, Long> idPackSize;
	public static volatile SingularAttribute<PackSize, String> namePackSize;
	
	public enum ListSortFieldEnum {

		ID_PACK_SIZE("idPackSize", "idPackSize"),
		NAME_PACK_SIZE("namePackSize", "namePackSize");
	    
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
