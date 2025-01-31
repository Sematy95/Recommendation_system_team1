package pro.sky.recommendation_service.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import pro.sky.recommendation_service.domain.enums.CompareType;
import pro.sky.recommendation_service.domain.enums.ProductType;
import pro.sky.recommendation_service.domain.enums.QueryType;
import pro.sky.recommendation_service.domain.enums.TransactionName;

import java.util.Objects;

@Entity
@Table(name = "condition")
public class Condition {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "condition_seq")
    @SequenceGenerator(name = "condition_seq", allocationSize = 1)
    @JsonIgnore
    private long id;

    private QueryType query;
    private ProductType productType;
    private TransactionName transactionName;
    private CompareType compareType;
    private Integer compareValue;
    private boolean negate;
    private Long parallelConditionId;


    public Condition() {
    }

    public Condition(QueryType query, ProductType productType, TransactionName transactionName, CompareType compareType, Integer compareValue, boolean negate, Long parallelConditionId) {
        this.query = query;
        this.productType = productType;
        this.transactionName = transactionName;
        this.compareType = compareType;
        this.compareValue = compareValue;
        this.negate = negate;
        this.parallelConditionId = parallelConditionId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public QueryType getQuery() {
        return query;
    }

    public void setQuery(QueryType query) {
        this.query = query;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public TransactionName getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(TransactionName transactionName) {
        this.transactionName = transactionName;
    }

    public CompareType getCompareType() {
        return compareType;
    }

    public void setCompareType(CompareType compareType) {
        this.compareType = compareType;
    }

    public Integer getCompareValue() {
        return compareValue;
    }

    public void setCompareValue(Integer compareValue) {
        this.compareValue = compareValue;
    }

    public boolean isNegate() {
        return negate;
    }

    public void setNegate(boolean negate) {
        this.negate = negate;
    }

    public Long getParallelConditionId() {
        return parallelConditionId;
    }

    public void setParallelConditionId(Long parallelConditionId) {
        this.parallelConditionId = parallelConditionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Condition condition = (Condition) o;
        return negate == condition.negate && query == condition.query && productType == condition.productType && transactionName == condition.transactionName && compareType == condition.compareType && Objects.equals(compareValue, condition.compareValue) && Objects.equals(parallelConditionId, condition.parallelConditionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(query, productType, transactionName, compareType, compareValue, negate, parallelConditionId);
    }

    @Override
    public String toString() {
        return "Condition{" +
                "id=" + id +
                ", query=" + query +
                ", productType=" + productType +
                ", transactionName=" + transactionName +
                ", compareType=" + compareType +
                ", compareValue=" + compareValue +
                ", negate=" + negate +
                ", parallelConditionId=" + parallelConditionId +
                '}';
    }
}
