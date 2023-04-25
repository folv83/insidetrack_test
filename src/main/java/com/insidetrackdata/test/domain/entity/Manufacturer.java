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
import com.insidetrackdata.test.domain.dto.ManufacturerDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterManufacturerDTO;

@Entity
@Table(name = "ITD_DT_MANUFACTURER")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Manufacturer implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "ID_MANUFACTURER")
	@EqualsAndHashCode.Include @ToString.Include
	private Long idManufacturer;
	
	@Column(name = "NA_MANUFACTURER")
	private String nameManufacturer;
	
	public Manufacturer(Long idManufacturer) {
        this.idManufacturer = idManufacturer;
    }
	
	public ManufacturerDTO getManufacturerDTO() {
		ManufacturerDTO manufacturerDTO = new ManufacturerDTO();
		manufacturerDTO.setIdManufacturer(idManufacturer);
		manufacturerDTO.setNameManufacturer(nameManufacturer);
		return manufacturerDTO;
	}
	
	public GenericBeanDTO getGenericBeanDTO(){
		GenericBeanDTO genericBeanDTO = new GenericBeanDTO();
		genericBeanDTO.setValue(idManufacturer.toString());
		genericBeanDTO.setLabel(nameManufacturer);
		return genericBeanDTO;
	}
	
	public static Specification<Manufacturer> createSpecifications(FilterManufacturerDTO filterManufacturerDTO) {
		Specification<Manufacturer> specifications = Specification.where(null);
		if(filterManufacturerDTO != null){
			if(filterManufacturerDTO.getIdManufacturer() != null){
	    		specifications = specifications.and(idManufacturerEqualsTo(filterManufacturerDTO.getIdManufacturer()));
    		}
			if(filterManufacturerDTO.getNameManufacturer() != null && !filterManufacturerDTO.getNameManufacturer().isEmpty()){
	    		specifications = specifications.and(nameManufacturerLikeTo(filterManufacturerDTO.getNameManufacturer()));
    		}
		}
		return specifications;
	}
	
	private static Specification<Manufacturer> idManufacturerEqualsTo(Long idManufacturer) {
		return (root, query, builder) -> 
			builder.equal(
					root.get(Manufacturer_.idManufacturer), 
					idManufacturer
					);
	}
	
	private static Specification<Manufacturer> nameManufacturerLikeTo(String nameManufacturer) {
		return (root, query, builder) -> {
			Expression<String> campo = builder.upper(builder.function("CONVERT", String.class, root.get(Manufacturer_.nameManufacturer), builder.literal("US7ASCII")));
			Expression<String> valor = builder.upper(builder.function("CONVERT", String.class, builder.literal("%" + nameManufacturer + "%"), builder.literal("US7ASCII")));
			return builder.like(campo, valor);
		};
	}

}
