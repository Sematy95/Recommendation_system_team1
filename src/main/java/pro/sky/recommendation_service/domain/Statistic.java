package pro.sky.recommendation_service.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "statistics")
public class Statistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rule_id", referencedColumnName = "id")
    private DynamicRule dynamicRule;
    private long counts;

    public Statistic(DynamicRule dynamicRule, long counts) {
        this.dynamicRule = dynamicRule;
        this.counts = counts;
    }

    public Statistic() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DynamicRule getDynamicRule() {
        return dynamicRule;
    }

    public void setDynamicRule(DynamicRule dynamicRule) {
        this.dynamicRule = dynamicRule;
    }

    public long getCount() {
        return counts;
    }

    public void setCount(long count) {
        this.counts = count;
    }

    public Statistic incrCount() {
        this.counts++;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statistic statistic = (Statistic) o;
        return id == statistic.id && counts == statistic.counts && Objects.equals(dynamicRule, statistic.dynamicRule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dynamicRule, counts);
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "id=" + id +
                ", dynamicRule=" + dynamicRule +
                ", count=" + counts +
                '}';
    }
}
