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
import com.insidetrackdata.test.domain.dto.GenericBeanDTO;
import com.insidetrackdata.test.domain.dto.ProductDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterProductDTO;

@Entity
@Table(name = "ITD_DT_PRODUCT")
@NamedEntityGraph(
		name = Product.COMMON_GRAPH,
		attributeNodes = {
				@NamedAttributeNode(value = "manufacturer"),
				@NamedAttributeNode(value = "category")
				}
		)
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Product implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final String COMMON_GRAPH = "productCommonGraph";

	@Id
    @Column(name = "ID_PRODUCT")
	@EqualsAndHashCode.Include @ToString.Include
	private Long idProduct;
	
	@Column(name = "DE_PRODUCT")
	private String descriptionProduct;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_MANUFACTURER")
	private Manufacturer manufacturer;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CATEGORY")
	private Category category;
	
	public Product(Long idProduct) {
        this.idProduct = idProduct;
    }
	
	public ProductDTO getProductDTO() {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setIdProduct(idProduct);
		productDTO.setDescriptionProduct(descriptionProduct);
		if(manufacturer != null){
			productDTO.setIdManufacturer(manufacturer.getIdManufacturer());
			productDTO.setNameManufacturer(manufacturer.getNameManufacturer());
		}
		if(category != null){
			productDTO.setIdCategory(category.getIdCategory());
			productDTO.setNameCategory(category.getNameCategory());
		}
		return productDTO;
	}
	
	public GenericBeanDTO getGenericBeanDTO(){
		GenericBeanDTO genericBeanDTO = new GenericBeanDTO();
		genericBeanDTO.setValue(idProduct.toString());
		genericBeanDTO.setLabel(descriptionProduct);
		return genericBeanDTO;
	}
	
	public static Specification<Product> createSpecifications(FilterProductDTO filterProductDTO) {
		Specification<Product> specifications = Specification.where(null);
		if(filterProductDTO != null){
			if(filterProductDTO.getIdProduct() != null){
	    		specifications = specifications.and(idProductEqualsTo(filterProductDTO.getIdProduct()));
    		}
			if(filterProductDTO.getDescriptionProduct() != null && !filterProductDTO.getDescriptionProduct().isEmpty()){
	    		specifications = specifications.and(descriptionProductLikeTo(filterProductDTO.getDescriptionProduct()));
    		}
			if(filterProductDTO.getIdManufacturer() != null){
	    		specifications = specifications.and(idManufacturerEqualsTo(filterProductDTO.getIdManufacturer()));
    		}
			if(filterProductDTO.getIdCategory() != null){
	    		specifications = specifications.and(idCategoryEqualsTo(filterProductDTO.getIdCategory()));
    		}
		}
		return specifications;
	}
	
	private static Specification<Product> idProductEqualsTo(Long idProduct) {
		return (root, query, builder) -> 
			builder.equal(
					root.get(Product_.idProduct), 
					idProduct
					);
	}
	
	private static Specification<Product> descriptionProductLikeTo(String descriptionProduct) {
		return (root, query, builder) -> {
			Expression<String> campo = builder.upper(builder.function("CONVERT", String.class, root.get(Product_.descriptionProduct), builder.literal("US7ASCII")));
			Expression<String> valor = builder.upper(builder.function("CONVERT", String.class, builder.literal("%" + descriptionProduct + "%"), builder.literal("US7ASCII")));
			return builder.like(campo, valor);
		};
	}
	
	private static Specification<Product> idManufacturerEqualsTo(Long idManufacturer) {
		return (root, query, builder) -> 
			builder.equal(
					root.get(Product_.manufacturer).get(Manufacturer_.idManufacturer), 
					idManufacturer
					);
	}
	
	private static Specification<Product> idCategoryEqualsTo(Long idCategory) {
		return (root, query, builder) -> 
			builder.equal(
					root.get(Product_.category).get(Category_.idCategory), 
					idCategory
					);
	}

}
