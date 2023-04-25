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
import com.insidetrackdata.test.domain.dto.GenericBeanDTO;
import com.insidetrackdata.test.domain.dto.UnitTypeDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterUnitTypeDTO;

@Entity
@Table(name = "ITD_DT_UNIT_TYPE")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class UnitType implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "ID_UNIT_TYPE")
	@EqualsAndHashCode.Include @ToString.Include
	private Long idUnitType;
	
	@Column(name = "NA_UNIT_TYPE")
	private String nameUnitType;
	
	public UnitType(Long idUnitType) {
        this.idUnitType = idUnitType;
    }
	
	public UnitTypeDTO getUnitTypeDTO() {
		UnitTypeDTO unitTypeDTO = new UnitTypeDTO();
		unitTypeDTO.setIdUnitType(idUnitType);
		unitTypeDTO.setNameUnitType(nameUnitType);
		return unitTypeDTO;
	}
	
	public GenericBeanDTO getGenericBeanDTO(){
		GenericBeanDTO genericBeanDTO = new GenericBeanDTO();
		genericBeanDTO.setValue(idUnitType.toString());
		genericBeanDTO.setLabel(nameUnitType);
		return genericBeanDTO;
	}
	
	public static Specification<UnitType> createSpecifications(FilterUnitTypeDTO filterUnitTypeDTO) {
		Specification<UnitType> specifications = Specification.where(null);
		if(filterUnitTypeDTO != null){
			if(filterUnitTypeDTO.getIdUnitType() != null){
	    		specifications = specifications.and(idUnitTypeEqualsTo(filterUnitTypeDTO.getIdUnitType()));
    		}
			if(filterUnitTypeDTO.getNameUnitType() != null && !filterUnitTypeDTO.getNameUnitType().isEmpty()){
	    		specifications = specifications.and(nameUnitTypeLikeTo(filterUnitTypeDTO.getNameUnitType()));
    		}
		}
		return specifications;
	}
	
	private static Specification<UnitType> idUnitTypeEqualsTo(Long idUnitType) {
		return (root, query, builder) -> 
			builder.equal(
					root.get(UnitType_.idUnitType), 
					idUnitType
					);
	}
	
	private static Specification<UnitType> nameUnitTypeLikeTo(String nameUnitType) {
		return (root, query, builder) -> {
			Expression<String> campo = builder.upper(builder.function("CONVERT", String.class, root.get(UnitType_.nameUnitType), builder.literal("US7ASCII")));
			Expression<String> valor = builder.upper(builder.function("CONVERT", String.class, builder.literal("%" + nameUnitType + "%"), builder.literal("US7ASCII")));
			return builder.like(campo, valor);
		};
	}

}
