package pro.sky.recommendation_service.domain;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table (name="request_object")
public class RequestObject {

    @Id
    @GeneratedValue
    private UUID id;

    private String query;


    private List<String> requestObjectArguments;
    private boolean negate;


    public RequestObject() {
    }

    public RequestObject(String query, List<String> requestObjectArguments, boolean negate) {
        this.query = query;
        this.requestObjectArguments = requestObjectArguments;
        this.negate = negate;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<String> getArguments() {
        return requestObjectArguments;
    }

    public void setArguments(List<String> requestObjectArguments) {
        this.requestObjectArguments = requestObjectArguments;
    }

    public boolean isNegate() {
        return negate;
    }

    public void setNegate(boolean negate) {
        this.negate = negate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestObject requestObject = (RequestObject) o;
        return negate == requestObject.negate && Objects.equals(query, requestObject.query) && Objects.equals(requestObjectArguments, requestObject.requestObjectArguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(query, requestObjectArguments, negate);
    }

    @Override
    public String toString() {
        return "Rule{" +
                "query='" + query + '\'' +
                ", arguments=" + requestObjectArguments +
                ", negate=" + negate +
                '}';
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
