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
import com.insidetrackdata.test.domain.dto.PackSizeDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterPackSizeDTO;

@Entity
@Table(name = "ITD_DT_PACK_SIZE")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class PackSize implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "ID_PACK_SIZE")
	@EqualsAndHashCode.Include @ToString.Include
	private Long idPackSize;
	
	@Column(name = "NA_PACK_SIZE")
	private String namePackSize;
	
	public PackSize(Long idPackSize) {
        this.idPackSize = idPackSize;
    }
	
	public PackSizeDTO getPackSizeDTO() {
		PackSizeDTO packSizeDTO = new PackSizeDTO();
		packSizeDTO.setIdPackSize(idPackSize);
		packSizeDTO.setNamePackSize(namePackSize);
		return packSizeDTO;
	}
	
	public GenericBeanDTO getGenericBeanDTO(){
		GenericBeanDTO genericBeanDTO = new GenericBeanDTO();
		genericBeanDTO.setValue(idPackSize.toString());
		genericBeanDTO.setLabel(namePackSize);
		return genericBeanDTO;
	}
	
	public static Specification<PackSize> createSpecifications(FilterPackSizeDTO filterPackSizeDTO) {
		Specification<PackSize> specifications = Specification.where(null);
		if(filterPackSizeDTO != null){
			if(filterPackSizeDTO.getIdPackSize() != null){
	    		specifications = specifications.and(idPackSizeEqualsTo(filterPackSizeDTO.getIdPackSize()));
    		}
			if(filterPackSizeDTO.getNamePackSize() != null && !filterPackSizeDTO.getNamePackSize().isEmpty()){
	    		specifications = specifications.and(namePackSizeLikeTo(filterPackSizeDTO.getNamePackSize()));
    		}
		}
		return specifications;
	}
	
	private static Specification<PackSize> idPackSizeEqualsTo(Long idPackSize) {
		return (root, query, builder) -> 
			builder.equal(
					root.get(PackSize_.idPackSize), 
					idPackSize
					);
	}
	
	private static Specification<PackSize> namePackSizeLikeTo(String namePackSize) {
		return (root, query, builder) -> {
			Expression<String> campo = builder.upper(builder.function("CONVERT", String.class, root.get(PackSize_.namePackSize), builder.literal("US7ASCII")));
			Expression<String> valor = builder.upper(builder.function("CONVERT", String.class, builder.literal("%" + namePackSize + "%"), builder.literal("US7ASCII")));
			return builder.like(campo, valor);
		};
	}

}
