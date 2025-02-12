package pro.sky.recommendation_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.recommendation_service.domain.Condition;

/**
 * Repository interface for managing Condition entities in the database.
 * This interface extends JpaRepository, providing standard CRUD operations
 * for Condition objects.
 */
// todo @Repository may have been missed
public interface ConditionsRepository extends JpaRepository<Condition, Long> {
}
