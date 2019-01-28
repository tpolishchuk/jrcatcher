package jrc.service;

import jrc.domain.Request;
import jrc.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static jrc.utils.PageableUtil.convertResultsListToPage;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Override
    public Page<Request> findByEndpointIdPaginated(Integer endpointId, Pageable pageable) {
        List<Request> requestsAll = requestRepository.findByEndpointIdOrderByIdDesc(endpointId);
        return convertResultsListToPage(requestsAll, pageable);
    }

    @Override
    public Page<Request> findByEndpointIdAndQueryPaginated(Integer endpointId,
                                                           String searchQuery,
                                                           Pageable pageable) {
        List<Request> requestsAll = requestRepository.findByEndpointIdAndQuery(endpointId, searchQuery);
        return convertResultsListToPage(requestsAll, pageable);
    }

    @Override
    public Request addOrUpdate(Request request) {
        return requestRepository.save(request);
    }

    @Override
    public void deleteByEndpointId(Integer endpointId) {
        requestRepository.deleteByEndpointId(endpointId);
    }


}
