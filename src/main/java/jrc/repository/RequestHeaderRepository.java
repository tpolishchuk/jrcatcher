package jrc.repository;

import jrc.domain.RequestHeader;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

//TODO: cover with tests
public interface RequestHeaderRepository extends CrudRepository<RequestHeader, Integer> {

    List<RequestHeader> findByRequestId(Integer requestId);
}
