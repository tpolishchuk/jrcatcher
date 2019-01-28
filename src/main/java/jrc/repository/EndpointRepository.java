package jrc.repository;

import jrc.domain.Endpoint;
import org.springframework.data.repository.CrudRepository;

public interface EndpointRepository extends CrudRepository<Endpoint, Integer> {

    Endpoint findByPath(String path);
}
