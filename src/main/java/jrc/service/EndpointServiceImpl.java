package jrc.service;

import jrc.domain.Endpoint;
import jrc.repository.EndpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class EndpointServiceImpl implements EndpointService {

    @Autowired
    private EndpointRepository endpointRepository;

    @Override
    public Page<Endpoint> findAllPaginated(Pageable pageable) {
        List<Endpoint> endpointsAll = (List<Endpoint>) endpointRepository.findAll();

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Endpoint> endpointsCurrentList;

        if (endpointsAll.size() < startItem) {
            endpointsCurrentList = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, endpointsAll.size());
            endpointsCurrentList = endpointsAll.subList(startItem, toIndex);
        }

        return new PageImpl<>(endpointsCurrentList,
                              PageRequest.of(currentPage, pageSize),
                              endpointsAll.size());
    }

    @Override
    public Endpoint findById(Integer id) {
        return endpointRepository.findById(id).get();
    }

    @Override
    public Endpoint findByPath(String path) {
        return endpointRepository.findByPath(path);
    }

    @Override
    public Endpoint addOrUpdate(Endpoint endpoint) {
        return endpointRepository.save(endpoint);
    }

    @Override
    public void deleteById(Integer id) {
        endpointRepository.deleteById(id);
    }
}
