package pro.sky.recommendation_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.recommendation_service.domain.DynamicRule;

/**
 * Repository interface for managing DynamicRule entities in the database.
 * This interface extends JpaRepository, providing standard CRUD operations
 * for DynamicRule objects.
 */
@Repository
public interface DynamicRuleRepository extends JpaRepository<DynamicRule, Long> {
}
