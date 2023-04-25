package com.insidetrackdata.test.domain.entity;

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
import com.insidetrackdata.test.domain.dto.CustomerLeafDTO;
import com.insidetrackdata.test.domain.dto.GenericBeanDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterCustomerLeafDTO;

@Entity
@Table(name = "ITD_DT_CUSTOMER_LEAF")
@NamedEntityGraph(
		name = CustomerLeaf.COMMON_GRAPH,
		attributeNodes = {
				@NamedAttributeNode(value = "customerRoot"),
				@NamedAttributeNode(value = "city")
				}
		)
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class CustomerLeaf implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final String COMMON_GRAPH = "customerLeafCommonGraph";

	@Id
    @Column(name = "ID_CUSTOMER_LEAF")
	@EqualsAndHashCode.Include @ToString.Include
	private Long idCustomerLeaf;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CUSTOMER_ROOT")
	private CustomerRoot customerRoot;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CITY")
	private City city;
	
	@Column(name = "NA_CUSTOMER_LEAF")
	private String nameCustomerLeaf;
	
	public CustomerLeaf(Long idCustomerLeaf) {
        this.idCustomerLeaf = idCustomerLeaf;
    }
	
	public CustomerLeafDTO getCustomerLeafDTO() {
		CustomerLeafDTO customerLeafDTO = new CustomerLeafDTO();
		customerLeafDTO.setIdCustomerLeaf(idCustomerLeaf);
		if(customerRoot != null){
			customerLeafDTO.setIdCustomerRoot(customerRoot.getIdCustomerRoot());
			customerLeafDTO.setNameCustomerRoot(customerRoot.getNameCustomerRoot());
		}
		if(city != null){
			customerLeafDTO.setIdCity(city.getIdCity());
			customerLeafDTO.setCodeCity(city.getCodeCity());
		}
		customerLeafDTO.setNameCustomerLeaf(nameCustomerLeaf);
		return customerLeafDTO;
	}
	
	public GenericBeanDTO getGenericBeanDTO(){
		GenericBeanDTO genericBeanDTO = new GenericBeanDTO();
		genericBeanDTO.setValue(idCustomerLeaf.toString());
		genericBeanDTO.setLabel(nameCustomerLeaf);
		return genericBeanDTO;
	}
	
	public static Specification<CustomerLeaf> createSpecifications(FilterCustomerLeafDTO filterCustomerLeafDTO) {
		Specification<CustomerLeaf> specifications = Specification.where(null);
		if(filterCustomerLeafDTO != null){
			if(filterCustomerLeafDTO.getIdCustomerLeaf() != null){
	    		specifications = specifications.and(idCustomerLeafEqualsTo(filterCustomerLeafDTO.getIdCustomerLeaf()));
    		}
			if(filterCustomerLeafDTO.getIdCustomerRoot() != null){
	    		specifications = specifications.and(idCustomerRootEqualsTo(filterCustomerLeafDTO.getIdCustomerRoot()));
    		}
			if(filterCustomerLeafDTO.getIdCity() != null){
	    		specifications = specifications.and(idCityEqualsTo(filterCustomerLeafDTO.getIdCity()));
    		}
			if(filterCustomerLeafDTO.getNameCustomerLeaf() != null && !filterCustomerLeafDTO.getNameCustomerLeaf().isEmpty()){
	    		specifications = specifications.and(nameCustomerLeafLikeTo(filterCustomerLeafDTO.getNameCustomerLeaf()));
    		}
		}
		return specifications;
	}
	
	private static Specification<CustomerLeaf> idCustomerLeafEqualsTo(Long idCustomerLeaf) {
		return (root, query, builder) -> 
			builder.equal(
					root.get(CustomerLeaf_.idCustomerLeaf), 
					idCustomerLeaf
					);
	}
	
	private static Specification<CustomerLeaf> idCustomerRootEqualsTo(Long idCustomerRoot) {
		return (root, query, builder) -> 
			builder.equal(
					root.get(CustomerLeaf_.customerRoot).get(CustomerRoot_.idCustomerRoot), 
					idCustomerRoot
					);
	}
	
	private static Specification<CustomerLeaf> idCityEqualsTo(Long idCity) {
		return (root, query, builder) -> 
			builder.equal(
					root.get(CustomerLeaf_.city).get(City_.idCity), 
					idCity
					);
	}
	
	private static Specification<CustomerLeaf> nameCustomerLeafLikeTo(String nameCustomerLeaf) {
		return (root, query, builder) -> {
			Expression<String> campo = builder.upper(builder.function("CONVERT", String.class, root.get(CustomerLeaf_.nameCustomerLeaf), builder.literal("US7ASCII")));
			Expression<String> valor = builder.upper(builder.function("CONVERT", String.class, builder.literal("%" + nameCustomerLeaf + "%"), builder.literal("US7ASCII")));
			return builder.like(campo, valor);
		};
	}

}
