package com.insidetrackdata.test.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public final class PaginatorUtil {

    public static Pageable generatePager(Integer page, Integer pageSize, String sortCriteria, String sortDirection) {
    	Pageable pager = null;
    	if(page != null && pageSize != null){
    		Sort sort = null;
    		if(sortCriteria != null && !sortCriteria.isEmpty()){
    			Direction direction = Sort.Direction.ASC;
    			if(Sort.Direction.DESC.name().equals(sortDirection)){
    				direction = Sort.Direction.DESC;
    			}
    			String[] criterias = sortCriteria.split(",");
    			sort = Sort.by(direction, criterias);
    		}else{
    			sort = Sort.unsorted();
    		}
    		pager = PageRequest.of(page.intValue(), pageSize.intValue(), sort);
    	}else{
    		pager = Pageable.unpaged();
    	}
    	return pager;
    }

}
