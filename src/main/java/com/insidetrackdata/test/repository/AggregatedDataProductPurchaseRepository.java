package com.insidetrackdata.test.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.insidetrackdata.test.domain.dto.ItemAggregatedDataDTO;
import com.insidetrackdata.test.domain.dto.filter.FilterAggregatedDataProductPurchaseDTO;

@Repository
public interface AggregatedDataProductPurchaseRepository {

	public Page<ItemAggregatedDataDTO> findAll(FilterAggregatedDataProductPurchaseDTO filterAggregatedDataProductPurchaseDTO, Pageable pager) throws Exception;

}
