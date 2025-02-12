package pro.sky.recommendation_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.recommendation_service.domain.DynamicRule;
import pro.sky.recommendation_service.domain.Statistic;

/**
 * Repository interface for managing Statistic entities in the database.
 * This interface extends JpaRepository, providing standard CRUD operations
 * for Statistic objects. It also defines a custom delete method.
 */
@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Long> {
    /**
     * Deletes Statistic entities associated with the given DynamicRule.
     * Spring Data JPA can derive the query from the method name.  Because
     * of the naming convention (deleteBy...), it will automatically generate
     * the appropriate SQL DELETE query.
     *
     * @param dynamicRule The DynamicRule whose associated statistics should be deleted.
     */
    public void deleteByDynamicRule(DynamicRule dynamicRule);
}
