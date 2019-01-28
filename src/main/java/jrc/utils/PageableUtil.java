package jrc.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

public class PageableUtil {

    public static <T> Page<T> convertResultsListToPage(List<T> entitiesList, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<T> entitiesCurrentList;

        if (entitiesList.size() < startItem) {
            entitiesCurrentList = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, entitiesList.size());
            entitiesCurrentList = entitiesList.subList(startItem, toIndex);
        }

        return new PageImpl<>(entitiesCurrentList,
                              PageRequest.of(currentPage, pageSize),
                              entitiesList.size());
    }
}
