package pro.sky.recommendation_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.recommendation_service.domain.DynamicRule;

public interface DynamicRuleRepository extends JpaRepository<DynamicRule, Long> {
}
