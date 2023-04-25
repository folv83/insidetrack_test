package com.insidetrackdata.test.domain.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(DistributorRoot.class)
public class DistributorRoot_ {

	public static volatile SingularAttribute<DistributorRoot, Long> idDistributorRoot;
	public static volatile SingularAttribute<DistributorRoot, String> nameDistributorRoot;
	
	public enum ListSortFieldEnum {

		ID_DISTRIBUTOR_ROOT("idDistributorRoot", "idDistributorRoot"),
		NAME_DISTRIBUTOR_ROOT("nameDistributorRoot", "nameDistributorRoot");
	    
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
