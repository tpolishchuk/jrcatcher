package jrc.service;

import jrc.domain.RequestHeader;
import jrc.repository.RequestHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestHeaderServiceImpl implements RequestHeaderService {

    @Autowired
    private RequestHeaderRepository requestHeaderRepository;

    @Override
    public Iterable<RequestHeader> addOrUpdateAll(List<RequestHeader> requestHeaders) {
        return requestHeaderRepository.saveAll(requestHeaders);
    }
}
