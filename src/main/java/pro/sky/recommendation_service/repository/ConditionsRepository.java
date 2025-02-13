package pro.sky.recommendation_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.recommendation_service.domain.Condition;

/**
 * Repository interface for managing Condition entities in the database.
 * This interface extends JpaRepository, providing standard CRUD operations
 * for Condition objects.
 */
@Repository
public interface ConditionsRepository extends JpaRepository<Condition, Long> {
}
