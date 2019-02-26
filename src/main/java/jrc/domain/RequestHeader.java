package jrc.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "request_headers")
public class RequestHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String value;

    @ManyToOne(cascade = CascadeType.ALL,
               fetch = FetchType.LAZY)
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
        RequestHeader that = (RequestHeader) o;
        return Objects.equals(name, that.name) &&
               Objects.equals(value, that.value) &&
               Objects.equals(request, that.request);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value, request);
    }
}
