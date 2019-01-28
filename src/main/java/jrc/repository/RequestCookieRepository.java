package jrc.repository;

import jrc.domain.RequestCookie;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

//TODO: cover with tests
public interface RequestCookieRepository extends CrudRepository<RequestCookie, Integer> {

    List<RequestCookie> findByRequestId(Integer requestId);
}
