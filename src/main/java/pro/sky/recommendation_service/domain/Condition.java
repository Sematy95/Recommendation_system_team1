package pro.sky.recommendation_service.domain;

import jakarta.persistence.*;

import java.util.Objects;

/**
 * Represents a condition used in dynamic rule evaluation.
 * This entity is stored in the "condition" table.
 */
@Entity
@Table(name = "condition")
public class Condition {
    /**
     * The unique identifier for the Condition entity.
     * This field represents the primary key in the "condition" table of the database.
     * It is automatically generated using a database sequence named "condition_seq".
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "condition_seq")
    @SequenceGenerator(name = "condition_seq", allocationSize = 1)
    private long id;

    private String query;
    @OneToOne
    private Arguments arguments;
    private boolean negate;

    /**
     * Default constructor for the Condition entity. Required by JPA.
     */
    public Condition() {
    }

    /**
     * Constructs a new Condition object.
     *
     * @param query     The query string representing the condition.
     * @param arguments The Arguments object associated with the condition.
     * @param negate    A boolean indicating whether the condition should be negated.
     */
    public Condition(String query, Arguments arguments, boolean negate) {
        this.query = query;
        this.arguments = arguments;
        this.negate = negate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Arguments getArguments() {
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
        return id == condition.id &&
                negate == condition.negate &&
                Objects.equals(query, condition.query) &&
                Objects.equals(arguments, condition.arguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, query, arguments, negate);
    }

    @Override
    public String toString() {
        return "Condition{" +
                "id=" + id +
                ", query='" + query + '\'' +
                ", arguments=" + arguments +
                ", negate=" + negate +
                '}';
    }
}
