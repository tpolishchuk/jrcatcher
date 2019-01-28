package jrc.service;

import jrc.domain.RequestCookie;
import jrc.repository.RequestCookieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestCookieServiceImpl implements RequestCookieService {

    @Autowired
    private RequestCookieRepository requestCookieRepository;

    @Override
    public Iterable<RequestCookie> addOrUpdateAll(List<RequestCookie> requestCookies) {
        return requestCookieRepository.saveAll(requestCookies);
    }
}
