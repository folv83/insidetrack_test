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
import com.insidetrackdata.test.domain.dto.DistributorLeafDTO;
import com.insidetrackdata.test.domain.dto.GenericBeanDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterDistributorLeafDTO;

@Entity
@Table(name = "ITD_DT_DISTRIBUTOR_LEAF")
@NamedEntityGraph(
		name = DistributorLeaf.COMMON_GRAPH,
		attributeNodes = {
				@NamedAttributeNode(value = "distributorRoot"),
				@NamedAttributeNode(value = "city")
				}
		)
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class DistributorLeaf implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final String COMMON_GRAPH = "distributorLeafCommonGraph";

	@Id
    @Column(name = "ID_DISTRIBUTOR_LEAF")
	@EqualsAndHashCode.Include @ToString.Include
	private Long idDistributorLeaf;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DISTRIBUTOR_ROOT")
	private DistributorRoot distributorRoot;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CITY")
	private City city;
	
	@Column(name = "NA_DISTRIBUTOR_LEAF")
	private String nameDistributorLeaf;
	
	public DistributorLeaf(Long idDistributorLeaf) {
        this.idDistributorLeaf = idDistributorLeaf;
    }
	
	public DistributorLeafDTO getDistributorLeafDTO() {
		DistributorLeafDTO distributorLeafDTO = new DistributorLeafDTO();
		distributorLeafDTO.setIdDistributorLeaf(idDistributorLeaf);
		if(distributorRoot != null){
			distributorLeafDTO.setIdDistributorRoot(distributorRoot.getIdDistributorRoot());
			distributorLeafDTO.setNameDistributorRoot(distributorRoot.getNameDistributorRoot());
		}
		if(city != null){
			distributorLeafDTO.setIdCity(city.getIdCity());
			distributorLeafDTO.setCodeCity(city.getCodeCity());
		}
		distributorLeafDTO.setNameDistributorLeaf(nameDistributorLeaf);
		return distributorLeafDTO;
	}
	
	public GenericBeanDTO getGenericBeanDTO(){
		GenericBeanDTO genericBeanDTO = new GenericBeanDTO();
		genericBeanDTO.setValue(idDistributorLeaf.toString());
		genericBeanDTO.setLabel(nameDistributorLeaf);
		return genericBeanDTO;
	}
	
	public static Specification<DistributorLeaf> createSpecifications(FilterDistributorLeafDTO filterDistributorLeafDTO) {
		Specification<DistributorLeaf> specifications = Specification.where(null);
		if(filterDistributorLeafDTO != null){
			if(filterDistributorLeafDTO.getIdDistributorLeaf() != null){
	    		specifications = specifications.and(idDistributorLeafEqualsTo(filterDistributorLeafDTO.getIdDistributorLeaf()));
    		}
			if(filterDistributorLeafDTO.getIdDistributorRoot() != null){
	    		specifications = specifications.and(idDistributorRootEqualsTo(filterDistributorLeafDTO.getIdDistributorRoot()));
    		}
			if(filterDistributorLeafDTO.getIdCity() != null){
	    		specifications = specifications.and(idCityEqualsTo(filterDistributorLeafDTO.getIdCity()));
    		}
			if(filterDistributorLeafDTO.getNameDistributorLeaf() != null && !filterDistributorLeafDTO.getNameDistributorLeaf().isEmpty()){
	    		specifications = specifications.and(nameDistributorLeafLikeTo(filterDistributorLeafDTO.getNameDistributorLeaf()));
    		}
		}
		return specifications;
	}
	
	private static Specification<DistributorLeaf> idDistributorLeafEqualsTo(Long idDistributorLeaf) {
		return (root, query, builder) -> 
			builder.equal(
					root.get(DistributorLeaf_.idDistributorLeaf), 
					idDistributorLeaf
					);
	}
	
	private static Specification<DistributorLeaf> idDistributorRootEqualsTo(Long idDistributorRoot) {
		return (root, query, builder) -> 
			builder.equal(
					root.get(DistributorLeaf_.distributorRoot).get(DistributorRoot_.idDistributorRoot), 
					idDistributorRoot
					);
	}
	
	private static Specification<DistributorLeaf> idCityEqualsTo(Long idCity) {
		return (root, query, builder) -> 
			builder.equal(
					root.get(DistributorLeaf_.city).get(City_.idCity), 
					idCity
					);
	}
	
	private static Specification<DistributorLeaf> nameDistributorLeafLikeTo(String nameDistributorLeaf) {
		return (root, query, builder) -> {
			Expression<String> campo = builder.upper(builder.function("CONVERT", String.class, root.get(DistributorLeaf_.nameDistributorLeaf), builder.literal("US7ASCII")));
			Expression<String> valor = builder.upper(builder.function("CONVERT", String.class, builder.literal("%" + nameDistributorLeaf + "%"), builder.literal("US7ASCII")));
			return builder.like(campo, valor);
		};
	}

}
