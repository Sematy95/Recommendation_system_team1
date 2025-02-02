package pro.sky.recommendation_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.recommendation_service.domain.Condition;

public interface ConditionsRepository extends JpaRepository<Condition, Long> {
}
