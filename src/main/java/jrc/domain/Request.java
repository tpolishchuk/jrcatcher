package jrc.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String url;

    private String body;

    private String requestMethod;

    @CreationTimestamp
    private LocalDateTime receivedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endpoint_id")
    private Endpoint endpoint;

    @OneToMany(cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               mappedBy = "request")
    private List<RequestCookie> requestCookies;

    @OneToMany(cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               mappedBy = "request")
    private List<RequestHeader> requestHeaders;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public LocalDateTime getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(LocalDateTime receivedAt) {
        this.receivedAt = receivedAt;
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    public List<RequestCookie> getRequestCookies() {
        return requestCookies;
    }

    public void setRequestCookies(List<RequestCookie> requestCookies) {
        this.requestCookies = requestCookies;
    }

    public List<RequestHeader> getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(List<RequestHeader> requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(url, request.url) &&
               Objects.equals(body, request.body) &&
               Objects.equals(requestMethod, request.requestMethod) &&
               Objects.equals(receivedAt, request.receivedAt) &&
               Objects.equals(endpoint, request.endpoint) &&
               Objects.equals(requestCookies, request.requestCookies) &&
               Objects.equals(requestHeaders, request.requestHeaders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, body, requestMethod, receivedAt, endpoint, requestCookies, requestHeaders);
    }
}
