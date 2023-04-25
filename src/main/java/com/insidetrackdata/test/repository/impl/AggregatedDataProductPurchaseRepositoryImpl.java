package com.insidetrackdata.test.repository.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.insidetrackdata.test.domain.dto.ItemAggregatedDataDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterAggregatedDataProductPurchaseDTO;
import com.insidetrackdata.test.domain.entity.Category_;
import com.insidetrackdata.test.domain.entity.City_;
import com.insidetrackdata.test.domain.entity.CustomerLeaf_;
import com.insidetrackdata.test.domain.entity.CustomerRoot_;
import com.insidetrackdata.test.domain.entity.DistributorLeaf_;
import com.insidetrackdata.test.domain.entity.DistributorRoot_;
import com.insidetrackdata.test.domain.entity.Manufacturer_;
import com.insidetrackdata.test.domain.entity.PackSize_;
import com.insidetrackdata.test.domain.entity.ProductPurchase;
import com.insidetrackdata.test.domain.entity.ProductPurchase_;
import com.insidetrackdata.test.domain.entity.Product_;
import com.insidetrackdata.test.domain.entity.UnitType_;
import com.insidetrackdata.test.repository.AggregatedDataProductPurchaseRepository;
import com.insidetrackdata.test.util.Constants;

@Service
@Transactional(readOnly = true)
public class AggregatedDataProductPurchaseRepositoryImpl implements AggregatedDataProductPurchaseRepository {

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public Page<ItemAggregatedDataDTO> findAll(FilterAggregatedDataProductPurchaseDTO filterAggregatedDataProductPurchaseDTO, Pageable pager) throws Exception {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createQuery(Tuple.class);
        Root<ProductPurchase> root = query.from(ProductPurchase.class);
        
        List<Selection<?>> listSelection = new ArrayList<Selection<?>>();
        List<Expression<?>> listGroupBy = new ArrayList<Expression<?>>();
        List<Order> listOrderBy = new ArrayList<Order>();
        
        //Adding "grouping by" date purchase
        if(filterAggregatedDataProductPurchaseDTO.getAggregatedByDatePurchaseFirst() != null && filterAggregatedDataProductPurchaseDTO.getAggregatedByDatePurchaseFirst().booleanValue()){
        	if(filterAggregatedDataProductPurchaseDTO.getUseDatePurchaseToAggregateAs() != null){
        		if(filterAggregatedDataProductPurchaseDTO.getUseDatePurchaseToAggregateAs().equals(Constants.USE_DATE_TO_AGGREGATE_AS_YEAR)){
        			//"grouping by" must first be by "year of purchase"
        			Expression<Integer> fieldYearPurchase = root.get(ProductPurchase_.yearPurchase);
                    listSelection.add(fieldYearPurchase);
                    listGroupBy.add(fieldYearPurchase);
                    listOrderBy.add(builder.asc(fieldYearPurchase));
        		}else if(filterAggregatedDataProductPurchaseDTO.getUseDatePurchaseToAggregateAs().equals(Constants.USE_DATE_TO_AGGREGATE_AS_MONTH)){
        			//"grouping by" must first be by "month of purchase"
        			Expression<String> fieldFullMonthPurchase = root.get(ProductPurchase_.fullMonthPurchase);
                    listSelection.add(fieldFullMonthPurchase);
                    listGroupBy.add(fieldFullMonthPurchase);
                    listOrderBy.add(builder.asc(fieldFullMonthPurchase));
        		}else{
        			//"grouping by" must first be by "date of purchase"
        			Expression<LocalDate> fieldDatePurchase = root.get(ProductPurchase_.datePurchase);
                    listSelection.add(fieldDatePurchase);
                    listGroupBy.add(fieldDatePurchase);
                    listOrderBy.add(builder.asc(fieldDatePurchase));
        		}
        	}else{
        		//"grouping by" must first be by "date of purchase"
    			Expression<LocalDate> fieldDatePurchase = root.get(ProductPurchase_.datePurchase);
                listSelection.add(fieldDatePurchase);
                listGroupBy.add(fieldDatePurchase);
                listOrderBy.add(builder.asc(fieldDatePurchase));
        	}
        }
        
        //Adding additional "group by"
        if(filterAggregatedDataProductPurchaseDTO.getAggregatedBy() != null && !filterAggregatedDataProductPurchaseDTO.getAggregatedBy().equals(ProductPurchase_.ListAggregationFieldEnum.ALL.getKey())){
        	Expression<?> field = null;
        	if(filterAggregatedDataProductPurchaseDTO.getAggregatedBy().equals(ProductPurchase_.ListAggregationFieldEnum.INVOICE.getKey())){
        		field = root.get(ProductPurchase_.codeInvoice);
        	}
        	if(filterAggregatedDataProductPurchaseDTO.getAggregatedBy().equals(ProductPurchase_.ListAggregationFieldEnum.CUSTOMER_ROOT.getKey())){
        		field = root.get(ProductPurchase_.customerRoot).get(CustomerRoot_.nameCustomerRoot);
        	}
        	if(filterAggregatedDataProductPurchaseDTO.getAggregatedBy().equals(ProductPurchase_.ListAggregationFieldEnum.CUSTOMER_LEAF.getKey())){
        		field = root.get(ProductPurchase_.customerLeaf).get(CustomerLeaf_.nameCustomerLeaf);
        	}
        	if(filterAggregatedDataProductPurchaseDTO.getAggregatedBy().equals(ProductPurchase_.ListAggregationFieldEnum.CITY_CUSTOMER_LEAF.getKey())){
        		field = root.get(ProductPurchase_.cityCustomerLeaf).get(City_.codeCity);
        	}
        	if(filterAggregatedDataProductPurchaseDTO.getAggregatedBy().equals(ProductPurchase_.ListAggregationFieldEnum.PRODUCT.getKey())){
        		field = root.get(ProductPurchase_.product).get(Product_.descriptionProduct);
        	}
        	if(filterAggregatedDataProductPurchaseDTO.getAggregatedBy().equals(ProductPurchase_.ListAggregationFieldEnum.MANUFACTURER.getKey())){
        		field = root.get(ProductPurchase_.manufacturer).get(Manufacturer_.nameManufacturer);
        	}
        	if(filterAggregatedDataProductPurchaseDTO.getAggregatedBy().equals(ProductPurchase_.ListAggregationFieldEnum.CATEGORY.getKey())){
        		field = root.get(ProductPurchase_.category).get(Category_.nameCategory);
        	}
        	if(filterAggregatedDataProductPurchaseDTO.getAggregatedBy().equals(ProductPurchase_.ListAggregationFieldEnum.UNIT_TYPE.getKey())){
        		field = root.get(ProductPurchase_.unitType).get(UnitType_.nameUnitType);
        	}
        	if(filterAggregatedDataProductPurchaseDTO.getAggregatedBy().equals(ProductPurchase_.ListAggregationFieldEnum.PACK_SIZE.getKey())){
        		field = root.get(ProductPurchase_.packSize).get(PackSize_.namePackSize);
        	}
        	if(filterAggregatedDataProductPurchaseDTO.getAggregatedBy().equals(ProductPurchase_.ListAggregationFieldEnum.DISTRIBUTOR_ROOT.getKey())){
        		field = root.get(ProductPurchase_.distributorRoot).get(DistributorRoot_.nameDistributorRoot);
        	}
        	if(filterAggregatedDataProductPurchaseDTO.getAggregatedBy().equals(ProductPurchase_.ListAggregationFieldEnum.DISTRIBUTOR_LEAF.getKey())){
        		field = root.get(ProductPurchase_.distributorLeaf).get(DistributorLeaf_.nameDistributorLeaf);
        	}
        	if(filterAggregatedDataProductPurchaseDTO.getAggregatedBy().equals(ProductPurchase_.ListAggregationFieldEnum.CITY_DISTRIBUTOR_LEAF.getKey())){
        		field = root.get(ProductPurchase_.cityDistributorLeaf).get(City_.codeCity);
        	}
            listSelection.add(field);
            listGroupBy.add(field);
            listOrderBy.add(builder.asc(field));
        }
        
        //Including aggregate fields in the select clause
        if(filterAggregatedDataProductPurchaseDTO.getGetCount() != null && filterAggregatedDataProductPurchaseDTO.getGetCount().booleanValue()){
        	listSelection.add(builder.count(root));
        }
        if(filterAggregatedDataProductPurchaseDTO.getGetSumQuantityProduct() != null && filterAggregatedDataProductPurchaseDTO.getGetSumQuantityProduct().booleanValue()){
        	listSelection.add(builder.sum(root.get(ProductPurchase_.quantityProduct)));
        }
        if(filterAggregatedDataProductPurchaseDTO.getGetAveragePriceProduct() != null && filterAggregatedDataProductPurchaseDTO.getGetAveragePriceProduct().booleanValue()){
        	listSelection.add(builder.function("ROUND", Double.class, builder.avg(root.get(ProductPurchase_.priceProduct))));
        }
        if(filterAggregatedDataProductPurchaseDTO.getGetMinimumPriceProduct() != null && filterAggregatedDataProductPurchaseDTO.getGetMinimumPriceProduct().booleanValue()){
        	listSelection.add(builder.min(root.get(ProductPurchase_.priceProduct)));
        }
        if(filterAggregatedDataProductPurchaseDTO.getGetMaximumPriceProduct() != null && filterAggregatedDataProductPurchaseDTO.getGetMaximumPriceProduct().booleanValue()){
        	listSelection.add(builder.max(root.get(ProductPurchase_.priceProduct)));
        }
        listSelection.add(builder.sum(root.get(ProductPurchase_.totalProduct)));
        
        //Generating query to execute
        query.multiselect(listSelection);
        Specification<ProductPurchase> specificationsWhere = ProductPurchase.createSpecifications(filterAggregatedDataProductPurchaseDTO);
        if(!specificationsWhere.equals(Specification.where(null))){
        	query.where(specificationsWhere.toPredicate(root, query, builder));
        }
        if(!listGroupBy.isEmpty()){
        	query.groupBy(listGroupBy);
        }
        if(!listOrderBy.isEmpty()){
        	query.orderBy(listOrderBy);
        }
        
        //Getting the results
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        List<ItemAggregatedDataDTO> listItemAggregatedDataDTO = entityManager.createQuery(query)
        		.setFirstResult((int)pager.getOffset())
        		.setMaxResults(pager.getPageSize())
        		.getResultList()
        		.stream()
        		.map(e -> {
        			String valueDate = null;
        			String valueItem = null;
        			Long count = null;
        			BigDecimal sumQuantityProduct = null;
        			BigDecimal averagePriceProduct = null;
        			BigDecimal minimumPriceProduct = null;
        			BigDecimal maximumPriceProduct = null;
        			BigDecimal sumTotalProduct = null;
        			int index = 0;
        			if(filterAggregatedDataProductPurchaseDTO.getAggregatedByDatePurchaseFirst() != null && filterAggregatedDataProductPurchaseDTO.getAggregatedByDatePurchaseFirst().booleanValue()){
        				if(filterAggregatedDataProductPurchaseDTO.getUseDatePurchaseToAggregateAs().equals(Constants.USE_DATE_TO_AGGREGATE_AS_DATE)){
        					valueDate = e.get(index, LocalDate.class).format(dtf);
        				}else if(filterAggregatedDataProductPurchaseDTO.getUseDatePurchaseToAggregateAs().equals(Constants.USE_DATE_TO_AGGREGATE_AS_YEAR)){
        					valueDate = e.get(index, Integer.class).toString();
        				}else{
        					valueDate = e.get(index, String.class);
        				}
        				index++;
        			}
        			if(filterAggregatedDataProductPurchaseDTO.getAggregatedBy() != null && !filterAggregatedDataProductPurchaseDTO.getAggregatedBy().equals(ProductPurchase_.ListAggregationFieldEnum.ALL.getKey())){
        				valueItem = e.get(index, String.class);
        				index++;
        			}
        			if(filterAggregatedDataProductPurchaseDTO.getGetCount() != null && filterAggregatedDataProductPurchaseDTO.getGetCount().booleanValue()){
        				count = e.get(index, Long.class);
        				index++;
        			}
        			if(filterAggregatedDataProductPurchaseDTO.getGetSumQuantityProduct() != null && filterAggregatedDataProductPurchaseDTO.getGetSumQuantityProduct().booleanValue()){
        				sumQuantityProduct = e.get(index, BigDecimal.class);
        				index++;
        	        }
        	        if(filterAggregatedDataProductPurchaseDTO.getGetAveragePriceProduct() != null && filterAggregatedDataProductPurchaseDTO.getGetAveragePriceProduct().booleanValue()){
        	        	averagePriceProduct = new BigDecimal(e.get(index, Double.class).doubleValue());
        				index++;
        	        }
        	        if(filterAggregatedDataProductPurchaseDTO.getGetMinimumPriceProduct() != null && filterAggregatedDataProductPurchaseDTO.getGetMinimumPriceProduct().booleanValue()){
        	        	minimumPriceProduct = e.get(index, BigDecimal.class);
        				index++;
        	        }
        	        if(filterAggregatedDataProductPurchaseDTO.getGetMaximumPriceProduct() != null && filterAggregatedDataProductPurchaseDTO.getGetMaximumPriceProduct().booleanValue()){
        	        	maximumPriceProduct = e.get(index, BigDecimal.class);
        				index++;
        	        }
        	        sumTotalProduct = e.get(index, BigDecimal.class);
        			return new ItemAggregatedDataDTO(valueDate, valueItem, count, sumQuantityProduct, averagePriceProduct, minimumPriceProduct, maximumPriceProduct, sumTotalProduct);
        		})
        		.collect(Collectors.toList());
        
        //Generating the return page of data
        Page<ItemAggregatedDataDTO> pageItemAggregatedDataDTO = new PageImpl<ItemAggregatedDataDTO>(listItemAggregatedDataDTO, pager, listItemAggregatedDataDTO.size());
        
        return pageItemAggregatedDataDTO;
	}

}
