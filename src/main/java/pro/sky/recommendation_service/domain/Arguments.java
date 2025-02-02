package pro.sky.recommendation_service.domain;

import jakarta.persistence.*;

import java.util.Objects;

/**
 * Represents a set of arguments used for dynamic rule evaluation.
 * This entity is stored in the "arguments" table.
 */
@Entity
@Table(name = "arguments")
public class Arguments {
    /**
     * The unique identifier for the Arguments entity.
     * This field represents the primary key in the "arguments" table of the database.
     * It is automatically generated using a database sequence named "arguments_seq".
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "arguments_seq")
    @SequenceGenerator(name = "arguments_seq", allocationSize = 1)
    private Long id;

    private String productType;
    private String transactionName;
    private String compareType;
    private String compareValue;

    /**
     * Default constructor for the Arguments entity.  Required by JPA.
     */
    public Arguments() {
    }

    /**
     * Constructs a new Arguments object.
     *
     * @param productType     The product type associated with the arguments.
     * @param transactionName The transaction name associated with the arguments.
     * @param compareType     The comparison type used in the rule.
     * @param compareValue    The value used for comparison in the rule.
     */
    public Arguments(String productType, String transactionName, String compareType, String compareValue) {
        this.productType = productType;
        this.transactionName = transactionName;
        this.compareType = compareType;
        this.compareValue = compareValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    public String getCompareType() {
        return compareType;
    }

    public void setCompareType(String compareType) {
        this.compareType = compareType;
    }

    public String getCompareValue() {
        return compareValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Arguments arguments = (Arguments) o;
        return Objects.equals(id, arguments.id)
                && Objects.equals(productType, arguments.productType)
                && Objects.equals(transactionName, arguments.transactionName)
                && Objects.equals(compareType, arguments.compareType)
                && Objects.equals(compareValue, arguments.compareValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productType, transactionName, compareType, compareValue);
    }

    public void setCompareValue(String compareValue) {
        this.compareValue = compareValue;
    }

    @Override
    public String toString() {
        return "Arguments{" +
                "id=" + id +
                ", productType='" + productType + '\'' +
                ", transactionName='" + transactionName + '\'' +
                ", compareType='" + compareType + '\'' +
                ", compareValue='" + compareValue + '\'' +
                '}';
    }
}
