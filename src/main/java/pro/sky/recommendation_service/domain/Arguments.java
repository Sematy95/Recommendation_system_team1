package pro.sky.recommendation_service.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="arguments")
public class Arguments {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "arguments_seq")
    @SequenceGenerator(name = "arguments_seq", allocationSize = 1)
    private Long id;

    private String productType;
    private String transactionName;
    private String compareType;
    private String compareValue;

    public Arguments() {
    }

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
        return Objects.equals(id, arguments.id) && Objects.equals(productType, arguments.productType) && Objects.equals(transactionName, arguments.transactionName) && Objects.equals(compareType, arguments.compareType) && Objects.equals(compareValue, arguments.compareValue);
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
