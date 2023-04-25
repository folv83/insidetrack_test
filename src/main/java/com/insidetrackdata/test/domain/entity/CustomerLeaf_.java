package com.insidetrackdata.test.domain.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(CustomerLeaf.class)
public class CustomerLeaf_ {

	public static volatile SingularAttribute<CustomerLeaf, Long> idCustomerLeaf;
	public static volatile SingularAttribute<CustomerLeaf, CustomerRoot> customerRoot;
	public static volatile SingularAttribute<CustomerLeaf, City> city;
	public static volatile SingularAttribute<CustomerLeaf, String> nameCustomerLeaf;
	
	public enum ListSortFieldEnum {

		ID_CUSTOMER_LEAF("idCustomerLeaf", "idCustomerLeaf"),
		NAME_CUSTOMER_ROOT("customerRoot.nameCustomerRoot", "nameCustomerRoot"),
		CODE_CITY("city.codeCity", "codeCity"),
		NAME_CUSTOMER_LEAF("nameCustomerLeaf", "nameCustomerLeaf");
	    
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
