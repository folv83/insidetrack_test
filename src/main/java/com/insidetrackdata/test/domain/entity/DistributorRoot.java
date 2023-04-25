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
import com.insidetrackdata.test.domain.dto.DistributorRootDTO;
import com.insidetrackdata.test.domain.dto.GenericBeanDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterDistributorRootDTO;

@Entity
@Table(name = "ITD_DT_DISTRIBUTOR_ROOT")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class DistributorRoot implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "ID_DISTRIBUTOR_ROOT")
	@EqualsAndHashCode.Include @ToString.Include
	private Long idDistributorRoot;
	
	@Column(name = "NA_DISTRIBUTOR_ROOT")
	private String nameDistributorRoot;
	
	public DistributorRoot(Long idDistributorRoot) {
        this.idDistributorRoot = idDistributorRoot;
    }
	
	public DistributorRootDTO getDistributorRootDTO() {
		DistributorRootDTO distributorRootDTO = new DistributorRootDTO();
		distributorRootDTO.setIdDistributorRoot(idDistributorRoot);
		distributorRootDTO.setNameDistributorRoot(nameDistributorRoot);
		return distributorRootDTO;
	}
	
	public GenericBeanDTO getGenericBeanDTO(){
		GenericBeanDTO genericBeanDTO = new GenericBeanDTO();
		genericBeanDTO.setValue(idDistributorRoot.toString());
		genericBeanDTO.setLabel(nameDistributorRoot);
		return genericBeanDTO;
	}
	
	public static Specification<DistributorRoot> createSpecifications(FilterDistributorRootDTO filterDistributorRootDTO) {
		Specification<DistributorRoot> specifications = Specification.where(null);
		if(filterDistributorRootDTO != null){
			if(filterDistributorRootDTO.getIdDistributorRoot() != null){
	    		specifications = specifications.and(idDistributorRootEqualsTo(filterDistributorRootDTO.getIdDistributorRoot()));
    		}
			if(filterDistributorRootDTO.getNameDistributorRoot() != null && !filterDistributorRootDTO.getNameDistributorRoot().isEmpty()){
	    		specifications = specifications.and(nameDistributorRootLikeTo(filterDistributorRootDTO.getNameDistributorRoot()));
    		}
		}
		return specifications;
	}
	
	private static Specification<DistributorRoot> idDistributorRootEqualsTo(Long idDistributorRoot) {
		return (root, query, builder) -> 
			builder.equal(
					root.get(DistributorRoot_.idDistributorRoot), 
					idDistributorRoot
					);
	}
	
	private static Specification<DistributorRoot> nameDistributorRootLikeTo(String nameDistributorRoot) {
		return (root, query, builder) -> {
			Expression<String> campo = builder.upper(builder.function("CONVERT", String.class, root.get(DistributorRoot_.nameDistributorRoot), builder.literal("US7ASCII")));
			Expression<String> valor = builder.upper(builder.function("CONVERT", String.class, builder.literal("%" + nameDistributorRoot + "%"), builder.literal("US7ASCII")));
			return builder.like(campo, valor);
		};
	}

}
