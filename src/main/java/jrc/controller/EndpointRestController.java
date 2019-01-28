package jrc.controller;

import jrc.domain.*;
import jrc.exception.EndpointNotActiveException;
import jrc.exception.EndpointNotFoundException;
import jrc.service.EndpointService;
import jrc.service.RequestCookieService;
import jrc.service.RequestHeaderService;
import jrc.service.RequestService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;

@RestController
public class EndpointRestController {

    @Value("${endpoints.default.path}")
    private String endpointsDefaultPath;

    @Autowired
    private EndpointService endpointService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private RequestHeaderService requestHeaderService;

    @Autowired
    private RequestCookieService requestCookieService;

    @RequestMapping("${endpoints.default.path}{path}")
    public APIResponse listenToEndpoint(@PathVariable String path,
                                        HttpServletRequest httpServletRequest) {
        path = endpointsDefaultPath + path;
        Endpoint endpoint = endpointService.findByPath(path);

        if (endpoint == null) {
            throw new EndpointNotFoundException("Unable to find an endpoint " + path);
        }

        if (!endpoint.getActive()) {
            throw new EndpointNotActiveException(path + " is not an active endpoint");
        }

        Request request = buildRequest(endpoint, httpServletRequest);

        requestService.addOrUpdate(request);

        List<RequestCookie> requestCookies = buildRequestCookiesList(request, httpServletRequest);
        List<RequestHeader> requestHeaders = buildRequestHeadersList(request, httpServletRequest);

        requestCookieService.addOrUpdateAll(requestCookies);
        requestHeaderService.addOrUpdateAll(requestHeaders);

        return new APIResponse("Request is captured successfully");
    }

    private String getFullRequestUrl(HttpServletRequest httpServletRequest) {
        String requestUrl = httpServletRequest.getRequestURL().toString();
        String queryString = httpServletRequest.getQueryString();

        if (queryString != null) {
            requestUrl += "?" + queryString;
        }

        return requestUrl;
    }

    private String getFullRequestBody(HttpServletRequest httpServletRequest) {
        try {
            Scanner scanner = new Scanner(httpServletRequest.getInputStream(),
                                          Charset.defaultCharset().name()).useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        } catch (IOException e) {
            return StringUtils.EMPTY;
        }
    }

    private Request buildRequest(Endpoint endpoint,
                                 HttpServletRequest httpServletRequest) {
        Request request = new Request();
        request.setEndpoint(endpoint);
        request.setUrl(getFullRequestUrl(httpServletRequest));
        request.setBody(getFullRequestBody(httpServletRequest));
        request.setRequestMethod(httpServletRequest.getMethod());
        return request;
    }

    private List<RequestCookie> buildRequestCookiesList(Request request,
                                                        HttpServletRequest httpServletRequest) {
        List<RequestCookie> requestCookies = new ArrayList<>();

        for (Cookie cookie : httpServletRequest.getCookies()) {
            RequestCookie requestCookie = new RequestCookie();
            requestCookie.setRequest(request);
            requestCookie.setName(cookie.getName());
            requestCookie.setValue(cookie.getValue());
            requestCookie.setDomain(cookie.getDomain());
            requestCookie.setPath(cookie.getPath());
            requestCookie.setMaxAge((long) cookie.getMaxAge());
            requestCookie.setHttpOnly(cookie.isHttpOnly());
            requestCookie.setSecure(cookie.getSecure());

            requestCookies.add(requestCookie);
        }

        return requestCookies;
    }

    private List<RequestHeader> buildRequestHeadersList(Request request,
                                                        HttpServletRequest httpServletRequest) {
        List<RequestHeader> requestHeaders = new ArrayList<>();
        Enumeration headerNames = httpServletRequest.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = httpServletRequest.getHeader(key);
            RequestHeader requestHeader = new RequestHeader();
            requestHeader.setRequest(request);
            requestHeader.setName(key);
            requestHeader.setValue(value);
            requestHeaders.add(requestHeader);
        }

        return requestHeaders;
    }
}
