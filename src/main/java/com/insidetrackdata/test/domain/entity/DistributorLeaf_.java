package com.insidetrackdata.test.domain.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(DistributorLeaf.class)
public class DistributorLeaf_ {

	public static volatile SingularAttribute<DistributorLeaf, Long> idDistributorLeaf;
	public static volatile SingularAttribute<DistributorLeaf, DistributorRoot> distributorRoot;
	public static volatile SingularAttribute<DistributorLeaf, City> city;
	public static volatile SingularAttribute<DistributorLeaf, String> nameDistributorLeaf;
	
	public enum ListSortFieldEnum {

		ID_DISTRIBUTOR_LEAF("idDistributorLeaf", "idDistributorLeaf"),
		NAME_DISTRIBUTOR_ROOT("distributorRoot.nameDistributorRoot", "nameDistributorRoot"),
		CODE_CITY("city.codeCity", "codeCity"),
		NAME_DISTRIBUTOR_LEAF("nameDistributorLeaf", "nameDistributorLeaf");
	    
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
