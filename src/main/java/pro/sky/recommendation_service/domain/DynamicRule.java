package pro.sky.recommendation_service.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name="dynamic_rule")
public class DynamicRule {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dynamic_rule_seq")
    @SequenceGenerator(name = "dynamic_rule_seq", allocationSize = 1)
    private Long id;

    private String product_name;
    private UUID product_id;
    private String product_text;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Condition> conditions;

    public DynamicRule() {
    }

    public DynamicRule(String product_name, UUID product_id, String product_text, List<Condition> conditions) {
        this.product_name = product_name;
        this.product_id = product_id;
        this.product_text = product_text;
        this.conditions = conditions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public UUID getProduct_id() {
        return product_id;
    }

    public void setProduct_id(UUID product_id) {
        this.product_id = product_id;
    }

    public String getProduct_text() {
        return product_text;
    }

    public void setProduct_text(String product_text) {
        this.product_text = product_text;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> rule) {
        this.conditions = rule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DynamicRule that = (DynamicRule) o;
        return Objects.equals(id, that.id) && Objects.equals(product_name, that.product_name) && Objects.equals(product_id, that.product_id) && Objects.equals(product_text, that.product_text) && Objects.equals(conditions, that.conditions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product_name, product_id, product_text, conditions);
    }

    @Override
    public String toString() {
        return "DynamicRule{" +
                "id=" + id +
                ", product_name='" + product_name + '\'' +
                ", product_id=" + product_id +
                ", product_text='" + product_text + '\'' +
                ", recommendationsObjects=" + conditions +
                '}';
    }
}
