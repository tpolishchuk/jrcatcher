package jrc;

import jrc.domain.Endpoint;
import jrc.domain.Request;
import jrc.domain.RequestCookie;
import jrc.domain.RequestHeader;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class AbstractIntegrationTest {

    protected Endpoint generateDummyEndpoint() {
        Endpoint endpoint = new Endpoint();
        endpoint.setActive(true);
        endpoint.setPath(RandomStringUtils.randomAlphanumeric(10));

        return endpoint;
    }

    protected Request generateDummyRequest(Endpoint endpoint) {
        Request request = new Request();
        request.setEndpoint(endpoint);
        request.setRequestMethod("POST");
        request.setBody(RandomStringUtils.randomAlphanumeric(20));
        request.setUrl("http://" + RandomStringUtils.randomAlphanumeric(10) + ".org");
        request.setReceivedAt(LocalDateTime.now());
        request.setRequestCookies(new ArrayList<>());
        request.setRequestHeaders(new ArrayList<>());

        return request;
    }

    protected RequestCookie generateDummyRequestCookie(Request request) {
        RequestCookie requestCookie = new RequestCookie();
        requestCookie.setRequest(request);
        requestCookie.setName("Foo");
        requestCookie.setValue("Bar");
        requestCookie.setMaxAge(-1L);
        requestCookie.setHttpOnly(false);
        requestCookie.setSecure(false);

        return requestCookie;
    }

    protected RequestHeader generateDummyRequestHeader(Request request) {
        RequestHeader requestHeader = new RequestHeader();
        requestHeader.setRequest(request);
        requestHeader.setName("Foo");
        requestHeader.setValue("Bar");

        return requestHeader;
    }
}
