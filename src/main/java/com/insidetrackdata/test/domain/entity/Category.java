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

import com.insidetrackdata.test.domain.dto.CategoryDTO;
import com.insidetrackdata.test.domain.dto.GenericBeanDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterCategoryDTO;

@Entity
@Table(name = "ITD_DT_CATEGORY")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Category implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "ID_CATEGORY")
	@EqualsAndHashCode.Include @ToString.Include
	private Long idCategory;
	
	@Column(name = "NA_CATEGORY")
	private String nameCategory;
	
	public Category(Long idCategory) {
        this.idCategory = idCategory;
    }
	
	public CategoryDTO getCategoryDTO() {
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setIdCategory(idCategory);
		categoryDTO.setNameCategory(nameCategory);
		return categoryDTO;
	}
	
	public GenericBeanDTO getGenericBeanDTO(){
		GenericBeanDTO genericBeanDTO = new GenericBeanDTO();
		genericBeanDTO.setValue(idCategory.toString());
		genericBeanDTO.setLabel(nameCategory);
		return genericBeanDTO;
	}
	
	public static Specification<Category> createSpecifications(FilterCategoryDTO filterCategoryDTO) {
		Specification<Category> specifications = Specification.where(null);
		if(filterCategoryDTO != null){
			if(filterCategoryDTO.getIdCategory() != null){
	    		specifications = specifications.and(idCategoryEqualsTo(filterCategoryDTO.getIdCategory()));
    		}
			if(filterCategoryDTO.getNameCategory() != null && !filterCategoryDTO.getNameCategory().isEmpty()){
	    		specifications = specifications.and(nameCategoryLikeTo(filterCategoryDTO.getNameCategory()));
    		}
		}
		return specifications;
	}
	
	private static Specification<Category> idCategoryEqualsTo(Long idCategory) {
		return (root, query, builder) -> 
			builder.equal(
					root.get(Category_.idCategory), 
					idCategory
					);
	}
	
	private static Specification<Category> nameCategoryLikeTo(String nameCategory) {
		return (root, query, builder) -> {
			Expression<String> campo = builder.upper(builder.function("CONVERT", String.class, root.get(Category_.nameCategory), builder.literal("US7ASCII")));
			Expression<String> valor = builder.upper(builder.function("CONVERT", String.class, builder.literal("%" + nameCategory + "%"), builder.literal("US7ASCII")));
			return builder.like(campo, valor);
		};
	}

}
