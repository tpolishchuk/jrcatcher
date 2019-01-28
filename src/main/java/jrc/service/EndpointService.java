package jrc.service;

import jrc.domain.Endpoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EndpointService {

    Page<Endpoint> findAllPaginated(Pageable pageable);

    Endpoint findById(Integer id);

    Endpoint findByPath(String path);

    Endpoint addOrUpdate(Endpoint endpoint);

    void deleteById(Integer id);
}
