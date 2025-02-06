package pro.sky.recommendation_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.recommendation_service.model.NotificationTask;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for managing NotificationTask entities in the database.
 * This interface extends JpaRepository, providing standard CRUD operations for NotificationTask objects.
 */
@Repository
public interface TaskRepository extends JpaRepository<NotificationTask, Long> {
    /**
     * Retrieves a list of NotificationTask entities whose notification date falls
     * within the specified start and end date/time range (inclusive).
     * This method leverages Spring Data JPA's query derivation mechanism.
     * Because of the naming convention (findBy...Between),
     * Spring Data JPA will automatically generate the appropriate SQL query to the database.
     *
     * @param start The start LocalDateTime (inclusive) of the date/time range.
     * @param end   The end LocalDateTime (inclusive) of the date/time range.
     * @return A list of NotificationTask entities within the specified date/time range.
     */
    List<NotificationTask> findByNotificationDateBetween(LocalDateTime start, LocalDateTime end);
}
