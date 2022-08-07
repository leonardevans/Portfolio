package com.lemutugi.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class Pager {
    public static org.springframework.data.domain.Pageable createPageable(int pageNo, int pageSize, String sortField, String sortDir){
        org.springframework.data.domain.Pageable pageable;

        if (!sortField.equals(null)){
            Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                    Sort.by(sortField).descending();
            pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        }else {
            pageable = PageRequest.of(pageNo - 1, pageSize);
        }
        return pageable;
    }
}