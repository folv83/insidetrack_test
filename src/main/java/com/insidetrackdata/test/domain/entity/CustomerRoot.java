package com.insidetrackdata.test.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.criteria.Expression;

import org.springframework.data.jpa.domain.Specification;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.insidetrackdata.test.domain.dto.CustomerRootDTO;
import com.insidetrackdata.test.domain.dto.GenericBeanDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterCustomerRootDTO;

@Entity
@Table(name = "ITD_DT_CUSTOMER_ROOT")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class CustomerRoot implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "ID_CUSTOMER_ROOT")
	@EqualsAndHashCode.Include @ToString.Include
	private Long idCustomerRoot;
	
	@Column(name = "NA_CUSTOMER_ROOT")
	private String nameCustomerRoot;
	
	public CustomerRoot(Long idCustomerRoot) {
        this.idCustomerRoot = idCustomerRoot;
    }
	
	public CustomerRootDTO getCustomerRootDTO() {
		CustomerRootDTO customerRootDTO = new CustomerRootDTO();
		customerRootDTO.setIdCustomerRoot(idCustomerRoot);
		customerRootDTO.setNameCustomerRoot(nameCustomerRoot);
		return customerRootDTO;
	}
	
	public GenericBeanDTO getGenericBeanDTO(){
		GenericBeanDTO genericBeanDTO = new GenericBeanDTO();
		genericBeanDTO.setValue(idCustomerRoot.toString());
		genericBeanDTO.setLabel(nameCustomerRoot);
		return genericBeanDTO;
	}
	
	public static Specification<CustomerRoot> createSpecifications(FilterCustomerRootDTO filterCustomerRootDTO) {
		Specification<CustomerRoot> specifications = Specification.where(null);
		if(filterCustomerRootDTO != null){
			if(filterCustomerRootDTO.getIdCustomerRoot() != null){
	    		specifications = specifications.and(idCustomerRootEqualsTo(filterCustomerRootDTO.getIdCustomerRoot()));
    		}
			if(filterCustomerRootDTO.getNameCustomerRoot() != null && !filterCustomerRootDTO.getNameCustomerRoot().isEmpty()){
	    		specifications = specifications.and(nameCustomerRootLikeTo(filterCustomerRootDTO.getNameCustomerRoot()));
    		}
		}
		return specifications;
	}
	
	private static Specification<CustomerRoot> idCustomerRootEqualsTo(Long idCustomerRoot) {
		return (root, query, builder) -> 
			builder.equal(
					root.get(CustomerRoot_.idCustomerRoot), 
					idCustomerRoot
					);
	}
	
	private static Specification<CustomerRoot> nameCustomerRootLikeTo(String nameCustomerRoot) {
		return (root, query, builder) -> {
			Expression<String> campo = builder.upper(builder.function("CONVERT", String.class, root.get(CustomerRoot_.nameCustomerRoot), builder.literal("US7ASCII")));
			Expression<String> valor = builder.upper(builder.function("CONVERT", String.class, builder.literal("%" + nameCustomerRoot + "%"), builder.literal("US7ASCII")));
			return builder.like(campo, valor);
		};
	}

}
