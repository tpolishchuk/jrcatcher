package jrc.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "request_cookies")
public class RequestCookie {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name;

    private String value;

    private String domain;

    private String path;

    private Long maxAge;

    private Boolean httpOnly;

    private Boolean secure;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    private Request request;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Long maxAge) {
        this.maxAge = maxAge;
    }

    public Boolean getHttpOnly() {
        return httpOnly;
    }

    public void setHttpOnly(Boolean httpOnly) {
        this.httpOnly = httpOnly;
    }

    public Boolean getSecure() {
        return secure;
    }

    public void setSecure(Boolean secure) {
        this.secure = secure;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestCookie that = (RequestCookie) o;
        return Objects.equals(name, that.name) &&
               Objects.equals(value, that.value) &&
               Objects.equals(domain, that.domain) &&
               Objects.equals(path, that.path) &&
               Objects.equals(maxAge, that.maxAge) &&
               Objects.equals(httpOnly, that.httpOnly) &&
               Objects.equals(secure, that.secure) &&
               Objects.equals(request, that.request);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value, domain, path, maxAge, httpOnly, secure, request);
    }
}
