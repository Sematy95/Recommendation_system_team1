package pro.sky.recommendation_service.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Argument {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private RequestObject requestObject;

    public Argument() {
    }
}
