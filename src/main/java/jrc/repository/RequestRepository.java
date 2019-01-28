package jrc.repository;

import jrc.domain.Request;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestRepository extends CrudRepository<Request, Integer> {

    List<Request> findByEndpointIdOrderByIdDesc(Integer endpointId);

    void deleteByEndpointId(Integer endpointId);

    //TODO: cover with tests
    @Query(value = "SELECT DISTINCT requests.* " +
                   "FROM requests " +
                   "INNER JOIN request_cookies ON requests.id = request_cookies.request_id " +
                   "INNER JOIN request_headers ON requests.id = request_headers.request_id " +
                   "WHERE  " +
                   "requests.endpoint_id=:endpoint_id AND " +
                   "requests.url LIKE %:search_query% OR " +
                   "requests.body LIKE %:search_query% OR " +
                   "request_cookies.name LIKE %:search_query% OR " +
                   "request_cookies.value LIKE %:search_query% OR " +
                   "request_headers.name LIKE %:search_query% OR " +
                   "request_headers.value LIKE %:search_query% " +
                   "ORDER BY requests.id DESC",
           countQuery = "SELECT COUNT(DISTINCT requests.id) " +
                        "FROM requests " +
                        "INNER JOIN request_cookies ON requests.id = request_cookies.request_id " +
                        "INNER JOIN request_headers ON requests.id = request_headers.request_id " +
                        "WHERE  " +
                        "requests.endpoint_id=:endpoint_id AND " +
                        "requests.url LIKE %:search_query% OR " +
                        "requests.body LIKE %:search_query% OR " +
                        "request_cookies.name LIKE %:search_query% OR " +
                        "request_cookies.value LIKE %:search_query% OR " +
                        "request_headers.name LIKE %:search_query% OR " +
                        "request_headers.value LIKE %:search_query%",
           nativeQuery = true)
    List<Request> findByEndpointIdAndQuery(@Param("endpoint_id") Integer endpointId,
                                           @Param("search_query") String searchQuery);
}
