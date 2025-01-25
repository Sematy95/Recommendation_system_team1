package pro.sky.recommendation_service.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "condition")
public class Condition {

    @Id
    @GeneratedValue
    private long id;

    private String query;

    @OneToOne
    private Arguments arguments;
    private boolean negate;


    public Condition() {
    }

    public Condition(String query, Arguments arguments, boolean negate) {
        this.query = query;
        this.arguments = arguments;
        this.negate = negate;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Arguments arguments() {
        return arguments;
    }

    public void setArguments(Arguments arguments) {
        this.arguments = arguments;
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
        Condition condition = (Condition) o;
        return negate == condition.negate && Objects.equals(query, condition.query) && Objects.equals(requestObjectArguments, condition.requestObjectArguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(query, arguments, negate);
    }

    @Override
    public String toString() {
        return "Rule{" +
                "query='" + query + '\'' +
                ", arguments=" + arguments +
                ", negate=" + negate +
                '}';
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
