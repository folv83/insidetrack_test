package com.insidetrackdata.test.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.Specification;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.insidetrackdata.test.domain.dto.CityDTO;
import com.insidetrackdata.test.domain.dto.GenericBeanDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterCityDTO;

@Entity
@Table(name = "ITD_DT_CITY")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class City implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "ID_CITY")
	@EqualsAndHashCode.Include @ToString.Include
	private Long idCity;
	
	@Column(name = "CO_CITY")
	private String codeCity;
	
	public City(Long idCity) {
        this.idCity = idCity;
    }
	
	public CityDTO getCityDTO() {
		CityDTO cityDTO = new CityDTO();
		cityDTO.setIdCity(idCity);
		cityDTO.setCodeCity(codeCity);
		return cityDTO;
	}
	
	public GenericBeanDTO getGenericBeanDTO(){
		GenericBeanDTO genericBeanDTO = new GenericBeanDTO();
		genericBeanDTO.setValue(idCity.toString());
		genericBeanDTO.setLabel(codeCity);
		return genericBeanDTO;
	}
	
	public static Specification<City> createSpecifications(FilterCityDTO filterCityDTO) {
		Specification<City> specifications = Specification.where(null);
		if(filterCityDTO != null){
			if(filterCityDTO.getIdCity() != null){
	    		specifications = specifications.and(idCityEqualsTo(filterCityDTO.getIdCity()));
    		}
			if(filterCityDTO.getCodeCity() != null && !filterCityDTO.getCodeCity().isEmpty()){
	    		specifications = specifications.and(codeCityEqualsTo(filterCityDTO.getCodeCity()));
    		}
		}
		return specifications;
	}
	
	private static Specification<City> idCityEqualsTo(Long idCity) {
		return (root, query, builder) -> 
			builder.equal(
					root.get(City_.idCity), 
					idCity
					);
	}
	
	private static Specification<City> codeCityEqualsTo(String codeCity) {
		return (root, query, builder) -> 
			builder.equal(
					root.get(City_.codeCity), 
					codeCity
					);
	}

}
