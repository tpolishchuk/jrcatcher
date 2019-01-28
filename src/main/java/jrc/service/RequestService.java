package jrc.service;

import jrc.domain.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface RequestService {

    Page<Request> findByEndpointIdPaginated(Integer endpointId, Pageable pageable);

    Page<Request> findByEndpointIdAndQueryPaginated(Integer endpointId, String query, Pageable pageable);

    Request addOrUpdate(Request request);

    @Transactional
    void deleteByEndpointId(Integer endpointId);
}
