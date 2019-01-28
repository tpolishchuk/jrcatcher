package jrc.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name="endpoints")
public class Endpoint {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(unique = true)
    @NotBlank(message = "Path value should not be blank.")
    @Size(min = 1,
          max = 255,
          message = "Path length should be between 1 and 255 characters.")
    @Pattern(regexp = "^(/endpoints/)?[\\p{Alnum}_]+$",
             message = "Only word characters [a-zA-Z_0-9] are allowed.")
    private String path;

    private Boolean active;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endpoint endpoint = (Endpoint) o;
        return Objects.equals(path, endpoint.path) &&
               Objects.equals(active, endpoint.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, active);
    }
}
